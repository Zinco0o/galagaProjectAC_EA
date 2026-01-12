package game.actors;

import engine.StdDraw;

public class Moth extends Enemy {
    private long chronoEtape = 0;
    private int vieVolee;

    public int getVieVolee() {
        return vieVolee;
    }

    public void setVieVolee(int vieVolee) {
        this.vieVolee = vieVolee;
    }

    public Moth(Double x, Double y, Double length, int valeur , double speed) {
        super(x, y, length, valeur, speed);
        vieVolee = 0;
    }

    @Override
    public void draw() {
        StdDraw.picture(this.x, this.y, "ressources/sprites/catcher.png", length, length);
    }
    @Override
    public void attaqueMouvement(Player player){
        long maintenant = System.currentTimeMillis();
        if (!detecterCapture(player)) {
            this.y -= this.speed;
        }
        

        if(detecterCapture(player)){
            double captureY = player.getY();
            if (chronoEtape == 0) chronoEtape = maintenant;
            if (maintenant - chronoEtape > 1000) { // Attendre 3 secondes
                this.y = 1.05;
                this.EnAttaque = false;
                player.perteVie();
                setVieVolee(1);
        }
            System.out.println("Capture !");
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.002);
            StdDraw.circle(this.x, this.y, 0.05);
            

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




    public Boolean detecterCapture(Player player){
        double rayonCapture = 0.05; 
        double rayonJoueur = 0.05;
        double distanceSeuil = rayonCapture + rayonJoueur;

        double dx = this.x - player.getX();
        double dy = this.y - player.getY();

        return (dx * dx + dy * dy) < (distanceSeuil * distanceSeuil);
    }
    
}
