package org.usfirst.frc.team1902.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class AutonomousSubsystem extends Subsystem {
    
	public List<String[]> data;
	public boolean recording = false;
    public NetworkTable table = null;
    
    public void enable() {
    	if (table == null) {
    		//NetworkTable.setTeam(1902);
    		//NetworkTable.setServerMode();
			//NetworkTable.initialize();    			
			table = NetworkTable.getTable("autonomous");
    	}
		recording = true;
		data = new ArrayList<>();
    }
    
    public void disable() {
    	table.putValue("autonomous", data);
    	recording = false;
    }
    
    public void add(String[] add) {
    	if (recording) {
    		data.add(add);
		}
    }

    public void initDefaultCommand() {        
    }
}

