package com.shadov.test.l2bot.tools;

import com.shadov.test.l2bot.model.Colors;
import com.shadov.test.l2bot.model.Mobs;
import com.shadov.test.l2bot.model.Positions;
import io.vavr.collection.List;

import java.awt.image.BufferedImage;

public class Targeter {
	public static boolean tryTargetSomething(boolean excludeNextTarget) {
		if (!excludeNextTarget) {
			Interactions.write(Commands.nextTarget());
			Interactions.delay(200);
			if (hasAliveTarget())
				return true;
		}

		final List<String> mobNames = Mobs.names().shuffle();
		for (String name : mobNames) {
			Interactions.write(Commands.target(name));
			Interactions.delay(200);

			if (hasAliveTarget())
				return false;
		}

		return false;
	}

	public static boolean hasAliveTarget() {
		final BufferedImage screenAfterTarget = Interactions.screen();

		final int rgb = screenAfterTarget.getRGB(Positions.END_MOB_HP.getX(), Positions.END_MOB_HP.getY());
		return Colors.ofRbg(rgb) != Colors.INVALID;
	}
}
