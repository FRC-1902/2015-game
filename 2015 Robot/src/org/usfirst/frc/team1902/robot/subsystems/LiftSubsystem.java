package org.usfirst.frc.team1902.robot.subsystems;

import org.usfirst.frc.team1902.robot.Robot;
import org.usfirst.frc.team1902.robot.RobotMap;
import org.usfirst.frc.team1902.robot.commands.LiftDPADCommand;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
                                                                                                           
public class LiftSubsystem extends Subsystem {
    
    public Talon lift = new Talon(RobotMap.liftTalon);
    public Solenoid totePusher = new Solenoid(RobotMap.totePusherSolenoid);
    
    public void lift(double motorValue) {
		lift.set(motorValue);
		Robot.autonomous.add(new String[]{"lift", motorValue + ""});
    }
    
    public void pushTote() {
    	totePusher.set(true);
    	totePusher.set(false);
    	Robot.autonomous.add(new String[]{"pushTote"});
    }
    
    public void initDefaultCommand() {
    	setDefaultCommand(new LiftDPADCommand());
    }
}

