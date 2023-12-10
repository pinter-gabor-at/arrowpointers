package eu.pintergabor.arrowpointers.main;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

import static eu.pintergabor.arrowpointers.main.ArrowRegistry.arrowMarkBlock;
import static eu.pintergabor.arrowpointers.main.ArrowRegistry.glowArrowMarkBlock;

public class ClientArrowRegistry {
	private ClientArrowRegistry() {
		// static class
	}

	public static void registerClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(arrowMarkBlock, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(glowArrowMarkBlock, RenderLayer.getCutout());
	}
}