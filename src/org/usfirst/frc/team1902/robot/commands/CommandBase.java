package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.subsystems.CompressorSubsystem;
import org.usfirst.frc.team1902.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CommandBase extends Command {

	public static DriveSubsystem drive;
	public static CompressorSubsystem compressor;
	
    public CommandBase() {
		System.out.println("Initializing Drive Subsystem");
        drive = new DriveSubsystem();
        System.out.println("Initilizing Compressor Subsystem");
        compressor = new CompressorSubsystem();
    }

    protected void initialize() {
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
