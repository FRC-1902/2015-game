package org.usfirst.frc.team1902.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutonomousCommand extends CommandGroup {
    
	//Use addSequential(command) or addParallel(command)
    public  AutonomousCommand() {
    	addSequential(new AutonomousDriveCommand(0.5, 0.5, 1));
    	addSequential(new WaitCommand(1));
    	addSequential(new AutonomousDriveCommand(-0.5, 0.5, 1));
    }
}
