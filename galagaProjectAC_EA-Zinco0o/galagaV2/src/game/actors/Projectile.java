package game.actors;

import engine.StdDraw;

public class Projectile {
    private double x; // postion du projectile sur l'axe des absices 
    private double y; // position du projectile sur l'axe des ordonnées
    private boolean lanceParJoueur; // true si c'est le joueur, false si c'est un ennemi

    public Projectile(double x, double y, boolean lanceParJoueur){
        this.x = x;
        this.y = y;
        this.lanceParJoueur = lanceParJoueur;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    public double getLength(){
        return 0.01;
    }

    public void draw(){
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledSquare(x, y, 0.005);
    }

    public Boolean out_range(){
        return getY()>1|| getY()<0;
    }

    public void update(){
        if(lanceParJoueur){
            this.y +=0.01;
        }
        else{
            this.y -=0.01;
        }
    }

    
}
