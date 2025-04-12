package eu.pintergabor.arrowpointers.main;

import static eu.pintergabor.arrowpointers.main.ArrowRegistry.arrowMarkBlock;
import static eu.pintergabor.arrowpointers.main.ArrowRegistry.glowArrowMarkBlock;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;

import net.minecraft.client.renderer.RenderType;


public class ClientArrowRegistry {

	public static void registerClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(
			arrowMarkBlock, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(
			glowArrowMarkBlock, RenderType.cutout());
	}
}
