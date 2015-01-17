package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.Robot;
import org.usfirst.frc.team1902.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class DriveTypeToggleCommand extends Command {

	Boolean status = null;
	
    public DriveTypeToggleCommand() {
        requires(Robot.drive);
    }
    
    public DriveTypeToggleCommand(boolean status) {
        requires(Robot.drive);
        this.status = status;
    }

    protected void initialize() {
    	RobotMap.arcadeDrive = status == null? !RobotMap.arcadeDrive : status;
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
