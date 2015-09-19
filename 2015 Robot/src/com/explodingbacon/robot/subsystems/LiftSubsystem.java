package com.explodingbacon.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;

import com.explodingbacon.robot.CodeThread;
import com.explodingbacon.robot.Lights.Action;
import com.explodingbacon.robot.Lights.Color;
import com.explodingbacon.robot.Lights.Strip;
import com.explodingbacon.robot.OI;
import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.Robot.ControlType;
import com.explodingbacon.robot.RobotMap;
import com.explodingbacon.robot.Util;
import com.explodingbacon.robot.XboxController.Direction;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
                                                                                                           
public class LiftSubsystem extends Subsystem {
    
    public VictorSP lift1 = new VictorSP(RobotMap.liftVictor1);
    public VictorSP lift2 = new VictorSP(RobotMap.liftVictor2);
    public Solenoid liftPiston = new Solenoid(RobotMap.liftPiston);
    public Encoder liftEncoder = new Encoder(RobotMap.liftEncoderA, RobotMap.liftEncoderB); //256 clicks
    public DigitalInput topLimit = new DigitalInput(RobotMap.liftTopLimit);
    public DigitalInput bottomLimit = new DigitalInput(RobotMap.liftBottomLimit);
    public LiftPThread liftPThread = new LiftPThread();
    
    boolean atTarget = false;
    boolean easingDown = false;
    boolean isManual = false;
    
    double min = 0.2;
    double max = 0.3;
    double kP = 0.0025;
    double kI = 0;
    double kI2 = 0;
    
    double error, setpoint, p;
    
    public int target = Position.BOTTOM;
    public int oldTarget = Position.BOTTOM;
    public int loopTarget = target;
    public int deadzone = 25;
    
    public LiftSubsystem() {
    	if (Robot.self.isTest()) {
			SmartDashboard.putNumber("Change", 1);
			SmartDashboard.putNumber("liftKP", kP);
			SmartDashboard.putNumber("liftKI", kI);
			SmartDashboard.putNumber("liftKI2", kI2);
			SmartDashboard.putNumber("liftMin", min);
			SmartDashboard.putNumber("liftMax", max);
		}
    	liftEncoder.setDistancePerPulse(1);
    	liftEncoder.reset();
    }
    
    public void setPiston(Boolean on)
    {
    	liftPiston.set(on);
    }
    
    public void setRaw(double motorValue) {
		lift1.set(-motorValue);
		lift2.set(-motorValue);
		if (motorValue > 0) {
			Strip.ELEVATOR.chase(Color.ORANGE, Color.GREEN);
		} else if (motorValue < 0) {
			Strip.ELEVATOR.chase(Color.ORANGE, Color.GREEN, Action.REVERSE);
		} else {
			Strip.ELEVATOR.setColor(Color.ORANGE);
		}
    }

    public void getTarget() {
    	Direction dir = OI.xbox.getDPad();
		if (dir.isUp()) {
			target = Position.TOP;
		} else if (dir.isDown()) {
			target = Position.BOTTOM;
		}

		if (Robot.oi.liftScoring.get() || (Robot.controlType == ControlType.NORMAL && (OI.xbox.getDPad().isLeft() || OI.xbox.getDPad().isRight()))) {
			target = Position.SCORING;
		}
		
		if(OI.xbox.getY() > 0.8) target -= 5;
		if(OI.xbox.getY() < -0.8) target += 5;
	}
    
    public void setTarget(int i) {
    	target = i;
    	atTarget = false;
    }
    
    public void waitForTarget() {
    	while (!atTarget) {Timer.delay(0.05);};
    }
    
    public void setTargetAndWait(int i) {
    	setTarget(i);
    	waitForTarget();
    }
    
    public int stringToTarget(String s) {
    	if (s.equalsIgnoreCase("bottom")) {
    		return Position.BOTTOM;
    	} else if (s.equalsIgnoreCase("scoring")) {
    		return Position.SCORING;
    	} else if (s.equalsIgnoreCase("top")) {
    		return Position.TOP;
    	}
    	return -1;
    }
    
    public boolean atTop() {
    	boolean status = false;
    	status = topLimit.get();
    	if (!status) {
    		status = Math.abs(Position.TOP - liftEncoder.getRaw()) <= deadzone;
    	}
    	return status;
    }
    
    public boolean atBottom() {
    	boolean status = false;
    	status = bottomLimit.get();
    	if (!status) {
    		status = Math.abs(Position.BOTTOM - liftEncoder.getRaw()) <= deadzone;
    	}
    	return status;
    }
    
