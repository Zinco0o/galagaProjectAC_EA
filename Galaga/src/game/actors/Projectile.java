package game.actors;

import engine.StdDraw;

public class Projectile {
    private double x; // postion du projectile sur l'axe des absices 
    private double y; // position du projectile sur l'axe des ordonnées
    
    public Projectile(double x, double y){
        this.x = x;
        this.y = y;
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

    public void draw(){
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledSquare(x, y, 0.01);
    }

    public Boolean out_range(){
        return getY()>1;
    }

    public void update(){
        this.y +=0.01;
        
    }

    
}
