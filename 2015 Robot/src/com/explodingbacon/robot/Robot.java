/*  
  ______          _      _     _  __ _   
  | ___ \        | |    | |   (_)/ _| |  
  | |_/ /__  _ __| | __ | |    _| |_| |_ 
  |  __/ _ \| '__| |/ / | |   | |  _| __|
  | | | (_) | |  |   <  | |___| | | | |_ 
  \_|  \___/|_|  |_|\_\ \_____/_|_|  \__|                                          
  
  Written for FIRST's 2015 FRC game "Recycle Rush"!
  
  Written by Ryan Shavell and Dominic Canora.
  
  All code is either sample code provided by FIRST or hand-written by team 1902.

*/
package com.explodingbacon.robot;

import java.io.File;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.explodingbacon.robot.Lights.Action;
import com.explodingbacon.robot.Lights.Color;
import com.explodingbacon.robot.Lights.Strip;
import com.explodingbacon.robot.commands.AutonomousCommand;
import com.explodingbacon.robot.subsystems.AutonomousSubsystem;
import com.explodingbacon.robot.subsystems.Wings;
import com.explodingbacon.robot.subsystems.DrawerSlideSubsystem;
import com.explodingbacon.robot.subsystems.DriveSubsystem;
import com.explodingbacon.robot.subsystems.IntakeSubsystem;
import com.explodingbacon.robot.subsystems.LiftSubsystem;
                                                                             
public class Robot extends IterativeRobot {

	public static DriveSubsystem drive;
	public static IntakeSubsystem intake;
	public static Wings wings;
	public static LiftSubsystem lift;
	public static DrawerSlideSubsystem drawerSlides;
	public static AutonomousSubsystem autonomous;
	public static OI oi;
	public static PowerDistributionPanel pdp;
	public static DriverStation ds;
	public static Robot self;
	public static double angle = 0;
	public static SendableChooser chooser = new SendableChooser();	
	public static ToteStackThread tst;

    AutonomousCommand autonomousCommand = null;

    public void robotInit() {
    	System.out.println("Initializing the Robot...");
		self = this;
		drive = new DriveSubsystem();
		intake = new IntakeSubsystem();
		wings = new Wings();
		lift = new LiftSubsystem();
		drawerSlides = new DrawerSlideSubsystem();
		autonomous = new AutonomousSubsystem();
		oi = new OI();
		pdp = new PowerDistributionPanel();
		ds = DriverStation.getInstance();
		autonomousCommand = new AutonomousCommand();
		tst = new ToteStackThread();
	//	intake.compressor.setClosedLoopControl(false);
		chooser.initTable(NetworkTable.getTable("TableThing"));
		File usbDir = new File("/u/auto/");
		if (usbDir != null && usbDir.exists()) {
			for (File f : usbDir.listFiles()) {
				chooser.addDefault("default.auto", new File("/u/auto/default.auto"));
				if (f.getName().contains("auto")) {
					System.out.println("Found a valid autonomous file named '" + f.getName() + "'.");
					chooser.addObject(f.getName(), f);
				}
			}
		}
		SmartDashboard.putData("Chooser", chooser);
		SmartDashboard.putNumber("Delay", 0);
		disabled();
		System.out.println("Robot initialization complete!");
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		periodic();
	}

    public void autonomousInit() {
        if (autonomousCommand != null) {
        	autonomousCommand.start();
        }
        init();
        Strip.BACK.chase(Color.ORANGE, Color.GREEN);
    }

    public void autonomousPeriodic() {
		periodic();
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();  
        init();
        OI.xbox.rumble(0.5f, 0.5f, 1);
        teleopLights();       
    }

    public void disabledInit(){
    	autonomous.disable();
    	disabled();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
		periodic();	
    }
    
    public void testPeriodic() {
        LiveWindow.run();
        periodic();
    }
    
    public void init() {
    	lift.startThread();
    }
    
    public void periodic() {
    	SmartDashboard.putData("PDP", pdp);
    }
    
    public void disabled() {
    	lift.stopThread();
    	Strip.TOTE_CHUTE.driverStation(getAllianceColor(), getDSLocation());
    	Strip.BACK.driverStation(getAllianceColor(), getDSLocation());
    	Strip.ELEVATOR.chase(Color.OFF, getAllianceColor());
    	Strip.ARC.fade(Color.RED, Color.OFF);
    }    
    
    public void teleopLights() {
    	Strip.BACK.fade(getAllianceColor(), Color.GREEN);
    	Strip.ARC.arcSpark();
    	Strip.ELEVATOR.setColor(Color.ORANGE);
    }
    
    public Color getAllianceColor() {
    	return ds.getAlliance() == Alliance.Blue ? Color.BLUE : Color.RED;
    }
    
    public Action getDSLocation() {
    	int location = ds.getLocation();
    	if (location == 1) return Action.DS_ID1;
    	if (location == 2) return Action.DS_ID2;
    	if (location == 3) return Action.DS_ID3;
    	return Action.DS_ID1;
    }
    
    public enum State {
    	OPEN,
    	CLOSED,
    	FORWARDS,
    	BACKWARDS
    }
}
