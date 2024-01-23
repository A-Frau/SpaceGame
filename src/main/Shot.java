package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Shot implements Entity {

    static final int size = 6;
    public boolean toRemove;
    int posX, posY, speed = 10;
    private final SpaceInvaders spaceInvaders;

    public Shot(SpaceInvaders spaceInvaders,int posX, int posY) {
        this.spaceInvaders = spaceInvaders;
        this.posX = posX;
        this.posY = posY;
    }

    public void update() {
        posY -= speed;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        if (spaceInvaders.getScore() >= 50 && spaceInvaders.getScore() <= 70 || spaceInvaders.getScore() >= 120) {
            gc.setFill(Color.YELLOWGREEN);
            speed = 50;
            gc.fillRect(posX - 5, posY, size + 10, size + 30);
        } else {
            gc.fillOval(posX, posY, size, size);
        }
    }

    public boolean collide(Rocket rocket) {
        int d = distance(this.posX + size / 2, this.posY + size / 2,
                rocket.posX + size / 2, rocket.posY + size / 2);
        return d < rocket.size / 2 + size / 2;
    }
}
