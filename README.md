# Fritzy & Friends
This project consists of 4 actors (or 4 submodules):
- LocalNetty
- Fritzy
- Batty
- Sunny
- Common

## Setup
TODO

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