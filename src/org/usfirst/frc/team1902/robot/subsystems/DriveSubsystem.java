package org.usfirst.frc.team1902.robot.subsystems;

import org.usfirst.frc.team1902.robot.Robot;
import org.usfirst.frc.team1902.robot.RobotMap;
import org.usfirst.frc.team1902.robot.commands.TankDriveCommand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSubsystem extends Subsystem {
    
	public Talon left = new Talon(RobotMap.leftTalon);
	public Talon right = new Talon(RobotMap.rightTalon);

	/**
     * Driving based off of left and right inputs ranging from -1 to 1.
     */
    public void tankDrive(double left, double right) {
    	Robot.drive.left.set(left);
    	Robot.drive.right.set(right);    	
    }
    
    /**
     * Driving based off a single joystick's X and Y values.
     */
    public void arcadeDrive(Joystick joy) {
    	Robot.drive.left.set(joy.getX() + joy.getY());
    	Robot.drive.right.set(joy.getX() - joy.getY());
    }

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new TankDriveCommand());
	}
}


