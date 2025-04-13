package eu.pintergabor.arrowpointers;

import java.util.List;
import java.util.Set;

import eu.pintergabor.arrowpointers.datagen.ModBlockLootTableGenerator;
import eu.pintergabor.arrowpointers.datagen.ModModelProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;


public final class DataGen {

	public static void init(GatherDataEvent.Client event) {
		// Create models.
		event.createProvider(ModModelProvider::new);
		// Create loot tables.
		event.createProvider((output, lookupProvider) ->
			new LootTableProvider(output, Set.of(), List.of(
				new LootTableProvider.SubProviderEntry(
					ModBlockLootTableGenerator::new,
					LootContextParamSets.BLOCK)), lookupProvider));
	}
}
