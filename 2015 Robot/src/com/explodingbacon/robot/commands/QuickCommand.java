package com.explodingbacon.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class QuickCommand extends Command {

	/*
	 * QuickCommand is used to quickly make commands instead of making a separate class for them.
	 * You're supposed to create an anonymous inner-classes of this and then override whatever you want.
	 */
	
    public QuickCommand() {
    	
    	//TODO Make sure that creating new magical subsystems like this won't cause any problems (i.e. memory leak or not working at all)
    	
    	//This is done so that there is NO CHANCE of two quick commands interfering with each other.
        requires(new Subsystem() {
			@Override
			protected void initDefaultCommand() {}        	
        });
    }

    protected void initialize() {}

    protected void execute() {}

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	done();
    }

    protected void interrupted() {
    	done();
    }
    
    /**
     * Convenience function for code you want to run in both end() and interrupted().
     */
    protected void done() {}
}
