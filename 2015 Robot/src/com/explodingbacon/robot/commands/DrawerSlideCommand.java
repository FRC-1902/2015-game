package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.OI;
import com.explodingbacon.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class DrawerSlideCommand extends Command {

    public DrawerSlideCommand() {
        requires(Robot.drawerSlides);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.drawerSlides.setDrawerSlides((OI.xbox.getRightTrigger() - OI.xbox.getLeftTrigger()) * 0.5);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
