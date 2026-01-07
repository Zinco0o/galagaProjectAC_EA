package game.actors;

import engine.StdDraw;

public class Butterfly extends Enemy {


    public Butterfly(Double x, Double y, Double length, int valeur, double speed) {
        super(x, y, length, valeur, speed);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void draw() {
        StdDraw.picture(this.x, this.y, "ressources/sprites/butterfly.png", length, length);
    }

    @Override
    public void attaqueMouvement(Player player){
        this.y -= this.speed;
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
