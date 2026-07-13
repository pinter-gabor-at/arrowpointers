package eu.pintergabor.arrowpointers.mixin;

import org.jspecify.annotations.NonNull;
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

	/**
	 * Restore the default action, which does nothing.
	 */
	@Override
	public @NonNull InteractionResult useOn(final @NonNull UseOnContext context) {
		return InteractionResult.PASS;
	}
}