    public void stackTote() {
    	boolean oldControl = Robot.intake.control;
    	Robot.intake.control = false;
		Strip.TOTE_CHUTE.fade(Color.ORANGE, Color.WHITE);		
		Robot.lift.setTargetAndWait(Position.TOP);
		Robot.intake.arms.set(true);
		Robot.intake.setMotors(1);
		Timer.delay(1.25);
		Robot.intake.setMotors(0);
		Robot.intake.arms.set(false);
		Robot.lift.setTargetAndWait(Position.BOTTOM);
		Robot.lift.setTargetAndWait(Position.TOP);
		if (oldControl) Robot.intake.control = true;
    }
    
    public void initDefaultCommand() {}
    
    public void startThread() {
    	liftPThread = new LiftPThread();
    	liftPThread.start();
    	setRaw(0);
    	target = liftEncoder.getRaw();
    	atTarget = false;
    }
    
    public void stopThread() {
    	if (liftPThread != null) {
    		liftPThread.stop();
    		liftPThread = null;
    	}
    }
    
    public class LiftPThread extends CodeThread {
    	
    	double max = 0.6;
    	boolean xboxTurnedOff = false;
    	
    	public LiftPThread() {
        	kP = SmartDashboard.getNumber("liftKP", kP);
    		kI = SmartDashboard.getNumber("liftKI", kI);
    		kI2 = SmartDashboard.getNumber("liftKI2", kI2);
    		min = SmartDashboard.getNumber("liftMin", min);
    		max = SmartDashboard.getNumber("liftMax", max);    		
    	}
    	
    	@Override
    	public void code() {
    		if (bottomLimit.get()) {
    			liftEncoder.reset();
    			if (target < 0) {
    				target = 0;
    			}
    		}
    		/*
    		SmartDashboard.putBoolean("Top Limit", topLimit.get());
    		SmartDashboard.putBoolean("Bottom Limit", bottomLimit.get());
    		
    		SmartDashboard.putNumber("Target", target);
    		*/
    		//System.out.println("Lift encoder: " + Robot.lift.liftEncoder.getRaw());

    		if(!OI.xbox.getDPad().isDown()) {
    			if(easingDown) {
    				target = Position.BOTTOM;
    				easingDown = false;
    			}
    			getTarget();
    		} else {
    			easingDown = true;
    			target -= 0.25;
    			if(target < 0) target = 0;
    		}   		

    		//TODO Comment this out if things break
    		if(isManual && !OI.xbox.y.get()) target = liftEncoder.getRaw();
    		
    		if(target < 0) target = 0;
    		if (target > Position.TOP) target = Position.TOP;
    		
    		SmartDashboard.putNumber("Target", target);
    		
    		loopTarget = target;
    		
    		error = loopTarget - liftEncoder.getRaw();
    		
    		p = error;
    		
    		setpoint = p*kP;
    		
    		double appropriateMax = OI.xbox.getDPad().isDown() ? 0.3 : max;
    		
    		setpoint = Math.abs(setpoint) > appropriateMax ? appropriateMax * Util.sign(setpoint) : setpoint;
    		
    		//setpoint = Util.minMax(setpoint, 0, (OI.xbox.getDPad().isDown() ? 0.3 : max));
    		
    		if(Math.abs(error) < deadzone || (Util.sign(error) == -1 && atBottom()) || (Util.sign(error) == 1 && atTop())) {
    			setpoint = 0;
    			atTarget = true;
    		} else {
    			atTarget = false;
    		}
    		
    		if(!OI.xbox.y.get()) {
    			setRaw(setpoint);
    			isManual = false;
    		}
    		else {
    			isManual = true;
    			setRaw(-Util.minMax(OI.xbox.getY()/2, 0.1, 0.5));
    		}
    		
			if (Robot.ds.isOperatorControl() && !Robot.ds.isDisabled()) {
				if (Math.abs(liftEncoder.getRate()) > 5) {
    				OI.xbox.rumble(0.2f, 0.2f);
    				xboxTurnedOff = false;
    			} else {
    				if (!xboxTurnedOff) {
    					OI.xbox.rumble(0, 0);
    					xboxTurnedOff = true;
    				}
    			}
    		}
    	}
    }
    
    public class Position {
    	public static final int BOTTOM = 0;
    	public static final int SCORING = 800;
    	public static final int TOP = 2150;
    }
}

