package eu.pintergabor.arrowpointers.mixin;

import eu.pintergabor.arrowpointers.main.ArrowRegistry;
import eu.pintergabor.arrowpointers.util.ClickAction;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.SpectralArrowItem;
import net.minecraft.world.item.context.UseOnContext;


@Mixin(SpectralArrowItem.class)
public abstract class SpectralArrowItemMixin extends ArrowItem {

	public SpectralArrowItemMixin(Properties props) {
		super(props);
	}

	@Override
	@NotNull
	public InteractionResult useOn(UseOnContext context) {
		return ClickAction.useOn(
			this, context, ArrowRegistry.glowArrowMarkBlock);
	}
}
