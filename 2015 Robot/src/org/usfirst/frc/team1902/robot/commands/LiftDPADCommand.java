package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.OI;
import org.usfirst.frc.team1902.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LiftDPADCommand extends Command {

    public LiftDPADCommand() {
        requires(Robot.lift);
    }

    protected void initialize() {
    }

    protected void execute() {
    	double angle = OI.manipulator.getPOV(0);
    	if (angle != -1) {
    		if (angle == 0 || angle == 45 || angle == 315) {
    			//do a liftPCommand to whatever encoder value we want
    		} else {
    			//do a liftPCommand to whatever encoder value we wan
    		}
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
