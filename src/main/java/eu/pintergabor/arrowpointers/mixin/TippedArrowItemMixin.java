package eu.pintergabor.arrowpointers.mixin;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.TippedArrowItem;
import net.minecraft.world.item.context.UseOnContext;


@Mixin(TippedArrowItem.class)
public abstract class TippedArrowItemMixin extends ArrowItem {

	public TippedArrowItemMixin(Properties props) {
		super(props);
	}

	@Override
	@NotNull
	public InteractionResult useOn(UseOnContext context) {
		// Restore default action.
		return InteractionResult.PASS;
	}
}
