package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.Lights.Color;
import com.explodingbacon.robot.Lights.Strip;
import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.subsystems.LiftSubsystem.Position;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class HumanPlayerStackCommand extends Command {
	
    public HumanPlayerStackCommand() {
        requires(Robot.drive);
        requires(Robot.intake);
        requires(Robot.lift);       
    }

    protected void initialize() {
    }

    protected void execute() {
    	int totesDone = 0;
    	Robot.intake.arms.set(true);
    	Robot.lift.target = Position.ONE_TOTE_UP;
    	while (!Robot.lift.atTarget()) {}
    	while (totesDone < 6) {
    		Strip.TOTE_CHUTE.chase(Color.WHITE, Color.GREEN);
    		while (!Robot.intake.chuteTouchSensor.get()) {}
    		Strip.TOTE_CHUTE.fade(Color.ORANGE, Color.WHITE);
    		Robot.intake.setMotors(1);
    		Robot.intake.arms.set(true);
    		Timer.delay(2);
    		Robot.intake.setMotors(0);
    		Robot.intake.arms.set(false);
    		Robot.lift.target = Position.BOTTOM;   
    		while (!Robot.lift.atTarget()) {}
    		Robot.lift.target = Position.ONE_TOTE_UP;
    		while (!Robot.lift.atTarget()) {}
    		totesDone++; 
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
