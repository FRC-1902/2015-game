/*  
  ______          _      _     _  __ _   
  | ___ \        | |    | |   (_)/ _| |  
  | |_/ /__  _ __| | __ | |    _| |_| |_ 
  |  __/ _ \| '__| |/ / | |   | |  _| __|
  | | | (_) | |  |   <  | |___| | | | |_ 
  \_|  \___/|_|  |_|\_\ \_____/_|_|  \__|                                          
  
  Written for FIRST's 2015 FRC game "Recycle Rush"!
  
  Written by Ryan Shavell and Dominic Canora.
  
  All code is either sample code provided by FIRST or written by team 1902.

*/
package com.explodingbacon.robot;

import java.io.File;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
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
import com.explodingbacon.robot.subsystems.DrawerSlideSubsystem;
import com.explodingbacon.robot.subsystems.DriveSubsystem;
import com.explodingbacon.robot.subsystems.IntakeSubsystem;
import com.explodingbacon.robot.subsystems.LiftSubsystem;
import com.explodingbacon.robot.subsystems.ToteStopSubsystem;
                                                                             
public class Robot extends IterativeRobot {

	public static DriveSubsystem drive;
	public static IntakeSubsystem intake;
	public static LiftSubsystem lift;
	public static DrawerSlideSubsystem drawerSlides;
	public static ToteStopSubsystem toteStop;
	public static OI oi;
	public static PowerDistributionPanel pdp;
	public static DriverStation ds;
	public static Robot self;
	public static double angle = 0;
	public static SendableChooser autoChooser = new SendableChooser();	
	public static SendableChooser controlTypeChooser = new SendableChooser();	
	public static ToteStackThread tst;
	
	public static boolean arcadeDrive = true;
	public static ControlType controlType = ControlType.COMPLEX;

	Timer chooserUpdater = new Timer(10, new TimerUser() {
		@Override
		public void timer() {
			SmartDashboard.putData("Chooser", autoChooser);
		}

		@Override
		public void timerStop() {			
		}		
	});

	AutonomousCommand autonomousCommand = null;

	public void robotInit() {
		System.out.println("Pork Lift initializing...");
		self = this;
		drive = new DriveSubsystem();
		intake = new IntakeSubsystem();
		lift = new LiftSubsystem();
		drawerSlides = new DrawerSlideSubsystem();
		toteStop = new ToteStopSubsystem();
		oi = new OI();
		pdp = new PowerDistributionPanel();
		ds = DriverStation.getInstance();
		autonomousCommand = new AutonomousCommand();
		tst = new ToteStackThread();
		//	intake.compressor.setClosedLoopControl(false);
		autoChooser.initTable(NetworkTable.getTable("TableThing"));
		File usbDir = new File("/u/auto/");
		if (usbDir != null && usbDir.exists()) {
			for (File f : usbDir.listFiles()) {
				autoChooser.addDefault("default.auto", new File("/u/auto/default.auto"));
				if (f.getName().contains("auto")) {
					//System.out.println("Found a valid autonomous file named '" + f.getName() + "'.");
					autoChooser.addObject(f.getName(), f);
				}
			}
		}
		chooserUpdater.start();
		chooserUpdater.user.timer();
		controlTypeChooser.initTable(NetworkTable.getTable("TableThing"));
		controlTypeChooser.addObject("Complex Controls (Experienced Driver)", ControlType.COMPLEX);
		controlTypeChooser.addDefault("Normal Controls (Team member)", ControlType.NORMAL);
		controlTypeChooser.addObject("Singleplayer Controls (Demo)", ControlType.SINGLEPLAYER);
		SmartDashboard.putData("Control Type Chooser", controlTypeChooser);
		SmartDashboard.putNumber("Delay", 0);
		SmartDashboard.putBoolean("Arcade Drive", true);
		disabled();
		System.out.println("Pork Lift initialized!");
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
		toteStop.set(true);
	}

	public void disabledInit(){
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
		
		arcadeDrive = SmartDashboard.getBoolean("Arcade Drive", true);		
		controlType = (ControlType) controlTypeChooser.getSelected();
		oi.initControls();
		
		OI.xbox.rumble(0.5f, 0.5f, 1);
		teleopLights();       
		lift.liftPiston.set(false);
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
    
    public enum ControlType {
    	COMPLEX,
    	NORMAL,
    	SINGLEPLAYER
    }
}
