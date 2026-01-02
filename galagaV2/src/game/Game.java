package game;

import engine.StdDraw;
import game.actors.Enemy;
import game.actors.Formation;
import game.actors.Player;
import game.actors.Projectile;
;



/**
 * Classe du jeu principal.
 * Gère la création de l'espace de jeu et la boucle de jeu en temps réel.
 */
public class Game {
    public Player player; // Jouer, seul éléments actuellement dans notre jeu
    public Formation formations;




    /**
     * Créé un jeu avec tous les éléments qui le composent
     */
    public Game() {
        player = new Player(0.5, 0.25, 0.05);
        this.formations = new Formation();
        this.formations.loadLevel("ressources/levels/Level1.lvl");
    }

    public void resetApresMort(){
        //reset les liste des projectiles
        player.getLstProj().clear();
        formations.getLstProj().clear();
        //reset la position de la formation
        formations.setOffsetX(0);

        //reset la position du joueur et le rendre invisible
        player.setVisible(false);
        player.setX(0.5);

        //attendre 3 secondes
        long tempsMort = System.currentTimeMillis();
        while(System.currentTimeMillis() - tempsMort < 3000) {}
        //rendre le joueur visible a nouveau
        player.setVisible(true);
    }

    /**
     * Initialise l'espace de jeu
     */
    private void init() {
        StdDraw.setCanvasSize(700, 700);     
        StdDraw.enableDoubleBuffering();
    }

    /**
     * Initialise le jeu et lance la boucle de jeu en temps réel
     */
    public void launch() {
        init();

        while (isGameRunning()) {
            StdDraw.clear(); // On efface tous ce qu'il y a sur l'interface
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledSquare(0.0,0.0, 700/ 2);

            update(); // on met a jour les attributs de chaque éléments
            draw(); // on dessine chaques éléments

            StdDraw.show(); // on montre l'interface
            StdDraw.pause(30); // on attend 30 milisecondes avant de recommencer
        }
    }

    /**
     * Condition d'arrêt du jeu
     * 
     * @return true car on n'as pas encore de conidtions d'arrêt
     */
    private boolean isGameRunning() {
        return true;
    }

    /**
     * Dessin tous les éléments du jeu
     */
    public void draw() {

        player.draw();
        player.ProjDraw();
        formations.FormationDraw();
        formations.ProjDraw();

            
        }
        
        
        
    

    /**
     * Met a jour les attributs de tous les éléments du jeu
     */
    private void update() {
        player.update();
        for (Enemy enemy : formations.getEnemies()) {
            enemy.update(formations.getEnemies());
           
            
        }
        formations.update();
        if (player.getSizeLstProj()>0){
            for(int i=0; i<player.getSizeLstProj(); i++){
                player.getLstProj().get(i).update(); 
                player.projectileSupr(i);
            }
            
        }
        for (int i = player.getSizeLstProj() - 1; i >= 0; i--) {
            Projectile p = player.getLstProj().get(i);
    
            // Pour chaque projectile, on teste tous les ennemis
            for (int j = formations.getEnemies().size() - 1; j >= 0; j--) {
                Enemy e = formations.getEnemies().get(j);
        
                if (e.collision(p)) {
                 // COLLISION DÉTECTÉE !
                 formations.getEnemies().remove(j);      // L'ennemi meurt
                 player.getLstProj().remove(i);  // Le projectile disparaît
                 // Ajouter des points au score ici
                 break; // On arrête de tester ce projectile puisqu'il a disparu
        }
    }
}
            for(int j = formations.getLstProj().size() - 1; j >= 0; j--) {
                Projectile p = formations.getLstProj().get(j);
                if(player.collision(p)){
                    // COLLISION DÉTECTÉE !
                    // Gérer la perte de vie du joueur ici
                    resetApresMort();
                }
                
            }
        }
    }

