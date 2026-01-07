CHAPERON ADRIEN 
ABGRALL ETHAN 

Explication des fonctionnalitées réalisée :

Nous avons suivi le cahier des charges pour atteindre les objectifs du Niveau 2, avec quelques éléments du Niveau 3. 
Voici le détail :

La gestion du Joueur : Déplacement horizontal fluide, système de points de vie (3 vies au départ) et gestion du tir.

La gestion des Ennemis : Les ennemis sont organisés en Formation. Nous avons implémenté différents types d'ennemis (Bee, Butterfly, Moth) avec leurs propres caractéristiques.

Système de Combat : * Détection précise des collisions entre projectiles et entités.

    Gestion des tirs ennemis et des attaques directes sur le joueur.
    Nettoyage des projectiles lorsqu'ils sortent de l'écran (méthode out_range).

Progression et Niveaux :

    Chargement dynamique des niveaux depuis les fichiers .lvl (Level 1, 2 et 3).
    Affichage du titre du niveau pendant 2 secondes avant le début de la partie.
    Passage automatique au niveau suivant une fois tous les ennemis éliminés.
    Le niveau 3 est composé d'un boss qui possède 7 vies.	

Score:

    Affichage du score actuel et du meilleur score (Highscore) pendant la partie.
    Lecture et écriture du meilleur score dans un fichier texte (highscore.sc) pour qu'il soit sauvegardé même après avoir fermé le jeu.



Guide d'exécution du projet :

Le projet est structuré pour être utilisé avec Visual Studio Code.

  1-  Ouvrez le dossier Galaga du projet dans VS Code.

  2-  Assurez-vous que le dossier ressources est bien présent à la racine.

  3-  Allez dans le fichier src/engine/App.java.

  4-  Lancez le programme (touche F5 ou bouton "Run").





Description de l'interface pour jouer avec votre projet :

    L'interface est divisée en plusieurs zones :

    Haut de l'écran : Affichage du score (à gauche) et du Highscore (à droite).

    Bas de l'écran : Affichage visuel des vies restantes sous forme de petits sprites de vaisseau.
                     Affichage Visuel du niveau où vous êtes, grace a des icones dont le nombre indique le niveau                           présent.

    Centre : Zone de combat.


    Les commandes :

    Flèche Gauche / Droite : Déplacer le vaisseau.

    Barre Espace : Tirer un missile.


    Barre Espace (Écran de fin) : Relancer une nouvelle partie après un Game Over.
