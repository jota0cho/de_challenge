package com.challenge.fund.adapter;

import com.challenge.fund.exception.ProcessException;
import com.challenge.fund.model.ResponseProcessManager;
import com.challenge.fund.port.IProcessManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProcessManager implements IProcessManager {

    private static Logger mylog = LoggerFactory.getLogger(ProcessManager.class);
    private static final int TIMEOUT = 360;
    private static final String ERR_PROC = "An error occurred while executing the process: ";

    public void show(List<String> command) {
        for (String sentence : command) {
            mylog.info(sentence);
        }
    }

    public ProcessBuilder create(List<String> command) {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(command);
        return builder;
    }


    public Process execute(ProcessBuilder builder) throws ProcessException {
        Process process;
        try {
            process = builder.start();
            StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), mylog::info);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            return process;
        } catch (Exception e) {
            throw new ProcessException(ERR_PROC + e.getMessage());
        }
    }

    public ResponseProcessManager response(Process process) throws ProcessException {
        String message;
        StringBuilder responseMessage = new StringBuilder();
        int exitCode = -1;
        ResponseProcessManager response = new ResponseProcessManager();
        try {
            if (process.waitFor(TIMEOUT, TimeUnit.SECONDS)) {
                exitCode = process.exitValue();
                BufferedReader is = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                while ((message = is.readLine()) != null) {
                    responseMessage.append(message);
                }
                response.setExitCode(exitCode);
                response.setMessage(responseMessage.toString());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ProcessException(ERR_PROC + e.getMessage());
        } catch (IOException e) {
            throw new ProcessException(ERR_PROC + e.getMessage());
        }
        return response;
    }

    @Override
    public ResponseProcessManager invoke(List<String> command) throws ProcessException {
        try {
            this.show(command);
            ProcessBuilder builder = this.create(command);
            Process process = this.execute(builder);
            ResponseProcessManager response = this.response(process);
            if (response != null && response.getMessage() != null) {
                mylog.info("response message: {}", response.getMessage());
                mylog.info("exit code: {}", response.getExitCode());
            }
            return response;
        } catch (ProcessException e) {
            throw new ProcessException(ERR_PROC + e.getMessage());
        }
    }

    @Override
    public ResponseProcessManager invoke(String request) throws ProcessException {
        return this.invoke(Arrays.asList(request.split(" ")));
    }

}
