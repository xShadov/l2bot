package com.shadov.test.l2bot.tools;

import com.shadov.test.l2bot.Situation;
import com.shadov.test.l2bot.model.Characters;
import com.shadov.test.l2bot.model.Colors;
import com.shadov.test.l2bot.model.Positions;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

public class MinionFighter {
	private static final AtomicInteger NOT_DAMAGING_TIME = new AtomicInteger(6000);

	public static boolean notDamagingMinion() {
		if (Situation.CHARACTER == Characters.MAGE)
			return Situation.currentFightTime().toMillis() > 2500 && isMinionFullHp();

		boolean result = Situation.currentFightTime().toMillis() > NOT_DAMAGING_TIME.get() && isMinionFullHp();
		if (result)
			NOT_DAMAGING_TIME.set(NOT_DAMAGING_TIME.get() - 1000);
		else
			NOT_DAMAGING_TIME.set(7000);
		return result;
	}

	private static boolean isMinionFullHp() {
		final BufferedImage screenAfterTarget = Interactions.screen();

		final int rgb = screenAfterTarget.getRGB(Positions.START_MOB_HP.getX(), Positions.START_MOB_HP.getY());
		return Colors.ofRbg(rgb) != Colors.INVALID;
	}
}
