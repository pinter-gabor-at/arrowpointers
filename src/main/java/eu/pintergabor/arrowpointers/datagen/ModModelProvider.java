package eu.pintergabor.arrowpointers.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;


public class ModModelProvider extends FabricModelProvider {
	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		//ExBlockStateModelGenerator exGenerator = new ExBlockStateModelGenerator(blockStateModelGenerator);
		//exGenerator.registerFlat9Direction(ArrowRegistry.testArrowMarkBlock);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
	}
}
