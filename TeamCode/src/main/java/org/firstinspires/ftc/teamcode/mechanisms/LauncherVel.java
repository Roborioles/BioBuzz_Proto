package org.firstinspires.ftc.teamcode.mechanisms;

import com.pedropathing.ivy.Command;

import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.units.Units;
import dev.nextftc.units.measuretypes.AngularVelocity;

public class LauncherVel implements Mechanism {
    public static final double COUNTS_PER_REV = 28.0; // Bare motor (28 CPR)
    public static final double MAX_RPM = 6000.0;
    public static final double MAX_RAD_PER_SEC = (MAX_RPM * 2.0 * Math.PI) / 60.0; // ~628.32 rad/s

    NextMotor launcher = new NextMotor("launcher", Units.getRadians((2.0 * Math.PI) / COUNTS_PER_REV));
    NextMotor launchfollower = new NextMotor("launchfollower") ;
    public double targetRPM = 0;
    // kV = 0.000172, kS = 0.32, Kp = 0.0006;

    public LauncherVel() {
        // Configure launchfollower to follow launcher motor in REVERSE
        launcher.setDirection(NextMotor.Direction.REVERSE);
        launchfollower.follow(launcher, NextMotor.Direction.REVERSE);

        // Feedforward-dominant tuning for high-inertia launcher flywheels:
        // kS: Static friction feedforward offset (~0.02)
        launcher.getVelocityConstants().setKS(0.32);

        // kV: Velocity feedforward gain = (1 - kS) / Max Rad/Sec
        launcher.getVelocityConstants().setKV(0.000172);

        // kP: Small proportional gain for mild feedback correction
        launcher.getVelocityConstants().setKP(0.0006);

        // kI: ZERO to prevent integral windup and flywheel surging/oscillation
        launcher.getVelocityConstants().setKI(0.0);
    }

    public void increaseSpeed() {
        targetRPM = Math.min(MAX_RPM, targetRPM + 50);
        updateVelocity();
    }

    public void decreaseSpeed() {
        targetRPM = Math.max(0, targetRPM - 50);
        updateVelocity();
    }

    public void stop() {
        targetRPM = 0;
        updateVelocity();
    }

    public double getCurrentRPM() {
        return (launcher.getEncoderVelocity().getMagnitude() * 60.0) / (2.0 * Math.PI);
    }

    private void updateVelocity() {
        double radiansPerSecond = (targetRPM * 2.0 * Math.PI) / 60.0;
        AngularVelocity vel = Units.getRadiansPerSecond(radiansPerSecond);
        launcher.setVelocitySetpoint(vel);
    }

    public Command speedup() { return instant(() -> increaseSpeed()); }
    public Command slowdown() { return instant(() -> decreaseSpeed()); }
    public Command stopLauncher() { return instant(() -> stop()); }
    public Command runLauncher() { return instant(() -> targetRPM=2700); }
}
