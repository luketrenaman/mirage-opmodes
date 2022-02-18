package org.firstinspires.ftc.teamcode.mirage.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

public class DriveControl {
    private static SampleMecanumDrive drive;
    private static Telemetry telemetry;

    public DriveControl(Telemetry telem, SampleMecanumDrive dr) {
        telemetry = telem;
        drive = dr;
    }
    public static void tick(Gamepad gamepad){
        float POWER_MULT = 1f;
        if(gamepad.dpad_up){
            TrajectorySequence traj = drive.trajectorySequenceBuilder(new Pose2d(0,0,0)).forward(29).build();
            drive.followTrajectorySequenceAsync(traj);
        }
        if(gamepad.dpad_left){
            TrajectorySequence traj = drive.trajectorySequenceBuilder(new Pose2d(0,0,0)).strafeLeft(29).build();
            drive.followTrajectorySequenceAsync(traj);
        }
        if(gamepad.dpad_down){
            TrajectorySequence traj = drive.trajectorySequenceBuilder(new Pose2d(0,0,0)).back(29).build();
            drive.followTrajectorySequenceAsync(traj);
        }
        if(gamepad.dpad_right){
            TrajectorySequence traj = drive.trajectorySequenceBuilder(new Pose2d(0,0,0)).strafeRight(29).build();
            drive.followTrajectorySequenceAsync(traj);
        }
        if(gamepad.circle){
            drive.breakFollowing(); //Cancel drive if it's being goofy
        }
        if(gamepad.left_trigger > 0.1f){
            POWER_MULT = 0.5f;
        } else{
            POWER_MULT = 1.0f;
        }
        drive.setWeightedDrivePower(
                new Pose2d(
                        -gamepad.left_stick_y,
                        -gamepad.left_stick_x,
                        -gamepad.right_stick_x
                )
        );
        drive.update();
        Pose2d poseEstimate = drive.getPoseEstimate();
        telemetry.addData("x", poseEstimate.getX());
        telemetry.addData("y", poseEstimate.getY());
        telemetry.addData("heading", poseEstimate.getHeading());
    }
}
