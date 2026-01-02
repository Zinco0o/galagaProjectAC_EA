package game.actors;

import engine.StdDraw;

public class Moth extends Enemy {

    public Moth(Double x, Double y, Double length, int valeur , double speed) {
        super(x, y, length, valeur, speed);
    }

    @Override
    public void draw() {
        StdDraw.picture(this.x, this.y, "ressources/sprites/catcher.png", length, length);
    }
}
