package com.shadov.test.l2bot;

import com.shadov.test.l2bot.model.Characters;
import com.shadov.test.l2bot.tools.*;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.KeyEvent;

@Slf4j
public class Application {
	public static void main(String[] args) {
		Situation.attach();

		while (true) {
			if (!Situation.isRunning()) {
				PointerInfo.print();
				Interactions.delay(250);
				continue;
			}

			Beeper.beepOnLongFight();

			Buffer.buff();

			//WeaponChange.handle();

			if (Situation.isFightingMob()) {
				if (Targeter.hasAliveTarget()) {
					LongFight.handle();

					if (MinionFighter.notDamagingMinion()) {
						log.info("Not damaging a minion");
						Situation.abandonMob();
						Interactions.press(KeyEvent.VK_ESCAPE);
						Targeter.tryTargetSomething(true);
						if (Targeter.hasAliveTarget())
							AttackMoves.perform(true);
					} else
						AttackMoves.perform(false);
				} else
					Situation.killMob();
			} else {
				boolean byNextTarget = Targeter.tryTargetSomething(false);

				if (Targeter.hasAliveTarget()) {
					AttackMoves.perform(true);

					// warp to minion
					if(Situation.CHARACTER == Characters.SOULHOUND) {
						if (!byNextTarget)
							Interactions.press(KeyEvent.VK_F1);
					}
				}
			}

			Interactions.delay(250);
		}
	}
}
