/* _    _                                      _   _____       _           _   
  | |  | |                                    | | |  __ \     | |         | |  
  | |  | |_ __  _ __   __ _ _ __ ___   ___  __| | | |__) |___ | |__   ___ | |_ 
  | |  | | '_ \| '_ \ / _` | '_ ` _ \ / _ \/ _` | |  _  // _ \| '_ \ / _ \| __|
  | |__| | | | | | | | (_| | | | | | |  __/ (_| | | | \ \ (_) | |_) | (_) | |_ 
   \____/|_| |_|_| |_|\__,_|_| |_| |_|\___|\__,_| |_|  \_\___/|_.__/ \___/ \__|
  
  
  Written for FIRST's 2015 FRC game "Recycle Rush"!
  
  All code is either sample code provided by FIRST or is hand-written by team 1902.

*/
package org.usfirst.frc.team1902.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team1902.robot.commands.AutonomousCommand;
import org.usfirst.frc.team1902.robot.subsystems.BinGrabberSubsystem;
import org.usfirst.frc.team1902.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1902.robot.subsystems.IntakeSubsystem;
import org.usfirst.frc.team1902.robot.subsystems.LiftSubsystem;
import org.usfirst.frc.team1902.robot.subsystems.AutonomousSubsystem;
                                                                             
public class Robot extends IterativeRobot {

	public static DriveSubsystem drive;
	public static IntakeSubsystem intake;
	public static BinGrabberSubsystem binGrabber;
	public static LiftSubsystem lift;
	public static AutonomousSubsystem autonomous;
	public static OI oi;
	public static Robot self;

    Command autonomousCommand = null;

    //Initialize our subsystems, autonomous, and Operator Interface (OI)
    public void robotInit() {
		System.out.println("Initializing DriveSubsystem...");
		drive = new DriveSubsystem();
		System.out.println("Initializing IntakeSubsystem...");
		intake = new IntakeSubsystem();
		//System.out.println("Initializing BinGrabberSubsystem");
		//binGrabber = new BinGrabberSubsystem();
		System.out.println("Initializing LiftSubsystem...");
		lift = new LiftSubsystem();
		System.out.println("Initializing AutonomousSubsystem...");
		autonomous = new AutonomousSubsystem();
		System.out.println("Initializing OI...");
		oi = new OI();
		System.out.println("Initializing AutonomousCommand...");
        autonomousCommand = new AutonomousCommand();
        System.out.println("Enabling intake arms and roller...");
		intake.setArms(true);
		//intake.roller.set(true);
		System.out.println("Robot initialization complete!");
		self = this;
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
}
