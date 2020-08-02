package com.shadov.test.l2bot.model;

import com.shadov.test.l2bot.Situation;
import io.vavr.collection.List;

public class Mobs {
	public static List<String> names() {
		switch (Situation.SPOT) {
			case RED_SOA:
				return redSoa();
			case LOWER_DV:
				return lowerDV();
			case MEDIUM_DV:
				return mediumDV();
			default:
				throw new UnsupportedOperationException();
		}
	}

	private static List<String> mediumDV() {
		return List.of(
				"Drake Mage",
				"Drake Scout",
				"Drake Leader",
				"Drake Warrior"
		);
	}

	private static List<String> lowerDV() {
		return List.of(
				"Maluk Hunter",
				"Maluk Warlord",
				"Dragon Knight"
		);
	}

	private static List<String> redSoa() {
		return List.of(
				"Elite Kanibi",
				"Elite Kiriona",
				"Kiriona",
				"Kaiona"
		);
	}
}
