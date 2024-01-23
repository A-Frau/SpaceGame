package main;

import javafx.scene.image.Image;

public class Rocket {
    int posX, posY, size;
    boolean exploding, destroyed;
    Image img;
    int explosionsStep = 0;

    public Rocket(int posX, int posY, int size, Image img) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.img = img;
    }
}
