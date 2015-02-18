package com.explodingbacon.robot.subsystems;

import com.explodingbacon.robot.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class BinGrabberSubsystem extends Subsystem {
    
    public Solenoid binGrabber = new Solenoid(RobotMap.binGrabberSolenoid);
    
    public void initDefaultCommand() {}
}

