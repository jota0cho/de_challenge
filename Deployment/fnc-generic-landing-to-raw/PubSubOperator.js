const { PubSub } = require ("@google-cloud/pubsub");


module.exports = class PubSubOperator {

    constructor(topicId, projectId) {
        this.topicId = topicId
        this.pubsub = new PubSub(projectId);
    }

    static makeFromEnvs() {
        const topicId = process.env.TOPIC_GKE;
        const projectId = process.env.PROJECT_ID;
        
        if(!topicId){
            console.error("Environment variable 'TOPIC_GKE' cannot be empty!");
        } else if (!projectId) {
            console.error("Environment variable 'PROJECT_ID' cannot be empty!");
        } else {
            return new PubSubOperator(topicId, projectId);
        }
    }

    async publish(message) {
        try {
            const data = Buffer.from(message);
            const messageId = await this.pubsub.topic(this.topicId).publish(data);
            console.log(`Message was published to ${this.topicId} with id ${messageId}!`);
        } catch(e) {
            console.error(`An error ocurred publishing message{\n\t- ${e}\n}`)
        }
        
    }
}