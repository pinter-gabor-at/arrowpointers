package eu.pintergabor.arrowpointers.util;

import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class ClickUtil {

	private ClickUtil() {
		// Static class
	}

	/**
	 * Calculate the fractional part of v
	 * @return Fractional part of v (always non-negative and less than 1)
	 */
	private static double frac(double v) {
		return v - Math.floor(v);
	}

	/**
	 * Calculates which region of the block was clicked
	 * @param rx [0, 1, 2] = [left, center, right]
	 * @param ry [0, 1, 2] = [top, center, bottom]
	 * @return region number (top-left = 0 … bottom-right = 8)
	 */
	private static int blockreg(int rx, int ry) {
		return 3 * rx + ry;
	}

	public static int getClickedRegion(@NotNull Vec3d clickLocation, Direction face) {
		final double dx = frac(clickLocation.x);
		final double dy = frac(clickLocation.y);
		final double dz = frac(clickLocation.z);

		return switch (face) {
			default ->
					blockreg(Math.min(2, (int) (3 * dz)), Math.min(2, (int) (3 * dx)));
			case NORTH, SOUTH ->
					blockreg(Math.min(2, (int) (3 * (1 - dy))), Math.min(2, (int) (3 * dx)));
			case WEST, EAST ->
					blockreg(Math.min(2, (int) (3 * (1 - dy))), Math.min(2, (int) (3 * dz)));
		};
	}

}
