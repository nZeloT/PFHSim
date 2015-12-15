## A proposal of the quality aspect
### Intro
The quality aspect in the game will be portrayed by an integer quality factor. The higher the factor the better the quality. More precisely,  it means that an element with the quality factor of 2*n is double as high-grade as an element with a quality factor of n.

### Quality in detail
The following elements within the game possess quality data:

####Required:

* WallType:
Each WallType has a static quality number, which is initially determined. The player has no influence on this value.

* Machine: 
A machine has a production quality which can be increased by upgrading it. 

* Wall:
Each concrete object of a wall, which has a WallType, has an individual quality-value that is influenced by the WallType’s quality factor and the machine’s quality factor. A wall’s quality will be calculated as follows: 
WallQuality = WallTypeQuality * MachineQuality

* PFHouse:
When the market orders a PFHouse of a given offer,  - just like the wall - each PFHouse has an individual quality, that results from the walls’ quality used to build the PFHouse:
wall-quality = (Sum of all WallQualities used in this one pfhouse)

* Offer:
For this procedure, an average-calculation-method in the enterprise’s warehouse is required in order to determine the average quality factor of a specific walltype. This average number is needed in the offer which then stores the average quality of a later created PFHouse. With this offer-quality-value the sales market classes can operate.

####Optional: 
-	Resource
-	Employee: ASSEMBLER
