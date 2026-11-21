package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FlywheelTwoMotor {
    private DcMotorEx top, bottom;
    private double encoderCountsPerRev = 28;
    private double kV = 0.000186, kS = 0.34, Kp = 0.0003;
    double gearRatio = 1.0;

    public void init(HardwareMap hwMap) {
        top = hwMap.get(DcMotorEx.class, "launcher");
        bottom = hwMap.get(DcMotorEx.class, "launchfollower");

        top.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        top.setDirection(DcMotorSimple.Direction.REVERSE);
        bottom.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        bottom.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setMotorPower(double power) {

        top.setPower(power);
        bottom.setPower(power);
    }

    public double getTicksPerSec(){
        return top.getVelocity();
    }

    public double getLaunchRPM(){
        return ((getTicksPerSec()/encoderCountsPerRev) * 60) / gearRatio;
    }
    public double getRPM() { return ((getTicksPerSec()/encoderCountsPerRev) * 60); }
}
