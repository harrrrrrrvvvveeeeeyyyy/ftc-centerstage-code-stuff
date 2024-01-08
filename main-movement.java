package org.firstinspires.ftc.teamcode;
//package org.firstinspires.ftc.ftccommon.internal;
//package org.firstinspires.ftc.ftccommon.external;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

//import frc.robot.RobotContainer;
//import frc.robot.Constants;

@TeleOp(name = "Main Movement Script")
public class MainMovement extends LinearOpMode {

  /**
   * This function is executed when this Op Mode is selected from the Driver Station. SO DO NOT TOUCH THIS.
   */
  DcMotor FR;
  DcMotor BR;
  DcMotor BL;
  DcMotor FL;
  DcMotor INTAKE;
  //DcMotor DRONE;
  DcMotor LARM;
  DcMotor RARM;
  CRServo LCLAW;
  CRServo RCLAW;
  //DcMotor LIFT;
  double LSY;
  double LSX;
  double RSX;
  double RSY;
  double LSYR;
  double LSXR;
  double RSXR;
  double RSYR;
  int modeSwitch = 0;
  boolean canSwitch = true;
  @Override
  public void runOpMode() {
    // Put initialization blocks here.
    FR = hardwareMap.dcMotor.get("FR");
    BR = hardwareMap.dcMotor.get("BR");
    BL = hardwareMap.dcMotor.get("BL");
    FL = hardwareMap.dcMotor.get("FL");
    INTAKE = hardwareMap.dcMotor.get("intake");
    LARM = hardwareMap.dcMotor.get("LARM");
    RARM = hardwareMap.dcMotor.get("RARM");
    //DRONE = hardwareMap.dcMotor.get("drone"); // added, not sure how it works
    LCLAW = hardwareMap.crservo.get("lclaw");
    RCLAW = hardwareMap.crservo.get("rclaw");
    //LIFT = hardwareMap.dcMotor.get("lift");

    FL.setDirection(DcMotor.Direction.REVERSE);
    BL.setDirection(DcMotor.Direction.REVERSE);
    waitForStart();
    if (opModeIsActive()) {
      /*if (gamepad1.a){
        modeSwitch++;
        if (modeSwitch % 2 == 1)
      }*/
      while (opModeIsActive()) {
        if (gamepad1.a && canSwitch){
          modeSwitch++;
          canSwitch = false;
        }
        if (!(gamepad1.a)){
          canSwitch = true;
        }
        if (modeSwitch % 2 == 0){
          LSY = gamepad1.left_stick_y / 2;
          LSX = gamepad1.left_stick_x / 2;
          RSX = gamepad1.right_stick_x / 2;
          RSY = 0;
          FL.setPower(- LSY + LSX + RSX);
          FR.setPower(- LSY - LSX - RSX);
          BL.setPower(- LSY - LSX + RSX);
          BR.setPower(- LSY + LSX - RSX);
        }else if (modeSwitch % 2 == 1){
          LSYR = - gamepad1.left_stick_y / 2;
          LSXR = - gamepad1.left_stick_x / 2;
          RSXR = - gamepad1.right_stick_x / 2;
          RSYR = 0;
          FL.setPower(- LSYR + LSXR + RSXR);
          FR.setPower(- LSYR - LSXR - RSXR);
          BL.setPower(- LSYR - LSXR + RSXR);
          BR.setPower(- LSYR + LSXR - RSXR);
        }


        //INTAKE.setPower(gamepad1.right_trigger);
        if(gamepad1.left_trigger > .2){
          LARM.setPower(-gamepad1.left_trigger);     //ARM move in
          RARM.setPower(-gamepad1.left_trigger);
        }
        else if(gamepad1.right_trigger > .2){
          LARM.setPower(gamepad1.right_trigger);     // ARM move out
          RARM.setPower(gamepad1.right_trigger);     
        }
        else{
          LARM.setPower(0);
          RARM.setPower(0);
        }
        
        LARM.setPower(-gamepad1.left_trigger);
        RARM.setPower(-gamepad1.left_trigger); 


        if(gamepad1.right_bumper){                   //claw close move
          LCLAW.setPower(.3);
          RCLAW.setPower(.3);
        } else if(gamepad1.left_bumper){
          LCLAW.setPower(-.3);                        //claw open move
          RCLAW.setPower(-.3);
        //} else {
          //LCLAW.setPower(0);
          //RCLAW.setPower(0);
        }

        /* 
        if(gamepad1.y){
          DRONE.setPower(1);                         // setting power for drone
        } else{
          DRONE.setPower(0);
        } 
        */
        
        if(gamepad1.x){
          INTAKE.setPower(.5);                          // setting movement power
        } else{
          INTAKE.setPower(0);
        }

        /* 
        if(gamepad1.b){                              // lifting the robot up
          LIFT.setPower(.5);
        } else {
          LIFT.setPower(0);
        }
        */

      /*
        if (gamepad1.a) {
          FL.setPower(0.5);
          FR.setPower(0.5);
          BL.setPower(0.5);
          BR.setPower(0.5);
        }*/
        telemetry.addData("LSY ", LSY);
        telemetry.addData("LSX ", LSX);
        telemetry.addData("RSY ", RSY);
        telemetry.addData("RSX ", RSX);
        telemetry.addData("LSYR ", LSYR);
        telemetry.addData("LSXR ", LSXR);
        telemetry.addData("RSYR ", RSYR);
        telemetry.addData("RSXR ", RSXR);
        telemetry.addData("Reversed? ", !(modeSwitch % 2 == 0));
        telemetry.addData("Can Switch? ", canSwitch);
        telemetry.update();
      }
    }
  }
}
