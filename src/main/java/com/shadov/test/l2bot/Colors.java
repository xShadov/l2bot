package com.shadov.test.l2bot;

import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Colors {
	// single screen mode
	HP(-9496813),
	PLAYER_RED(-8772325),
	/*// multiple screen mode
	HP(-8579569),
	PLAYER_RED(-8772325),*/
	INVALID(0);

	private int rgb;

	public static Colors ofRbg(int rgb) {
		return List.of(values())
				.find(color -> isClose(color.rgb, rgb))
				.getOrElse(Colors.INVALID);
	}

	private static boolean isClose(int source, int target) {
		return Math.abs(target - source) < 100000;
	}
}
