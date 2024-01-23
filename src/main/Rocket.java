package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Rocket implements Entity {
    private final SpaceInvaders spaceInvaders;
    int posX, posY, size;
    boolean exploding, destroyed;
    Image img;
    int explosionsStep = 0;

    public Rocket(SpaceInvaders spaceInvaders, int posX, int posY, int size, Image img) {
        this.spaceInvaders = spaceInvaders;
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.img = img;
    }

    public Shot shoot() {
        return new Shot(spaceInvaders,posX + size / 2 - Shot.size / 2, posY - Shot.size);
    }

    public void update() {

        if (exploding) explosionsStep++;
        destroyed = explosionsStep > SpaceInvaders.EXPLOSION_STEPS;
    }

    public void draw(GraphicsContext gc) {
        if (exploding) {
            gc.drawImage(SpaceInvaders.EXPLOSION_IMG, explosionsStep % SpaceInvaders.EXPLOSION_COL * SpaceInvaders.EXPLOSION_W,
                    ((double) explosionsStep / SpaceInvaders.EXPLOSION_ROWS) * SpaceInvaders.EXPLOSION_H + 1, SpaceInvaders.EXPLOSION_W,
                    SpaceInvaders.EXPLOSION_H, posX, posY, size, size);
        } else {
            gc.drawImage(img, posX, posY, size, size);
        }
    }

    public boolean collide(Rocket other) {
        int d = distance(this.posX + size / 2, this.posY + size / 2,
                other.posX + size / 2, other.posY + size / 2);
        return d < other.size / 2 + this.size / 2;
    }

    public void explode() {
        exploding = true;
        explosionsStep = -1;
    }


}
