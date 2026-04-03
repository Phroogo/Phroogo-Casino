package com.example.frugocasino;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Random;


public class RouletteBall extends Circle{
    private double ballAngle, ballSpeed, radiusFromCenter, targetRelativeAngle;
    private boolean isAligned;
    private final Random random = new Random();
    public RouletteBall(double radius, Paint fill) {
        this.setRadius(radius);
        this.setFill(fill);
    }

    public void spinTheBallFrame(double wheelAngle) {
        if (ballSpeed <= 4.5 && radiusFromCenter >= 224.5) {
            radiusFromCenter -= 1.5;
        }

        if (ballSpeed >= 3) {
            ballAngle -= ballSpeed;
            ballSpeed -= random.nextDouble(0.04, 0.05);
        } else if (ballSpeed >= .2) {
            ballAngle -= ballSpeed;
            ballSpeed -= random.nextDouble(0.02, 0.03);
        } else {
            if (isAligned) {
                ballAngle = (wheelAngle + targetRelativeAngle + .5) % 360;
            } else {
                double relativeAngle = ((ballAngle % 360) + 360 - wheelAngle - 7) % 360 + 360;
                double step = 360.0 / 37;
                targetRelativeAngle = (Math.ceil(relativeAngle / step) * step) % 360 - random.nextDouble(2, 3);
                isAligned = true;
            }
        }

        double x = radiusFromCenter * Math.cos(Math.toRadians(ballAngle));
        double y = radiusFromCenter * Math.sin(Math.toRadians(ballAngle));

        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    public void setUpBallStart(boolean firstSpin) {
        radiusFromCenter = 374;
        ballAngle %= 360;
        ballSpeed = random.nextDouble(6,8);
        if (!firstSpin) {
            isAligned = false;
            this.setLayoutX(374 * Math.cos(Math.toRadians(ballAngle + ballSpeed)));
            this.setLayoutY(374 * Math.sin(Math.toRadians(ballAngle + ballSpeed)));
        }
    }

    public double getBallAngle() {
        return ballAngle;
    }

    public void setBallAngle(double ballAngle) {
        this.ballAngle = ballAngle;
    }

    public double getBallSpeed() {
        return ballSpeed;
    }

    public void setBallSpeed(double ballSpeed) {
        this.ballSpeed = ballSpeed;
    }

    public double getRadiusFromCenter() {
        return radiusFromCenter;
    }

    public void setRadiusFromCenter(double radiusFromCenter) {
        this.radiusFromCenter = radiusFromCenter;
    }

    public double getTargetRelativeAngle() {
        return targetRelativeAngle;
    }

    public void setTargetRelativeAngle(double targetRelativeAngle) {
        this.targetRelativeAngle = targetRelativeAngle;
    }

    public boolean isAligned() {
        return isAligned;
    }

    public void setAligned(boolean aligned) {
        isAligned = aligned;
    }
}