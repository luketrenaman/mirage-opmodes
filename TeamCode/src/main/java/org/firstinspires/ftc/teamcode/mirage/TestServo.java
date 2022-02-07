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
    Servo   armServo;
    double  armPosition,leftY,rightY;
    double  MIN_POSITION = 0.69, MAX_POSITION = 1;
    public void runOpMode(){
        DcMotor flyWheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        armServo = hardwareMap.servo.get("Arm");
        telemetry.addData("Mode", "waiting");
        telemetry.update();
        waitForStart();
        armPosition = 0.69;
        while (opModeIsActive())
        {
            leftY = gamepad1.left_stick_y * -1;
            rightY = gamepad1.right_stick_y * -1;

            telemetry.addData("Mode", "running");
            telemetry.addData("sticks", "  left=" + leftY + "  right=" + rightY);

            // check the gamepad buttons and if pressed, increment the appropriate position
            // variable to change the servo location.

            /* SERVO CODE */
            // move arm down on A button if not already at lowest position.
            if (gamepad1.a && armPosition > MIN_POSITION) armPosition -= .01;

            // move arm up on B button if not already at the highest position.
            if (gamepad1.y && armPosition < MAX_POSITION) armPosition += .01;
            armServo.setPosition(Range.clip(armPosition, MIN_POSITION, MAX_POSITION));
            telemetry.addData("arm servo", "position=" + armPosition + "  actual=" + armServo.getPosition());

            /* FLYWHEEL CODE */
            if(gamepad1.x) flyWheel.setPower(1.0);
            else if(gamepad1.b) flyWheel.setPower(-1.0);
            else{
                flyWheel.setPower(0.0);
            }
            /* MOVEMENT CODE (uses roadrunner) */
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

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
