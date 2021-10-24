package com.challenge.fund.model;

import com.challenge.fund.exception.ParserException;

public class BigQueryConfiguration {


    private String schema;
    private String table;
    private String bucketConfig;
    private String bucketRaw;
    private String pathCsv;
    private String pathSchema;
    private String serviceAccount;
    private String key;

    public BigQueryConfiguration() {}

    public BigQueryConfiguration(String schema, String table, String bucketConfig, String bucketRaw,
                                 String pathCsv, String pathSchema, String serviceAccount, String key) {
        super();
        this.schema = schema;
        this.table = table;
        this.bucketConfig = bucketConfig;
        this.bucketRaw = bucketRaw;
        this.pathCsv = pathCsv;
        this.pathSchema = pathSchema;
        this.serviceAccount = serviceAccount;
        this.key = key;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getBucketConfig() {
        return bucketConfig;
    }

    public void setBucketConfig(String bucketConfig) {
        this.bucketConfig = bucketConfig;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getBucketRaw() {
        return bucketRaw;
    }
    public void setBucketRaw(String bucketRaw) {
        this.bucketRaw = bucketRaw;
    }
    public String getPathCsv() {
        return pathCsv;
    }
    public void setPathCsv(String pathAvro) {
        this.pathCsv = pathCsv;
    }
    public String getPathSchema() {
        return pathSchema;
    }
    public void setPathSchema(String pathSchema) {
        this.pathSchema = pathSchema;
    }
    public String getServiceAccount() {
        return serviceAccount;
    }
    public void setServiceAccount(String serviceAccount) {
        this.serviceAccount = serviceAccount;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    private void validateSchema() throws ParserException {
        if (this.getSchema() == null || this.getSchema().isEmpty())
            throw new ParserException("schema cannot be null or empty!");
    }
    private void validateTable() throws ParserException {
        if (this.getTable() == null || this.getTable().isEmpty())
            throw new ParserException("table cannot be null or empty!");
    }
    private void validateBucketConfig() throws ParserException {
        if (this.getBucketConfig() == null || this.getBucketConfig().isEmpty())
            throw new ParserException("bucket_config cannot be null or empty!");
    }
    private void validateBucketRaw() throws ParserException {
        if (this.getBucketRaw() == null || this.getBucketRaw().isEmpty())
            throw new ParserException("bucket_raw cannot be null or empty!");
    }
    private void validatePathCsv() throws ParserException {
        if (this.getPathCsv() == null || this.getPathCsv().isEmpty())
            throw new ParserException("path_avro cannot be null or empty!");
    }
    private void validatePathSchema() throws ParserException {
        if (this.getPathSchema() == null || this.getPathSchema().isEmpty())
            throw new ParserException("path_schema cannot be null or empty!");
    }
    private void validateServiceAccount() throws ParserException {
        if (this.getServiceAccount() == null || this.getServiceAccount().isEmpty())
            throw new ParserException("service_account cannot be null or empty!");
    }
    private void validateKey() throws ParserException {
        if (this.getKey() == null || this.getKey().isEmpty())
            throw new ParserException("key cannot be null or empty!");
    }

    public boolean isValid() throws ParserException {
        this.validateSchema();
        this.validateTable();
        this.validateBucketConfig();
        this.validateBucketRaw();
        this.validatePathCsv();
        this.validatePathSchema();
        this.validateServiceAccount();
        this.validateKey();
        return true;
    }

}
