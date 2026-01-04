package game.actors;

public abstract class Entity {
    protected double x; // postion du joueur sur l'axe des abscisses
    protected double y; // position du joueur sur l'axe des ordonnées
    protected double length; // largeur du joueur
    protected int life; //Nombre de vie
    

    public Entity(double x, double y, double length) {
        this.x = x;
        this.y = y;
        this.length = length;
    }
     public Boolean collision(Projectile p){
        double dx = this.x - p.getX();
        double dy = this.y - p.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (this.length + p.getLength()) / 2;
        
    }
      public Boolean collision(Enemy p){
        double dx = this.x - p.getX();
        double dy = this.y - p.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (this.length + p.getLength()) / 2;
        
    }

     public double getX() {
        return x;
     }
     public void setX(double x) {
        this.x = x;
     }
     public double getY() {
        return y;
     }
     public void setY(double y) {
        this.y = y;
     }
     public double getLength() {
        return length;
     }
     public void setLength(double length) {
        this.length = length;
     }
     public int getLife() {
        return life;
     }
     public void setLife(int life) {
        this.life = life;
     }
}
