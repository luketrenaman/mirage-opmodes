package org.firstinspires.ftc.teamcode.mirage;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.mirage.auto.ColorDetection;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(group = "drive")
public class AutoBlue extends LinearOpMode {
    Servo armServo,outtake;
    private static int ARM_MIN_POSITION = 0;
    private static int ARM_MAX_POSITION = 500;
    public static Vector2d transform(double xIn,double yIn){
        double theta = Math.toRadians(-90);
        double x = ( xIn - 72 ) * Math.cos(theta) - (yIn - 72) * Math.sin(theta);
        double y = ( yIn - 72 ) * Math.cos(theta) + (xIn - 72) * Math.sin(theta);

        Vector2d output = new Vector2d(x,y);
        return output;
    }
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor flyWheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        DcMotor linearSlide = hardwareMap.get(DcMotorEx.class,"linearSlide");
        DcMotor arm = hardwareMap.get(DcMotorEx.class,"arm");
        arm.setTargetPosition(-ARM_MAX_POSITION);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(1.0f);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtake = hardwareMap.servo.get("outtake");
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        outtake.setPosition(0.73f);
        waitForStart();
        double ROT = 90;
        /*
        *
        Vector2d pos2 = transform(8.0625, 24) ;
        Vector2d pos3 = transform(8.0625, 16.6);
        Vector2d pos4 = transform(23.8132, 28.757);
        Vector2d pos5 = transform(8.0625, 82);
        Vector2d pos6 = transform(8.0625, 109);
        * */
        Vector2d shippingLoc = new Vector2d(-12,24);
        Vector2d pos2 = new Vector2d(-49.4, 63.9375);
        Vector2d pos3 = new Vector2d(-56.2, 63.975);
        Vector2d pos4 = new Vector2d(-30, 36.3);
        Vector2d pos5 = new Vector2d(-8.0625, 77);
        Vector2d pos6 = new Vector2d(48.2292830602, 77);
        drive.setPoseEstimate(new Pose2d(pos2.getX(),pos2.getY(),Math.toRadians(0 + ROT - 180)));
        // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
        TrajectorySequence goToFlywheel = drive.trajectorySequenceBuilder(new Pose2d(pos2.getX(), pos2.getY(), Math.toRadians(0 + ROT - 180))) //Start
                .waitSeconds(1f)
                .lineToLinearHeading(new Pose2d(pos3.getX(), pos3.getY(), Math.toRadians(0 + ROT - 180))) //Flywhee
                .waitSeconds(1f)
                .build();
        double yDiff = (pos4.getY()-shippingLoc.getY());
        double xDiff = (pos4.getX() - shippingLoc.getX());
        double angle = Math.atan2(yDiff,xDiff);
        TrajectorySequence goToShippingHub = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(pos4.getX(), pos4.getY(), angle)) //Ship
                .waitSeconds(1f)
                .build();
        TrajectorySequence goToWarehouse = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(pos5.getX(), pos5.getY(), Math.toRadians(-90 + ROT))) //Wall
                .waitSeconds(1f)
                .lineToLinearHeading(new Pose2d(pos6.getX(), pos6.getY(), Math.toRadians(-90 + ROT))) //Park
                .build();
        drive.followTrajectorySequence(goToFlywheel);
        flyWheel.setPower(0.725f/2f);
        Thread.sleep(4000);
        flyWheel.setPower(0f);
        drive.followTrajectorySequence(goToShippingHub);
        outtake.setPosition(0.1f);
        Thread.sleep(2000);
        outtake.setPosition(0.73f);
        drive.followTrajectorySequence(goToWarehouse);
        //0.725
        }
    }
