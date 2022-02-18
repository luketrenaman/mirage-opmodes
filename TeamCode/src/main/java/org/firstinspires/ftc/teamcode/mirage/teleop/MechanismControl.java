package org.firstinspires.ftc.teamcode.mirage.teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MechanismControl {
    private static DcMotor flyWheel, linearSlide,intake;
    private static Servo armServo, outtake;
    private static Telemetry telemetry;
    private static double  armPosition, outtakePosition,leftY,rightY;
    private static double  MIN_POSITION = 0.69, MAX_POSITION = 1;
    private static double MIN_OUTTAKE_POSITION = 0, MAX_OUTTAKE_POSITION = 1;
    public MechanismControl(Telemetry telem, DcMotor fw, DcMotor ls, Servo as, Servo ot, DcMotor it) {
        armPosition = 0.69;
        outtakePosition = 1;
        telemetry = telem;
        flyWheel = fw;
        linearSlide = ls;
        armServo = as;
        outtake = ot;
        intake = it;
    }
    public static void tick(Gamepad gamepad){

        /* SERVO CODE */
        // move arm down on A button if not already at lowest position.
        if (gamepad.dpad_up && armPosition > MIN_POSITION) armPosition -= .01;

        // move arm up on B button if not already at the highest position.
        if (gamepad.dpad_down && armPosition < MAX_POSITION) armPosition += .01;
        armServo.setPosition(Range.clip(armPosition, MIN_POSITION, MAX_POSITION));
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

        if(gamepad.dpad_left && outtakePosition < MAX_OUTTAKE_POSITION){
            outtakePosition += 0.01;
        }
        if(gamepad.dpad_right && outtakePosition > MIN_OUTTAKE_POSITION){
            outtakePosition -= 0.01;
        }
        outtake.setPosition(Range.clip(outtakePosition, MIN_OUTTAKE_POSITION, MAX_OUTTAKE_POSITION));

        telemetry.addData("arm servo", "position=" + armPosition + "  actual=" + armServo.getPosition());
        telemetry.addData("arm servo", "position=" + outtakePosition + "  actual=" + outtake.getPosition());

        /* FLYWHEEL CODE */
        if(gamepad.x) flyWheel.setPower(0.725);
        else if(gamepad.b) flyWheel.setPower(-0.725);
        else{
            flyWheel.setPower(0.0);
        }
    }
}
