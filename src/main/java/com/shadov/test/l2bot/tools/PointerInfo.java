package com.shadov.test.l2bot.tools;

import com.shadov.test.l2bot.model.Colors;
import com.shadov.test.l2bot.model.Positions;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;

@Slf4j
public class PointerInfo {
	public static void print() {
		try {
			final BufferedImage screen = Interactions.screen();

			final Point pointer = MouseInfo.getPointerInfo().getLocation();
			//System.out.printf("Pointer(x=%d,y=%d%n", pointer.x, pointer.y);

			final int rgb = screen.getRGB(pointer.x, pointer.y);
			//System.out.println("Color at pointer: " + rgb);

			final Colors color = Colors.ofRbg(rgb);
			if (color != Colors.INVALID) {
				log.info("Color(" + color + ") " + rgb + " at (" + pointer.x + "," + pointer.y + ")");
			}
			//System.out.println("Parsed color at cursor: " + color);

			log.info("Color at positions");
			log.info("End mob hp: " + screen.getRGB(Positions.END_MOB_HP.getX(), Positions.END_MOB_HP.getY()));
			log.info("Start mob hp: " + screen.getRGB(Positions.START_MOB_HP.getX(), Positions.START_MOB_HP.getY()));
			log.info("Player low hp: " + screen.getRGB(Positions.PLAYER_LOW_HP.getX(), Positions.PLAYER_LOW_HP.getY()));
			log.info("Player high hp: " + screen.getRGB(Positions.PLAYER_HIGH_HP.getX(), Positions.PLAYER_HIGH_HP.getY()));
		} catch (Exception ex) {

		}
	}
}
