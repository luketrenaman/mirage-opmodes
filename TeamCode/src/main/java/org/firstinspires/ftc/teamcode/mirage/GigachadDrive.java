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
import org.firstinspires.ftc.teamcode.mirage.teleop.DriveControl;
import org.firstinspires.ftc.teamcode.mirage.teleop.MechanismControl;

@TeleOp(group = "drive")
public class GigachadDrive extends LinearOpMode {
    Servo   armServo,outtake;
    //DcMotor intake;
    public void runOpMode(){
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        DcMotor intake = hardwareMap.get(DcMotorEx.class, "intake");
        DcMotor flyWheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        DcMotor linearSlide = hardwareMap.get(DcMotorEx.class,"linearSlide");
        DcMotor arm = hardwareMap.get(DcMotorEx.class,"arm");
        linearSlide.setTargetPosition(0);
        linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlide.setPower(0.2f);

        //arm.setTargetPosition(0);
        //arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //arm.setPower(0.5f);
        //arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        outtake = hardwareMap.servo.get("outtake");
        telemetry.addData("Mode", "waiting");
        telemetry.update();

        DriveControl driveControl = new DriveControl(telemetry,drive);
        MechanismControl mechanismControl = new MechanismControl(telemetry,flyWheel,linearSlide,arm,outtake,intake);
        waitForStart();

        //double targetHeading = 0;
        //boolean turning = false;

        while (opModeIsActive())
        {

            telemetry.addData("Mode", "running");
            // check the gamepad buttons and if pressed, increment the appropriate position
            // variable to change the servo location.
            /* 180 degree turn clockwise */

            driveControl.tick(gamepad1);
            mechanismControl.tick(gamepad2);
            telemetry.update();
            idle();
        }
    }
}
