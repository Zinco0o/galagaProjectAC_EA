package game.actors;

import engine.StdDraw;

public class Butterfly extends Enemy {

    public Butterfly(Double x, Double y, Double length) {
        super(x, y, length);
        // TODO Auto-generated constructor stub
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.filledSquare(x, y, length / 2);
    }
}
