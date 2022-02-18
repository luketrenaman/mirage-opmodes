package org.firstinspires.ftc.teamcode.mirage;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Test Linear Slide", group="Linear Opmode")
public class TestLinearSlide extends LinearOpMode {
    public void runOpMode() {
        DcMotor linearSlide = hardwareMap.get(DcMotorEx.class,"linearSlide");
        //float MOTOR_RESOLUTION = 384.5f;
        int linearSlidePosition = 0;
        int SLIDE_MIN_POSITION = 0;
        int SLIDE_MAX_POSITION = 504; //Measured in ticks
        linearSlide.setTargetPosition(linearSlidePosition);
        linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlide.setPower(0.2f);
        waitForStart();
        while(opModeIsActive()){
            if(gamepad1.dpad_up && linearSlidePosition < SLIDE_MAX_POSITION){
                linearSlidePosition += 1;

            }
            if(gamepad1.dpad_down && linearSlidePosition > SLIDE_MIN_POSITION){
                linearSlidePosition -= 1;
            }
            if(linearSlide.getCurrentPosition() > linearSlide.getTargetPosition()){
                linearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                linearSlide.setPower(0);
            } else{
                linearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                linearSlide.setPower(0.8f);
            }
            linearSlide.setTargetPosition(linearSlidePosition);
            telemetry.addData("Linear slide position in ticks: %s",linearSlidePosition);
            telemetry.update();
            idle();
        }
    }
}
