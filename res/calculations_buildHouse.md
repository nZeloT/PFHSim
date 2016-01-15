### resource market
* wood        = 10
* insulation  = 20
* brick       = 18
* window      = 30
* concrete    = 6
* roof tile   = 1

---
* Holz(Eiche): 160 € m³ Volumen 300
* Beton: 100€ m³ Volumen 300
* Dachziegel: 1 Volumen 1
* Dämmwolle: 3,45€ Volumen 100
* Fenster: 50-80€ Volumen 50
* Ziegel: 1,04€ Stück Volumen 1


### wall types
* light weight conctruction 
  * 5 wood, 1 insulation, 1 window
  * price = 
* light weight plus construction
  * 5 wood, 3 insulation, 1 window
  * price = 
* massive light construction
  * 5 brick, 3 insulation, 1 window, 1 concrete
  * price = 
* massive plus construction 
  * 5 brick, 5 insulation, 1 window, 1 concrete
  * price = 
* panorama wall
  * 2 brick, 5 windows, 1 concrete, 3 insulation
  * price

### house types
* bungalow
  * 5 walls, 25 roof tiles, 6 wood for roof
  * 3 employees
  * 1 period
* block house
  * only light weight / - plus construction walls
  * 7 walls, 50 roof tiles, 12 wood for roof
  * 5 employees
  * 1 period
* effienciency house
  * 4 concrete for roof, 6 walls
  * 5 employees
  * 1 period
* multi family house
  * 12 walls, 80 roof tiles, 19 wood for roof
  * 9 employees
  * 2 periods
* comfort house
  * only plus construcion walls
  * 1 panorama wall, 6 walls, 80 roof tiles, 19 wood for roof
  * 8 employees
  * 2 periods
* city villa
  * 2 panorama, 6 walls, 100 roof tiles, 21 wood for roof
  * 10 employees
  * 2 periods
* trendhouse
  * 3 panorama, 5 walls, 10 concrete for roof
  * 9 employees
  * 2 periods
  
###machines
* start = 1 woodwall machine
* woodwall machine
  * output 25 walls per period
  * need 3 employees 
  * operating cost : 1.000
  * price : 10.000
* brickwall machine
 * output 20 walls per period
 * need 3 employees
 * operating cost : 1.500
 * price : 15.000
* panorama wall machine
 * output 5 walls per period  
 * need 3 employees
 * operationg cost : 1.000
 * price : 17.500
 

###employee
* hr employee
  * manages 50 employees
  * salary per period : 1.000 
* worker in production 
  * can be assigned to one machine
  * salary per period : 800
* assembler (Bauarbeiter)
  * salary per period : 1.200
* sales man
  * salary per period : 1.500
  * per two types of houses --> one sales man is needed
* market researcher
  * salary per period : 1.000
* store keeper (Lagerarbeiter)
  * salary per period : 700
  * need 3 for your start storage
* architect 
  * salary per period : 1.500
  * designes every three period a new house type
  * works at research project lab
  
### storage
 * operation cost per period : 3.000
 * per upgrade +1.000
 
### upgrade 
* for employees
 * hr employee
  * need one who is NOT on training 
  * hr training cost : 5000 
  * only one time
  * can manage 100 employee
  * increases salary by 10%
  * training duration: 1 period
* assembler (Bauarbeiter)
  * salary per period : 1.200
  * training goal: if every assembler for the house is trained, house can be build by one less employee
  * training cost: 1.000
  * training duration: 1 period
  * increases salary by 10%
* architect 
  * reduces time of design thinking of new houses by one period
  * during training no design thinking
  * training cost : 10.000
  * training duration: 1 period
   
* for houses
 * order like mentioned above in description

* for machines
 * + 7 walls 
 * duration for upgrade: 1 period
 * upgrade cost: 1/3 of purchase price
 * only three times of upgrade is possible
 
*zweite Iteration
 * Upgrade erhöht  Qualität der Wände --> Unterscheidung in hohe performance oder gute Qualität fur die Produktion

* for storage
 * volume of storage increses by 50%  
 * cost: 50.000
 * level up duration: 1 period
 * during level up you can still use the storage 
 * need +1 storage keeper 

###catalog for market
* one housetype in every varition of walls
* price is variable
 * see all operation costs (machine + employees for machine) + material costs + employee cost + 
   Sockelbetrag: hr manager salary, storage keeper salary, storage cost, architect salary, design thinking project cost 
* training cost, upgrades, new purchases are effect bank account directly 
