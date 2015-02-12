package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.OI;
import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.subsystems.IntakeArmsSubsystem.Arm;
import com.explodingbacon.robot.subsystems.IntakeArmsSubsystem.State;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeArmsControlCommand extends Command {

	double deadZone = 0.2;
	
    public IntakeArmsControlCommand() {
        requires(Robot.intakeArms);
    }

    protected void initialize() {
    }

    protected void execute() {
    	if (Math.abs(OI.xbox.getLeftTrigger()) > deadZone) {
    		Robot.intakeArms.setArm(Arm.LEFT, State.CLOSED);
    	} else {
    		Robot.intakeArms.setArm(Arm.LEFT, State.OPEN);
    	}
    	
    	if (Math.abs(OI.xbox.getRightTrigger()) > deadZone) {
    		Robot.intakeArms.setArm(Arm.RIGHT, State.CLOSED);
    	} else {
    		Robot.intakeArms.setArm(Arm.RIGHT, State.OPEN);
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
