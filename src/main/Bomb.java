package main;

import javafx.scene.image.Image;

public class Bomb extends Rocket {
    int speed;

    public Bomb(SpaceInvaders spaceInvaders,int posX, int posY, int size, Image img) {
        super(spaceInvaders, posX, posY, size, img);
        this.speed = (spaceInvaders.getScore() / 5) + 2;
    }

    public void update() {
        if (!exploding && !destroyed) posY += speed;
        if (posY > SpaceInvaders.HEIGHT) destroyed = true;
    }


}
