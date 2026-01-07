package game.actors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import game.actors.MissileAmeliorer;


import engine.StdDraw;
import game.Game;


public class Formation {
    public ArrayList<Enemy> enemies = new ArrayList<Enemy>(); // Liste des ennemis dans la formation
    public ArrayList<Enemy> enemiesAtt = new ArrayList<Enemy>(); // Liste des ennemis en attaque
    public double enemySpeed; // Vitesse de déplacement de l'enemies
    public double formationSpeed; // Vitesse de déplacement de la formation

    public String levelName; // Nom du niveau chargé
    public double cooldownAtt; //Le cooldown entre chaque attaque, si -1 pas d'attaque
    public int attaqueMouvement;
    protected ArrayList<Projectile> LstProj = new ArrayList<>(); // Liste des projectiles tirés par la formation
    protected  long dernierTirTemps = 0;   // Stocke l'heure du dernier tir (en ms)
    protected  long dernierAttTemps = 0;   // Stocke l'heure de la dernière attaque (en ms)
    protected int cooldownTir;  // Le cooldown entre les tirs des ennemis (en ms)


    private double offsetX = 0; // Décalage horizontal appliqué à toute la formation
    private double direction = 1; // 1 = droite, -1 = gauche
    private double limiteSiderale = 0.08; // Limite de déplacement avant de changer de direction

    public void instantiateEnemy(String type, double x, double y, double length, int score, double speed) {
        if(type.equals("Moth")){
            Moth moth = new Moth(x, y, length, score, speed);
            enemies.add(moth);
        }
        else if(type.equals("Butterfly")){
            Butterfly butterfly = new Butterfly(x, y, length, score, speed);
            enemies.add(butterfly);
        }
        else if(type.equals("Boss")){
            Boss boss = new Boss(x, y, length, score, speed);
            enemies.add(boss);
        }
        else{
            Bee bee = new Bee(x, y, length, score, speed);
            enemies.add(bee);
        }
    }

