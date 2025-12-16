package game.actors;

import engine.StdDraw;

public class Moth extends Enemy {

    public Moth(Double x, Double y, Double lenght) {
        super(x, y, lenght);
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.PINK);
        StdDraw.filledSquare(this.x, this.y, this.length / 2);
    }
}
