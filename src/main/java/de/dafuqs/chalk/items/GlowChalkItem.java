package de.dafuqs.chalk.items;

import de.dafuqs.chalk.ChalkRegistry;
import net.minecraft.block.Block;
import net.minecraft.util.DyeColor;

public class GlowChalkItem extends ChalkItem {
    public GlowChalkItem(Settings settings, DyeColor dyeColor) {
        super(settings, dyeColor);
    }
    public Block getChalkMarkBlock() {
        return ChalkRegistry.chalkVariants.get(this.dyeColor).glowChalkBlock;
    }
}