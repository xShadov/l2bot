package com.shadov.test.l2bot.tools;

import com.shadov.test.l2bot.Situation;
import com.shadov.test.l2bot.model.Spots;

public class Beeper {
	public static void beepOnLongFight() {
		if(Situation.SPOT == Spots.RED_SOA)
			return;

		if(Situation.sinceLastFight().getSeconds() > 20)
			Interactions.beep(1);
	}
}
