package com.shadov.test.l2bot.tools;

import com.shadov.test.l2bot.Situation;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.KeyEvent;

@Slf4j
public class Buffer {
	public static void buff() {
		if (!Situation.buffTime())
			return;

		log.info("Buff time");
		Interactions.press(KeyEvent.VK_F10);
		Interactions.delay(95);
	}
}
