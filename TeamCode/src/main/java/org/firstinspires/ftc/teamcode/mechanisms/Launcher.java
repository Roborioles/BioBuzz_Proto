package org.firstinspires.ftc.teamcode.mechanisms;

import com.pedropathing.ivy.Command;

import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.actuators.NextServo;
import dev.nextftc.robot.Mechanism;

public class Launcher implements Mechanism {
    private final NextMotor launcher = new NextMotor("launcher");
    private final NextServo goLight = new NextServo("goLight");
    public double speed = 0;
    private double encoderCountsPerRev = 28;
    // kV = 0.000172, kS = 0.32, Kp = 0.0006;
    private double kV = 0.000178, kS = 0.32, kP = 0.0006;
    public double goalRPM = 0;

    public void init() {
        launcher.setDirection(NextMotor.Direction.REVERSE);
    }

    public void setRPM(double targetRPM) {
        if (targetRPM <= 0) {
            setMotorPower(0);
            return;
        }
        double feedForward = (kV * targetRPM) + kS;
        double error = targetRPM - getRPM();
        double feedBack = kP * error;
        setMotorPower(feedForward + feedBack);
    }

    public void setMotorPower(double Power) {
        Power = Math.min(1.0, Math.max(-1.0, Power));
        launcher.setThrottle(Power);
    }

    public double getTicksPerSec() { return launcher.getEncoderVelocity().getMagnitude(); }

    public double getRPM(){
        return getTicksPerSec() * 60 / encoderCountsPerRev;
    }

    public void increaseSpeed() {
        goalRPM+=50;
    }

    public void decreaseSpeed() {
        goalRPM-=50;
    }
    public double getkP() {
        return kP;
    }

    @Override
    public void periodic() {
        setRPM(goalRPM);
        if (goalRPM == 0) {
            goLight.setPosition(0.0);
        } else if (Math.abs(getRPM() - goalRPM) <= 50) {
            goLight.setPosition(0.444);
        } else {
            goLight.setPosition(0.277);
        }
    }

    public Command increasekP() { return instant(() -> kP += 0.00001); }
    public Command decreasekP() { return instant(() -> kP -= 0.00001); }
    public Command startLauncher() { return instant(() -> goalRPM=2700); }
    public Command stopLauncher() { return instant(() -> goalRPM=0); }
    public Command speedup() { return instant(() -> increaseSpeed()); }
    public Command slowdown() { return instant(() -> decreaseSpeed()); }
}
