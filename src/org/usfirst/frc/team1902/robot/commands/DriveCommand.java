
package org.usfirst.frc.team1902.robot.commands;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1902.robot.Robot;

public class DriveCommand extends Command implements ActionListener {
	
	public Timer timer;
	public boolean timerOver = false;

    public DriveCommand() {
        requires(Robot.drive);
    }

    protected void initialize() {
    }

    protected void execute() {   	
    }

    protected boolean isFinished() {
        return timerOver;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
    public void drive(double left, double right) {
    	Robot.drive.left.set(left);
    	Robot.drive.right.set(right);
    }
    
    public void drive(double left, double right, double time) {
    	drive(left, right);
    	timer = new Timer(((int) time * 1000), this);
    	timer.start();
    }
    
    public void actionPerformed(ActionEvent a) {
    	drive(0, 0);
    	timer.stop();
    	timerOver = true;
    }
}
