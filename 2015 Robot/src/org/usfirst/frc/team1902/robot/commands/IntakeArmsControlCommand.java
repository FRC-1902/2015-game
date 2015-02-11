package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.OI;
import org.usfirst.frc.team1902.robot.Robot;
import org.usfirst.frc.team1902.robot.subsystems.IntakeArmsSubsystem.Arm;
import org.usfirst.frc.team1902.robot.subsystems.IntakeArmsSubsystem.State;

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
