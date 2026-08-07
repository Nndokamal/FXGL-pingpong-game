package com.nando;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

public class PingpongApp extends GameApplication {

    Entity player;
    Entity ball;

    Entity[] border = new Entity[3];
    Entity[][] bricks = new Entity[5][10];

    boolean ballRun = false;
    boolean ballToRight = true;
    boolean ballToUp = false;

    int ballXVelocity;
    int ballYVelocity;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(640);
        settings.setHeight(360);
        settings.setTitle("ping pong");
        settings.setVersion("");
    }

    @Override
    protected void initGame() {

        player = FXGL.entityBuilder()
                .at(320 - 50, 340)
                .viewWithBBox(new Rectangle(100, 10))
                .with(new CollidableComponent(true))
                .type(AppType.PLAYER)
                .buildAndAttach();

        ball = FXGL.entityBuilder()
                .at(310, 315)
                .viewWithBBox(new Rectangle(20, 20))
                .with(new CollidableComponent(true))
                .type(AppType.BALL)
                .buildAndAttach();

        border[0] = FXGL.entityBuilder()
                .at(-1, 0)
                .viewWithBBox(new Rectangle(1, 360))
                .with(new CollidableComponent(true))
                .type(AppType.WALL)
                .buildAndAttach();

        border[1] = FXGL.entityBuilder()
                .at(0, -1)
                .viewWithBBox(new Rectangle(640, 1))
                .with(new CollidableComponent(true))
                .type(AppType.WALL)
                .buildAndAttach();

        border[2] = FXGL.entityBuilder()
                .at(640, 0)
                .viewWithBBox(new Rectangle(1, 360))
                .with(new CollidableComponent(true))
                .type(AppType.WALL)
                .buildAndAttach();

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 10; col++) {
                bricks[row][col] = FXGL.entityBuilder()
                        .at(10 + col * (50 + 2), 30 + row * (20 + 2))
                        .viewWithBBox(new Rectangle(50, 20))
                        .with(new CollidableComponent(true))
                        .type(AppType.BRICK)
                        .buildAndAttach();
            }
        }
    }

    @Override
    protected void initInput() {

        FXGL.getInput().addAction(new UserAction("kanan") {
            @Override
            protected void onAction() {
                if (!ballRun) {
                    ball.translateX(5);
                }
                player.translateX(5);
            }
        }, KeyCode.RIGHT);

        FXGL.getInput().addAction(new UserAction("kiri") {
            @Override
            protected void onAction() {
                if (!ballRun) {
                    ball.translateX(-5);
                }
                player.translateX(-5);
            }
        }, KeyCode.LEFT);

        FXGL.getInput().addAction(new UserAction("ballrun") {
            @Override
            protected void onAction() {

                int xCenterBall = (int) ball.getCenter().getX();
                int xCenterPlayer = (int) player.getCenter().getX();

                if (xCenterBall < (xCenterPlayer - 50)) {
                    ballXVelocity = 360;
                    ballYVelocity = 360;
                    ballToRight = false;
                } else if (xCenterBall < (xCenterPlayer - 40)) {
                    ballXVelocity = 300;
                    ballYVelocity = 360;
                    ballToRight = false;
                } else if (xCenterBall < (xCenterPlayer - 30)) {
                    ballXVelocity = 240;
                    ballYVelocity = 360;
                    ballToRight = false;
                } else if (xCenterBall < (xCenterPlayer - 20)) {
                    ballXVelocity = 180;
                    ballYVelocity = 360;
                    ballToRight = false;
                } else if (xCenterBall < (xCenterPlayer - 10)) {
                    ballXVelocity = 120;
                    ballYVelocity = 360;
                    ballToRight = false;
                } else if (xCenterBall < xCenterPlayer) {
                    ballXVelocity = 60;
                    ballYVelocity = 360;
                    ballToRight = false;
                } else if (xCenterBall > (xCenterPlayer + 50)) {
                    ballXVelocity = 360;
                    ballYVelocity = 360;
                    ballToRight = true;
                } else if (xCenterBall > (xCenterPlayer + 40)) {
                    ballXVelocity = 300;
                    ballYVelocity = 360;
                    ballToRight = true;
                } else if (xCenterBall > (xCenterPlayer + 30)) {
                    ballXVelocity = 240;
                    ballYVelocity = 360;
                    ballToRight = true;
                } else if (xCenterBall > (xCenterPlayer + 20)) {
                    ballXVelocity = 180;
                    ballYVelocity = 360;
                    ballToRight = true;
                } else if (xCenterBall > (xCenterPlayer + 10)) {
                    ballXVelocity = 120;
                    ballYVelocity = 360;
                    ballToRight = true;
                } else if (xCenterBall > xCenterPlayer) {
                    ballXVelocity = 60;
                    ballYVelocity = 360;
                    ballToRight = true;
                }

                ballToUp = true;
                ballRun = true;
            }
        }, KeyCode.SPACE);
    }

    @Override
    protected void initPhysics() {

        // PLAYER <-> BALL
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(AppType.PLAYER, AppType.BALL) {

            @Override
            protected void onCollision(Entity a, Entity b) {

                int xCenterBall = (int) ball.getCenter().getX();
                int xCenterPlayer = (int) player.getCenter().getX();

                if (xCenterBall < (xCenterPlayer - 50)) {
                    ballXVelocity = 360;
                    ballToRight = false;
                    ballToUp = true;
                } else if (xCenterBall < (xCenterPlayer - 40)) {
                    ballXVelocity = 300;
                    ballToRight = false;
                    ballToUp = true;
                } else if (xCenterBall < (xCenterPlayer - 30)) {
                    ballXVelocity = 240;
                    ballToRight = false;
                    ballToUp = true;
                } else if (xCenterBall < (xCenterPlayer - 20)) {
                    ballXVelocity = 180;
                    ballToRight = false;
                    ballToUp = true;
                } else if (xCenterBall < (xCenterPlayer - 10)) {
                    ballXVelocity = 120;
                    ballToRight = false;
                    ballToUp = true;
                } else if (xCenterBall < xCenterPlayer) {
                    ballXVelocity = 60;
                    ballToRight = false;
                    ballToUp = true;
                } else if (xCenterBall > (xCenterPlayer + 50)) {
                    ballXVelocity = 360;
                    ballToRight = true;
                    ballToUp = true;
                } else if (xCenterBall > (xCenterPlayer + 40)) {
                    ballXVelocity = 300;
                    ballToRight = true;
                    ballToUp = true;
                } else if (xCenterBall > (xCenterPlayer + 30)) {
                    ballXVelocity = 240;
                    ballToRight = true;
                    ballToUp = true;
                } else if (xCenterBall > (xCenterPlayer + 20)) {
                    ballXVelocity = 180;
                    ballToRight = true;
                    ballToUp = true;
                } else if (xCenterBall > (xCenterPlayer + 10)) {
                    ballXVelocity = 120;
                    ballToRight = true;
                    ballToUp = true;
                } else if (xCenterBall > xCenterPlayer) {
                    ballXVelocity = 60;
                    ballToRight = true;
                    ballToUp = true;
                }
            }
        });

        // WALL <-> BALL
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(AppType.WALL, AppType.BALL) {

            @Override
            protected void onCollision(Entity a, Entity b) {

                if (a.getCenter().getX() == -0.5) {
                    ballToRight = true;
                } else if (a.getCenter().getX() == 320) {
                    ballToUp = false;
                } else if (a.getCenter().getX() == 640.5) {
                    ballToRight = false;
                }

                FXGL.play("sound.wav");
            }
        });

        // BRICK <-> BALL
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(AppType.BRICK, AppType.BALL) {

            @Override
            protected void onCollision(Entity a, Entity b) {

                double dx = b.getCenter().getX() - a.getCenter().getX();
                double dy = b.getCenter().getY() - a.getCenter().getY();

                if (Math.abs(dx) > Math.abs(dy)) {

                    ballToRight = !(dx < 0);
                    System.out.println(ballToRight ? "kekanan" : "kekiri");

                } else {

                    ballToUp = !(dy > 0);
                    System.out.println(ballToUp ? "keatas" : "kebawah");
                }

                a.removeFromWorld();
                FXGL.play("sound.wav");
            }
        });
    }

    @Override
    protected void onUpdate(double tpf) {

        if (!ballRun) {

            int xb = (int) ball.getCenter().getX();
            int xp = (int) player.getCenter().getX();

            if (xb > (xp + 45)) {
                ballToRight = false;
            } else if (xb < (xp - 45)) {
                ballToRight = true;
            }

            ball.translateX(ballToRight ? 2 : -2);

        } else {

            ball.translateX((ballToRight ? ballXVelocity : -ballXVelocity) * tpf);
            ball.translateY((ballToUp ? -ballYVelocity : ballYVelocity) * tpf);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

enum AppType {
    PLAYER,
    BALL,
    WALL,
    BRICK
}