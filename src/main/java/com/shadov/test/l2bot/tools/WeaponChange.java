package com.shadov.test.l2bot.tools;

import com.shadov.test.l2bot.model.Colors;
import com.shadov.test.l2bot.model.Positions;
import com.shadov.test.l2bot.tools.Interactions;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class WeaponChange {
	private static final AtomicBoolean WEARING_RAPER = new AtomicBoolean(false);
	private static final AtomicReference<Instant> LAST_DOUBLE_WEAR = new AtomicReference<>(Instant.now());

	public static void handle() {
		final BufferedImage screen = Interactions.screen();

		if(!WEARING_RAPER.get()) {
			final int rgb = screen.getRGB(Positions.PLAYER_LOW_HP.getX(), Positions.PLAYER_LOW_HP.getY());
			final boolean hasLowHp = Colors.ofRbg(rgb) != Colors.PLAYER_RED_HP;

			if(hasLowHp) {
				log.info("Low hp, putting on rapier");

				WEARING_RAPER.set(true);
				Interactions.press(KeyEvent.VK_F8);
			}
		} else {
			final int rgb = screen.getRGB(Positions.PLAYER_HIGH_HP.getX(), Positions.PLAYER_HIGH_HP.getY());
			final boolean hasEnoughHp = Colors.ofRbg(rgb) == Colors.PLAYER_RED_HP;

			if(hasEnoughHp) {
				log.info("Hp back to high, putting on bow");

				WEARING_RAPER.set(false);
				Interactions.press(KeyEvent.VK_F7);
			} else {
				final int lowRgb =  screen.getRGB(Positions.PLAYER_EVEN_LOWER_HP.getX(), Positions.PLAYER_EVEN_LOWER_HP.getY());
				final boolean isVeryLow = Colors.ofRbg(lowRgb) != Colors.PLAYER_RED_HP;

				if(isVeryLow) {
					if (Duration.between(LAST_DOUBLE_WEAR.get(), Instant.now()).getSeconds() > 2) {
						Interactions.press(KeyEvent.VK_F8);
						Interactions.press(KeyEvent.VK_F8);
						LAST_DOUBLE_WEAR.set(Instant.now());
					}
				}
			}
		}
	}
}
