package com.challenge.fund.port;

import com.challenge.fund.exception.BigQueryOperatorException;

public interface IBigQueryOperator {

    public boolean isValidTable() throws BigQueryOperatorException;

    public String auth();

    public String schema();

    public String createTable();

    public String loadData();
}
