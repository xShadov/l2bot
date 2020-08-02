package com.shadov.test.l2bot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@Getter
@AllArgsConstructor
public class Positions {
	public static Position END_MOB_HP = new Position(313, 59);
	public static Position START_MOB_HP = new Position(659, 59);
	public static Position PLAYER_LOW_HP = new Position(110, 75);
	public static Position PLAYER_HIGH_HP = new Position(220, 75);
	public static Position PLAYER_EVEN_LOWER_HP = new Position(70, 75);

	@Getter
	@AllArgsConstructor
	public static class Position {
		private int x;
		private int y;
	}
}
