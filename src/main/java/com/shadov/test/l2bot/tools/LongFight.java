package com.shadov.test.l2bot.tools;

import com.shadov.test.l2bot.Situation;

import java.awt.event.KeyEvent;

public class LongFight {
	public static void handle() {
		if (longFight())
			Interactions.press(KeyEvent.VK_F9);
	}

	private static boolean longFight() {
		return Situation.currentFightTime().getSeconds() > 2;
	}
}
