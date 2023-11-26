package eu.pintergabor.arrowpointers.items;

import eu.pintergabor.arrowpointers.ArrowRegistry;
import eu.pintergabor.arrowpointers.util.ClickAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class ChalkItem extends Item {

	public ChalkItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		return ClickAction.useOnBlock(this, context, ArrowRegistry.arrowVariant.arrowMarkBlock);
	}

}