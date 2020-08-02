package com.shadov.test.l2bot.tools;

import io.vavr.collection.List;
import io.vavr.control.Try;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class Interactions {
	private static final File file = new File("../sound.wav");
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
			playSound();
		}
	}

	public static void playSound()  {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL());
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
