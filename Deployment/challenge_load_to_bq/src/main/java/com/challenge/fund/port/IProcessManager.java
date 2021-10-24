package com.challenge.fund.port;

import com.challenge.fund.exception.ProcessException;
import com.challenge.fund.model.ResponseProcessManager;

import java.util.List;

public interface IProcessManager {

    public ResponseProcessManager invoke(List<String> command) throws ProcessException;
    public ResponseProcessManager invoke(String request) throws ProcessException;

}
