package eu.pintergabor.arrowpointers.util;

import eu.pintergabor.arrowpointers.blocks.ArrowMarkBlock;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.phys.Vec3;


public enum BlockRegion implements StringRepresentable {
	TOPLEFT("top_left"), TOPCENTER("top_center"), TOPRIGHT("top_right"),
	MIDDLELEFT("middle_left"), MIDDLECENTER("middle_center"), MIDDLERIGHT("middle_right"),
	BOTTOMLEFT("bottom_left"), BOTTOMCENTER("bottom_center"), BOTTOMRIGHT("bottom_right");
	/**
	 * Used in {@link ArrowMarkBlock} to create the {@link ArrowMarkBlock#ORIENTATION} property
	 * and here to create enums from calculated values.
	 */
	public static final BlockRegion[] VALUES = BlockRegion.values();
	/**
	 * Internally stored name.
	 */
	private final String name;

	BlockRegion(String name) {
		this.name = name;
	}

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
	 * @return region.
	 */
	private static BlockRegion blockreg(int rx, int ry) {
		return VALUES[3 * rx + ry];
	}

	/**
	 * Calculates which region of the block was clicked.
	 *
	 * @param rx [0 ... 1] = [left, center, right]
	 * @param ry [0 ... 1] = [top, center, bottom]
	 * @return region.
	 */
	private static BlockRegion blockreg3(double rx, double ry) {
		return blockreg(Math.min(2, (int) (3 * rx)), Math.min(2, (int) (3 * ry)));
	}

	/**
	 * Calculates which region of the block was clicked.
	 * <pre>
	 * 012
	 * 345
	 * 678
	 * </pre>
	 *
	 * @return region.
	 */
	public static BlockRegion getClickedRegion(@NotNull Vec3 clickLocation, Direction face) {
		final double dx = frac(clickLocation.x);
		final double dy = frac(clickLocation.y);
		final double dz = frac(clickLocation.z);
		return switch (face) {
			case UP -> blockreg3(dz, dx);
			case DOWN -> blockreg3(1 - dz, dx);
			case NORTH -> blockreg3(1 - dy, 1 - dx);
			case SOUTH -> blockreg3(1 - dy, dx);
			case EAST -> blockreg3(1 - dy, 1 - dz);
			case WEST -> blockreg3(1 - dy, dz);
		};
	}

	@Override
	@NotNull
	public String getSerializedName() {
		return name;
	}
}
