## Review 2015-12-13
* Finished the group of buyers
* separated server and client
 * server finished, client for tests is implmented 
* developed presentation for monday
* played the game via console

# Review 2015-12-07
### ToDo for presenationday

* Define Use Cases // show use case diagram
* Show Unittest, show Coding
* Show Fachkonzeptschicht // Business Logik
* What is the highlight of your game?
* show simulation/ Group of Buyers 
* show component diagram

* Präsentationszeit: 15 min, max. 20min
* PowerPoint erwünscht, nicht verpflichtend 
 
### Review Notes
####Strategy 
1. Produce/Calculate houses
2. Produce new walls with the machines

#### ToDo at Sprintweek three
 * Reduce machine output and choose which wall type to build 
 * Check if houose is realizable, implement a maximal amount of production which are realizable
 * Continue implementing the group of buyers
 * Implement a second cash integer
 * Think about server-client-model
 * Start realisation of UI
 * Prepare presenation for 2015-12-14

### Information from the call with Greg
* Server-Client-Architecture : he is not interested in, so it is our fault
 * pay attention to not announce too many advices/restriction to the players 
* catch useless input data from input fields - e.g.: number of employees - "abc"
* UI Ideas from other groups
 * header information with player's name, time left, number of round
 * end page with scores
* 60% test coverage are enough (our actual coverage is ca. 90%)
#Review 2015-12-06

Done:
* Cost calculation and test cases
* building houses, types of houses and material changed
* MockUp done (80%)
* Upgrade System
* groups of buyers simulation startet


* Question/Problem: What if you save resources for a villa, but your simulation bot wants 1000 bungalows from you and your warehouse is now empty? How to simulate the rivalry thought?

* At present: Simulate different groups of buyers

What happens in a new round?
* calculate new prices for resources
* Upgrade and building time decreases by 1
* give machine a new plan what to produce in this round
* producing walls: material is immediatly away from warehouse, when starting the machine

* simulation: if an upgrade is finished now, update atrributes from employee/house catalog/machine 
* simulation: reduce material in warehouse from house projects which were selled
* simulation: add produced walls 

* simulation: picks employees for house project, picks material from warehouse, transfer money on your account

# Review 2015-11-29

* neue Backlogitems erstellt

Done:
* aus Material Wände bauen
* aus Wände, Material, Mitarbeiter Häuser bauen
* Ausfuhrung des Upgrade fur Mitarbeiter, Maschine, noch nicht Auswirkungen

### Bericht für Greg 2015-11-29

* Grundkonzept (Business Logik) steht 
* Implementierung gestartet
* gleichzeitige Produktion von UnitTest - funktionieren im Moment alle
* kein IntegrationTest - demnächst
* UI noch nicht ausgearbeitet 
* MockUp in Entwicklung
* Zugangsdaten für https://ninjamock.com/Designer/Workplace USER: marcel@wolf4.de Passwort pfh


# Brainstormin Session Two 2015-11-28

departments
*	Procurement
   * 1. Iteration: wood
      * 2. Iteration: quality classes of different wood sorts
   * 2. Iteration: insulation material, windows
*	Research and development (R&D)
    * Product Research and Planning
      * explore new house types  
* Market Research
*	Production
   * production planning	  
*	Human Resources
*	Zweite Iterationsphase:
    * Sales (Marketing)
    * Accounting and Controlling

* first market: market for raw material 
* second market: sales market
   * for our houses

   
Newsfeed am Ende jeder Runde
*	Informieren über aktuelle Trends / Random Events
*	Wer in Marktforschung investiert, bekommt mehr Neuigkeiten

# Fertighäuser

Faktoren:
 * Zuliefererkonflikte
 * Mitarbeiterzufriedenheit
 * KÃ¤uferzufriedenheit
 * Kreditvergabe
 * Hausbau
 * Lagerverwaltung
 * Lage des Baugebietes
 * Kudentypenn:
     * Okohausbauer
     * Niedrigenergiehäuser
 * Spezialaufgaben als Bonusauftrage

Runde:
 * Großes Lager
 * Bessere Architekten
 * Fortbildungen für Mitarbeiter

Rundenende
 * Angebote für Hausbau erhalten 
 * Angebote für Häuser reinstellen

Taktzyklus
 * Zeitbegrenzung , vorher ready sein möglich

Informationsausgabe/Tutorial/Ausgangspunkt/Startpunkt:
 * Ein neues UN mit 1.000.000
 * 10 Mitarbeiter
 * Gegebene Auftragssituation

Level anhand von Erfahrungspunkten:
 * Häuser bauen
 * Personal
      * einstellen
      * Fortbilden
 * Produktion
      * Maschine upgraden 
      * Lager bauen
      
Runden Ende:
 * Mitarbeiter stehen am Anfang der nÃ¤chsten Runde wieder zur Verfügung
 * Ressourcen mÃ¼ssen neu einkauft werden
 * FÃ¼nf bis sechs verschiedenene AuftrÃ¤ge heran ziehen

Einfluss auf nÃ¤chste Runde:
 * Zufriedenstellend AuftrÃ¤ge erfÃ¼llt
 * Angenommene AuftrÃ¤ge auch gleich erfÃ¼llt
 * Weniger Mitarbeiter als benÃ¶tigt eingestellt 
 * Maschinen fÃ¼r nÃ¤chste Runde 

Worin kann man investieren?
 * Ausbildung der Mitarbeiter
 * Maschinen 
 * Mitarbeiterausbildung

Was kann man machen?
 * Ressourcen, Mitarbeiter auf AuftrÃ¤gen verteilen
 * Investieren und Produktionsfaktoren upgraden
 * Ressourcen einkaufen
 * AuftrÃ¤ge aus Auftragspool wÃ¤hlen

Was kann man beeinflussen?
 * Mehr oder weniger ArbeitskrÃ¤fte
 * Ressourcen komplettieren

Kosten bei Bau:
 * Je nach Erfahrung kostet Mitarbeiter mehr , arbeitet  schneller
 * Ressourcen kÃ¶nnen wÃ¤hrenddessen noch nachgereicht werden

Simulationphase:
 * Fortschritt pro Haus
 * Neue Preise fÃ¼r Rohstoffe
 * AuftrÃ¤ge generieren
 * Kapital kalkulieren
 * Laufende Kosten
 * Laufende ErtrÃ¤ge
 * Zinsen fÃ¼r Dispo
 * Kredite sowohl die laufende Kosten als aucRohstoffeeinkÃ¤ufe

Wann wird Preis kalkuliert?
 * Direkte Verrechnung
 * Anzahlung, bei Fertigstellung Rest
 * Bei frÃ¼herer Fertigstellung gibt es mehr Erfahrungspunkte

Gewinnerkriterien:
 * Nach bestimmter Anzahl an Runden
 * Gewinner wer hÃ¶chstes Level

Random Events:
 * Kunde zahlt nicht
 * Maschine kaputt, Mitarbeiter krank
 * Rohstoffmarkt verÃ¤ndert sich
 * Lager brennt 

Anmerkung
 * AuftrÃ¤ge des nÃ¤chsten Levels
 * Laufzeit des Hausbaus
 * Faktoren beeinflussen, Entscheidungen treffen
 * Ergebnis fÃ¼r Entscheidung kommen erst sehr spÃ¤t
