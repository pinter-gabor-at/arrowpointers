package eu.pintergabor.arrowpointers.mixin;

import eu.pintergabor.arrowpointers.main.ArrowRegistry;
import eu.pintergabor.arrowpointers.util.ClickAction;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SpectralArrowItem;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SpectralArrowItem.class)
public abstract class SpectralArrowItemMixin extends ArrowItem {

	public SpectralArrowItemMixin(Item.Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		return ClickAction.useOnBlock(
				this, context, ArrowRegistry.glowArrowMarkBlock);
	}
}
