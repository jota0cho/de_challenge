package com.challenge.fund.adapter;

import com.challenge.fund.exception.BigQueryOperatorException;
import com.challenge.fund.model.BigQueryConfiguration;
import com.challenge.fund.port.IBigQueryOperator;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Table;

public class BigQueryOperator implements IBigQueryOperator {


    private BigQuery bigQueryClient;
    private BigQueryConfiguration configuration;
    public final String pathGcloud = "path";

    public BigQueryOperator(BigQueryConfiguration configuration)throws BigQueryOperatorException {
        this.configuration = configuration;
        this.bigQueryClient = BigQueryOptions.getDefaultInstance().getService();
        this.isValid();
    }

    private boolean isValid() throws BigQueryOperatorException {
        if (pathGcloud == null || pathGcloud.isEmpty())
            throw new BigQueryOperatorException("ENV_PATH_GCLOUD cannot be null or empty!");
        return true;
    }

    @Override
    public boolean isValidTable() throws BigQueryOperatorException {
        try {
            Table table = bigQueryClient.getTable(configuration.getSchema(), configuration.getTable());
            return (table != null);

        } catch (Exception e) {
            throw new BigQueryOperatorException("An error have ocurred while checked the table: " + e.getMessage());
        }
    }

    @Override
    public String auth() {
        String sa = configuration.getServiceAccount();
        String key = configuration.getKey();
        return pathGcloud + "gcloud auth activate-service-account "+sa+" --key-file="+key;
    }

    @Override
    public String schema() {
        String schema = configuration.getSchema();
        String table = configuration.getTable();
        String pathSchema = configuration.getPathSchema();
        String bucketName = configuration.getBucketConfig();
        return pathGcloud + "gsutil cp ".concat(bucketName).concat("/")
                .concat(pathSchema).concat(" ").concat(schema).concat(".").concat(table).concat(".json");
    }

    private String makePathCsv() {

        String data = configuration.getPathCsv();
        return data;
        //return data.substring(0, data.lastIndexOf('/')).concat("/*.csv");
    }

    @Override
    public String createTable () {
        String schema = configuration.getSchema();
        String table = configuration.getTable();
        String entity = schema.concat(".").concat(table);
        return pathGcloud + "bq mk --time_partitioning_type DAY --require_partition_filter --table ".concat(entity).concat(" ").concat(entity).concat(".json");
    }

    @Override
    public String loadData() {
        String schema = configuration.getSchema();
        String table = configuration.getTable();
        String bucketName = configuration.getBucketRaw();
        String pathCsv = makePathCsv();
        return pathGcloud + "bq load --location=US --source_format=CSV --replace=True "+schema+"."+table+" "+bucketName+"/"+pathCsv+"";
    }

}
