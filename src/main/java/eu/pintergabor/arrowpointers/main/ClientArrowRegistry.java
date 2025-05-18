package eu.pintergabor.arrowpointers.main;

import static eu.pintergabor.arrowpointers.main.ArrowRegistry.arrowMarkBlock;
import static eu.pintergabor.arrowpointers.main.ArrowRegistry.glowArrowMarkBlock;

import net.minecraft.client.renderer.RenderType;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;


public final class ClientArrowRegistry {

	/**
	 * Arrow marks are partly transparent.
	 */
	public static void registerClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(
			arrowMarkBlock, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(
			glowArrowMarkBlock, RenderType.cutout());
	}
}
