def load_raw_to_bq(event, context):
    """Triggered by a change to a Cloud Storage bucket.
    Args:
         event (dict): Event payload.
         context (google.cloud.functions.Context): Metadata for the event.
    """

    import os


    print(f"Processing .....")

    file = event
    project = os.environ.get('ENV_PROJECT')
    dataset = os.environ.get('ENV_DATASET')
    bucket =  file.get("bucket")
    tableCsv = file.get("name")
    tableDestList = tableCsv.split(".")
    tableDest = tableDestList[0]
    table_id = f'{project}.{dataset}.{tableDest}'

    from Configuration import Configuration

    Configuration(tableCsv,bucket,table_id)


    print(f"End Process.")