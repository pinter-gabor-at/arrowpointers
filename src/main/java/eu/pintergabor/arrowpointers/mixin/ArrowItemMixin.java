package eu.pintergabor.arrowpointers.mixin;

import eu.pintergabor.arrowpointers.main.ArrowRegistry;
import eu.pintergabor.arrowpointers.util.ClickAction;
import org.jetbrains.annotations.NotNull;
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
	@NotNull
	public InteractionResult useOn(@NotNull UseOnContext context) {
		return ClickAction.useOn(context, ArrowRegistry.arrowMarkBlock.get());
	}
}
