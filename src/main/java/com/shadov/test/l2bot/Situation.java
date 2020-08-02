package com.shadov.test.l2bot;

import com.shadov.test.l2bot.model.Characters;
import com.shadov.test.l2bot.model.Colors;
import com.shadov.test.l2bot.model.Positions;
import com.shadov.test.l2bot.model.Spots;
import com.shadov.test.l2bot.tools.Interactions;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
public class Situation implements NativeKeyListener, NativeMouseInputListener {
	public static final Spots SPOT = Spots.LOWER_DV;
	public static final Characters CHARACTER = Characters.MAGE;

	private static final AtomicBoolean fightingMob = new AtomicBoolean(false);
	private static final AtomicBoolean running = new AtomicBoolean(false);
	private static final AtomicReference<Instant> buffRun = new AtomicReference<>();
	private static final AtomicReference<Instant> fightMobStart = new AtomicReference<>(Instant.now());
	private static final AtomicReference<Instant> fightMobEnd = new AtomicReference<>(Instant.now());

	public static void fightMob() {
		fightMobStart.set(Instant.now());
		fightingMob.set(true);
	}

	public static Duration currentFightTime() {
		return Duration.between(fightMobStart.get(), Instant.now());
	}

	public static Duration sinceLastFight() {
		return Duration.between(fightMobEnd.get(), Instant.now());
	}

	public static void killMob() {
		abandonMob();
		fightMobEnd.set(Instant.now());
	}

	public static void abandonMob() {
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

		Situation nativeKeyListener = new Situation();
		GlobalScreen.addNativeKeyListener(nativeKeyListener);
		GlobalScreen.addNativeMouseListener(nativeKeyListener);
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
		if (nativeKeyEvent.getKeyCode() == 3655) {
			log.info("Home pressed, {}", Situation.running.get() ? "stopping" : "running");

			if (Situation.isRunning())
				running.set(false);
			else {
				final BufferedImage screen = Interactions.screen();
				final int rgb = screen.getRGB(Positions.START_MOB_HP.getX(), Positions.START_MOB_HP.getY());
				Colors.modifyHP(rgb);

				final int rgb2 = screen.getRGB(Positions.PLAYER_LOW_HP.getX(), Positions.PLAYER_LOW_HP.getY());
				Colors.modifyPlayerHP(rgb2);

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

	@Override
	public void nativeMouseClicked(final NativeMouseEvent nativeMouseEvent) {
		if(!Situation.isRunning()) {
			System.out.println("Mouse clicked("+nativeMouseEvent.getX()+","+nativeMouseEvent.getY()+")");
		}
	}

	@Override
	public void nativeMousePressed(final NativeMouseEvent nativeMouseEvent) {

	}

	@Override
	public void nativeMouseReleased(final NativeMouseEvent nativeMouseEvent) {

	}

	@Override
	public void nativeMouseMoved(final NativeMouseEvent nativeMouseEvent) {

	}

	@Override
	public void nativeMouseDragged(final NativeMouseEvent nativeMouseEvent) {

	}
}
