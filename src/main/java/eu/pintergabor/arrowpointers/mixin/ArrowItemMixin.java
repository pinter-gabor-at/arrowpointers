package eu.pintergabor.arrowpointers.mixin;

import eu.pintergabor.arrowpointers.main.ArrowRegistry;
import eu.pintergabor.arrowpointers.util.ClickAction;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;


@Mixin(ArrowItem.class)
public abstract class ArrowItemMixin extends Item {

	public ArrowItemMixin(Properties props) {
		super(props);
	}

	/**
	 * Override the default method, which does nothing, with a custom action.
	 */
	@Override
	public @NonNull InteractionResult useOn(final @NonNull UseOnContext context) {
		return ClickAction.useOn(context, ArrowRegistry.arrowMarkBlock);
	}
}
