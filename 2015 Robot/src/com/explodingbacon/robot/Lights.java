package com.explodingbacon.robot;

public class Lights {
    	
	private static RIODuino rioDuino = new RIODuino(); //The RIODuino has been disabled. To re-enable it, uncomment the code in the RIODuino class.
	
	public enum Strip {
		BACK('t'),
		TOTE_CHUTE('r'),
		ELEVATOR('e'),
		ARC('q');
		
		char data;
		
		Strip(char c) {
			data = c;
		}
		
		public void setColor(Color color) {
			send(new Object[]{this, color, Action.SHOW});
		}
		
		public void chase(Color color1, Color color2) {
			send(new Object[]{this, color1, Action.PUSH, color2, Action.CHASE});
		}
		
		public void chase(Color color1, Color color2, Action direction) {
			send(new Object[]{this, color1, Action.PUSH, color2, direction, Action.CHASE});
		}
		
		public void fade(Color color1, Color color2) {
			send(new Object[]{this, color1, Action.PUSH, color2, Action.FADE});
		}
		
		public void freeze() {
			send(new Object[]{this, Action.FREEZE});
		}
		
		public void driverStation(Color color, Action number) {
			send(new Object[]{this, color, number, Action.DS_ANIMATION});
		}
		
		public void seizure() {
			send(new Object[]{this, Action.SEIZURE});
		}
		
		public void arcSpark() {
			send(new Object[]{this, Action.ARC_SPARK});
		}
	}
	    
	public enum Color {
		WHITE('f'),
		OFF('g'),
		ORANGE('a'),
		GREEN('s'),
		RED('d'),
		BLUE('h');
		
		char data;
		
		Color(char c) {
			data = c;
		}
	}
	
	public enum Action {
		SHOW('1'),
		CHASE('z'),
		FADE('x'),
		PUSH ('4'),
		FREEZE('p'),
		FORWARD('i'),
		REVERSE('o'),
		DS_ID1('6'),
		DS_ID2('7'),
		DS_ID3('8'),
		DS_ANIMATION('c'),
		SEIZURE('v'),
		ARC_SPARK('b');	
		
		char data;
		
		Action(char c) {
			data = c;
		}
	}
	
	public static void send(Object[] objects) {
		byte[] bytes = new byte[objects.length];
		int index = 0;
		for (Object o : objects) {
			bytes[index] = (byte) getData(o);
			index++;
		}
		rioDuino.send(bytes);
	}
	
	public static char getData(Object o) {
		if (o instanceof Strip) return ((Strip) o).data;
		if (o instanceof Color) return ((Color) o).data;
		if (o instanceof Action) return ((Action) o).data;
		return '/';
	}
}

