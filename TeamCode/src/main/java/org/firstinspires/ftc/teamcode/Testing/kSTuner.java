package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class kSTuner extends OpMode {
    Flywheel flywheel = new Flywheel();
    public double kS = 0.35;
    double[] increments = {0.000001,0.0001,0.0001,0.001,0.01};
    int incrementIndex = 4;

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

        if(gamepad1.dpadUpWasPressed()) {kS += currentStep;}
        if(gamepad1.dpadDownWasPressed()) {kS -= currentStep;}


        flywheel.setMotorPower(kS);
        telemetry.addData("currentStep","%.6f",currentStep);

        telemetry.addData("kS","%.6f",kS);
        telemetry.addData("Top RPM","%.6f",flywheel.getRPM());
        telemetry.update();
    }

}
