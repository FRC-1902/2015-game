
package org.usfirst.frc.team1902.robot.subsystems;

import org.usfirst.frc.team1902.robot.commands.TankDriveCommand;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSubsystem extends Subsystem {
    
	public Talon left = new Talon(0);
	public Talon right = new Talon(1);

    public void initDefaultCommand() {
    	setDefaultCommand(new TankDriveCommand());
    }
}


