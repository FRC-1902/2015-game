package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LiftPCommand extends Command {

	double setpoint = 0;

    public LiftPCommand(double sp) {
        requires(Robot.lift);
        
        if(sp > 0) setpoint = sp;
    }

    protected void initialize() {
    }

    protected void execute() {
    	if(Robot.lift.topLimit.get()) Robot.lift.setRaw(0);
    	else if(Robot.lift.bottomLimit.get()) Robot.lift.home();
    		else Robot.lift.absoluteLift(setpoint);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
