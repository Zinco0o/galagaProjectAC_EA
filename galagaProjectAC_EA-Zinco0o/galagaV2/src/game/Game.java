package game;

import engine.StdDraw;
import game.actors.Enemy;
import game.actors.Formation;
import game.actors.Moth;
import game.actors.Player;
import game.actors.Projectile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
;



/**
 * Classe du jeu principal.
 * Gère la création de l'espace de jeu et la boucle de jeu en temps réel.
 */
public class Game {
    public Player player; // Jouer, seul éléments actuellement dans notre jeu
    public Formation formations;
    public int score ;
    private int etat = 0; // 0: Titre Niveau, 1: En jeu, 2: Mort/Reset
    private long chronoEtape = 0;
    private int niveauActuel = 1;
    private int highScore;




    /**
     * Créé un jeu avec tous les éléments qui le composent
     */
    public Game() {
        player = new Player(0.5, 0.15, 0.05);
        this.formations = new Formation();
        this.score = 0;
        this.formations.loadLevel("ressources/levels/Level1.lvl");
        
    }

    public void lectureHighScore(){
        File fichier = new File("ressources/highscore/highscore.sc");
        try {
        if (!fichier.exists()) return ;
        
        Scanner scanner = new Scanner(fichier);
        if (scanner.hasNextInt()) {
            this.highScore = scanner.nextInt();
        }
        
        scanner.close();
    }catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    }

