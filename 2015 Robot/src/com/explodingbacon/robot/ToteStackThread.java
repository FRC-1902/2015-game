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
				Robot.intake.control = false;
				Strip.TOTE_CHUTE.fade(Color.ORANGE, Color.WHITE);
				Robot.intake.arms.set(true);		
				Robot.lift.setTargetAndWait(Position.TOP);
				Robot.intake.setMotors(0.6);
				Robot.intake.arms.set(true);
				Timer.delay(1.25);
				Robot.intake.setMotors(0);
				Robot.intake.arms.set(false);
				Robot.lift.setTargetAndWait(Position.BOTTOM);
				Robot.lift.setTargetAndWait(Position.TOP);
				Robot.intake.control = true;
			}
		} else {
			if (wasTrue) {
				Robot.intake.setRoller(false);
				wasTrue = false;
			}
		}
	}
}
