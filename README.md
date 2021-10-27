# Challenge DE

# Prerequisites

## Create buckets in #PROJECT_ID

```
gsutil mb -p #PROJECT_ID -c standard -l us-central1 -b on gs://landing-buckets-dev2/
```

## Create Dataset in #PROJECT_ID
```
   bq --location=us-central1 mk --dataset --description "shiny section for videogames (result data engineer challenge)" #PROJECT_ID:challenge
```

## Create Cloud Functions in #PROJECT_ID

#### Create cloud function
+ Create cloud function name : fnc-generate-landing-to-raw
+ Trigger type:  Cloud Storage
+ Event Type: Finalize/Create
+ buckets : gs://landing-buckets-dev/

+ #### ADD variables
```
BUCKETS_RAW:  raw-buckets-dev
TOPIC_GKE:  mov_gcp
PROJECT_ID:  #PROJECTID
```
+ add code github : de_challenge/Deployment/fnc-generic-landing-to-raw/

## Create second Cloud Functions in #PROJECT_ID

#### Create cloud function
+ Create cloud function name : fnc-load-raw-to-bq
+ Trigger type:  Cloud Storage
+ Event Type: Finalize/Create
+ buckets : gs://raw-buckets-dev/

+ #### ADD variables
```
ENV_DATASET: challenge
ENV_PROJECT: #PROJECTID
TABLE_DEST: table_result
```
+ add code github : de_challenge/Deployment/fnc-load-raw-to-bq/


* ### Upload files .csv
```
 upload result.csv in gs://landing-buckets-dev/result.csv
 upload consoles.csv in gs://landing-buckets-dev/consoles.csv
```

view the tables in bigquery  #PROJECTID.challenge.result

+ The data model queries can be found in the readme.md of the DataModel folder
