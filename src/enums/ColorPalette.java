package enums;

import java.awt.*;

/**
 * @ColorPalette is Sweetie 16 palette from lospec
 * @link https://lospec.com/palette-list/sweetie-16
 */

public enum ColorPalette {
	white("#f4f4f4"),
	black("#1a1c2c"),

	purple("#5d275d"),
	red("#b13e53"),
	orange("#ef7d57"),

	light_green("#a7f070"),
	green("#38b764"),
	dark_green("#257179"),

	wall("#333c57"),
	wall_border("#566c86"),

	light_blue("#73eff7"),
	yellow("#ffcd75");

	public final String hex;
	public final Color color;

	private ColorPalette(String hex) {
		this.hex = hex;
		this.color = Color.decode(hex);
	}
}
