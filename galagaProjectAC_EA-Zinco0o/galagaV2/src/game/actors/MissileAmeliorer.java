package game.actors;

import engine.StdDraw;

public class MissileAmeliorer extends Projectile {
    private boolean estExplose = false;
    private final double SEUIL_Y = 0.2; // Seuil pour l'explosion
    private long tempsExplosion = 0; //durer de l'affichage de l'explosion.
    public MissileAmeliorer(double x, double y, boolean lanceParJoueur){
        super(x, y, lanceParJoueur);
    }

    @Override
    public void update() {
        if (!estExplose) {
            setY(getY()-0.008); // Vitesse de descente

            // Condition d'explosion au seuil
            if (getY() <= SEUIL_Y) {
                this.estExplose = true;
                this.tempsExplosion = System.currentTimeMillis();
            }
        }

    }
        @Override
        public void draw() {
        if (!estExplose) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.filledSquare(getX(), getY(), 0.01); // Forme différente pour le Boss
        } else {
            // Dessin de l'explosion circulaire
            StdDraw.setPenColor(StdDraw.ORANGE);
            StdDraw.filledCircle(getX(), getY(), 0.06); 
            StdDraw.setPenColor(StdDraw.YELLOW);
            StdDraw.circle(getX(), getY(), 0.08);
        }
    }

    public boolean isEstExplose() {
        return estExplose;
    }

    public boolean doitEtreSupprime() {
        // Supprimer s'il sort de l'écran OU s'il a fini son explosion (0.5 seconde)
        if (estExplose && (System.currentTimeMillis() - tempsExplosion > 500)) return true;
        return false;
    }
    }




