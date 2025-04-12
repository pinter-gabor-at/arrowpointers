package eu.pintergabor.arrowpointers.util;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;


public final class BlockRegion {

	private BlockRegion() {
		// Static class.
	}

	@SuppressWarnings("unused")
	public static final int TOPLEFT = 0;
	@SuppressWarnings("unused")
	public static final int TOPCENTER = 1;
	@SuppressWarnings("unused")
	public static final int TOPRIGHT = 2;
	@SuppressWarnings("unused")
	public static final int MIDDLELEFT = 3;
	@SuppressWarnings("unused")
	public static final int MIDDLECENTER = 4;
	@SuppressWarnings("unused")
	public static final int MIDDLERIGHT = 5;
	@SuppressWarnings("unused")
	public static final int BOTTOMLEFT = 6;
	@SuppressWarnings("unused")
	public static final int BOTTOMCENTER = 7;
	@SuppressWarnings("unused")
	public static final int BOTTOMRIGHT = 8;

	/**
	 * Calculate the fractional part of v.
	 *
	 * @return Fractional part of v (always non-negative and less than 1)
	 */
	private static double frac(double v) {
		return v - Math.floor(v);
	}

	/**
	 * Calculates which region of the block was clicked.
	 *
	 * @param rx [0, 1, 2] = [left, center, right]
	 * @param ry [0, 1, 2] = [top, center, bottom]
	 * @return region number (top-left = 0 ... bottom-right = 8)
	 */
	private static int blockreg(int rx, int ry) {
		return 3 * rx + ry;
	}

	/**
	 * Calculates which region of the block was clicked.
	 *
	 * @param rx [0 ... 1] = [left, center, right]
	 * @param ry [0 ... 1] = [top, center, bottom]
	 * @return region number (top-left = 0 ... bottom-right = 8)
	 */
	private static int blockreg3(double rx, double ry) {
		return blockreg(Math.min(2, (int) (3 * rx)), Math.min(2, (int) (3 * ry)));
	}

	/**
	 * Calculates which region of the block was clicked.
	 * <pre>
	 * 012
	 * 345
	 * 678
	 * @return region number (top-left = 0 ... bottom-right = 8)
	 */
	public static int getClickedRegion(@NotNull Vec3 clickLocation, Direction face) {
		final double dx = frac(clickLocation.x);
		final double dy = frac(clickLocation.y);
		final double dz = frac(clickLocation.z);
		return switch (face) {
			case DOWN -> blockreg3(1 - dz, dx);
			case NORTH -> blockreg3(1 - dy, 1 - dx);
			case SOUTH -> blockreg3(1 - dy, dx);
			case EAST -> blockreg3(1 - dy, 1 - dz);
			case WEST -> blockreg3(1 - dy, dz);
			default -> blockreg3(dz, dx);
		};
	}
}
