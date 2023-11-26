package eu.pintergabor.arrowpointers.mixin;

import eu.pintergabor.arrowpointers.ArrowRegistry;
import eu.pintergabor.arrowpointers.util.ClickAction;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ArrowItem.class)
public class ArrowItemMixin extends Item {
	public ArrowItemMixin(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		return ClickAction.useOnBlock(this, context, ArrowRegistry.arrowVariant.arrowMarkBlock);
	}

}
