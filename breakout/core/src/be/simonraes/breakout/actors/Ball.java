package be.simonraes.breakout.actors;

import be.simonraes.breakout.screen.GameScreen;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Simon Raes on 7/08/2014.
 */
public class Ball {

    private Vector2 previousPosition; // Used to calculated ball angle.
    private Vector2 position;
    private Vector2 velocity;

    private final int START_SPEED_X = 60;
    private final int START_SPEED_Y = -70;

    private int radius;

    public Ball(float xPosition, float yPosition, int radius) {
        this.radius = radius;
        previousPosition = new Vector2(0, 0);
        position = new Vector2(xPosition, yPosition);
        velocity = new Vector2(0, 0);
    }

    public void update(float delta) {
        previousPosition.x = position.x;
        previousPosition.y = position.y;

        // Don't let ball move out of the screen
        if (position.x < 0 + radius) {
            position.x = 0 + radius;
            velocity.x = -velocity.x;
        }
        if (position.x + radius > GameScreen.gameWidth) {
            position.x = GameScreen.gameWidth - radius;
            velocity.x = -velocity.x;
        }
        if (position.y < 0 + radius) {
            position.y = 0 + radius;
            velocity.y = -velocity.y;
        }

        // Update ball position
        position.add(velocity.cpy().scl(delta));
    }


    public void launch() {
        velocity.x = START_SPEED_X;
        velocity.y = START_SPEED_Y;
    }


//    public void collision(float paddleX, float paddleY, int paddleWidth, int paddleHeight) {
//        float degrees = (float) ((Math.atan2(previousPosition.x - position.x, -(previousPosition.y - position.y)) * 180.0d / Math.PI));
//        System.out.println("degrees:" + degrees);
//
//        float depth;
//        if(velocity.y>0){
//            depth = position.y - paddleY;
//        } else {
//             depth = paddleY+paddleHeight - position.y;
//        }
//        double impactPoint = depth * Math.tan(degrees);
//
//        double impactOnPaddle = position.x - paddleX + impactPoint;
//
//        System.out.println("depth: "+depth);
//        System.out.println("impactpoint relative to current position: " + impactPoint);
//        System.out.println("impact on paddle "+impactOnPaddle);
//
//
//        if (impactOnPaddle < 0) {
//            System.out.println("bouncing left");
//            velocity.x = -velocity.x;
//            position.x = paddleX - radius - 1;
//        } else if (impactOnPaddle > paddleWidth) {
//            System.out.println("bouncing right");
//            velocity.x = -velocity.x;
//            position.x = paddleX + paddleWidth + radius + 1;
//        } else {
//            System.out.println("bouncing up/down");
//            if(velocity.y<0){
//                position.y = paddleY +paddleHeight + radius +1;
//
//            } else {
//                position.y = paddleY - radius - 1;
//
//            }
//            velocity.y = -velocity.y;
//        }
//    }

    public void collision(float paddleX, float paddleY, int paddleWidth, int paddleHeight) {
//        System.out.println("ball posY: " + position.y + ", paddleY: " + paddleY);
        if (position.y < paddleY) {
            reverseYVelocity();
            position.y = paddleY - radius - 1;
        } else if (position.y > paddleY + paddleHeight) {
            reverseYVelocity();
            position.y = paddleY + paddleHeight + radius + 1;
        } else {
            if (position.x < paddleX) {
                reverseXVelocity();
                position.x = paddleX - radius - 1;
            } else if (position.x > paddleX + paddleWidth) {
                reverseXVelocity();
                position.x = paddleX + paddleWidth + radius + 1;
            } else {
                // midPoint is inside or below the paddle, need to check angle
//                float degrees = (float) ((Math.atan2(previousPosition.x - position.x, -(previousPosition.y - position.y)) * 180.0d / Math.PI));
//                System.out.println("degrees:" + degrees);
//
//                float depth;
//                if (velocity.y > 0) {
//                    depth = position.y - paddleY;
//                } else {
//                    depth = paddleY + paddleHeight - position.y;
//                }
//                double impactPoint = depth * Math.tan(degrees);
//
//                double impactOnPaddle = position.x - paddleX + impactPoint;
//
//                System.out.println("depth: " + depth);
//                System.out.println("impactpoint relative to current position: " + impactPoint);
//                System.out.println("impact on paddle " + impactOnPaddle);
//
//
//                if (impactOnPaddle < 0) {
//                    System.out.println("bouncing left");
//                    velocity.x = -velocity.x;
//                    position.x = paddleX - radius - 1;
//                } else if (impactOnPaddle > paddleWidth) {
//                    System.out.println("bouncing right");
//                    velocity.x = -velocity.x;
//                    position.x = paddleX + paddleWidth + radius + 1;
//                } else {
//                    System.out.println("bouncing up/down");
//                    if (velocity.y < 0) {
//                        position.y = paddleY + paddleHeight + radius + 1;
//
//                    } else {
//                        position.y = paddleY - radius - 1;
//
//                    }
//                    velocity.y = -velocity.y;
//                }
            }
        }
    }

    private void reverseXVelocity() {
        velocity.x = -velocity.x;
    }

    private void reverseYVelocity() {
        velocity.y = -velocity.y;
    }

    /**
     * Sets a new direction for the ball based on where it hit the paddle.
     */
    public void paddleCollision(float ballCenterPosition, float paddleCenterPosition, int paddleWidth, float paddleXVelocity) {
        if (paddleXVelocity > 0) {
            // Paddle was moving right, move ball right
            velocity.x = 20;
        } else if (paddleXVelocity < 5 || paddleXVelocity > -5) {
            // Paddle was not moving, no need to change X velocity
            // velocity.x = -velocity.x;
        } else {
            // Paddle was moving left, move ball left
            velocity.x = -20;
        }


        // todo: this will also have to change to keep the overal speed the same.
        // if the x velocity increases the y velocity should decrease
        velocity.y = -velocity.y;

        // Move the ball up 1 pixel to avoid multiple collisions
        position.y = position.y - 1;
    }

    public void paddleSideCollision(float ballPosition, float paddlePosition, int paddleWidth) {

        velocity.x = -velocity.x;

        if (ballPosition < paddlePosition + paddleWidth / 2) {
            position.x = paddlePosition - 1 - radius;
        } else {
            position.x = paddlePosition + paddleWidth + 1 + radius;
        }
    }

    public void stop() {
        velocity.x = 0;
        velocity.y = 0;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getRadius() {
        return radius;
    }

    public Circle getCircle(){
        return new Circle(position.x, position.y, radius);
    }

    public void blockSideCollision() {
        velocity.x = -velocity.x;
    }

    public void blockTopOrBottomCollision() {
        velocity.y = -velocity.y;
    }
}
