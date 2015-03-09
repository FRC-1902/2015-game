package com.explodingbacon.robot.subsystems;

import com.explodingbacon.robot.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Wings extends Subsystem {
    
    public Solenoid wings = new Solenoid(RobotMap.wings);
    
    public void set(boolean b) {
    	wings.set(b);
    }
    
    public void initDefaultCommand() {}
}

