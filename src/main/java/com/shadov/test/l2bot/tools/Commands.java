package com.shadov.test.l2bot.tools;

import io.vavr.collection.CharSeq;
import io.vavr.collection.List;

import java.awt.event.KeyEvent;

public class Commands {
	public static List<Integer> target(String target) {
		return keyCodesFor("/target " + target);
	}

	public static List<Integer> nextTarget() {
		return keyCodesFor("/targetnext");
	}

	public static List<Integer> attack() {
		return keyCodesFor("/attack");
	}

	private static List<Integer> keyCodesFor(final String command) {
		return CharSeq.of(command)
				.map(KeyEvent::getExtendedKeyCodeForChar)
				.toList();
	}
}
