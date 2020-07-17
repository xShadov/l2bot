package com.shadov.test.l2bot;

import io.vavr.collection.List;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

@Slf4j
public class Application {
	public static void main(String[] args) {
		Situation.attach();

		while (true) {
			pointerInfo(Interactions.screen());

			if (!Situation.isRunning()) {
				Interactions.delay(250);
				continue;
			}

			if (Situation.buffTime()) {
				log.info("Buff time");
				Interactions.press(KeyEvent.VK_F10);
				Interactions.delay(50);
			}

			if (Situation.isFightingMob()) {
				if (hasAliveTarget()) {
					if (longFight()) {
						log.info("Long fight detected");
						Interactions.beep(3);
					}

					if (notDamagingMinion()) {
						log.info("Not damaging a minion");
						Situation.killMob();
						Interactions.beep(2);
						Interactions.press(KeyEvent.VK_ESCAPE);
						tryTargetSomething(true);
						if (hasAliveTarget())
							fightMob(true);
					} else
						fightMob(false);
				} else
					Situation.killMob();
			} else {
				tryTargetSomething(false);

				if (hasAliveTarget())
					fightMob(true);
			}

			Interactions.delay(250);
		}
	}

	private static boolean notDamagingMinion() {
		return Situation.currentFightTime().toMillis() > 2500 && isMinionFullHp();
	}

	private static boolean longFight() {
		return Situation.currentFightTime().getSeconds() > 15;
	}

	private static boolean hasAliveTarget() {
		final BufferedImage screenAfterTarget = Interactions.screen();

		final int rgb = screenAfterTarget.getRGB(Positions.END_MOB_HP.getX(), Positions.END_MOB_HP.getY());
		return Colors.ofRbg(rgb) != Colors.INVALID;
	}

	private static boolean isMinionFullHp() {
		final BufferedImage screenAfterTarget = Interactions.screen();

		final int rgb = screenAfterTarget.getRGB(Positions.START_MOB_HP.getX(), Positions.START_MOB_HP.getY());
		return Colors.ofRbg(rgb) != Colors.INVALID;
	}

	private static void tryTargetSomething(boolean excludeNextTarget) {
		if (!excludeNextTarget) {
			Interactions.write(Commands.nextTarget());
			Interactions.delay(200);
			if (hasAliveTarget())
				return;
		}

		final List<String> mobNames = Mobs.NAMES.shuffle();
		for (String name : mobNames) {
			Interactions.write(Commands.target(name));
			Interactions.delay(200);

			if (hasAliveTarget())
				return;
		}
	}

	private static void fightMob(boolean isFirstHit) {
		if (isFirstHit) {
			Situation.fightMob();
			Interactions.press(KeyEvent.VK_F11);
		}

		Interactions.press(KeyEvent.VK_F3);
		Interactions.delay(100);
		Interactions.press(KeyEvent.VK_F1);
		//Interactions.press(KeyEvent.VK_F2);
	}
	private static void pointerInfo(final BufferedImage screen) {
		try {
			final Point pointer = MouseInfo.getPointerInfo().getLocation();
			//System.out.printf("Pointer(x=%d,y=%d%n", pointer.x, pointer.y);

			final int rgb = screen.getRGB(pointer.x, pointer.y);
			//System.out.println("Color at pointer: " + rgb);

			final Colors color = Colors.ofRbg(rgb);
			if (color != Colors.INVALID) {
				log.info("Color(" + color + ") " + rgb + " at (" + pointer.x + "," + pointer.y + ")");
			}
			//System.out.println("Parsed color at cursor: " + color);
		} catch (Exception ex) {

		}
	}
}
