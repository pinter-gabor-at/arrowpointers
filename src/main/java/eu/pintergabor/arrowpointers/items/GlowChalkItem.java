package eu.pintergabor.arrowpointers.items;

import eu.pintergabor.arrowpointers.ArrowRegistry;
import eu.pintergabor.arrowpointers.util.ClickAction;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class GlowChalkItem extends ChalkItem {

    public GlowChalkItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return ClickAction.useOnBlock(this, context, ArrowRegistry.arrowVariant.glowArrowMarkBlock);
    }
}