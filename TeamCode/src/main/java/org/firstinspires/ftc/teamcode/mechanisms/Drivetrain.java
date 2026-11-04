package org.firstinspires.ftc.teamcode.mechanisms;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.Gamepad;

import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.drive.DriveCommands;

public class Drivetrain implements Mechanism {
    public final NextMotor frontLeft = new NextMotor("frontLeft");
    public final NextMotor frontRight = new NextMotor("frontRight");
    public final NextMotor backLeft = new NextMotor("backLeft");
    public final NextMotor backRight = new NextMotor("backRight");

    public void init() {
        frontLeft.setDirection(NextMotor.Direction.REVERSE);
        backLeft.setDirection(NextMotor.Direction.REVERSE);
    }

    public void startDrive(Gamepad gamepad) {
        init();
        DriveCommands.mecanumDrive(frontLeft, frontRight, backLeft, backRight, gamepad).schedule();
    }

    public Command runFrontLeft() { return instant(() -> frontLeft.setThrottle(0.5)); }
    public Command runFrontRight() { return instant(() -> frontRight.setThrottle(0.5)); }
    public Command runBackLeft() { return instant(() -> backLeft.setThrottle(0.5)); }
    public Command runBackRight() { return instant(() -> backRight.setThrottle(0.5)); }
    public Command stopMotors() {
        return instant(() -> {
            frontLeft.setThrottle(0);
            frontRight.setThrottle(0);
            backLeft.setThrottle(0);
            backRight.setThrottle(0);
        });
    }
}