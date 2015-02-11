package com.explodingbacon.robot.subsystems;

import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class BinGrabberSubsystem extends Subsystem {
    
    public Solenoid canGrabber = new Solenoid(RobotMap.canGrabberSolenoid);
    
    public void setGrabber(boolean state) {
    	canGrabber.set(state);
    	Robot.autonomous.add(new String[]{"canGrabber", state + ""});
    }
    
    public void initDefaultCommand() {
    }
}
