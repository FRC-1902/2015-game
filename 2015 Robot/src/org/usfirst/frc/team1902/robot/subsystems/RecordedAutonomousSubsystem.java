package org.usfirst.frc.team1902.robot.subsystems;

import java.io.IOException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class RecordedAutonomousSubsystem extends Subsystem {
    
	public String data = "";
	public boolean recording = false;
    NetworkTable table = null;
    
    public void enable() {
    	try {
    		NetworkTable.setTeam(1902);
    		NetworkTable.setServerMode();
			NetworkTable.initialize();		
			table = NetworkTable.getTable("recordedAutonomous");
			recording = true;
			data = "";
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void disable() {
    	table.putString("autonomous", data);
    	recording = false;
    }

    public void initDefaultCommand() {        
    }
}

