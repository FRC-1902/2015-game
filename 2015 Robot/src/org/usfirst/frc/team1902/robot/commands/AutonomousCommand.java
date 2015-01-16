package org.usfirst.frc.team1902.robot.commands;

import java.util.ArrayList;
import java.util.List;
import org.usfirst.frc.team1902.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class AutonomousCommand extends Command {

	public List<String[]> commands = new ArrayList<>();
	
    public AutonomousCommand(String auto) {
    	requires(Robot.recordedAutonomous);
    	for (String s : auto.split("]")) {
    		commands.add(s.split("|"));
    	}
    }

    protected void initialize() {
    }

    protected void execute() { //Note: If there are timing problems with actions taking place while driving, try having the code run the next command if it's not a drive command and the code that just ran IS a drive command
    	if (!commands.isEmpty()) {
    		String[] s = commands.get(0);
    		if (s[0].equals("drive")) {
    			Robot.drive.tankDrive(Double.parseDouble(s[1]), Double.parseDouble(s[2]));
    		} else if (s[0].equals("intakeMotor")) {
    			Robot.intake.setMotors(Boolean.parseBoolean(s[1]));
    		} else if (s[0].equals("reverseIntake")) {
    			Robot.intake.setReversed(Boolean.parseBoolean(s[1]));
    		} else if (s[0].equals("rotateIntake")) {
    			Robot.intake.setRotated(Boolean.parseBoolean(s[1]));
    		} else if (s[0].equals("intakeArms")) {
    			Robot.intake.setArms(Boolean.parseBoolean(s[1]));
    		} else if (s[0].equals("canGrabber")) {
    			Robot.canGrabber.setGrabber(Boolean.parseBoolean(s[1]));
    		} else if (s[0].equals("lift")) {
    			Robot.lift.lift(Integer.parseInt(s[1]));   		    		
    		} else if (s[0].equals("pushTote")) {
    			Robot.lift.pushTote();
    		}
    		commands.remove(commands.get(0));
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
