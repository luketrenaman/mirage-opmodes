package org.firstinspires.ftc.teamcode.mirage.teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MechanismControl {
    private static DcMotor flyWheel, linearSlide,intake,arm;
    private static Servo outtake;
    private static Telemetry telemetry;
    private static double  outtakePosition,leftY,rightY;
    private static double  MIN_POSITION = 0, MAX_POSITION = 1;
    private static double MIN_OUTTAKE_POSITION = 0.3, MAX_OUTTAKE_POSITION = 1;
    private static int linearSlidePosition = 0,armPosition = 0;
    private static int SLIDE_MIN_POSITION = 0;
    private static int SLIDE_MAX_POSITION = -504; //Measured in ticks
    private static int ARM_MIN_POSITION = 0;
    private static int ARM_MAX_POSITION = 500;
    public MechanismControl(Telemetry telem, DcMotor fw, DcMotor ls, DcMotor a, Servo ot, DcMotor it) {
        outtakePosition = MAX_OUTTAKE_POSITION;
        telemetry = telem;
        flyWheel = fw;
        linearSlide = ls;
        arm = a;
        outtake = ot;
        intake = it;
    }
    public static void tick(Gamepad gamepad){

        /* SERVO CODE */
        // move arm down on A button if not already at lowest position.
//        if (gamepad.dpad_up && armPosition < ARM_MAX_POSITION) armPosition += 10;
//        else if (gamepad.dpad_down && armPosition > ARM_MIN_POSITION) armPosition -= 10;

//        if(Math.abs(arm.getCurrentPosition()) > Math.abs(arm.getTargetPosition())){
//            arm.setPower(0);
//        } else{
//            arm.setPower(0.8f);
//        }
//        arm.setTargetPosition(-armPosition);
        if(gamepad.dpad_up) arm.setPower(1.0f);
        else if(gamepad.dpad_down) arm.setPower(-1.0f);
        else{
            arm.setPower(0f);
        }
        telemetry.addData("Arm position in ticks: %s",-armPosition);

        // move arm up on B button if not already at the highest position.
        //armServo.setPosition(Range.clip(armPosition, MIN_POSITION, MAX_POSITION));
        /* INTAKE */
        if(gamepad.cross){
            intake.setPower(1.0f);
        }
        else if(gamepad.triangle){
            intake.setPower(-1.0f);
        } else{
            intake.setPower(0f);
        }
        /* OUTTAKE */
        if(gamepad.left_bumper){
            outtakePosition = MIN_OUTTAKE_POSITION;
        } else{
            outtakePosition = MAX_OUTTAKE_POSITION;
        }
        outtake.setPosition(Range.clip(outtakePosition, MIN_OUTTAKE_POSITION, MAX_OUTTAKE_POSITION));

        //telemetry.addData("arm servo", "position=" + armPosition + "  actual=" + armServo.getPosition());
        //telemetry.addData("arm servo", "position=" + outtakePosition + "  actual=" + outtake.getPosition());

        /* FLYWHEEL CODE */
        if(gamepad.x) flyWheel.setPower(0.725);
        else if(gamepad.b) flyWheel.setPower(-0.725);
        else{
            flyWheel.setPower(0.0);
        }
        /* LINEAR SLIDE */
        /* This is potential debug code if we aren't sure what the maximum extension ticks of the linear slide are
        There also might be situations where the robot's pulley starts in the wrong position.
        Manual calibration with unused bumpers? idk lol

        if(gamepad.right_trigger > 0.1 && linearSlidePosition < SLIDE_MAX_POSITION) {
            linearSlidePosition += 1;
        }
        if(gamepad.right_bumper &&  linearSlidePosition > SLIDE_MIN_POSITION){
            linearSlidePosition -= 1;
        }
        */

        if(gamepad.left_trigger > 0.1){
            linearSlidePosition = SLIDE_MAX_POSITION;
        } else{
            linearSlidePosition = SLIDE_MIN_POSITION;
        }
        if(Math.abs(linearSlide.getCurrentPosition()) > Math.abs(linearSlide.getTargetPosition())){
             linearSlide.setPower(0);
            linearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        } else{
            linearSlide.setPower(0.8f);
            linearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        linearSlide.setTargetPosition(linearSlidePosition);
        telemetry.addData("Linear slide position in ticks: %s",linearSlidePosition);
    }
}
