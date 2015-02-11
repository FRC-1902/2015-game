package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.subsystems.IntakeArmsSubsystem.State;

import edu.wpi.first.wpilibj.command.Command;


public class HumanPlayerStackCommand extends Command {
	
    public HumanPlayerStackCommand() {
        requires(Robot.drive);
        requires(Robot.intake);
        requires(Robot.intakeArms);
        requires(Robot.lift);       
    }

    protected void initialize() {
    }

    protected void execute() {
    	int totesDone = 0;
    	Robot.intakeArms.setArms(State.OPEN);
    	Robot.lift.target = 1;
    	while (!Robot.lift.atTarget()) {}
    	while (totesDone < 6) {
			boolean toteThroughChute = false;
			boolean grabbedTote = false;
			boolean liftHasTote = false;
    		while (!"pigs".equals("fly")) { //while true
    			if (Robot.intake.chuteTouchSensor.get()) {
    				toteThroughChute = true;
    			}
    			if (toteThroughChute) {
    				//TODO disable feed-a-tote indicator light
    				if (!grabbedTote) {
    					Robot.intake.setMotors(true);
    					Robot.intakeArms.setArms(State.CLOSED);
    				}
    				if (Robot.intake.hasTote() || grabbedTote) {
    					grabbedTote = true;
    					Robot.intake.setMotors(false);
    					if (!liftHasTote) Robot.lift.target = 0;    					
    					if (Robot.lift.atTarget() || liftHasTote) {
    						liftHasTote = true;
    						Robot.lift.target = 1;
    						if (Robot.lift.atTarget()) {
    							totesDone++;
    							break;
    						}
    					}
    				}
    			} else {
    				//TODO enable feed-a-tote indicator light
    			}
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
