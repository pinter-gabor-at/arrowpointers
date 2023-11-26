package eu.pintergabor.arrowpointers.items;

import eu.pintergabor.arrowpointers.ArrowRegistry;
import net.minecraft.block.Block;

public class GlowChalkItem extends ChalkItem {
    public GlowChalkItem(Settings settings) {
        super(settings);
    }
    public Block getChalkMarkBlock() {
        return ArrowRegistry.arrowVariant.glowArrowMarkBlock;
    }
}