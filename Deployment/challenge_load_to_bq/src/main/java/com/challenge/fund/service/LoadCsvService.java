package com.challenge.fund.service;

import com.challenge.fund.adapter.BigQueryOperator;
import com.challenge.fund.exception.BigQueryOperatorException;
import com.challenge.fund.exception.ProcessException;
import com.challenge.fund.model.BigQueryConfiguration;
import com.challenge.fund.port.IProcessManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadCsvService {

    private BigQueryConfiguration configuration;
    private IProcessManager process;

    private static Logger mylog = LoggerFactory.getLogger(LoadCsvService.class);

    public LoadCsvService(BigQueryConfiguration configuration,IProcessManager process) {
        this.configuration = configuration;
        this.process = process;

    }

    public boolean invoke() throws BigQueryOperatorException, ProcessException {

        try {
            mylog.info("start invoke!");
            BigQueryOperator operator = new BigQueryOperator(configuration);
            mylog.info("logging with service account!");
            String sa = operator.auth();
            process.invoke(sa);
            if (operator.isValidTable()) {
                mylog.info("table already exist!");
            }else {
                mylog.info("creating table in BigQuery!");
                String schema = operator.schema();
                process.invoke(schema);

                String create = operator.createTable();
                process.invoke(create);
            }


            mylog.info("loading data to BigQuery!");
            String load = operator.loadData();

            process.invoke(load);

            return true;
        } catch (BigQueryOperatorException e) {
            mylog.error(e.getMessage());
            throw new BigQueryOperatorException(e.getMessage());
        }


    }

}
