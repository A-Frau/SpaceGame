package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class SpaceInvaders extends Application {

    protected static final Random RAND = new Random();
    protected static final int WIDTH = 800;
    protected static final int HEIGHT = 600;
    protected static final int PLAYER_SIZE = 60;

    static final Image PLAYER_IMG = new Image("player.png");
    static final Image EXPLOSION_IMG =  new Image("explosion.png");

    static final int EXPLOSION_W = 128;
    static final int EXPLOSION_ROWS = 3;
    static final int EXPLOSION_COL = 3;
    static final int EXPLOSION_H = 128;
    static final int EXPLOSION_STEPS = 15;

    static final Image[] BOMBS_IMG = {
            new Image("1.png"),
            new Image("2.png"),
            new Image("3.png"),
            new Image("4.png"),
            new Image("5.png"),
            new Image("6.png"),
            new Image("7.png"),
            new Image("8.png"),
            new Image("9.png"),
            new Image("10.png"),
    };
    int score;
    final int MAX_BOMBS = 10;
    final int MAX_SHOTS = MAX_BOMBS * 2;
    boolean gameOver = false;
    Rocket player;
    List<Shot> shots;
    List<Universe> univ;
    List<Bomb> bombs;

    double mouseX;
    private GraphicsContext gc;

    public static void main(String[] args) {
        launch(args);
    }

    public int getScore() {
        return score;
    }

    public void start(Stage stage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        canvas.setCursor(Cursor.MOVE);
        canvas.setOnMouseMoved(e -> mouseX = e.getX());
        canvas.setOnMouseClicked(e -> {
            if (shots.size() < MAX_SHOTS) shots.add((player.shoot()));
            if (gameOver) {
                gameOver = false;
                setup();
            }
        });
        setup();
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setTitle("Space Invaders!");
        stage.show();

    }

    private void setup() {
        univ = new ArrayList<>();
        shots = new ArrayList<>();
        bombs = new ArrayList<>();
        player = new Rocket(this,WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG);
        score = 0;
        IntStream.range(0, MAX_BOMBS).mapToObj(i -> this.newBomb()).forEach(bombs::add);


    }

    private void run(GraphicsContext gc) {

        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(20));
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 60, 20);

        if (gameOver) {
            gc.setFont(Font.font(35));
            gc.setFill(Color.YELLOW);
            gc.fillText("Game Over \n Your Score is: " + score, WIDTH / 2.0, HEIGHT / 2.5);
        }
        univ.forEach(universe -> universe.draw(gc));

        player.update();
        player.draw(this.gc);
        player.posX = (int) mouseX;

        bombs.stream().peek(Rocket::update).peek(Rocket -> Rocket.draw(gc)).forEach(e -> {
            if (player.collide(e) && !player.exploding) {
                player.explode();
            }
        });

        for (int i = shots.size() - 1; i >= 0; i--) {
            Shot shot = shots.get(i);
            if (shot.posY <= 0 || shot.toRemove) {
                shots.remove(i);
                continue;
            }
            shot.update();
            shot.draw(gc);
            for (Bomb bomb : bombs) {
                if (shot.collide(bomb) && !bomb.exploding) {
                    score++;
                    bomb.explode();
                    shot.toRemove = true;
                }
            }
        }

        for (int i = bombs.size() - 1; i >= 0; i--) {
            if (bombs.get(i).destroyed) {
                bombs.set(i, newBomb());
            }
        }

        gameOver = player.destroyed;

        if (RAND.nextInt(10) > 2) {
            univ.add(new Universe());
        }

        for (int i = 0; i < univ.size(); i++) {
            if (univ.get(i).posY > HEIGHT) univ.remove(i);
        }


    }


    private Bomb newBomb() {
        return new Bomb(this,50 + SpaceInvaders.RAND.nextInt(SpaceInvaders.WIDTH - 100),
                0,
                SpaceInvaders.PLAYER_SIZE,
                SpaceInvaders.BOMBS_IMG[SpaceInvaders.RAND.nextInt(SpaceInvaders.BOMBS_IMG.length)]);
    }
}


