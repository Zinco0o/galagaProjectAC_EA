package game.actors;

import engine.StdDraw;

public class Boss extends Enemy {
    private long dernierTir = 0;
    private final long DELAI_TIR = 2000; // Un missile toutes les 2 secondes
    public Boss(Double x, Double y, Double length, int valeur, double speed){
        super(x, y, length , valeur, speed);
        this.life = 10;
    }

    @Override
    public void draw(){
        StdDraw.picture(this.x, this.y, "ressources/sprites/Boss.png", length, length);
    }

    @Override
    public void attaqueMouvement(Player player){}

    public void drawLife(){
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(0.9, 0.1, ""+getLife());
    }

}
