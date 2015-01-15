/* _    _                                      _   _____       _           _   
  | |  | |                                    | | |  __ \     | |         | |  
  | |  | |_ __  _ __   __ _ _ __ ___   ___  __| | | |__) |___ | |__   ___ | |_ 
  | |  | | '_ \| '_ \ / _` | '_ ` _ \ / _ \/ _` | |  _  // _ \| '_ \ / _ \| __|
  | |__| | | | | | | | (_| | | | | | |  __/ (_| | | | \ \ (_) | |_) | (_) | |_ 
   \____/|_| |_|_| |_|\__,_|_| |_| |_|\___|\__,_| |_|  \_\___/|_.__/ \___/ \__|
  
  
  Written for FIRST's 2015 FRC game "Recycle Rush"!
  
  Written by Ryan Shavell & Dominic Canora.
  
  All code is either sample code provided by FIRST or is hand-written by team 1902.

*/
package org.usfirst.frc.team1902.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team1902.robot.commands.AutonomousCommand;
import org.usfirst.frc.team1902.robot.subsystems.CanGrabberSubsystem;
import org.usfirst.frc.team1902.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1902.robot.subsystems.IntakeSubsystem;
import org.usfirst.frc.team1902.robot.subsystems.RecordedAutonomousSubsystem;
                                                                             
public class Robot extends IterativeRobot {

	public static DriveSubsystem drive;
	public static IntakeSubsystem intake;
	public static CanGrabberSubsystem canGrabber;
	public static RecordedAutonomousSubsystem recordedAutonomous;
	public static OI oi;
	
	public static boolean recordAutonomous = false;
	public static String recordedAutonomousString = "";

    Command autonomousCommand;

    //Initialize our subsystems, autonomous, and Operator Interface (OI)
    public void robotInit() {
		oi = new OI();
		drive = new DriveSubsystem();
		intake = new IntakeSubsystem();
		canGrabber = new CanGrabberSubsystem();
		recordedAutonomous = new RecordedAutonomousSubsystem();
        autonomousCommand = new AutonomousCommand("hi");
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        //Schedule autonomous
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    public static void addToAuto(String add) {
    	if (Robot.recordAutonomous) {
			Robot.recordedAutonomousString = Robot.recordedAutonomousString + add;
		}
    }
}
