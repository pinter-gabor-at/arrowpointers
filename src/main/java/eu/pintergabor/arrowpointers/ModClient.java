package eu.pintergabor.arrowpointers;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.jspecify.annotations.NonNull;


/**
 * Client side startup.
 */
@Mod(value = Global.MODID, dist = Dist.CLIENT)
public final class ModClient {

	@SuppressWarnings("unused")
	public ModClient(
		final @NonNull IEventBus modEventBus,
		final @NonNull ModContainer modContainer
	) {
		// Data generator.
		modEventBus.addListener(ModDataGenerator::listen0);
		modEventBus.addListener(ModDataGenerator::listen1);
	}
}
