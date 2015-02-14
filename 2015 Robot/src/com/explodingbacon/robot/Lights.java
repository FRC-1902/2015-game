package com.explodingbacon.robot;

public class Lights {
    	
	private static RIODuino rioDuino = new RIODuino();
	
	//Strips
	public static final char BRAKES = 't';
	public static final char ELEVATOR = 'e';
	public static final char ARC = 'q';
	public static final char TOTE_CHUTE = 'r';
	    	  	
	//Colors
	public static final char WHITE = 'f';
	public static final char OFF = 'g';
	public static final char ORANGE = 'a';
	public static final char GREEN = 's';
	public static final char RED = 'd';
	public static final char BLUE = 'h';
	
	//Commands
	public static final char SHOW = '1';
	public static final char CHASE = 'z';
	public static final char FADE = 'x';
	public static final char PUSH = '4';
	public static final char FREEZE = 'p';
	public static final char DIRECTION_1 = 'i';
	public static final char DIRECTION_2 = 'o';
	public static final char DS_ID1 = '6';
	public static final char DS_ID2 = '7';
	public static final char DS_ID3 = '8';
	public static final char DS_ANIMATION = 'c';
	public static final char SEIZURE = 'v';
	public static final char ARC_SPARK = 'b';
	
	public static void setColor(char strip, char color) {
		send(new char[]{strip, color, SHOW});
	}
	
	public static void chase(char strip, char color1, char color2) {
		send(new char[]{strip, color1, PUSH, color2, CHASE});
	}
	
	public static void fade(char strip, char color1, char color2) {
		send(new char[]{strip, color1, PUSH, color2, FADE});
	}
	
	public static void freeze(char strip) {
		send(new char[]{strip, FREEZE});
	}
	
	public static void driverStation(char strip, char color, char number) {
		send(new char[]{strip, color, number, DS_ANIMATION});
	}
	
	public static void seizure(char strip) {
		send(new char[]{strip, SEIZURE});
	}
	
	public static void arcSpark(char strip) {
		send(new char[]{strip, ARC_SPARK});
	}
	
	public static void send(char[] chars) {
		String data = "";
		byte[] bytes = new byte[chars.length];
		int index = 0;
		for (char c : chars) {
			bytes[index] = (byte) c;
			index++;
			data = data + c + " ";
		}
		rioDuino.send(bytes);
	}	
}

