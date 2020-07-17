package com.shadov.test.l2bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@Getter
@AllArgsConstructor
public class Positions {
	/*public static Position END_MOB_HP = new Position(301, 59);
	public static Position START_MOB_HP = new Position(649, 60);*/
	public static Position END_MOB_HP = new Position(417, 59);
	public static Position START_MOB_HP = new Position(763, 59);
	public static Position PLAYER_LOW_HP = new Position(107, 83);

	public static void update(Point pointer) {
		END_MOB_HP = new Positions.Position(pointer.x, pointer.y);
		START_MOB_HP = new Positions.Position(pointer.x + (763-420), pointer.y);
		PLAYER_LOW_HP = new Positions.Position(pointer.x - (420-107), pointer.y + 23);
	}

	@Getter
	@AllArgsConstructor
	public static class Position {
		private int x;
		private int y;
	}
}
