package org.usfirst.frc.team1902.robot.subsystems;

import org.usfirst.frc.team1902.robot.OI;
import org.usfirst.frc.team1902.robot.Robot;
import org.usfirst.frc.team1902.robot.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSubsystem extends Subsystem {
    
    //public Talon left = new Talon(RobotMap.leftIntakeTalon);
	public Talon left = new Talon(2);
    //public Talon right = new Talon(RobotMap.rightIntakeTalon);
	public Talon right = new Talon(3);
    //public Talon roller = new Talon(RobotMap.rollerTalon);
    public Solenoid arms = new Solenoid(RobotMap.intakeArmsSolenoid);
    public boolean motorStatus = false;
    public boolean reverse = false;
    public boolean rotate = false;

	public void setMotors(boolean status) {
		double power = OI.action.getZ();
		if (status == false) {
			left.set(0);
			right.set(0);
		} else {
			if (rotate) {
				left.set(power);
				right.set(power);
			} else {
				if (reverse) {
					left.set(power);
					right.set(-power);
				} else {
					left.set(-power);
					right.set(power);
				}
			}
		}
    	motorStatus = status;
    	Robot.addToAuto("intakeMotor|" + status);
    }
    
    public void setReversed(boolean status) {
    	reverse = status;
    	if (left.get() != 0) {
    		setMotors(true);
    	}
    	Robot.addToAuto("reverseIntake|" + status);    	
    }
    
    public void setRotated(boolean status) {
    	rotate = status;
    	if (left.get() != 0) {
    		setMotors(true);
    	}
    	Robot.addToAuto("rotateIntake|" + status);    	
    }
    
    public void setArms(boolean status) {
    	arms.set(status);
    	Robot.addToAuto("intakeArms|" + status);
    }
    
    public void initDefaultCommand() {
    }
}

