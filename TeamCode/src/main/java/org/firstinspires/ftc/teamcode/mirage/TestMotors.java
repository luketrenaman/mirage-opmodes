package org.firstinspires.ftc.teamcode.mirage;


import com .qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="Test Motors", group="Linear Opmode")
public class TestMotors extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        /*
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
         */
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("leftFront");
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("leftRear");
        DcMotor motorFrontRight = hardwareMap.dcMotor.get("rightFront");
        DcMotor motorBackRight = hardwareMap.dcMotor.get("rightRear");

        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        telemetry.log().add("Press Colored Buttons to move directionally, this version has extraLeftPower");

        waitForStart();

        if (isStopRequested()) return;
        double extraLeftPower = 1.0;
        double cFL = 1.0;
        double cFR = 1.0;
        double cBL = 1.0;
        double cBR = 1.0;
        while (opModeIsActive()) {
            telemetry.addData("extraLeftPower","%.4f",extraLeftPower);
            telemetry.addData("Motor Mult (FL, FR, BL, BR)","%.0f",cFL,cFR,cBL,cBR);
            telemetry.update();
            cFL = gamepad1.dpad_up ? -1.0 : 1.0;
            cFR = gamepad1.dpad_down ? -1.0 : 1.0;
            cBL = gamepad1.dpad_left ? -1.0 : 1.0;
            cBR = gamepad1.dpad_right ? -1.0 : 1.0;
            if(gamepad1.b){
                extraLeftPower += 0.00001;
            }
            if(gamepad1.x){
                extraLeftPower -= 0.00001;
            }
            if(gamepad1.a){
                motorFrontLeft.setPower(-0.8 * extraLeftPower * cFL);
                motorBackLeft.setPower(-0.8 * extraLeftPower * cBL);
                motorFrontRight.setPower(-0.8 * cFR);
                motorBackRight.setPower(-0.8 * cBR);
            }
            else if(gamepad1.y){
                motorFrontLeft.setPower(0.8 * extraLeftPower * cFL);
                motorBackLeft.setPower(0.8 * extraLeftPower * cBL);
                motorFrontRight.setPower(0.8 * cFR);
                motorBackRight.setPower(0.8 * cBR );
            } else{
                motorFrontLeft.setPower(0 * extraLeftPower);
                motorBackLeft.setPower(0);
                motorFrontRight.setPower(0 * extraLeftPower);
                motorBackRight.setPower(0);
                double y = -gamepad1.left_stick_y; // Remember, this is reversed!
                double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
                double rx = gamepad1.right_stick_x;


                // Denominator is the largest motor power (absolute value) or 1
                // This ensures all the powers maintain the same ratio, but only when
                // at least one is out of the range [-1, 1]
                double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                double frontLeftPower = (y + x + rx) / denominator * cFL;
                double backLeftPower = (y - x + rx) / denominator * cBL;
                double frontRightPower = (y - x - rx) / denominator * cFR;
                double backRightPower = (y + x - rx) / denominator * cBR;

                motorFrontLeft.setPower(frontLeftPower);
                motorBackLeft.setPower(backLeftPower);
                motorFrontRight.setPower(frontRightPower);
                motorBackRight.setPower(backRightPower);
            }


        }
    }
}