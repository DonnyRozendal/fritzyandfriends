# Fritzy & Friends
This project consists of 4 actors (or 4 submodules):
- LocalNetty
- Fritzy
- Batty
- Sunny
- Common

TODO: Further introduction...

### Project Setup
1. Clone the project
2. Open it in an IDE like IntelliJ IDEA
3. IntelliJ IDEA should prompt you to configure the Spring Boot Applications as runnable Services, which you can then run from the Services tab. If not, then the actors can be started from their respective Application classes. You can run each actor independent from each other, but keep in mind that LocalNetty tries to connect to a MySQL database when it is started. In order to configure the database, see the steps below.

### Database Setup
1. Install Docker
2. Create a MySQL container:
```
docker run -e MYSQL_ROOT_PASSWORD=fritzyandfriends --name ffdb -d -p 3307:3306 mysql:latest
```
- Database user `root` is automatically generated.
- Database password is set to `fritzyandfriends`.
- Database name is set to `ffdb`
- External port is set to `3307`, and the internal port to `3306`.

This information is then used by LocalNetty from its `application.properties` file. Here you can also configure the port of the database.

To access the MySQL shell of the container from a terminal for testing, run the following command:
```
docker exec -i -t ffdb mysql -uroot -pfritzyandfriends
```
IMPORTANT: You don't need to create a database or tables. LocalNetty does this for you.
The database is created with `mydb?createDatabaseIfNotExist=true` from `application.properties`.
The tables are created from the `Entity` data classes from the common module.

### Solidity Compiler
TODO

correct version v5.....
Gradle version - compatability with the solc-web3j plugin



### LocalNetty
This is the central point of the application. It contains the following features:
- Register other devices
- Receiving power data from the other actors
- Deploying and loading smart contracts
- Combining the data from the other actors to determine the data for a transcation
- Execute a transaction to the smart contract

### Fritzy, Batty, Sunny
These are the actors that revolve around Local Netty. They contain the following features:
- Register itself
- Relay power data to Local Netty

### Common
This module contains classes that are used by multiple other modules. It also contains the loop simulator.
