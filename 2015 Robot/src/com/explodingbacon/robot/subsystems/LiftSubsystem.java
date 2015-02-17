package com.explodingbacon.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;

import com.explodingbacon.robot.CodeThread;
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
    
    boolean withinTolerance = false;
    
    double min = 0.2;
    double max = 0.3;
    double kP = 0.075;
    double kI = 0;
    double kI2 = 0;
    
    public double target = 0;
    
    public LiftSubsystem() {
    	if (/*Robot.self.isTest()*/ true) {
			SmartDashboard.putNumber("Change", 1);
			SmartDashboard.putNumber("liftKP", kP);
			SmartDashboard.putNumber("liftKI", kI);
			SmartDashboard.putNumber("liftKI2", kI2);
			SmartDashboard.putNumber("liftMin", min);
			SmartDashboard.putNumber("liftMax", max);
		}
    	liftEncoder.reset();
    }
    
    public void setRaw(double motorValue) {
		lift1.set(-motorValue);
		lift2.set(-motorValue);
		Robot.autonomous.add(new String[]{"lift", motorValue + ""});
    }
    
    public void home() {
    	setRaw(0);
    	
    	liftEncoder.reset();
    }
    
    public void getTarget() {
    	Direction dir = OI.xbox.getDPad();
    	if (dir.isNorth()) {
    		if (!atTop()) {
    			target = Position.ONE_TOTE_UP;
    		}
    	} else if (dir.isSouth()) {
    		if (!atBottom()) {
    			target = Position.BOTTOM;
    		}
    	}
    	//target = Util.minMax(target, Position.BOTTOM, Position.TOP);
    }
    
    public boolean atTarget() {
    	return withinTolerance; 
    }
    
    public boolean atTop() {
    	boolean status = false;
    	status = topLimit.get();
    	if (!status) {
    		status = liftEncoder.getRaw() >= Position.TOP;
    	}
    	return status;
    }
    
    public boolean atBottom() {
    	boolean status = false;
    	status = bottomLimit.get();
    	if (!status) {
    		status = liftEncoder.getRaw() <= Position.BOTTOM;
    	}
    	return status;
    }
    
    public void initDefaultCommand() {
    	//setDefaultCommand(new LiftControlCommand());
    }
    
    public void startThread() {
    	liftPThread = new LiftPThread();
    	liftPThread.start();
    }
    
    public void stopThread() {
    	if (liftPThread != null) {
    		liftPThread.stop();
    		liftPThread = null;
    	}
    }
    
    public class LiftPThread extends CodeThread {
    	
    	double error, p, i, setpoint;
    	boolean turnedOff = false;
    	
    	public LiftPThread() {
        	i = 0;
        	
        	getTarget();
        	
        	kP = SmartDashboard.getNumber("liftKP", kP);
    		kI = SmartDashboard.getNumber("liftKI", kI);
    		kI2 = SmartDashboard.getNumber("liftKI2", kI2);
    		min = SmartDashboard.getNumber("liftMin", min);
    		max = SmartDashboard.getNumber("liftMax", max);
    	}
    	
    	@Override
    	public void code() {
    		if (bottomLimit.get()) liftEncoder.reset();
    		
    		System.out.println("Lift encoder: " + Robot.lift.liftEncoder.getRaw());
    		
    		getTarget();
    		
    		SmartDashboard.putNumber("Target", target);
    		SmartDashboard.putBoolean("Top Limit", topLimit.get());
    		SmartDashboard.putBoolean("Bottom Limit", bottomLimit.get());
    		
    		error = target - liftEncoder.getRaw();

    		p = error;

    		i += error;

    		System.out.println("Raw set: " + (p*kP + i*kI));

    		setpoint = Util.minMax((p*kP + i*kI), min, max);
    		//setpoint += i*kI2;
    		
    		if (atTop() && Util.sign(setpoint) == 1) {
    			setpoint = 0;
    		} else if (atBottom() && Util.sign(setpoint) == -1) {
    			setpoint = 0;
    		}

    		setRaw(setpoint);

    		if(Math.abs(setpoint) <= min) withinTolerance = true;
    		else withinTolerance = false;
    		   		
			if (Robot.ds.isOperatorControl()) {
				if (liftEncoder.getRate() > 10) {
    				OI.xbox.rumble(0.2f, 0.2f);
    				turnedOff = false;
    			} else {
    				if (!turnedOff) {
    					OI.xbox.rumble(0, 0);
    					turnedOff = true;
    				}
    			}
				
    		}
    	}
    }
    
    public class Position {
    	public static final int BOTTOM = 0;
    	public static final int ONE_TOTE_UP = 1700;
    	public static final int TOP = 2100;
    }
}

