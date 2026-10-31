package org.firstinspires.ftc.teamcode.mechanisms;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.actuators.NextServo;
import dev.nextftc.hardware.sensors.NextColorDistanceSensor;
import dev.nextftc.hardware.sensors.NextDigitalSensor;
import dev.nextftc.hardware.sensors.colors.ColorProfile;
import dev.nextftc.hardware.sensors.colors.ColorSpace;
import dev.nextftc.hardware.sensors.colors.NextColor;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.Telemetry;

public class Intake implements Mechanism {
    NextServo intakeServo = new NextServo("intake_servo");
    NextMotor intakeMotor = new NextMotor("intake_motor");
    NextDigitalSensor intakeSensor = new NextDigitalSensor("intake_sensor",false);
    public NextColorDistanceSensor intakeColor = new NextColorDistanceSensor("intake_color");
    public double count = 0;
    boolean nowTrue, wasTrue = false;
    ColorProfile red = new ColorProfile(
            ColorSpace.RGB,
            NextColor.Companion.rgb(255,0,0),
            NextColor.Companion.rgb(25,0,0)
    );

    @Override
    public void periodic() {
        nowTrue = intakeSensor.isTriggered();
        if(nowTrue & !wasTrue) {
            count += 1;
            if(count == 4) {
                intakeMotor.setThrottle(0.0);
            }
        }
        wasTrue = nowTrue;

        if(intakeColor.isColorWithinDistance(red,20)) {
            intakeServo.setPosition(0.75);
        }
    }
    public Command left() { return instant(() -> intakeServo.setPosition(0.25)); }
    public Command right() { return instant(() -> intakeServo.setPosition(0.75)); }
    public Command runintake() { return instant(() -> intakeMotor.setThrottle(0.5));}
    public Command resetCount() { return instant(() -> count = 0); }
}
