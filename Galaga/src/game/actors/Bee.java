package game.actors;

import engine.StdDraw;

public class Bee extends Enemy {

    public Bee(Double x, Double y, Double length) {
        super(x, y, length);

        // TODO Auto-generated constructor stub
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledSquare(x, y, length / 2);
    }
}
