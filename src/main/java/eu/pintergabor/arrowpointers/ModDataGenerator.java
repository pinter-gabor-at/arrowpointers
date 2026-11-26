package eu.pintergabor.arrowpointers;

import java.util.List;
import java.util.Set;

import eu.pintergabor.arrowpointers.datagen.ModBlockLootTableGenerator;
import eu.pintergabor.arrowpointers.datagen.ModModelProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;


public final class ModDataGenerator {

	public static void listen0(final @NonNull GatherDataRegistryEntriesEvent event) {
		event
			// Create loot tables.
			.add(Registries.LOOT_TABLE, new LootTableProvider(Set.of(), List.of(
				new LootTableProvider.SubProviderEntry(ModBlockLootTableGenerator::new, LootContextParamSets.BLOCK)
			)));
	}

	public static void listen1(final GatherDataEvent.@NonNull Client event) {
		final DataGenerator generator = event.getGenerator();
		final PackOutput output = generator.getPackOutput();
		// Create models.
		generator.addProvider(true, new ModModelProvider(output));
	}
}
