module.exports = class InputRequest {

    constructor(metascore, name, console, userscore, date){
        this.metascore = metascore;
        this.name = name;
        this.console = console;
        this.userscore = userscore;
        this.date = date;

    }
    

    static makeFromStorageObjectPath(gcsPath) {

        if(!gcsPath){
            console.error(`The variable path cannot be null or empty`)
        }
        const splitedPath = gcsPath.split(",");
        const arrayLength = splitedPath.length;

        let metascore = splitedPath.slice(0, arrayLength - 1).join("/");        
        let name = splitedPath[arrayLength -1];
        let console = splitedPath[6];
        let userscore = splitedPath[4];
        let date = splitedPath[3];


        return new InputRequest(metascore, name, console, userscore, date);
    }


    getMetascore() { return this.metascore; }

    getName(){ return this.name; }

    getConsole(){ return this.console; }

    getUserscore(){ return this.userscore; }

    getDate(){ return this.date; }


}
