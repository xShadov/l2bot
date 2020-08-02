package com.shadov.test.l2bot.model;

import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Colors {
	HP(-9956595),
	PLAYER_RED_HP(-8772325),
	INVALID(0);

	private int rgb;

	public static Colors ofRbg(int rgb) {
		return List.of(values())
				.find(color -> isClose(color.rgb, rgb))
				.getOrElse(Colors.INVALID);
	}

	private static boolean isClose(int source, int target) {
		return Math.abs(target - source) < 700000;
	}

	public static void modifyHP(int rgb) {
		Colors.HP.rgb = rgb;
	}

	public static void modifyPlayerHP(int rgb) {
		Colors.PLAYER_RED_HP.rgb = rgb;
	}
}
