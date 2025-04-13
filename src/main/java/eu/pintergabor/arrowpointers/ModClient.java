package eu.pintergabor.arrowpointers;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;


/**
 * Client side startup.
 */
@Mod(value = Global.MODID, dist = Dist.CLIENT)
public final class ModClient {

	@SuppressWarnings("unused")
	public ModClient(IEventBus modEventBus, ModContainer modContainer) {
		// Data generator.
		modEventBus.addListener(DataGen::init);
	}
}
