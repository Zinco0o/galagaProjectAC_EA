package game.actors;

import java.util.ArrayList;

public abstract class Enemy extends Entity {
    protected double Xbase;
    protected double Ybase;
    protected int valeur;
    protected double speed;
    protected Projectile projectile;
    protected Boolean bottom;
    protected Boolean tire;
    protected boolean EnAttaque;

    // Projectile tir

    public Enemy(Double x, Double y, double length, int valeur, double speed) {
        super(x, y, length);
        this.valeur = valeur;
        this.Xbase = x;
        this.Ybase = y;
        this.life = 1;
        this.speed = speed;
        this.tire = false;
    }

    public abstract void draw();

   public boolean isDead() {
        return this.life <= 0;
        
    }

    public void estEnBas(ArrayList<Enemy> enemies){
        for(Enemy enemy : enemies){
            if(enemy==this){
                continue;
            }
            if(getX() == enemy.getX()){
                if(getY() < enemy.getY()){
                    this.bottom = true;
                }
                else{
                    this.bottom = false;
                }
            }
        }
    }

    public abstract void attaqueMouvement(Player player);

   
    public void  update(ArrayList<Enemy> enemies){
        estEnBas(enemies);
      
        
    }

    public int getValeur() {
        return valeur;
    }

    public double getSpeed() {
        return speed;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public double getXBase() {
        return Xbase;
    }

    public boolean GetTire() {
        return tire;
    }
    public Boolean IsBottom(){
        return bottom;
    }

    public boolean isEnAttaque() {
        return EnAttaque;
    }

    public void setEnAttaque(boolean EnAttaque) {
        this.EnAttaque = EnAttaque;
    }

    public double getYBase() {
        return Ybase;
    }
    
}
