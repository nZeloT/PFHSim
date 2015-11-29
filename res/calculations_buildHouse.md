### resource market
* wood        = 10
* insulation  = 20
* brick       = 18
* window      = 30
* concrete    = 6
* roof tile   = 1
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
  * price: 
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
* architect 
  * salary per period : 1.500
  * designes every three period a new house type
