const { Storage } = require('@google-cloud/storage');


module.exports = class StorageOperator {

    constructor(sourceBucket){
        this.sourceBucket = sourceBucket
        this.storage = new Storage();
    }



    async copyFile(sourceBucket, destBucket, fileName) {
      await this.storage
      .bucket(sourceBucket)
      .file(fileName)
      .copy(this.storage.bucket(destBucket).file(fileName));
      console.log(`gs://${sourceBucket}/${fileName} copied to gs://${destBucket}/${fileName}`
    );
    }


}