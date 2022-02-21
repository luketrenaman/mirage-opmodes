package org.firstinspires.ftc.teamcode.mirage;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Test Linear Slide", group="Linear Opmode")
public class TestLinearSlide extends LinearOpMode {
    public void runOpMode() {
        //float MOTOR_RESOLUTION = 384.5f;
        int linearSlidePosition = 0;
        int SLIDE_MIN_POSITION = 0;
        int SLIDE_MAX_POSITION = 504; //Measured in ticks
        DcMotor linearSlide = hardwareMap.get(DcMotorEx.class,"linearSlide");
        linearSlide.setTargetPosition(0);
        linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlide.setPower(1.0f);
        waitForStart();
        while(opModeIsActive()){
            if(gamepad2.dpad_up){
                linearSlidePosition += 1;
            } else if (gamepad2.dpad_down){
                linearSlidePosition -= 1;
            } else{

            }
            if(Math.abs(linearSlide.getCurrentPosition()) > Math.abs(linearSlide.getTargetPosition())){
                linearSlide.setPower(0);
                linearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            } else{
                linearSlide.setPower(1.0f);
                linearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
            linearSlide.setTargetPosition(linearSlidePosition);
            telemetry.addData("Linear slide ticks %s",linearSlidePosition);
            telemetry.update();
            idle();
        }
    }
}
