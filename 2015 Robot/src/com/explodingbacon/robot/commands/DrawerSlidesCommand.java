package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.OI;
import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.Robot.ControlType;

import edu.wpi.first.wpilibj.command.Command;

public class DrawerSlidesCommand extends Command {

    public DrawerSlidesCommand() {
        requires(Robot.drawerSlides);
    }

    protected void initialize() {}

    protected void execute() {
    	double speed = 0;

    	if (Robot.controlType == ControlType.NORMAL) {
    		speed = OI.xbox.getY2();    	
    	} else {
    		speed = OI.xbox.getLeftTrigger() - OI.xbox.getRightTrigger();
    	}

    	if (Math.abs(speed) > 0.1) {
    		Robot.drawerSlides.set(speed * 0.5);
    	} else {
    		Robot.drawerSlides.set(0);
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}