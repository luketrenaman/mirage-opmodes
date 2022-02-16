package org.firstinspires.ftc.teamcode.mirage;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.mirage.auto.ColorDetection;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(group = "drive")
public class AutoRed extends LinearOpMode {
    Servo armServo,outtake;
    @Override
    public void runOpMode() throws InterruptedException {
        armServo = hardwareMap.servo.get("Arm");
        armServo.setPosition(.69);
         SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        TrajectorySequence traj = drive.trajectorySequenceBuilder(new Pose2d())
                .forward(37).build();
                //.strafeLeft(37).build();
        waitForStart();
        /*
        ColorDetection color = new ColorDetection();
        drive.setPoseEstimate(new Pose2d(8.5,35.5,0));
        waitForStart();
        int location = 0;

        if (isStopRequested()) return;
        /*
        Trajectory traj = drive.trajectoryBuilder(new Pose2d())
                .splineTo(new Vector2d(8.5, 35.5), Math.toRadians(0)) //Start
                .splineTo(new Vector2d(9.9525, 14.216), Math.toRadians(31)) //Flywhee
                .splineTo(new Vector2d(23.8132, 28.757), Math.toRadians(-40)) //Ship
                .splineTo(new Vector2d(7.0625, 82), Math.toRadians(-90)) //Wall
                .splineTo(new Vector2d(7.0625, 109), Math.toRadians(-90)) //Park
                .build();
         */


        drive.followTrajectorySequence(traj);


    }
}
