package com.shadov.test.l2bot;

import io.vavr.collection.List;
import io.vavr.control.Try;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Interactions {
	private static final Toolkit toolkit = Toolkit.getDefaultToolkit();
	private static final Robot robot = Try.of(() -> new Robot(MouseInfo.getPointerInfo().getDevice())).getOrElseThrow(ex -> new RuntimeException(ex));
	private static final Rectangle screenRect = new Rectangle(toolkit.getScreenSize());

	public static BufferedImage screen() {
		return robot.createScreenCapture(screenRect);
	}

	public static void delay(int ms) {
		robot.delay(ms);
	}

	public static void write(List<Integer> keyStrokes) {
		keyStrokes.forEach(Interactions::press);
		press(KeyEvent.VK_ENTER);
	}

	public static void press(Integer keyStroke) {
		robot.keyPress(keyStroke);
		robot.keyRelease(keyStroke);
	}

	public static void beep(int count) {
		for (int i = 0; i < count; i++) {
			toolkit.beep();
		}
	}
}
