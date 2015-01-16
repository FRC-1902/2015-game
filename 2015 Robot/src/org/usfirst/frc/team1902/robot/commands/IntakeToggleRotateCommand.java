package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class IntakeToggleRotateCommand extends Command {

	Boolean rotate = null;
	
    public IntakeToggleRotateCommand() {
        requires(Robot.intake);
    }
    
    public IntakeToggleRotateCommand(boolean rotate) {
        requires(Robot.intake);
        this.rotate = rotate;
        
    }

    protected void initialize() {
    	Robot.intake.setRotated(rotate == null ? !Robot.intake.rotate : rotate);
    }

    protected void execute() { 	
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
