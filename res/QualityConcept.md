## A proposal of the quality aspect
### Intro
The quality aspect in the game will be portrayed by an integer quality factor. The higher the factor the better the quality. More precisely,  it means that an element with the quality factor of 2*n is double as high-grade as an element with a quality factor of n. So it really is a linear scale.

### Quality in detail
The following elements within the game possess quality data:

####Required:

* WallType:
Each WallType has a static quality number, which is initially determined. The player has no influence on this value.

* Machine: 
A machine has a production quality which can be increased by upgrading it. 

* Wall:
Each concrete object of a wall has a quality-value that is influenced by the basic WallType's quality factor and the average of all machine's quality factor being able to build the respective walltype. A wall's quality will be calculated as follows: 
WallQuality = BasicWallTypeQuality * AverageMachineQualityOfRespectiveMachines (excluding the machines being in an upgrade)
Consequently, two walls of the same walltype, always have the same quality factor.
Therefore, an average-calculation-method in the enterprise's production-house is required in order to determine the average quality factor of a specific walltype.
Furthermore, each round the average wall-quality must be calculated anew.

* NOT PFHouse:
The quality of a pfhouse is similar to the quality determined in the Offer.

* Offer:
The offer-quality-value is needed for the sales market calculations.
The sum of the quality of all walls chosen for the offer (of a specific walltype) ergibt the offer-quality-value.

####Optional: 
-	Resource
-	Employee: ASSEMBLER


#Scale:
#####Walltypes:
- Light weight construction = 1
- Light weight construction plus = 2
- Massive light weight construction = 4
- Massive plus construction = 5
- Panorama wall = 8 

######Machines: (the values behind the '+' are for the upgrade-increases)
- Woodwall machine = 5 + 1 + 1 + 1
- Brickwall machine = 5 + 2 + 2 + 2
- Panorama wall machine = 5 + 2 + 2 + 2

######Examples:
By using these values, all other quality factors can be calculated dynamically.
For example, the minimal quality of a Bungalow is: 5 x 5 x 1 (avg. machine-quality x number of walls required x light weight construction quality) = 25 
or the maximum quality of a City villa is: 11 x (2x8 + 6x8) = 704
or another example: player with woodwall-machine (quality = 6) and panorama wall machine (quality = 11)
--> quality of a city villa using panorama and light weight construction walls [as specified in an offer]:
11 x (2 x 8) + 6 x (6 x 1) = 212
