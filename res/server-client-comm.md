#Server Client Communication Draft

Version: 1; Author: Leon

###Server Tasks
Required:
* **RES**: Handle the resource selling; Every Enterprise buys resources on the same market
* **BUY**: Handle the House selling; Every Enterprise has a cataloge of offers with an maximum amount of sold items; Simulate differend groups of buyers which buy different houses
* **DIS**: Distribute market information to the different enterprises according to their investment in market research

Optional:
* **CHK**:Check that the actions executed by the enterprise on the client side are valid
* more to come ...

###Analysis

1. **RES**
  * Instant Response required; Origin is the Client;
  * Resources should be sold and processed **instantly**; Don't wait for the round to finish and the simulation to start
  * Is the user allowed to buy resources more than once per round?
  
2. **BUY**
  * Result from the Server-sided simulation step; Origin is the Server;
  * The simulation decides how many houses are bought from which enterprise; This is an update after the simulation finished

3. **DIS**
  * Result from the Server-sided simulation step; Origin is the Server;
  * Depending on the investment; a specific amount of data is send to the user
  
###Processing Steps
0. server is initialized with an end game criteria
1. user buys resources; sends info to server
2. server executes the purchase; sends info to the respective client
3. user finished round; sends info about market research to the server;
4. after all user sent data; server does simulation
5. server decides about whether the game is finished; if so sends game finished info else continue;
6. server sends info about sold houses to the respective clients
7. server sends info about market research to the client
8. a new round starts; resume at point 1

###Protocol
Transferring real Java Objects containing the necessary data; the suggested text strings are only for easier imagination;

######Setup-Phase
not needed

######Simulation-Phase

**RES**: 
* C: RES AMOUNT-TYPE-0 AMOUNT-TYPE-1 AMOUNT-TYPE-2 ...
* S: RES COSTS

**BUY**:
* C: BUY OFFER-CATALOG
* S: BUY AMOUNT-OFFER-0 AMOUNT-OFFER-1 AMOUNT-OFFER-2 ...

**DIS**:
* C: DIS INVEST
* S: DIS CLNT-0-INFO CLNT-1-INFO ...

######End-Game
transfer the full **DIS** report

* S: END CLNT-0-INFO CLNT-1-INFO ...