    public void loadLevel(String path) {
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            // Ignorer la première ligne (Header du niveau)

            if (scanner.hasNextLine()){
                String firstLine = scanner.nextLine();
            String[] headerParts = firstLine.split("\\s+");

            // Extraction des infos (ajuste les index selon tes besoins)
            this.levelName = headerParts[0];             // "Level1"
            this.formationSpeed = Double.parseDouble(headerParts[1]); // 0.001
            this.cooldownAtt = Integer.parseInt(headerParts[2]);       // -1
            this.cooldownTir = Integer.parseInt(headerParts[3]);
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;
                // Séparer les données par les espaces
                String[] parts = line.split("\\s+");

                String type = parts[0];           // ex: Moth, Butterfly
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double length = Double.parseDouble(parts[3]);
                int score = Integer.parseInt(parts[4]);
                double speed = Double.parseDouble(parts[5]);
                instantiateEnemy(type, x, y, length, score, speed);

            
            }
            scanner.close();
            } catch (FileNotFoundException e) {
            System.out.println("Erreur : Fichier de niveau introuvable.");
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public String getLevelName() {
        return levelName;
    }

    public double getCooldownAtt() {
        return cooldownAtt;
    }

    public int getAttaqueMouvement() {
        return attaqueMouvement;
    }

    public double getCooldownTir() {
        return cooldownTir;
    }
    public ArrayList<Projectile> getLstProj(){
        return LstProj;
    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }


    public void FormationDraw(){
        for(Enemy e : enemies){
            if (e instanceof Boss) {
                ((Boss) e).drawLife();
                e.draw();
                
            }else{
                e.draw();
            }

            
        }
    }

      public void AttaquantDraw(){
        for(Enemy e : enemiesAtt){
            e.draw();
        }
    }

    public void DrawVieVolee(){
        for(Enemy e : enemiesAtt){
            if(e instanceof Moth){
                Moth m = (Moth) e;
                if(m.getVieVolee() ==1){
                    StdDraw.picture(0.95, 0.95, "ressources/sprites/Ship.png", 0.05,0.05);
                }
        }
    }
    for(Enemy e : enemies){
            if(e instanceof Moth){
                Moth m = (Moth) e;
                if(m.getVieVolee() ==1){
                    StdDraw.picture(0.95, 0.95, "ressources/sprites/Ship.png", 0.05,0.05);
                }
        }
    }
}


    public void gererTirGroupe() {
        long tempsActuel = System.currentTimeMillis();

        // On vérifie si 4000ms se sont écoulées depuis le dernier tir
        if (tempsActuel - dernierTirTemps >= cooldownTir) {
            
            // On cherche un ennemi en bas pour tirer
            for (Enemy e : enemies) {
                if (e.IsBottom() && Math.random() < 0.1) { // Si estEnBas() a dit OK
                    
                    Projectile m;
                    // Vérification si l'ennemi est le Boss pour changer le type de missile
                    if (e instanceof Boss) {
                        // Création du missile qui explosera au seuil Y (ex: 0.4)
                        m = new MissileAmeliorer(e.getX(), e.getY(), false); 
                    } else {
                        // Tir classique pour les Bee, Butterfly et Moth
                        m = new Projectile(e.getX(), e.getY(), false);
                    }
                    
                    
                    LstProj.add(m);
                    
                    // On enregistre l'heure de ce tir pour relancer le cooldown
                    dernierTirTemps = tempsActuel;
                    
                    // On arrête la boucle pour qu'un seul ennemi tire
                    return; 
                }
            }
        }
    }


    public void updateDeplacement() { // Aider pas IA
       // 1. Faire progresser le décalage
        offsetX += this.formationSpeed * direction;

        // 2. Inverser la direction si on atteint les bords
        if (Math.abs(offsetX) > limiteSiderale) {
            direction *= -1;
            
        }

        // 3. Appliquer la nouvelle position à chaque ennemi
        for (Enemy e : enemies) {
            
            // On met à jour la position réelle de l'ennemi pour les collisions et le tir
            // xBase est la coordonnée lue dans le fichier .lvl
            e.setX(e.getXBase() + offsetX);
        }
    }

    public void updateTirProjectiles() {
        // Met à jour la position de chaque projectile ennemi
        for (int i = LstProj.size() - 1; i >= 0; i--) {
            Projectile p = LstProj.get(i);
            p.update();

            if (p instanceof MissileAmeliorer) {
                if (((MissileAmeliorer) p).doitEtreSupprime()) {
                    LstProj.remove(i);
                    continue; 
            }
        }
            // Supprime le projectile s'il est hors de l'écran
            if (p.out_range()) {
                LstProj.remove(i);
            }
        }
    }
    public void ProjDraw(){
        if(!LstProj.isEmpty()){
            for(int i = 0; i<LstProj.size();i++){
                LstProj.get(i).draw();
            }
        }
    }

    public void addEnemyAtt(Enemy e){
        enemiesAtt.add(e);
    }
    public void removeEnemyAtt(Enemy e){
        enemiesAtt.remove(e);
    }

    public void gererAttaqueEnnemis(){
        if(cooldownAtt != -1){

            long tempsActuel = System.currentTimeMillis();

            // On vérifie si le cooldown s'est écoulé depuis la dernière attaque
            if (tempsActuel - dernierAttTemps >= cooldownAtt) {
                for (Enemy e : enemies) {
                    if (e.IsBottom() && Math.random() < 0.05 && e.isEnAttaque() == false && !(e instanceof Boss)) { // Si estEnBas() a dit OK
                        e.setEnAttaque(true);
                        enemiesAtt.add(e);
                        enemies.remove(e);
                        // On enregistre l'heure de cette attaque pour relancer le cooldown
                        dernierAttTemps = tempsActuel;
                        
                        // On arrête la boucle pour qu'un seul ennemi attaque
                        return;
                    }

                }
            }

        }
    }

    public void gererFinAttaque(){
        for(int i = enemiesAtt.size() -1; i>=0; i--){
            if(!enemiesAtt.get(i).isEnAttaque()){
                enemies.add(enemiesAtt.get(i));
                enemiesAtt.remove(i);
            }
        }
    }

    public void updateDeplacementAttaque(Player player){
        for(Enemy e : enemiesAtt){
            e.attaqueMouvement(player);
        }
    }


    public void update(Player player){
        updateDeplacement();
        gererTirGroupe();
        updateTirProjectiles();
        updateDeplacementAttaque(player);
        gererAttaqueEnnemis();
        gererFinAttaque();
    }

    public ArrayList<Enemy> getEnemiesAtt() {
        return enemiesAtt;
    }
}
    



