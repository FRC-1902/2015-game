package org.usfirst.frc.team1902.robot.subsystems;

import org.usfirst.frc.team1902.robot.Robot;
import org.usfirst.frc.team1902.robot.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LiftSubsystem extends Subsystem {
    
    //public Talon lift = new Talon(RobotMap.liftTalon);
	public Talon lift = new Talon(4);
    public Solenoid totePusher = new Solenoid(RobotMap.totePusherSolenoid);
    
    public void lift(int motorValue) {
		lift.set(motorValue);
    	Robot.addToAuto("lift|" + motorValue);
    }
    
    public void pushTote() {
    	totePusher.set(true);
    	totePusher.set(false);
    	Robot.addToAuto("pushTote");
    }
    
    public void initDefaultCommand() {
    }
}

