package org.usfirst.frc.team1902.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CompressorSubsystem extends Subsystem {

	public Compressor compressor = new Compressor();
	
	@Override
	protected void initDefaultCommand() {
		//setDefaultCommand(new CompressorCommand());
	}
}
