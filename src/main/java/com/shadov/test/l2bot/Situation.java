package com.shadov.test.l2bot;

import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
public class Situation implements NativeKeyListener {
	private static final AtomicBoolean fightingMob = new AtomicBoolean(false);
	private static final AtomicBoolean running = new AtomicBoolean(false);
	private static final AtomicReference<Instant> buffRun = new AtomicReference<>();
	private static final AtomicReference<Instant> fightMobStart = new AtomicReference<>();

	public static void fightMob() {
		fightMobStart.set(Instant.now());
		fightingMob.set(true);
	}

	public static Duration currentFightTime() {
		return Duration.between(fightMobStart.get(), Instant.now());
	}

	public static void killMob() {
		fightingMob.set(false);
	}

	public static boolean isFightingMob() {
		return fightingMob.get();
	}

	public static boolean isRunning() {
		return running.get();
	}

	public static boolean buffTime() {
		final Instant previousBuffRun = buffRun.get();
		if (previousBuffRun == null || Duration.between(previousBuffRun, Instant.now()).getSeconds() >= 23) {
			buffRun.set(Instant.now());
			return true;
		}
		return false;
	}

	public static void attach() {
		try {
			Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
			logger.setLevel(Level.OFF);
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			log.error("There was a problem registering the native hook.", ex);

			System.runFinalization();
			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(new Situation());
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException ex) {
				throw new RuntimeException(ex);
			}
		}));
	}

	@Override
	public void nativeKeyPressed(final NativeKeyEvent nativeKeyEvent) {
		if (nativeKeyEvent.getKeyCode() == 3663) {
			final Point pointer = MouseInfo.getPointerInfo().getLocation();
			log.info("End pressed, new position: ({},{})", pointer.x, pointer.y);
			Positions.update(pointer);
		}

		if (nativeKeyEvent.getKeyCode() == 3655) {
			log.info("Home pressed, {}", Situation.running.get() ? "stopping" : "running");

			if (Situation.isRunning())
				running.set(false);
			else {
				running.set(true);
			}
		}
	}

	@Override
	public void nativeKeyTyped(final NativeKeyEvent nativeKeyEvent) {
	}

	@Override
	public void nativeKeyReleased(final NativeKeyEvent nativeKeyEvent) {
	}
}
