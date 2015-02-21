package com.explodingbacon.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;

import com.explodingbacon.robot.CodeThread;
import com.explodingbacon.robot.Lights.Action;
import com.explodingbacon.robot.Lights.Color;
import com.explodingbacon.robot.Lights.Strip;
import com.explodingbacon.robot.OI;
import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.RobotMap;
import com.explodingbacon.robot.Util;
import com.explodingbacon.robot.XboxController.Direction;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
                                                                                                           
public class LiftSubsystem extends Subsystem {
    
    public VictorSP lift1 = new VictorSP(RobotMap.liftVictor1);
    public VictorSP lift2 = new VictorSP(RobotMap.liftVictor2);
    public Encoder liftEncoder = new Encoder(RobotMap.liftEncoderA, RobotMap.liftEncoderB); //256 clicks
    public DigitalInput topLimit = new DigitalInput(RobotMap.liftTopLimit);
    public DigitalInput bottomLimit = new DigitalInput(RobotMap.liftBottomLimit);
    public LiftPThread liftPThread = new LiftPThread();
    
    boolean atTarget = false;
    boolean easingDown = false;
    
    double min = 0.2;
    double max = 0.3;
    double kP = 0.0025;
    double kI = 0;
    double kI2 = 0;
    
    double error, setpoint, p;
    
    public int target = Position.DONT_MOVE;
    public int privateTarget = Position.BOTTOM;
    public int loopTarget = privateTarget;
    public int deadzone = 50;
    
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
		Robot.autonomous.add(new String[]{"lift", motorValue + ""});
    }

    public void getTarget() {
    	Direction dir = OI.xbox.getDPad();
    	if (dir.isUp()) {
    		if (!atTop()) {
    			target = Position.TOP;
    			privateTarget = Position.TOP;
    		}
    	} else if (dir.isDown()) {
    		if (!atBottom()) {
    			target = Position.BOTTOM;
    			privateTarget = Position.BOTTOM;
    		}
    	}
    	if (Robot.oi.liftScoring.get()) { 
    		target = Position.SCORING; 
    		privateTarget = Position.SCORING; 
    	}
    }
    
    public boolean atTarget() {
    	return atTarget; 
    }
    
    public void waitForTarget() {
    	while (!atTarget()) {Timer.delay(0.1);};
    }
    
    public void setTargetAndWait(int i) {
    	target = i;
    	waitForTarget();
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
    
    public void initDefaultCommand() {
    	//setDefaultCommand(new LiftCommand());
    }
    
    public void startThread() {
    	liftPThread = new LiftPThread();
    	liftPThread.start();
    	setRaw(0);
    	target = Position.DONT_MOVE;
    	atTarget = true;
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
    		if (bottomLimit.get()) liftEncoder.reset();
    		
    		//System.out.println("Lift encoder: " + Robot.lift.liftEncoder.getRaw());
    		
    		if(!OI.xbox.getDPad().isDown()) {
    			if(easingDown) {
    				target = Position.BOTTOM;
    				privateTarget = Position.BOTTOM;
    				easingDown = false;
    			}
    			getTarget();
    		}
    		else {
    			easingDown = true;
    			privateTarget -= 0.25;
    			if(privateTarget <= 0) privateTarget = 0;
    		}
    		
    		loopTarget = privateTarget;
    		
    		//if(atTarget && privateTarget > 100) loopTarget -= 20;
    		
    		error = loopTarget - liftEncoder.getRaw();
    		
    		p = error;
    		
    		setpoint = p*kP;
    		
    		if(Math.abs(error) < deadzone || (Util.sign(error) == -1 && atBottom()) || (Util.sign(error) == 1 && atTop())) {
    			setpoint = 0;
    			atTarget = true;
    			target = Position.DONT_MOVE;
    		}
    		else {
    			atTarget = false;
    		}
    		
    		setpoint = Util.minMax(setpoint, 0, (OI.xbox.getDPad().isDown() ? 0.3 : max));
    		
    		setRaw(setpoint);
    		
			if (Robot.ds.isOperatorControl() && !Robot.ds.isDisabled()) {
				if (Math.abs(liftEncoder.getRate()) > 10) {
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
    	public static final int DONT_MOVE = -2;
    	public static final int STOPPED = -1;
    	public static final int BOTTOM = 0;
    	public static final int SCORING = 1700;
    	public static final int TOP = 2300;
    }
}

