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
}
