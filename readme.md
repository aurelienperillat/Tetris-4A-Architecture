Groupe : Aurélien Perillat - Thomas Frick - Nicolas Michaud

Compiler et lancer le client : 
- Se placer à la racine du dossier (là ou se trouve le fichier pom.xml)
- mvn clean install
- cd target
- java -jar tetris-0.0.1-SNAPSHOT.jar (ou double clique dessus)

compiler et lancer le serveur :
- Se placer à la racine du dossier
- cd src
- javac Serveur.java
- java Serveur

features faites :
- interface graphique pour le menu
- mode solo
- mode multi
- sérialisation des scores
- 7 types de pièces
- 2 types de pouvoirs

Comment jouer :
Le jeux se joue avec les 4 flèches, gauche et droite pour déplacer la pièce, haut pour la faire tourner, bas pour la faire tomber.
En mode multi-joueur chaque fois qu'un des joueurs remplies une ligne ou plus d'un coup, un pouvoir est lancé :
- pouvoir 1 la pièce adverse tombe
- pouvoir 2 la pièce adverse est changée
Nous avons modifié la règle des dix lignes car lors de nos parties tests nous avons trouvé cela plus amusant lorsque les pouvoirs
étaient lancés souvents.
Pour jouer en mode multi il faut lancer le serveur chez l'un des joueurs, qu'ils soient tous sur le même sous-réseau, et 
enfin sur la page de connexion rentrer un pseudo et l'adresse ip de la machine qui run le serveur.
Chaque joueur peut lancer une partie sur le serveur quand il le souhaite(pas de départ communs obligatoirs) et si un des joueurs meurt cela 
n'entraine pas un arrêt de partie pour les autres joueurs(autrement dit un joueur peut mourrir une fois pendant qu'un autre meurt 10 fois).

Exercice architecture :

Nous avons essayé de suivre le modèle MVC pour se projet. Nous désirions faire une interface graphique pour le menu, cela implique donc beaucoup
de gestion des interactions utilisateurs et l'archtecture MVC se prête tout à fait aux systêmes interactifs. Nous avons utilisé les librairies swing
et awt comme moteur graphique. Notre classe App représente le controler, les classes Accueil, Grille, Connexion, Statistique, toutes héritant de JPanel
représentent nos vues, et le modèle bien que difficile à représenter ici (car souvent BDD ou JSON) est constitué du fichier save.txt qui contient la 
sauvegarde des scores.

Notre classe App est donc le controler, elle instancie toutes les classes nécéssaires au fonctionement du logiciel et effectue les initialisations 
de bases. Elle implémente tous les listeners nécéssaires pour répondre aux interactions de l'utillisateur et aux évènements générés par les autres
classes du projet. Lors du lancement d'une partie elle délègue une partie de son fonctionement à deux types de classes: runGame (pour une 
partie solo) ou runGameMulti (pour une partie Multi). Ces deux classes implémentent l'interface Runnable et sont lancées dans leur propre Thread.
Elle permettent de lancer une série de test et de modification sur la grille à intervalle régulier (c'est elles qui font descendre la pièce à
intervalle régulier) et font aussi office de facade pour le controler et lui donnent un niveau d'indentation élevé d'accès à la grille(expliqué dans
l'exercice design patern).

Exercice Design Patern: 

Design Patern Observer : Notre classe app étant notre controler c'est donc elle qui répond aux différents évènements et modifie les vues en 
conséquences. La plupart des évènements sont générés par l'utilisateur mais il peut être intéressant de mettre en écoute un classe sur une autre
pour gérer dans notre cas les évènements suivants : game over partie solo, game over partie multi, connecté sur le serveur. Pour cela nous 
avons créé l'interface GameOverListener qui est implémentée par notre classe App et lui permet d'écouter des évènements générés par les classes
RunGame,RunGameMulti et Connexion.

Design Patern Facade : Je vous ai dit plus tôt que les classes runGame et runGameMulti en plus de servir à gérér la routine d'une partie faisaient 
aussi office de façade pour notre controler afin de manipuler la grille. Cela nous permet de simplifier grandement le code du controler.
exemple : appuie sur la fêche gauche par l'utilisateur
- code chez le controler : runGame.moveLeft();
-code dans la classe runGame :
public void moveLeft(){
		grille.removePiece(currentPiece, posX, posY, position); on supprime la pièce aux coordonés d'entrées
		boolean can = grille.canMoveLeft(currentPiece, posX, posY, position); on test si la pièce peut être dessiner un cran plus à gauche
		
		if(can == true){ si oui
			posX--; on diminue l'absice de la pièce
			grille.addPiece(currentPiece, posX, posY, position); on redéssine avec les nouvelles coordonnées
		}
		else sinon
			grille.addPiece(currentPiece, posX, posY, position); on ne modifie pas les coordonnées et on redéssine la pièce
	} 

Design Patern Lazy initialization : Lors de l'instantiation de le classe runGameMulti par notre controler, nous avons besoin de récupérer
l'inputstream et l'outputstream du socket de connexion afin de lancer un thread d'écoute sur le serveur et de pouvoir aussi émmettre des
messages. Cependant cela entraine des problèmes si la classe runGameMutli est instanciée dans le constructeur du controler car l'utilisateur
n'est pas encore connecté sur le serveur. Il a donc fallut déporter l'instanciation de cette classe après la connexion de l'utilisateur 
sur le serveur.

Concernant les test nous avons utilisé findBugs et retiré tout les warnings qui ont été générés, Nous avons effectué des tests globaux en
créant des scnénarios utilisteurs et en comparant le comportement du programme par rapport à celui attendu, mais aussi que celui ci s'exécute
sans stacktrace. Le seul cas de stacktrace actuel est côté serveur, si un des joueurs ferme son client sans attendre la fin de la partie, qui
est le moment ou le socket est fermé. Malheureusement par manque de temps nous n'avons pas pu écrire de tests unitaires.

Il existe encore quelques bugs lors d'une parties. Bugs qui viennent du fait que la classe runGame ou la classe runGameMulti testent et actualisent
le contenue de la grille toutes les 0.5 secondes mais des évènements utilisateur et serveur peuvent survenir n'importe quand, ce qui peut entrainer des
bugs de synchronization dans quelques cas de figure lorsque ces évènements sont déclenchés pendant le temps de sleep du thread du runGame.  




 
