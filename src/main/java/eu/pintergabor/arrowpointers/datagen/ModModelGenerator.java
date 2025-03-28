package eu.pintergabor.arrowpointers.datagen;

import static net.minecraft.client.data.BlockStateModelGenerator.*;

import java.util.Optional;

import eu.pintergabor.arrowpointers.Global;
import eu.pintergabor.arrowpointers.blocks.ArrowMarkBlock;

import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.data.Model;
import net.minecraft.client.data.ModelIds;
import net.minecraft.client.data.TextureKey;
import net.minecraft.client.data.TextureMap;
import net.minecraft.client.data.VariantsBlockModelDefinitionCreator;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;


public class ModModelGenerator {
	private final BlockStateModelGenerator generator;

	public ModModelGenerator(BlockStateModelGenerator generator) {
		this.generator = generator;
	}

	/**
	 * Create blockstates for all directions and one orientation
	 *
	 * @param map     to add the blockstates
	 * @param modelId block model *.json file name
	 */
	public static void registerFlatNormal(
		BlockStateVariantMap.DoubleProperty<WeightedVariant, Direction, Integer> map,
		int orientation, Identifier modelId) {
		map.register(Direction.DOWN, orientation,
			BlockStateModelGenerator.createWeightedVariant(modelId)
				.apply(ROTATE_X_180));
		map.register(Direction.UP, orientation,
			BlockStateModelGenerator.createWeightedVariant(modelId));
		map.register(Direction.NORTH, orientation,
			BlockStateModelGenerator.createWeightedVariant(modelId)
				.apply(ROTATE_X_270.then(ROTATE_Y_180)));
		map.register(Direction.SOUTH, orientation,
			BlockStateModelGenerator.createWeightedVariant(modelId)
				.apply(ROTATE_X_270));
		map.register(Direction.WEST, orientation,
			BlockStateModelGenerator.createWeightedVariant(modelId)
				.apply(ROTATE_X_270.then(ROTATE_Y_90)));
		map.register(Direction.EAST, orientation,
			BlockStateModelGenerator.createWeightedVariant(modelId)
				.apply(ROTATE_X_270.then(ROTATE_Y_270)));
	}

	/**
	 * Create blockstates for all directions and one orientation
	 * <p>
	 * Flip model 180 degree in Y direction
	 *
	 * @param map     to add the blockstates
	 * @param modelId block model *.json file name
	 */
	public static void registerFlatFlipped(
		BlockStateVariantMap.DoubleProperty<WeightedVariant, Direction, Integer> map,
		int orientation, Identifier modelId) {
		map.register(Direction.DOWN, orientation,
			BlockStateModelGenerator.createWeightedVariant(modelId)
				.apply(ROTATE_X_180.then(ROTATE_Y_180)));
		map.register(Direction.UP, orientation,
			BlockStateModelGenerator.createWeightedVariant(modelId)
				.apply(ROTATE_Y_180));
		map.register(Direction.NORTH, orientation,
			BlockStateModelGenerator.createWeightedVariant(modelId)
				.apply(ROTATE_X_90));
		map.register(Direction.SOUTH, orientation,
			BlockStateModelGenerator.createWeightedVariant(modelId)
				.apply(ROTATE_X_90.then(ROTATE_Y_180)));
		map.register(Direction.WEST, orientation,
			BlockStateModelGenerator.createWeightedVariant(modelId)
				.apply(ROTATE_X_90.then(ROTATE_Y_270)));
		map.register(Direction.EAST, orientation,
			BlockStateModelGenerator.createWeightedVariant(modelId)
				.apply(ROTATE_X_90.then(ROTATE_Y_90)));
	}

	public static final String TEMPLATE = "block/template_arrow_mark";
	public static final String TEMPLATE_ROTATED = "block/template_arrow_mark_rotated";

	/**
	 * Create one model
	 */
	public Identifier createFlatModel(Block block, String template, String modelSuffix, String textureSuffix) {
		Model model = new Model(Optional.of(Global.ModIdentifier(template)),
			Optional.empty(), TextureKey.TEXTURE);
		return generator.createSubModel(block, modelSuffix, model, identifier -> new TextureMap()
			.put(TextureKey.TEXTURE, ModelIds.getBlockSubModelId(block, textureSuffix)));
	}

	/**
	 * Create models and blockstates for 6 directions and 9 orientations
	 */
	public BlockStateVariantMap<WeightedVariant> createFlat9Direction(Block block) {
		// Models
		Identifier center = createFlatModel(block,
			TEMPLATE, "_center", "_center");
		Identifier topleft = createFlatModel(block,
			TEMPLATE_ROTATED, "_top_left", "_diagonal");
		Identifier top = createFlatModel(block,
			TEMPLATE, "_top", "");
		Identifier topright = createFlatModel(block,
			TEMPLATE, "_top_right", "_diagonal");
		Identifier left = createFlatModel(block,
			TEMPLATE_ROTATED, "_left", "");
		// BlockStates
		var map = BlockStateVariantMap
			.models(Properties.FACING, ArrowMarkBlock.ORIENTATION);
		registerFlatNormal(map, 0, topleft);
		registerFlatNormal(map, 1, top);
		registerFlatNormal(map, 2, topright);
		registerFlatNormal(map, 3, left);
		registerFlatNormal(map, 4, center);
		registerFlatFlipped(map, 5, left);
		registerFlatFlipped(map, 6, topright);
		registerFlatFlipped(map, 7, top);
		registerFlatFlipped(map, 8, topleft);
		return map;
	}

	/**
	 * Generate models and blockstates for a thin, flat model that has 6 directions and 9 orientations
	 */
	public void registerFlat9Direction(Block block) {
		generator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator
			.of(block).with(createFlat9Direction(block)));
	}
}
