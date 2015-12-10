#Server Client Communication Draft

Version: 2; Author: Leon


###Server Tasks
Required:
* **RES**: Handle the resource selling; Every Enterprise buys resources on the same market
* **BUY**: Handle the House selling; Every Enterprise has a cataloge of offers with an maximum amount of sold items; Simulate differend groups of buyers which buy different houses
* **DIS**: Distribute market information to the different enterprises according to their investment in market research

Optional:
* **CHK**:Check that the actions executed by the enterprise on the client side are valid
* more to come ...

====================================================================================================================
##Everything rethought
Version 2; Author: Leon

###Idea
The idea is basically to have one message exchange per round with the server. At the beginning of eaach round all the necessary informations are transferred from the server. This includes:
* new resource prices, 
* amount of sold offers and 
* the respective amount of market research info
* flag whether the game is finished

At the end of the round the client sends a round finished, waiting for simulation to go message with the following additional info:
* the amount of resources bought for the price of the finished round
* the new offer catalog
* the investment in market research
* (any additional info that may be required for market research)
* (an action log to make validation possible)

Still when sending only one message per round the server client core should map arround some kind of aggregation storage. Meaning provide an API as it is now, e.g. buy resources whenever you want, but store the amount values unti the round is finished and pack them into one single message.

###Possible Problems
This, as the previous version does not allow reentering the game after a disconnect because we don't know exactly what happend on the client side.

###Processing Steps
0. the server is initilized with a end game criteria
1. user buys resources and does all the other stuff; amounts get stored and summed
2. user finishes the round; all required infos are send to the server
3. server calculates the new resource prices
4. sells some houses
5. does some market research
6. client recieves the respective info
7. is the game finished? if not continue with step 1.

I skip a detialed description of the protocol, as it is only one message on each side.

====================================================================================================================
##The old stuff
Version 1; Author: Leon

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


#######Comments, Florian:

* Analysis-->RES: I would really prefer a solution where you can buy resources multiple times a round. This makes it much more fexible for the user. Send one request for one buying action instantly when button is clicked.

* Simulation Phase: I would also transfer the new resource prices at the end of a simulation phase, so that the player is able to see how much his invests would cost now. We can leave the price at the RES-Protocoll as an ACK/Recheck.

* End Game criteria: We also need to think about that. Different opportunities, xxxx Euro reached, xx% Market Shar (xx>70%?), last player with positiv cash. Easiest would be EURO reached.., but is that the best? Also possible when all houses are available for everyone, go for 2 more rounds or something like that.

* CHK-->something for iteration 2 :D
