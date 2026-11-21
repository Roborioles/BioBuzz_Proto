package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class kPTuner extends OpMode {
    Flywheel flywheel = new Flywheel();
    public double kV = 0.001242;
    public double kS = 0.26;
    public double kP = 0.0006;
    public double goalRPM = 2700;
    double[] increments = {0.0000001, 0.000001, 0.0001, 0.0001, 0.001, 0.01};
    int incrementIndex = 2;

    @Override
    public void init() {
        flywheel.init(hardwareMap);
    }

    @Override
    public void loop() {

        if(gamepad1.dpadRightWasPressed()) {
            incrementIndex++;
        }
        if(gamepad1.dpadLeftWasPressed()) {
            incrementIndex--;
        }
        incrementIndex = Math.max(0, Math.min(increments.length - 1, incrementIndex));

        double currentStep = increments[incrementIndex];

        if(gamepad1.dpadUpWasPressed()) {kP += currentStep;}
        if(gamepad1.dpadDownWasPressed()) {kP -= currentStep;}

        if(gamepad1.aWasPressed()) {
            goalRPM = 2700;
        } else if(gamepad1.bWasPressed()) {
            goalRPM = 2000;
        }

        double feedForward = (kV * goalRPM) + kS;
        double error = goalRPM - flywheel.getRPM();
        double feedBack = kP * error;
        double power = feedForward + feedBack;

        flywheel.setMotorPower(power);
        telemetry.addData("currentStep","%.6f",currentStep);

        telemetry.addData("kP","%.6f",kP);
        telemetry.addData("Error", error);
        telemetry.addData("Motor RPM","%.6f",flywheel.getRPM());
        telemetry.addData("Launch RPM", flywheel.getRPM());
        telemetry.addData("goalRPM", goalRPM);
        telemetry.update();
    }

}
