package org.usfirst.frc.team1902.robot.subsystems;

import org.usfirst.frc.team1902.robot.Robot;
import org.usfirst.frc.team1902.robot.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CanGrabberSubsystem extends Subsystem {
    
    public Solenoid canGrabber = new Solenoid(RobotMap.canGrabberSolenoid);
    
    public void setGrabber(boolean state) {
    	canGrabber.set(state);
    	Robot.addToAuto("canGrabber|" + state);
    }
    
    public void initDefaultCommand() {
    }
}

