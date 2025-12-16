package game.actors;

import engine.StdDraw;

abstract class Enemy extends Entity {

    // Projectile tir

    public Enemy(Double x, Double y, double length) {
        super(0, 0, 0);
    }

    abstract void draw();
}
