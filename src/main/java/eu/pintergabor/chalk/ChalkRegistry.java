package eu.pintergabor.chalk;

import net.minecraft.util.DyeColor;

import java.util.HashMap;


public class ChalkRegistry {

	private ChalkRegistry() {
		// static class
	}

	private static final HashMap<DyeColor, Integer> dyeColors = new HashMap<>() {{
		put(DyeColor.BLACK, 0x171717);
		put(DyeColor.BLUE, 0x2c2e8e);
		put(DyeColor.BROWN, 0x613c20);
		put(DyeColor.CYAN, 0x157687);
		put(DyeColor.GRAY, 0x292929);
		put(DyeColor.GREEN, 0x495b24);
		put(DyeColor.LIGHT_BLUE, 0x258ac8);
		put(DyeColor.LIGHT_GRAY, 0x8b8b8b);
		put(DyeColor.LIME, 0x5faa19);
		put(DyeColor.MAGENTA, 0xaa32a0);
		put(DyeColor.ORANGE, 0xe16201);
		put(DyeColor.PINK, 0xd6658f);
		put(DyeColor.PURPLE, 0x641f9c);
		put(DyeColor.RED, 0x8f2121);
		put(DyeColor.WHITE, 0xFFFFFF);
		put(DyeColor.YELLOW, 0xf0ff15);
	}};
	public static HashMap<DyeColor, ChalkVariant> chalkVariants = new HashMap<>();

	public static void init() {
		Constants.LOGGER.info("Registering blocks and items...");
		ChalkVariant chalkVariant;
		for (DyeColor dyeColor : DyeColor.values()) {
			int color = dyeColors.get(dyeColor);
			// Maintain backward compatibility
			chalkVariant = new ChalkVariant(dyeColor, color,
					dyeColor.equals(DyeColor.WHITE) ? "" : dyeColor + "_");
			chalkVariant.register();
			chalkVariant.registerClient();
			chalkVariants.put(dyeColor, chalkVariant);
		}
	}
}