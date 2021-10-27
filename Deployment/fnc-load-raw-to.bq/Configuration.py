from google.cloud import bigquery

class Configuration():


    def __init__(self,
                 tableCsv,bucket,table_id):
        self._bucket = bucket
        self._tableCsv = tableCsv
        self._table_id = table_id
        self.createConfig()

    def createConfig(self):
        client = bigquery.Client()

        if ( self._tableCsv == 'result.csv'):
            print(f"Processing name: {self._tableCsv}")
            job_config = bigquery.LoadJobConfig(
            schema=[
            bigquery.SchemaField("metascore", "STRING"),
            bigquery.SchemaField("name", "STRING"),
            bigquery.SchemaField("console", "STRING"),
            bigquery.SchemaField("userscore", "STRING"),
            bigquery.SchemaField("date", "STRING"),
            ],
            skip_leading_rows=1,
            # The source format defaults to CSV, so the line below is optional.
            source_format=bigquery.SourceFormat.CSV,
            )

        if ( self._tableCsv == 'consoles.csv'):
            print(f"Processing name: {self._tableCsv}")
            job_config = bigquery.LoadJobConfig(
            schema=[
            bigquery.SchemaField("console", "STRING"),
            bigquery.SchemaField("company", "STRING"),
            ],
            skip_leading_rows=1,
            # The source format defaults to CSV, so the line below is optional.
            source_format=bigquery.SourceFormat.CSV,
            )

        uri = 'gs://{}/{}'.format(self._bucket,self._tableCsv)

        load_job = client.load_table_from_uri(
        uri, self._table_id, job_config=job_config
        )  # Make an API request.

        load_job.result()  # Waits for the job to complete.

        destination_table = client.get_table(self._table_id)  # Make an API request.
        print("Loaded {} rows.".format(destination_table.num_rows))



