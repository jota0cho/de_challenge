'use strict';
const PubSubOperator = require("./PubSubOperator");
const StorageOperator = require("./StorageOperator");

exports.landing_to_raw = async (event, context) => {
  const file = event.name;
  const bucketId = event.bucket;
  const bucketRaw = process.env.BUCKETS_RAW
  const output_path = bucketRaw+"/"+file;
  console.log(`Processing file: ${file}`);

  const input_path = bucketId +"/"+ file;
  
    if (file.endsWith('.csv')){
      const storageOperator = new StorageOperator(bucketId);
      console.log(`Processing input_path-.....: ${input_path}`);
  
      const options = { prefix: input_path };
      options.delimiter = ',';
      console.log(`listing files in bucket ${bucketId}`);

      await storageOperator.copyFile(bucketId,bucketRaw,file );
      
     
      const pubsub = PubSubOperator.makeFromEnvs();
      const message ="{PathFile:"+output_path+"}" ;
      console.log(`Publihing message: ${message}`);
      
      await pubsub.publish(message);

      console.log(`Landing to raw end`);
    }


};