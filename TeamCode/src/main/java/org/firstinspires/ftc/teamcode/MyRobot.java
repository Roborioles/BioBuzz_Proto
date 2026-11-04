package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;
import org.firstinspires.ftc.teamcode.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.Launcher;
import org.firstinspires.ftc.teamcode.mechanisms.LauncherVel;

import java.util.Set;

import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.NextRobot;

public class MyRobot implements NextRobot {
//    public Intake intake = new Intake();
    public Drivetrain drivetrain = new Drivetrain();
    public LauncherVel launcher = new LauncherVel();

    @Override
    public Set<Mechanism> getMechanisms() {
        return Set.of(drivetrain, launcher);
    }
}
