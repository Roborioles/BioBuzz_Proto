package org.firstinspires.ftc.teamcode.mechanisms;

import com.pedropathing.ivy.Command;

import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.units.Units;
import dev.nextftc.units.measuretypes.AngularVelocity;

public class LauncherVel implements Mechanism {
    public static final double COUNTS_PER_REV = 103.8; // GoBILDA 1620 RPM motor (103.8 CPR)
    public static final double MAX_RPM = 1620.0;
    public static final double MAX_RAD_PER_SEC = (MAX_RPM * 2.0 * Math.PI) / 60.0; // ~169.65 rad/s

    NextMotor launcher = new NextMotor("launcher", Units.getRadians((2.0 * Math.PI) / COUNTS_PER_REV));
    public double targetRPM = 0;

    public LauncherVel() {
        // Feedforward gain (kV) = 1.0 / Max Speed in rad/s (~169.65 rad/s at 1620 RPM)
        launcher.getVelocityConstants().setKV(1.0 / MAX_RAD_PER_SEC);
        launcher.getVelocityConstants().setKP(0.005);
    }

    public void increaseSpeed() {
        targetRPM = Math.min(MAX_RPM, targetRPM + 100);
        updateVelocity();
    }

    public void decreaseSpeed() {
        targetRPM = Math.max(0, targetRPM - 100);
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
}
