package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.teamcode.MyRobot;

import dev.nextftc.robot.Telemetry;
import dev.nextftc.robot.opmode.NextOpMode;
import dev.nextftc.robot.opmode.NextTeleop;
import dev.nextftc.robot.triggers.CommandGamepad;

@NextTeleop(name = "My Teleop")
public class MyTeleop extends NextOpMode {
    private final MyRobot robot;
    public MyTeleop(MyRobot robot) {
        super(robot);
        this.robot = robot;
    }
    CommandGamepad gp1 = new CommandGamepad(gamepad1);

    @Override
    public void start(){
        robot.launcher.stop();
        robot.drivetrain.init();
        robot.drivetrain.startDrive(gamepad1);
        gp1.leftBumper().onTrue(robot.launcher.speedup());
        gp1.rightBumper().onTrue(robot.launcher.slowdown());

        // Drive motor wiring test bindings
        //gp1.a().onTrue(robot.drivetrain.runFrontLeft()).onFalse(robot.drivetrain.stopMotors());
        //gp1.b().onTrue(robot.drivetrain.runFrontRight()).onFalse(robot.drivetrain.stopMotors());
        //gp1.x().onTrue(robot.drivetrain.runBackLeft()).onFalse(robot.drivetrain.stopMotors());
        //gp1.y().onTrue(robot.drivetrain.runBackRight()).onFalse(robot.drivetrain.stopMotors());
    }

    @Override
    public void periodic(){
        Telemetry.log("Status","Running");
        //Telemetry.log("Count: ",robot.intake.count);
        //Telemetry.log("Color: ",robot.intake.intakeColor.debug());

        //gp1.leftBumper().onTrue(robot.intake.left());
        //gp1.rightBumper().onTrue(robot.intake.right());
        //gp1.a().onTrue(robot.intake.runintake());
        //gp1.b().onTrue(robot.intake.resetCount());
        Telemetry.log("Launcher Target RPM: ", robot.launcher.targetRPM);
        Telemetry.log("Launcher Current RPM: ", robot.launcher.getCurrentRPM());
    }

}
