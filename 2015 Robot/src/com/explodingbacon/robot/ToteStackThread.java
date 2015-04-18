package com.explodingbacon.robot;

import com.explodingbacon.robot.Lights.Color;
import com.explodingbacon.robot.Lights.Strip;
import com.explodingbacon.robot.subsystems.LiftSubsystem.Position;

import edu.wpi.first.wpilibj.Timer;

public class ToteStackThread extends CodeThread {

	boolean wasTrue = false;
	
	public ToteStackThread() {
		start();
	}

	public void code() {
		if (Robot.oi.toteStack.get()) {
			Robot.lift.setTarget(Position.TOP);
			wasTrue = true;
			Robot.intake.setRoller(true);
			Strip.TOTE_CHUTE.chase(Color.WHITE, Color.GREEN);
			boolean hasTote = false;
			while (!(Robot.oi.doToteStack.get() || hasTote) && Robot.oi.toteStack.get()) {
				if (Robot.intake.chuteTouchSensor.get()) {
					Timer.delay(0.75);
					hasTote = true;
				} else {
					Timer.delay(0.05);
				}
			}
			if (Robot.oi.toteStack.get()) {
				//Robot.lift.stackTote();;		
				Robot.lift.setTargetAndWait(Position.BOTTOM);
				Robot.lift.setTargetAndWait(Position.TOP);
			}
		} else {
			if (wasTrue) {
				Robot.intake.setRoller(false);
				wasTrue = false;
			}
		}
	}
}
