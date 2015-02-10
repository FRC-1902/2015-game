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

import java.io.File;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
	public static PowerDistributionPanel pdp;
	public static DriverStation ds;
	public static Robot self;
	public static double angle = 0;
	public static SendableChooser chooser = new SendableChooser();

    AutonomousCommand autonomousCommand = null;

    //Initialize our subsystems, autonomous, and Operator Interface (OI)
    public void robotInit() {
		self = this;
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
		System.out.println("Initializing Power Distribution Panel...");
		pdp = new PowerDistributionPanel();
		System.out.println("Intializing Driver Station...");
		ds = DriverStation.getInstance();
        System.out.println("Enabling intake arms and roller...");
		intake.setArms(true);
		//intake.roller.set(true);		
		intake.compressor.setClosedLoopControl(false);
		System.out.println("Initializing AutonomousCommand...");
		autonomousCommand = new AutonomousCommand();
		chooser.initTable(NetworkTable.getTable("TableThing"));
		File usbDir = new File("/u/.auto/");
		if (usbDir != null && usbDir.exists()) {
			for (File f : usbDir.listFiles()) {
				//if (f != null) {
					chooser.addDefault("default.auto", new File("/u/.auto/default.auto"));
					if (f.getName().contains(".auto")) {
						System.out.println("Found a valid autonomous file named '" + f.getName() + "'.");
						chooser.addObject(f.getName(), f);
					}
				//}
			}
		}
		SmartDashboard.putData("Chooser", chooser);
		autonomousCommand.actuallyInit();
		System.out.println("Robot initialization complete!");
    }
	
	public void disabledPeriodic() {
		SmartDashboard.putData("PDP", pdp);
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
		SmartDashboard.putData("PDP", pdp);
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
    	autonomous.disable();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
		SmartDashboard.putData("PDP", pdp);
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }   
    
    public static double sign(double d) {
    	double sign = 1;
    	if (d != 0) {
    		sign = (Math.abs(d) / d);
    	}
    	return sign;
    }
}
