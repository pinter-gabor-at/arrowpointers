package eu.pintergabor.arrowpointers.main;

import static eu.pintergabor.arrowpointers.main.ArrowRegistry.arrowMarkBlock;
import static eu.pintergabor.arrowpointers.main.ArrowRegistry.glowArrowMarkBlock;

import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;


public final class ClientArrowRegistry {

	/**
	 * Arrow marks are partly transparent.
	 */
	public static void init() {
		BlockRenderLayerMap.putBlock(
			arrowMarkBlock, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(
			glowArrowMarkBlock, ChunkSectionLayer.CUTOUT);
	}
}
