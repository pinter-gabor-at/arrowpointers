package eu.pintergabor.arrowpointers;

import eu.pintergabor.arrowpointers.main.ArrowRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.jspecify.annotations.NonNull;


/**
 * Common startup.
 */
@Mod(Global.MODID)
public final class ModCommon {

	@SuppressWarnings("unused")
	public ModCommon(
		final @NonNull IEventBus modEventBus,
		final @NonNull ModContainer modContainer,
		final @NonNull Dist dist
	) {
		// Create and register everything.
		ArrowRegistry.init(modEventBus);
	}
}
