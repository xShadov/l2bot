package com.shadov.test.l2bot.tools;

import com.shadov.test.l2bot.Situation;
import com.shadov.test.l2bot.model.Characters;

import java.awt.event.KeyEvent;

public class AttackMoves {
	public static void perform(boolean isFirstHit) {
		if (isFirstHit) {
			Situation.fightMob();
			Interactions.press(KeyEvent.VK_F12);
		}

		if(Situation.CHARACTER == Characters.MAGE) {
			Interactions.press(KeyEvent.VK_F3);
			Interactions.delay(100);
			Interactions.press(KeyEvent.VK_F1);
		} else {
			Interactions.press(KeyEvent.VK_F2);
		}
	}
}
