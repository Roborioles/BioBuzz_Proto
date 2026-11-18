package org.firstinspires.ftc.teamcode.mechanisms;

import com.pedropathing.ivy.Command;

import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.units.Units;
import dev.nextftc.units.measuretypes.AngularVelocity;

public class LauncherVelTwo implements Mechanism {
    public static final double COUNTS_PER_REV = 28.0; // Bare motor (28 CPR)
    public static final double MAX_RPM = 6000.0;
    public static final double MAX_RAD_PER_SEC = (MAX_RPM * 2.0 * Math.PI) / 60.0; // ~628.32 rad/s

    NextMotor launcher2top = new NextMotor("launcher2top", Units.getRadians((2.0 * Math.PI) / COUNTS_PER_REV));
    NextMotor launcher2bottom = new NextMotor("launcher2bottom", Units.getRadians((2.0 * Math.PI) / COUNTS_PER_REV));
    public double targetTopRPM = 0, targetBottomRPM = 0;

    public LauncherVelTwo() {
        // Configure launchfollower to follow launcher motor in REVERSE
        launcher2bottom.setDirection(NextMotor.Direction.REVERSE);

        // Feedforward-dominant tuning for high-inertia launcher flywheels:
        // kS: Static friction feedforward offset (~0.02)
        launcher2top.getVelocityConstants().setKS(0.02);
        launcher2bottom.getVelocityConstants().setKS(0.02);

        // kV: Velocity feedforward gain = (1 - kS) / Max Rad/Sec
        launcher2top.getVelocityConstants().setKV((1.0 - 0.02) / MAX_RAD_PER_SEC);
        launcher2bottom.getVelocityConstants().setKV((1.0 - 0.02) / MAX_RAD_PER_SEC);

        // kP: Small proportional gain for mild feedback correction
        launcher2top.getVelocityConstants().setKP(0.002);
        launcher2bottom.getVelocityConstants().setKP(0.002);

        // kI: ZERO to prevent integral windup and flywheel surging/oscillation
        launcher2top.getVelocityConstants().setKI(0.0);
        launcher2bottom.getVelocityConstants().setKI(0.0);
    }

    public void increaseTopSpeed() {
        targetTopRPM = Math.min(MAX_RPM, targetTopRPM + 100);
        updateTopVelocity();
    }

    public void decreaseTopSpeed() {
        targetTopRPM = Math.max(0, targetTopRPM - 100);
        updateTopVelocity();
    }

    public void increaseBottomSpeed() {
        targetBottomRPM = Math.min(MAX_RPM, targetBottomRPM + 100);
        updateBottomVelocity();
    }

    public void decreaseBottomSpeed() {
        targetBottomRPM = Math.max(0, targetBottomRPM - 100);
        updateBottomVelocity();
    }

    public void stop() {
        targetTopRPM = 0;
        targetBottomRPM = 0;
        updateTopVelocity();
        updateBottomVelocity();
    }

    public double getCurrentTopRPM() {
        return (launcher2top.getEncoderVelocity().getMagnitude() * 60.0) / (2.0 * Math.PI);
    }
    public double getCurrentBottomRPM() {
        return (launcher2bottom.getEncoderVelocity().getMagnitude() * 60.0) / (2.0 * Math.PI);
    }

    private void updateTopVelocity() {
        double radiansPerSecond = (targetTopRPM * 2.0 * Math.PI) / 60.0;
        AngularVelocity vel = Units.getRadiansPerSecond(radiansPerSecond);
        launcher2top.setVelocitySetpoint(vel);
    }
    private void updateBottomVelocity() {
        double radiansPerSecond = (targetBottomRPM * 2.0 * Math.PI) / 60.0;
        AngularVelocity vel = Units.getRadiansPerSecond(radiansPerSecond);
        launcher2bottom.setVelocitySetpoint(vel);
    }
    public Command speedupTop() { return instant(() -> increaseTopSpeed()); }
    public Command slowdownTop() { return instant(() -> decreaseTopSpeed()); }
    public Command speedupBottom() { return instant(() -> increaseBottomSpeed()); }
    public Command slowdownBottom() { return instant(() -> decreaseBottomSpeed()); }
}
