package org.usfirst.frc.team1902.robot.subsystems;

import java.io.IOException;
import org.usfirst.frc.team1902.robot.Robot;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class RecordedAutonomousSubsystem extends Subsystem {
    
    NetworkTable table = null;
    
    public void enable() {
    	try {
    		NetworkTable.setTeam(1902);
    		NetworkTable.setServerMode();
			NetworkTable.initialize();		
			table = NetworkTable.getTable("recordedAutonomous");
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void disable() {
    	table.putString("autonomous", Robot.recordedAutonomousString);
    }

    public void initDefaultCommand() {        
    }
}

