package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.Lights.Color;
import com.explodingbacon.robot.Lights.Strip;
import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.subsystems.LiftSubsystem.Position;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class ToteStackCommand extends Command {

    public ToteStackCommand() {
        requires(Robot.drive);
        requires(Robot.intake);
        requires(Robot.lift);       
    }

    protected void initialize() {
	}

	protected void execute() {
		Robot.intake.setRoller(true);
		Strip.TOTE_CHUTE.chase(Color.WHITE, Color.GREEN);
		while (!Robot.oi.doToteStack.get() && !Robot.intake.chuteTouchSensor.get()) {Timer.delay(0.1);}
		Strip.TOTE_CHUTE.fade(Color.ORANGE, Color.WHITE);
		Robot.intake.arms.set(true);		
		Robot.lift.setTargetAndWait(Position.TOP);
		Robot.intake.setMotors(1);
		Robot.intake.arms.set(true);
		Timer.delay(2);
		Robot.intake.setMotors(0);
		Robot.intake.arms.set(false);
		Robot.lift.setTargetAndWait(Position.BOTTOM);
		Robot.lift.setTargetAndWait(Position.TOP);
	}

	protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	wrapUp();
    }

    protected void interrupted() {
    	wrapUp();
    }
    
    public void wrapUp() {
    	Robot.intake.setRoller(false);
    }
}
