Groupe : Aur�lien Perillat - Thomas Frick - Nicolas Michaud

Compiler et lancer le client : 
- Se placer � la racine du dossier (l� ou se trouve le fichier pom.xml)
- mvn clean install
- cd target
- java -jar tetris-0.0.1-SNAPSHOT.jar (ou double clique dessus)

compiler et lancer le serveur :
- Se placer � la racine du dossier
- cd src
- javac Serveur.java
- java Serveur

features faites :
- interface graphique pour le menu
- mode solo
- mode multi
- s�rialisation des scores
- 7 types de pi�ces
- 2 types de pouvoirs

Comment jouer :
Le jeux se joue avec les 4 fl�ches, gauche et droite pour d�placer la pi�ce, haut pour la faire tourner, bas pour la faire tomber.
En mode multi-joueur chaque fois qu'un des joueurs remplies une ligne ou plus d'un coup, un pouvoir est lanc� :
- pouvoir 1 la pi�ce adverse tombe
- pouvoir 2 la pi�ce adverse est chang�e
Nous avons modifi� la r�gle des dix lignes car lors de nos parties tests nous avons trouv� cela plus amusant lorsque les pouvoirs
�taient lanc�s souvents.
Pour jouer en mode multi il faut lancer le serveur chez l'un des joueurs, qu'ils soient tous sur le m�me sous-r�seau, et 
enfin sur la page de connexion rentrer un pseudo et l'adresse ip de la machine qui run le serveur.
Chaque joueur peut lancer une partie sur le serveur quand il le souhaite(pas de d�part communs obligatoirs) et si un des joueurs meurt cela 
n'entraine pas un arr�t de partie pour les autres joueurs(autrement dit un joueur peut mourrir une fois pendant qu'un autre meurt 10 fois).

Exercice architecture :

Nous avons essay� de suivre le mod�le MVC pour se projet. Nous d�sirions faire une interface graphique pour le menu, cela implique donc beaucoup
de gestion des interactions utilisateurs et l'archtecture MVC se pr�te tout � fait aux syst�mes interactifs. Nous avons utilis� les librairies swing
et awt comme moteur graphique. Notre classe App repr�sente le controler, les classes Accueil, Grille, Connexion, Statistique, toutes h�ritant de JPanel
repr�sentent nos vues, et le mod�le bien que difficile � repr�senter ici (car souvent BDD ou JSON) est constitu� du fichier save.txt qui contient la 
sauvegarde des scores.

Notre classe App est donc le controler, elle instancie toutes les classes n�c�ssaires au fonctionement du logiciel et effectue les initialisations 
de bases. Elle impl�mente tous les listeners n�c�ssaires pour r�pondre aux interactions de l'utillisateur et aux �v�nements g�n�r�s par les autres
classes du projet. Lors du lancement d'une partie elle d�l�gue une partie de son fonctionement � deux types de classes: runGame (pour une 
partie solo) ou runGameMulti (pour une partie Multi). Ces deux classes impl�mentent l'interface Runnable et sont lanc�es dans leur propre Thread.
Elle permettent de lancer une s�rie de test et de modification sur la grille � intervalle r�gulier (c'est elles qui font descendre la pi�ce �
intervalle r�gulier) et font aussi office de facade pour le controler et lui donnent un niveau d'indentation �lev� d'acc�s � la grille(expliqu� dans
l'exercice design patern).

Exercice Design Patern: 

Design Patern Observer : Notre classe app �tant notre controler c'est donc elle qui r�pond aux diff�rents �v�nements et modifie les vues en 
cons�quences. La plupart des �v�nements sont g�n�r�s par l'utilisateur mais il peut �tre int�ressant de mettre en �coute un classe sur une autre
pour g�rer dans notre cas les �v�nements suivants : game over partie solo, game over partie multi, connect� sur le serveur. Pour cela nous 
avons cr�� l'interface GameOverListener qui est impl�ment�e par notre classe App et lui permet d'�couter des �v�nements g�n�r�s par les classes
RunGame,RunGameMulti et Connexion.

Design Patern Facade : Je vous ai dit plus t�t que les classes runGame et runGameMulti en plus de servir � g�r�r la routine d'une partie faisaient 
aussi office de fa�ade pour notre controler afin de manipuler la grille. Cela nous permet de simplifier grandement le code du controler.
exemple : appuie sur la f�che gauche par l'utilisateur
- code chez le controler : runGame.moveLeft();
-code dans la classe runGame :
public void moveLeft(){
		grille.removePiece(currentPiece, posX, posY, position); on supprime la pi�ce aux coordon�s d'entr�es
		boolean can = grille.canMoveLeft(currentPiece, posX, posY, position); on test si la pi�ce peut �tre dessiner un cran plus � gauche
		
		if(can == true){ si oui
			posX--; on diminue l'absice de la pi�ce
			grille.addPiece(currentPiece, posX, posY, position); on red�ssine avec les nouvelles coordonn�es
		}
		else sinon
			grille.addPiece(currentPiece, posX, posY, position); on ne modifie pas les coordonn�es et on red�ssine la pi�ce
	} 

Design Patern Lazy initialization : Lors de l'instantiation de le classe runGameMulti par notre controler, nous avons besoin de r�cup�rer
l'inputstream et l'outputstream du socket de connexion afin de lancer un thread d'�coute sur le serveur et de pouvoir aussi �mmettre des
messages. Cependant cela entraine des probl�mes si la classe runGameMutli est instanci�e dans le constructeur du controler car l'utilisateur
n'est pas encore connect� sur le serveur. Il a donc fallut d�porter l'instanciation de cette classe apr�s la connexion de l'utilisateur 
sur le serveur.

Concernant les test nous avons utilis� findBugs et retir� tout les warnings qui ont �t� g�n�r�s, Nous avons effectu� des tests globaux en
cr�ant des scn�narios utilisteurs et en comparant le comportement du programme par rapport � celui attendu, mais aussi que celui ci s'ex�cute
sans stacktrace. Le seul cas de stacktrace actuel est c�t� serveur, si un des joueurs ferme son client sans attendre la fin de la partie, qui
est le moment ou le socket est ferm�. Malheureusement par manque de temps nous n'avons pas pu �crire de tests unitaires.

Il existe encore quelques bugs lors d'une parties. Bugs qui viennent du fait que la classe runGame ou la classe runGameMulti testent et actualisent
le contenue de la grille toutes les 0.5 secondes mais des �v�nements utilisateur et serveur peuvent survenir n'importe quand, ce qui peut entrainer des
bugs de synchronization dans quelques cas de figure lorsque ces �v�nements sont d�clench�s pendant le temps de sleep du thread du runGame.  




 
