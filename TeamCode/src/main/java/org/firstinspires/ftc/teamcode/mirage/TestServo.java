package org.firstinspires.ftc.teamcode.mirage;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.Range;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(group = "drive")
public class TestServo extends LinearOpMode {
    Servo   armServo,outtake;
    //DcMotor intake;
    double  armPosition, outtakePosition,leftY,rightY;
    double  MIN_POSITION = 0.69, MAX_POSITION = 1;
    double MIN_OUTTAKE_POSITION = 0, MAX_OUTTAKE_POSITION = 1;
    public void runOpMode(){
        DcMotor flyWheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        DcMotor linearSlide = hardwareMap.get(DcMotorEx.class,"linearSlide");
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        armServo = hardwareMap.servo.get("Arm");
        //DcMotor intake = hardwareMap.get(DcMotorEx.class, "intake");
        outtake = hardwareMap.servo.get("outtake");
        telemetry.addData("Mode", "waiting");
        telemetry.update();
        waitForStart();
        armPosition = 0.69;
        outtakePosition = 1;

        //double targetHeading = 0;
        //boolean turning = false;

        while (opModeIsActive())
        {
            leftY = gamepad1.left_stick_y * -1;
            rightY = gamepad1.right_stick_y * -1;

            telemetry.addData("Mode", "running");
            telemetry.addData("sticks", "  left=" + leftY + "  right=" + rightY);

            // check the gamepad buttons and if pressed, increment the appropriate position
            // variable to change the servo location.
            /* 180 degree turn clockwise */
            if(gamepad1.left_bumper) {
                drive.setPoseEstimate(new Pose2d(0,0,0));
            }
            /* 180 degree turn counter clockwise */
            if(gamepad1.right_bumper) {
                double heading = drive.getExternalHeading();
                double roundRadians = Math.round(heading / (0.5f * Math.PI)) * (0.5f * Math.PI);
                drive.turn(roundRadians - heading)  ;
            }


            /* SERVO CODE */
            // move arm down on A button if not already at lowest position.
            if (gamepad1.dpad_up && armPosition > MIN_POSITION) armPosition -= .01;

            // move arm up on B button if not already at the highest position.
            if (gamepad1.dpad_down && armPosition < MAX_POSITION) armPosition += .01;
            armServo.setPosition(Range.clip(armPosition, MIN_POSITION, MAX_POSITION));

            /* OUTTAKE */

            if(gamepad1.dpad_left && outtakePosition < MAX_OUTTAKE_POSITION){
                outtakePosition += 0.01;
            }
            if(gamepad1.dpad_right && outtakePosition > MIN_OUTTAKE_POSITION){
                outtakePosition -= 0.01;
            }
            outtake.setPosition(Range.clip(outtakePosition, MIN_OUTTAKE_POSITION, MAX_OUTTAKE_POSITION));

            telemetry.addData("arm servo", "position=" + armPosition + "  actual=" + armServo.getPosition());
            telemetry.addData("arm servo", "position=" + outtakePosition + "  actual=" + outtake.getPosition());

            /* FLYWHEEL CODE */
            if(gamepad1.x) flyWheel.setPower(0.725);
            else if(gamepad1.b) flyWheel.setPower(-0.725);
            else if(gamepad1.b) flyWheel.setPower(-0.725 );
            else{
                flyWheel.setPower(0.0);
            }

            /* LINEAR SLIDE CODE */
            /*
            if(gamepad1.dpad_up) linearSlide.setPower(1.0);
            else if(gamepad1.dpad_down) linearSlide.setPower(-1.0);
            else{
                linearSlide.setPower(0.0);
            }*/
            /* MOVEMENT CODE (uses roadrunner) */
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            /* INTAKE */
            /*
            if(gamepad1.y){
                intake.setPower(1.0);
            }
            else if(gamepad1.b){
                intake.setPower(-1.0);
            } else{
                intake.setPower(0);
            }
            */
            drive.update();

            Pose2d poseEstimate = drive.getPoseEstimate();
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.update();
            telemetry.update();
            idle();
        }
    }
}
