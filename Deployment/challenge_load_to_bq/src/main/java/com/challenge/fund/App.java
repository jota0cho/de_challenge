package com.challenge.fund;


import com.challenge.fund.adapter.ProcessManager;
import com.challenge.fund.model.BigQueryConfiguration;
import com.challenge.fund.port.IProcessManager;
import com.challenge.fund.service.LoadCsvService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    private BigQueryConfiguration makeBigQueryConfiguration() {
        String schema = System.getenv("ENV_SCHEMA");
        String table = System.getenv("ENV_TABLE");
        String bucketConfig = System.getenv("ENV_BUCKET_CONFIG");
        String bucketRaw = System.getenv("ENV_BUCKET_RAW");
        String pathCsv = System.getenv("ENV_PATH_AVRO");
        String pathSchema = System.getenv("ENV_PATH_SCHEMA");
        String serviceAccount = System.getenv("ENV_SERVICE_ACCOUNT");
        String key = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        return new BigQueryConfiguration(schema, table, bucketConfig, bucketRaw,
                pathCsv, pathSchema, serviceAccount, key);
    }


    @Override
    public void run(String... args) throws Exception {
        BigQueryConfiguration configuration = makeBigQueryConfiguration();
        IProcessManager process = new ProcessManager();

        if (configuration.isValid()) {
            LoadCsvService service = new LoadCsvService(configuration,process);
            service.invoke();
        }

    }
}
