package game.actors;

import engine.StdDraw;
import java.util.ArrayList;

/**
 * Classe représentant le jouuer.
 * A ce stade cen'est qu'un point rouge qui se déplace avec les flèches du
 * clavier.
 */
public class Player extends Entity{
    protected ArrayList<Projectile> LstProj;

    /**
     * Créé un joueur.
     * 
     * @param x      postion du joueur sur l'axe des abscisses
     * @param y      position du joueur sur l'axe des ordonnées
     * @param length largeur du joueur
     */
    public Player(double x, double y, double length) {
        super(x, y, length);
        LstProj= new ArrayList<>();
        this.life = 3;
    }

    /**
     * Dessine le joueur, ici c'est un rond rouge
     */
    public void draw() {
        StdDraw.picture(this.x, this.y, "ressources/sprites/Ship.png", 0.05,0.05);
    }

    public ArrayList<Projectile> getLstProj(){
        return LstProj;
    }
    public int getSizeLstProj(){
        return LstProj.size();
    }

    public void addProj(){
        Projectile proj = new Projectile(this.x, this.y);
        int tmp= LstProj.size();
        if(tmp<3){
            LstProj.add(proj);
        }

    }

    public void projectileSupr(int i){
        if(LstProj.get(i).out_range()){
            LstProj.remove(i);
            
        }
        
    }



    public void ProjDraw(){
        if(getSizeLstProj()>0){
            for(int i = 0; i<getSizeLstProj();i++){
                getLstProj().get(i).draw();
            }
        }
    }

    

    /**
     * Met à jour la position du joueur en fonction des touches préssé.
     */
    public void update() {
        double speed = 0.01; // vitesse de déplacement du joueur
        // Si la flèche gauche est préssé
        if (StdDraw.isKeyPressed(37)) {
            if (x > 0){
                x -= speed;
            }
        }
        // Si la flèche droite est préssé
        if (StdDraw.isKeyPressed(39)) {
            if (x < 1){
                x += speed;
            }
            
        }
        // Si Espace est préssé
        if (StdDraw.isKeyPressed(32)) {
            addProj();
            StdDraw.pause(50);
            
            
        }
    }

    public static void main(String[] args) throws Exception {
        //COMPREHENSION DES FONCTION POUR LINKEDLIST
        Player test = new Player(1, 1, 1);
        test.addProj();
        test.addProj();
        test.addProj();
        test.addProj();
        test.addProj();

        

        System.out.println(test.getLstProj().size());
    }

}
