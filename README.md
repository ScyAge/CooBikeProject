# Projet COO 2024

## GIARDINELLI Léo & JOLY Evan

## ATTENTION ! 
## **LE PROJET COMPILE AVEC JAVA 22 ET AVEC MAVEN**
## ATTENTION !

### Introduction sur le sujet du projet

Le projet consiste à mettre en œuvre les différents design patterns vus en cours et à les appliquer dans un cadre concret.  
Il s'agit également de comprendre quelle solution implémenter dans chaque situation et de justifier son choix.

Pour ce projet, nous avons conçu un système de gestion de location de véhicules similaire à v'Lille.  
Dans ce projet, il y a 10 stations définies dans le `main`.  
Chaque station contient un nombre aléatoire maximum de véhicules, compris entre 10 et 20, répartis en différents types.  
Les utilisateurs peuvent louer ou déposer des véhicules dans une station, et ils peuvent choisir le type de véhicule qu'ils souhaitent louer.

---

### HowTo

Rubrique « HowTo » décrivant les étapes principales :
- Récupération des sources du projet depuis le dépôt.
```bash
git clone https://gitlab-etu.fil.univ-lille.fr/evan.joly.etu/joly_giardinelli_coo.git
```
- Génération de la documentation.
```bash
javadoc -sourcepath src -subpackages -d docs
```
- Compilation et exécution des sources.
```bash
mvn compile
```
```bash
mvn exec:java
```
- Compilation et exécution des tests.
```bash
mvn test
```

- Génération et exécution de l’archive (.jar) du projet.
```bash
jar cvfe joly-giardinelli-coo.jar Main -C target/classes .
```
Nous n'avons pas pu exécuter le JAR en raison d'une incompatibilité avec la version de Java.  
Cependant, l'exécution via la commande Maven mentionnée ci-dessus fonctionne correctement en cas de besoin.
```bash
java -jar joly-giardinelli-coo.jar
```
- Nettoyage des fichiers générés
```bash
mvn clean
```

---

### Éléments de code saillants

#### Éléments de conception intéressants/importants

- Modularité et réutilisabilité des composants du projet.

#### Présentation des principes de conception mis en œuvre

- **Stratégie de redistribution** (deux méthodes, une aléatoire et l'autre round robin).
- **Un observer** pour la notification des stations au `ControlCenter`.
- **Un visitor** de station (les visiteurs tels que voleurs) qui n'est pas vraiment un vrai visiteur, car il agit toujours sur un même type de station.
- **Un ClientStation**, qui est un peu comme un visiteur mais sans méthode `accept`, car il visite uniquement le même type de station.
- **Un état de chaque station**.
- **Un décorateur de véhicule** (équipement, panier, etc.).
- **Un état des véhicules**.
- **Un builder** pour la création de véhicules, notamment pour gérer des identifiants uniques.
- **Un visitor de véhicule** (par exemple pour le réparateur).

##### Stratégie de redistribution

Pour équilibrer les véhicules entre stations, deux stratégies principales de redistribution ont été définies.  
Ces stratégies interviennent lorsque des stations deviennent pleines ou vides.

- **Redistribution Aléatoire** :
  - **Stations pleines** : Une station pleine redistribue un nombre aléatoire de véhicules, compris entre 2 et la capacité maximale de la station, vers des stations choisies aléatoirement parmi celles qui ne sont pas pleines.
  - **Stations vides** : Une station vide reçoit un nombre aléatoire de véhicules, compris entre 2 et la capacité maximale de la station, provenant des stations qui ne sont pas vides.

- **Redistribution Round-Robin** :
  - **Stations pleines** : Une station pleine redistribue la moitié de sa capacité maximale, véhicule par véhicule, vers les stations les plus vides parmi les autres.
  - **Stations vides** : Une station vide reçoit des véhicules un par un, jusqu'à atteindre la moitié de sa capacité maximale, en provenance des stations les plus pleines.

##### Observer

Afin de gérer les différentes notifications qu'une station peut envoyer au `ControlCenter`, comme un ajout, un véhicule loué, ou lorsque qu'une station est pleine ou vide, les différents attributs du `ControlCenter` sont mis à jour automatiquement.

##### Les visitors de station et de véhicule

Nous avons décidé de gérer séparément les différentes actions que l'on pourrait réaliser sur des stations et des véhicules grâce au design pattern Visitor.

- **VehicleVisitor** : Il peut agir différemment selon le type de véhicule qu'il visite.
- **StationVisitor** : Cette classe permet de gérer des événements sur des stations, bien que son fonctionnement ne corresponde pas tout à fait au pattern Visitor.

Ces actions sont ensuite gérées dans le `ControlCenter`, qui peut, grâce à des méthodes dédiées, lancer tout type d'événement sur les stations et les véhicules.

##### ClientStation

Voir la section « Points à mettre en avant pour valoriser le projet ».

##### États des stations

Les états des stations sont au nombre de 3. Ils permettent de gérer les cas suivants :
- Une station vide.
- Une station pleine.
- Une station ni pleine ni vide.

Ces états adaptent les différentes méthodes de la classe, par exemple pour savoir si l'on peut louer un véhicule.

##### Équipement des véhicules

Pour gérer les équipements des véhicules, nous avons utilisé un décorateur.  
Ce décorateur ajoute progressivement différents équipements, affichés dans une chaîne de caractères.

##### États des véhicules

Les états des véhicules sont au nombre de 3 :
- **En service** : Le véhicule peut être loué.
- **Volé** : Le véhicule ne peut plus être loué ou réparé et son statut reste volé.
- **Hors service (HS)** : Le véhicule est immobilisé, mais peut être réparé par le réparateur après 2 tours de jeu.

##### Builder de véhicule

Le builder a été utilisé pour la création d'identifiants uniques pour chaque véhicule créé, répondant ainsi aux attentes du projet.

##### Visitor de véhicule

Ce visitor permet à toute classe qui étend `VehicleVisitor` de définir un comportement spécifique lorsqu'elle visite un véhicule.  
Exemple : Le réparateur remet en service un véhicule HS après 2 tours de boucle.

---

### Points à mettre en avant pour valoriser le projet

#### ClientStation

Un point clé du projet était de gérer les différents véhicules dans la station : comment les retirer, avoir le type de véhicule que l'on veut, etc.  
Pour réaliser cela, nous avions au départ pensé à ajouter un état dans Station qui gérerait quel type de véhicule on voulait renvoyer avec rentVehicle. Or, cela n'avait pas trop de sens de donner la responsabilité à la station de renvoyer tel type de véhicule.  
C'est pour cela que nous avons décidé à la base de gérer cela comme un visiteur de Station, dont la méthode visit nous permettrait de renvoyer le véhicule du type souhaité selon le visiteur choisi. Or, ces deux visiteurs n'avaient rien à voir, car les deux ne devaient pas forcément implémenter les mêmes méthodes.  
Au final, nous avons donc séparé ce visiteur et l'avons placé dans un package à part entière. Ce visiteur permet donc de retirer un véhicule du type que l'on veut et de le récupérer.  
Pour faire cela, nous utilisons une interface anonyme avec une méthode de test qui effectue un instanceof pour vérifier la présence d'un véhicule voulu dans une station.  
Sachant que, comme pour le visiteur de station, ils n'ont de visiteur que le nom, car ils ne se comportent pas réellement de la même manière.

---

### UML

![Texte alternatif](./uml.png)