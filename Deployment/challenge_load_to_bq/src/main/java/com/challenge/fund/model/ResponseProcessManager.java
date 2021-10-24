package com.challenge.fund.model;

import com.challenge.fund.exception.ParserException;

public class ResponseProcessManager {

    private Integer exitCode;
    private String message;

    public ResponseProcessManager() {}


    public ResponseProcessManager(Integer exitCode, String message) {
        super();
        this.exitCode = exitCode;
        this.message = message;
    }


    public Integer getExitCode() {
        return exitCode;
    }
    public void setExitCode(Integer exitCode) {
        this.exitCode = exitCode;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }


    public boolean isValid() throws ParserException {
        if (this.getExitCode() == null || this.getExitCode() == 0)
            throw new ParserException("exitCode cannot be null or zero!");
        if (this.getMessage() == null || this.getMessage().isEmpty())
            throw new ParserException("message cannot be null or empty!");
        return true;
    }
}
