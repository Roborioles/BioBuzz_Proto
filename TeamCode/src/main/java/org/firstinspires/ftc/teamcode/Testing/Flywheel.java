package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Flywheel {
    private DcMotorEx top;
    private double encoderCountsPerRev = 28;
    private double kV = 0.000242, kS = 0.35, Kp = 0.0006;
    double gearRatio = 1.5;

    public void init(HardwareMap hwMap) {
        top = hwMap.get(DcMotorEx.class, "launcher");
        top.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        top.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setMotorPower(double topPower) {
        top.setPower(topPower);
    }

    public double getTicksPerSec(){
        return top.getVelocity();
    }

    public double getLaunchRPM(){
        return ((getTicksPerSec()/encoderCountsPerRev) * 60) / gearRatio;
    }
    public double getRPM() { return ((getTicksPerSec()/encoderCountsPerRev) * 60); }
}