    public void ecrireHighScore() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("ressources/highscore/highscore.sc"));
            writer.print(this.highScore);
            writer.close();
    }   catch (IOException e) {
            System.out.println("Erreur lors de l'enregistrement du score");
    }
}


    public void DrawNiveau(){
        if(this.niveauActuel==3){
            StdDraw.picture(0.95, 0.05, "ressources/sprites/level.png", 0.05,0.07);
            StdDraw.picture(0.9, 0.05, "ressources/sprites/level.png", 0.05,0.07);
            StdDraw.picture(0.85, 0.05, "ressources/sprites/level.png", 0.05,0.07);
        }
        else if(this.niveauActuel==2){
            StdDraw.picture(0.95, 0.05, "ressources/sprites/level.png", 0.05,0.07);
            StdDraw.picture(0.9, 0.05, "ressources/sprites/level.png", 0.05,0.07);
        }
        else if(this.niveauActuel==1){
            StdDraw.picture(0.95, 0.05, "ressources/sprites/level.png", 0.05,0.07);
        }
    }

    public void resetApresMort(){
        //reset les liste des projectiles
        player.getLstProj().clear();
        formations.getLstProj().clear();
        //remet les ennemis en attaque dans la formation principale
        for(int j = formations.getEnemiesAtt().size() -1; j>=0; j--){
            Enemy e = formations.getEnemiesAtt().get(j);
            e.setEnAttaque(false);
            e.setY(e.getYBase());
            e.setX(e.getXBase());
            formations.getEnemies().add(e);
            formations.getEnemiesAtt().remove(e);
        }
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
        if (this.etat == 0) {
            // Écran de titre du niveau
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(0.5, 0.5, "Niveau : " + formations.levelName);
        }
        else if (this.etat == 1) {
            //Interface graphique
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.line(0,0.9,1,0.9 );
            StdDraw.line(0,player.getY()-0.06,1,player.getY()-0.06 );
            player.DrawVie();
            DrawNiveau();

            // En jeu
            player.draw();
            player.ProjDraw();
            formations.FormationDraw();
            formations.ProjDraw();
            formations.AttaquantDraw();
            formations.DrawVieVolee();
            //Affichage du score
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(0.1, 0.95, "Score: " + score);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.text(0.9, 0.95, "Highscore: " + this.highScore);
            
        }
        else if(this.etat == 3){
            // Jeu gagné ou Mort définitive
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(0.5, 0.6, "Jeu Terminé !");
            StdDraw.text(0.5, 0.5, "Score final : " + score);
            StdDraw.text(0.5, 0.4, "Appuyez sur Espace pour rejouer");
        }
    }
        
        
        
    

    /**
     * Met a jour les attributs de tous les éléments du jeu
     */
    private void update() {
        long maintenant = System.currentTimeMillis();
        if (etat == 0) { // Écran de titre du niveau
            if (chronoEtape == 0) chronoEtape = maintenant;
            if (maintenant - chronoEtape > 2000) { // Attendre 2 secondes
                lectureHighScore();
                resetApresMort();
                chronoEtape = 0;
                etat = 1;
                
            }
        }
        else if (etat == 1) { // En jeu
            
        
            player.update();
            for (Enemy enemy : formations.getEnemies()) {//met a jour chaque ennemi pour savoir si il est en bas
                enemy.update(formations.getEnemies());
           
            }
            formations.update(player);
            if (player.getSizeLstProj()>0){ //gère les projectiles du joueur
                for(int i=0; i<player.getSizeLstProj(); i++){
                    player.getLstProj().get(i).update(); 
                    player.projectileSupr(i);
                }
            
            }
            for (int i = player.getSizeLstProj() - 1; i >= 0; i--) {
                Projectile p = player.getLstProj().get(i);
    
                // Pour chaque projectile, on teste tous les ennemis de la formation
                for (int j = formations.getEnemies().size() - 1; j >= 0; j--) {
                    Enemy e = formations.getEnemies().get(j);
        
                    if (e.collision(p)) { //collision entre projectile joueur et ennemi
                        // COLLISION DÉTECTÉE !
                        e.perdreVie();
                        if(e instanceof Moth){
                            Moth m = (Moth) e;
                            if(m.getVieVolee() ==1){
                                player.gagneVie();
                            }
                        }
                        if(e.getLife()==0){
                            formations.getEnemies().remove(j); // L'ennemi meurt
                            // Ajouter des points au score ici
                            score += e.getValeur();
                        }
                              
                        player.getLstProj().remove(i);  // Le projectile disparaît
                        
                        
                        break; // On arrête de tester ce projectile puisqu'il a disparu
            }
        }
                for (int j = formations.getEnemiesAtt().size() - 1; j >= 0; j--) {
                    Enemy e = formations.getEnemiesAtt().get(j);
        
                    if (e.collision(p)) { //collision entre projectile joueur et ennemi
                        // COLLISION DÉTECTÉE !
                        formations.getEnemiesAtt().remove(j);      // L'ennemi meurt
                        player.getLstProj().remove(i);  // Le projectile disparaît
                        // Ajouter des points au score ici
                        score += e.getValeur();
                        break; // On arrête de tester ce projectile puisqu'il a disparu
            }
        }
        
    }
                for(int j = formations.getLstProj().size() - 1; j >= 0; j--) {//collisions projectiles ennemis joueur
                    Projectile p = formations.getLstProj().get(j);
                    if(player.collision(p)){
                        // COLLISION DÉTECTÉE !
                        player.perteVie();      // Le joueur perd une vie
                        etat = 2; // Le joueur est mort
                    }
                
                }
                 for(int j = formations.getEnemiesAtt().size() - 1; j >= 0; j--) {//collisions ennemis en attaque joueur
                    Enemy e = formations.getEnemiesAtt().get(j);
                    if(player.collision(e)){
                        // COLLISION DÉTECTÉE !
                        player.perteVie();      // Le joueur perd une vie
                        etat = 2; // Le joueur est mort
                    }
                
                }


                if(formations.getEnemies().isEmpty()){
                    //changer de niveau
                    niveauActuel +=1;
                    if(niveauActuel ==2){
                        formations.loadLevel("ressources/levels/Level2.lvl");
                    }
                    if(niveauActuel ==3){
                        formations.loadLevel("ressources/levels/Level3.lvl");
                    }
                    
                    etat = 0;
            }
        }
        else if (etat == 2) { // Mort / Reset
            if(player.estMort()) etat = 3; // Partie finie
            
            if (chronoEtape == 0) chronoEtape = maintenant;
            if (maintenant - chronoEtape > 3000) { // Attendre 3 secondes
                resetApresMort();
                etat = 1;
                chronoEtape = 0;
            }
    }

    else if(etat ==3){
        // Jeu gagné ou Mort définitive
        if(score > highScore){
            highScore = score;
            ecrireHighScore();
        }
        // Si Espace est préssé
        if (StdDraw.isKeyPressed(32)) {
            // Reset du jeu
            player = new Player(0.5, 0.25, 0.05);
            this.formations = new Formation();
            this.score = 0;
            this.formations.loadLevel("ressources/levels/Level1.lvl");
            etat = 0;
        }
    }
}
}

