package org.usfirst.frc.team1902.robot.subsystems;

import java.util.ArrayList;
import java.util.List;
import org.usfirst.frc.team1902.robot.Robot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class AutonomousSubsystem extends Subsystem {
    
	public List<String[]> data;
	public boolean recording = false;
	public Solenoid light = new Solenoid(6);
	//public Timer timer = null;
    //public NetworkTable table = null;
    
    public void enable() {
    	//if (table == null) {  			
			//table = NetworkTable.getTable("autonomous");
    	//}
		recording = true;
		data = new ArrayList<>();
		Robot.drive.leftEncoder.reset();
		Robot.drive.rightEncoder.reset();
		Timer.delay(0.25);
		if (Math.abs(Robot.drive.leftEncoder.getDistance()) <= 15) {
			light.set(true);
		}
		//timer = new Timer(1000, this);
		//timer.start();
		System.out.println("Recording teleop actions for autonomous!");
    }
    
    public void disable() {
    	//table.putValue("autonomous", data);
    	//TODO write the autonomous to a file on the roboRIO
    	recording = false;
    	light.set(false);
    	Robot.drive.leftEncoder.reset();
		Robot.drive.rightEncoder.reset();
    	System.out.println("No longer recording teleop actions.");
    }
    
    public void add(String[] add) {
    	if (recording) {
    		data.add(add);
		}
    }

    public void initDefaultCommand() {        
    }

	//@Override
	//public void actionPerformed(ActionEvent a) {
		//if (recording) {
			//light.set(!light.get());
		//}
	//}
}

