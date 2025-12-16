package game.actors;

abstract class Entity {
    protected double x; // postion du joueur sur l'axe des abscisses
    protected double y; // position du joueur sur l'axe des ordonnées
    protected double length; // largeur du joueur
    

    public Entity(double x, double y, double length) {
        this.x = x;
        this.y = y;
        this.length = length;
    }
}
