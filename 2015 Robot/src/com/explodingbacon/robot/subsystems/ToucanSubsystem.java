package com.explodingbacon.robot.subsystems;

import com.explodingbacon.robot.RobotMap;
import com.explodingbacon.robot.Timer;
import com.explodingbacon.robot.TimerUser;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ToucanSubsystem extends Subsystem {
    
	private CANTalon toucans = new CANTalon(RobotMap.toucans);
	private boolean enabled = false;
	private Timer t = null;
	
	public boolean get() {
		return enabled;
	}

	public void set(boolean b) {
		if (b != enabled) {
			toucans.set(1 * (b ? 1 : -1));
			t = new Timer(0.5, false, new TimerUser() {
				@Override
				public void timer() {
					toucans.set(0);
				}

				@Override
				public void timerStop() {	
					t = null;
				}				
			}).start();
			enabled = true;
		}
	}
	
    public void initDefaultCommand() {}
}

