# Fritzy & Friends
This project serves as a simulation of a multi module system of a decentralized energy grid that runs
transactions on the Ethereum blockchain. For a more detailed explanation, please go to
the [wiki](https://github.com/Terazeus/fritzyandfriends/wiki).

### Project Setup
1. Clone the project.
2. Open it in an IDE like IntelliJ IDEA.
3. Set up the database for LocalNetty (see database setup).
4. Set up the Solidity compiler for LocalNetty (see Solidity compiler setup).
5. Run each actor from the Services tab or its Application class.
6. Use the LoopSimulator in the common module to simulate system behavior, or use the Postman collection from the postman folder.

How to add another module (actor) if needed:
1. Right click on the parent module `fritzyandfriends` > New > Module.
2. Select `Gradle` on the left column.
3. Select `Java` as a library.
4. Give the module a name and click finish.
5. Add `implementation project(':common')` to its `build.gradle`.
6. Make a `resources` package with an `application.properties` file to specify the port of the actor.
7. Generate a main class and add the `@SpringBootApplication` annotation to it.

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

This information is set in `application.properties` of `LocalNetty`.

To access the MySQL shell of the container from a terminal for testing, run the following command:
```
docker exec -i -t ffdb mysql -uroot -pfritzyandfriends
```
IMPORTANT: You don't need to create a database or tables. LocalNetty does this for you.
The database is created with `mydb?createDatabaseIfNotExist=true` from `application.properties`.
The tables are created from the `Entity` data classes from the common module.

### Solidity Compiler

The solidity compiler is needed to create a java wrapper of our Fritzy smart contract for the BlockchainController to interact with.

1. Download the compiler at https://github.com/ethereum/solidity/releases/tag/v0.5.15

For windows download: solidity-windows.zip
For linux download: solc-static-linux

2. Unzip the file somewhere and add the FOLDER to your path.

How to add to System Path for Windows 
https://www.architectryan.com/2018/03/17/add-to-the-path-on-windows-10/

3. If everything is done correctly if you go to your cmd and type in solc you should get a list of all the allowed options


### Web3j-gradle-plugin

The way the project is currently setup we are using web3j-gradle-plugin which automatically uses the solidity compiler on your computer at gradle:build phase to generate the contracts that are stored in common/solidity. The java wrappers that are generated through the solc are then stored in build/contracts. 

<b> Major points to take note of !! </b>

The web3j-solc plugin is not compatible with gradle version 6.0 and up
https://github.com/web3j/web3j-gradle-plugin/issues/31

The web3j-solc-plugin also forces us to use Pragma version <= 5.1.15 because smart contracts of pragma version >= 0.6.x don't get generated correctly with the plugin.
https://github.com/web3j/web3j-gradle-plugin/issues/34

These are frustrating issues to deal with however the upside is that any changes to the smart contract don't require using the solc compiler manually since the plugin deals with this during gradle:build. Keep an eye out on these github issues because updates may come that fix this issue making the use of gradle 6.0 > and pragma version 0.6.x > possible with the plugin. 

Ofcourse the plugin could be cut out making it possible to use gradle 6.0 > and 0.6.x > however i would only think this would be worth it if ABIEncoderV2 was not an experimental pragma feature anymore since otherwise any time the smart contract is changed you would need to manually recompile.

This can be quite a chore if you are experimenting with the contract while testing some things out with the Blockchain Controller.

Improvements for the smart contract: ABIEncoderV2 can be implemented once this is not an experimental feature anymore.