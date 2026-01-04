package game.actors;

import engine.StdDraw;

public class Bee extends Enemy {

    public Bee(Double x, Double y, Double length, int valeur , double speed) {
        super(x, y, length , valeur, speed);

        // TODO Auto-generated constructor stub
    }
    @Override
    public void draw() {
        StdDraw.picture(this.x, this.y, "ressources/sprites/bee.png", length, length);
    }

    @Override
    public void attaqueMouvement(Player player){
        this.y -= this.speed;
        if(this.x<0.05 || Math.random()<=0.5){
            this.x = getX() + Math.sin(this.y * 0.5) * 0.1;
        }
        if(this.x>0.95 || Math.random()>0.5){
            this.x = getX() + Math.sin(this.y * -0.5) * 0.1;
        }
        if(this.y < -0.05){
            this.y = 1.05;
            this.EnAttaque = false;
            

        }
        if(!this.EnAttaque){
            this.x = Xbase;
            this.y = Ybase;
        }
    }
}
