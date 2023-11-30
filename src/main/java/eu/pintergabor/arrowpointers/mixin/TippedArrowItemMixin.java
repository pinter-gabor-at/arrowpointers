package eu.pintergabor.arrowpointers.mixin;

import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.TippedArrowItem;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TippedArrowItem.class)
public class TippedArrowItemMixin extends ArrowItem {

	public TippedArrowItemMixin(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		// Restore default action
		return ActionResult.PASS;
	}

}
