package org.usfirst.frc.team1902.robot.subsystems;

import org.usfirst.frc.team1902.robot.Robot;
import org.usfirst.frc.team1902.robot.RobotMap;
import org.usfirst.frc.team1902.robot.Util;
import org.usfirst.frc.team1902.robot.commands.LiftStopCommand;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

import org.usfirst.frc.team1902.robot.commands.LiftDPADCommand;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
                                                                                                           
public class LiftSubsystem extends Subsystem {
    
    public Talon lift = new Talon(RobotMap.liftTalon);
    public Solenoid totePusher = new Solenoid(RobotMap.totePusherSolenoid);
    public Encoder liftEncoder = new Encoder(RobotMap.liftEncoderA, RobotMap.liftEncoderB);
    public DigitalInput topLimit = new DigitalInput(RobotMap.liftTopLimit);
    public DigitalInput bottomLimit = new DigitalInput(RobotMap.liftBottomLimit);
    
    double min = 0.2;
    double max = 0.7;
    double kP = 0.5;
    double kI = 0.05;
    double kI2 = 0.0005;
    
    public LiftSubsystem()
    {
    	if (Robot.self.isTest()) {
			SmartDashboard.putNumber("liftKP", kP);
			SmartDashboard.putNumber("liftKI", kI);
			SmartDashboard.putNumber("liftKI2", kI2);
			SmartDashboard.putNumber("liftMin", min);
			SmartDashboard.putNumber("liftMax", max);
		}
    }
    
    public void initDefaultCommand() {
    	setDefaultCommand(new LiftStopCommand());
    }
    
    public void setRaw(double motorValue) {
		lift.set(motorValue);
		Robot.autonomous.add(new String[]{"lift", motorValue + ""});
    }
    
    public void pushTote() {
    	totePusher.set(true);
    	totePusher.set(false);
    	Robot.autonomous.add(new String[]{"pushTote"});
    }
    
    public void absoluteLift(double pos)
    {
    	double error, p, i, setpoint;
    	i = 0;
    	
    	kP = SmartDashboard.getNumber("liftKP", kP);
		kI = SmartDashboard.getNumber("liftKI", kI);
		kI2 = SmartDashboard.getNumber("liftKI2", kI2);
		min = SmartDashboard.getNumber("liftMin", min);
		max = SmartDashboard.getNumber("liftMax", max);
    	
    	do
    	{
    		error = pos - liftEncoder.getRaw();
    		
    		p = error;
    		
    		i += error;
    		
    		setpoint = minMax(p*kP + i*kI, min, max);
    		setpoint += i*kI2;
    		
    		lift.set(setpoint);
    	} while(!"pigs".equals("fly"));
    }
    
    public void home()
    {
    	setRaw(0);
    	
    	liftEncoder.reset();
    }
    
    public double minMax(double d, double min, double max) {
		double minMaxed = d;
		if (Math.abs(d) >= Math.abs(max)) {
			minMaxed = max * Util.sign(d);
		} else if (Math.abs(d) < Math.abs(min)) {
			minMaxed = 0;
		}
		return minMaxed;
	}
}

