package org.firstinspires.ftc.teamcode.mechanisms;

import com.pedropathing.ivy.Command;

import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.robot.Mechanism;

public class Launcher implements Mechanism {
    NextMotor launcher = new NextMotor("launcher");
    public double speed = 0;
    public void increaseSpeed() {
        speed = Math.min(1.0, speed + 0.05);
        launcher.setThrottle(speed);
    }

    public void decreaseSpeed() {
        speed = Math.max(-1.0, speed - 0.05);
        launcher.setThrottle(speed);
    }

    public void stop() {
        speed = 0;
        launcher.setThrottle(0);
    }
//    public void periodic() {
//        launcher.setThrottle(speed);
//    }

    public Command speedup() { return instant(() -> increaseSpeed()); }
    public Command slowdown() { return instant(() -> decreaseSpeed()); }
}
