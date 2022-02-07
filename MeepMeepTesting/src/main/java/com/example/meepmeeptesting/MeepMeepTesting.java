package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static Vector2d transform(double xIn,double yIn){
        double theta = Math.toRadians(-90);
        double x = ( xIn - 72 ) * Math.cos(theta) - (yIn - 72) * Math.sin(theta);
        double y = ( yIn - 72 ) * Math.cos(theta) + (xIn - 72) * Math.sin(theta);

        Vector2d output = new Vector2d(x,y);
        return output;
    }
    public static void main(String[] args) {
        double ROT = 90;
        MeepMeep meepMeep = new MeepMeep(800);
        Vector2d pos2 = transform(8.0625,35.5);
        Vector2d pos3 = transform(9.9525,14.216);
        Vector2d pos4 = transform(23.8132,28.757);
        Vector2d pos5 = transform(8.0625,82);
        Vector2d pos6 = transform(8.0625,109);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(pos2.getX(), pos2.getY(), Math.toRadians(0 - ROT))) //Start
                                .lineToLinearHeading(new Pose2d(pos3.getX(), pos3.getY(), Math.toRadians(31 - ROT))) //Flywhee
                                .lineToLinearHeading(new Pose2d(pos4.getX(), pos4.getY(), Math.toRadians(31 - ROT))) //Ship
                                .lineToLinearHeading(new Pose2d(pos5.getX(), pos5.getY(), Math.toRadians(-90 - ROT))) //Wall
                                .lineToLinearHeading(new Pose2d(pos6.getX(), pos6.getY(), Math.toRadians(-90 - ROT))) //Park
                                .build()
                );
        myBot.setDimensions(12,16);
        meepMeep.setBackground(MeepMeep.Background.FIELD_FREIGHTFRENZY_ADI_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}