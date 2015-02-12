package com.explodingbacon.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import com.explodingbacon.robot.OI;
import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.RobotMap;
import com.explodingbacon.robot.Util;
import com.explodingbacon.robot.commands.LiftPCommand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
                                                                                                           
public class LiftSubsystem extends Subsystem {
    
    public VictorSP lift1 = new VictorSP(RobotMap.liftVictor1);
    public VictorSP lift2 = new VictorSP(RobotMap.liftVictor2);
    public Encoder liftEncoder = new Encoder(RobotMap.liftEncoderA, RobotMap.liftEncoderB);
    public DigitalInput topLimit = new DigitalInput(RobotMap.liftTopLimit);
    public DigitalInput bottomLimit = new DigitalInput(RobotMap.liftBottomLimit);
    
    boolean withinTolerance = false;
    
    double min = 0.2;
    double max = 0.7;
    double kP = 0.5;
    double kI = 0.05;
    double kI2 = 0.0005;
    
    public double target = 0;
    
    public LiftSubsystem() {
    	if (Robot.self.isTest()) {
			SmartDashboard.putNumber("liftKP", kP);
			SmartDashboard.putNumber("liftKI", kI);
			SmartDashboard.putNumber("liftKI2", kI2);
			SmartDashboard.putNumber("liftMin", min);
			SmartDashboard.putNumber("liftMax", max);
		}
    }
    
    public void setRaw(double motorValue) {
		lift1.set(motorValue);
		lift2.set(motorValue);
		Robot.autonomous.add(new String[]{"lift", motorValue + ""});
    }
    
    public void absoluteLift() {
    	double error, p, i, setpoint;
    	i = 0;
    	
    	getTarget();
    	
    	kP = SmartDashboard.getNumber("liftKP", kP);
		kI = SmartDashboard.getNumber("liftKI", kI);
		kI2 = SmartDashboard.getNumber("liftKI2", kI2);
		min = SmartDashboard.getNumber("liftMin", min);
		max = SmartDashboard.getNumber("liftMax", max);
    	
    	do
    	{
    		error = target - liftEncoder.getRaw();
    		
    		p = error;
    		
    		i += error;
    		
    		setpoint = Util.minMax(p*kP + i*kI, min, max);
    		setpoint += i*kI2;
    		
    		lift1.set(setpoint);
    		lift2.set(setpoint);
    		
    		if(Math.abs(setpoint) <= min) withinTolerance = true;
    		else withinTolerance = false;
    	} while(!"pigs".equals("fly"));
    }
    
    public void home() {
    	setRaw(0);
    	
    	liftEncoder.reset();
    }
    
    public void getTarget() {
    	double angle = OI.xbox.getDPad();
    	if (angle != -1) {
    		if (angle == 0 || angle == 45 || angle == 315) {
    			target += 1;
    		} else {
    			target -= 1;
    		}
    	}
    }
    
    public boolean atTarget() {
    	return withinTolerance; 
    }
    
    
    public void initDefaultCommand() {
    	setDefaultCommand(new LiftPCommand());
    }
}

