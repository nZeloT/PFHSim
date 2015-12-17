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


##COMMENTS:

###Leon:
1) Why is a quality factor on PFHouse needed? We have one for Offer which results in different sale numbers. So at the time the house is build using PFHouse it´s already sold, so there is no need for an quality factor there. Is it?
2) Rest seems fine. Could you continue with outlining a concrete scale? Say for example the simplest wall has factor one or so.
