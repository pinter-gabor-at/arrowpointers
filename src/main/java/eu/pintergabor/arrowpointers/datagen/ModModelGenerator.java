package eu.pintergabor.arrowpointers.datagen;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

import java.util.Optional;

import eu.pintergabor.arrowpointers.Global;
import eu.pintergabor.arrowpointers.blocks.ArrowMarkBlock;

import eu.pintergabor.arrowpointers.util.BlockRegion;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;


public class ModModelGenerator {
	private final BlockModelGenerators generator;

	public ModModelGenerator(BlockModelGenerators generator) {
		this.generator = generator;
	}

	/**
	 * Create blockstates for all directions and one orientation.
	 *
	 * @param map     to add the blockstates.
	 * @param modelId block model *.json file name.
	 */
	public static void registerFlatNormal(
		PropertyDispatch.C2<MultiVariant, Direction, BlockRegion> map,
		BlockRegion orientation, ResourceLocation modelId) {
		map.select(Direction.DOWN, orientation,
			BlockModelGenerators.plainVariant(modelId)
				.with(X_ROT_180));
		map.select(Direction.UP, orientation,
			BlockModelGenerators.plainVariant(modelId));
		map.select(Direction.NORTH, orientation,
			BlockModelGenerators.plainVariant(modelId)
				.with(X_ROT_270.then(Y_ROT_180)));
		map.select(Direction.SOUTH, orientation,
			BlockModelGenerators.plainVariant(modelId)
				.with(X_ROT_270));
		map.select(Direction.WEST, orientation,
			BlockModelGenerators.plainVariant(modelId)
				.with(X_ROT_270.then(Y_ROT_90)));
		map.select(Direction.EAST, orientation,
			BlockModelGenerators.plainVariant(modelId)
				.with(X_ROT_270.then(Y_ROT_270)));
	}

	/**
	 * Create blockstates for all directions and one orientation.
	 * <p>
	 * Flip model 180 degree in Y direction.
	 *
	 * @param map     to add the blockstates.
	 * @param modelId block model *.json file name.
	 */
	public static void registerFlatFlipped(
		PropertyDispatch.C2<MultiVariant, Direction, BlockRegion> map,
		BlockRegion orientation, ResourceLocation modelId) {
		map.select(Direction.DOWN, orientation,
			BlockModelGenerators.plainVariant(modelId)
				.with(X_ROT_180.then(Y_ROT_180)));
		map.select(Direction.UP, orientation,
			BlockModelGenerators.plainVariant(modelId)
				.with(Y_ROT_180));
		map.select(Direction.NORTH, orientation,
			BlockModelGenerators.plainVariant(modelId)
				.with(X_ROT_90));
		map.select(Direction.SOUTH, orientation,
			BlockModelGenerators.plainVariant(modelId)
				.with(X_ROT_90.then(Y_ROT_180)));
		map.select(Direction.WEST, orientation,
			BlockModelGenerators.plainVariant(modelId)
				.with(X_ROT_90.then(Y_ROT_270)));
		map.select(Direction.EAST, orientation,
			BlockModelGenerators.plainVariant(modelId)
				.with(X_ROT_90.then(Y_ROT_90)));
	}

	public static final String TEMPLATE = "block/template_arrow_mark";
	public static final String TEMPLATE_ROTATED = "block/template_arrow_mark_rotated";

	/**
	 * Create one model.
	 */
	public ResourceLocation createFlatModel(
		Block block, String template, String modelSuffix, String textureSuffix) {
		ModelTemplate model = new ModelTemplate(Optional.of(Global.modId(template)),
			Optional.empty(), TextureSlot.TEXTURE);
		return generator.createSuffixedVariant(block, modelSuffix, model,
			identifier -> new TextureMapping()
				.put(TextureSlot.TEXTURE, ModelLocationUtils.getModelLocation(block, textureSuffix)));
	}

	/**
	 * Create models and blockstates for 6 directions and 9 orientations.
	 */
	public PropertyDispatch<MultiVariant> createFlat9Direction(Block block) {
		// Models.
		ResourceLocation center = createFlatModel(block,
			TEMPLATE, "_center", "_center");
		ResourceLocation topleft = createFlatModel(block,
			TEMPLATE_ROTATED, "_top_left", "_diagonal");
		ResourceLocation top = createFlatModel(block,
			TEMPLATE, "_top", "");
		ResourceLocation topright = createFlatModel(block,
			TEMPLATE, "_top_right", "_diagonal");
		ResourceLocation left = createFlatModel(block,
			TEMPLATE_ROTATED, "_left", "");
		// Block states.
		var map = PropertyDispatch
			.initial(BlockStateProperties.FACING, ArrowMarkBlock.ORIENTATION);
		registerFlatNormal(map, BlockRegion.TOPLEFT, topleft);
		registerFlatNormal(map, BlockRegion.TOPCENTER, top);
		registerFlatNormal(map, BlockRegion.TOPRIGHT, topright);
		registerFlatNormal(map, BlockRegion.MIDDLELEFT, left);
		registerFlatNormal(map, BlockRegion.MIDDLECENTER, center);
		registerFlatFlipped(map, BlockRegion.MIDDLERIGHT, left);
		registerFlatFlipped(map, BlockRegion.BOTTOMLEFT, topright);
		registerFlatFlipped(map, BlockRegion.BOTTOMCENTER, top);
		registerFlatFlipped(map, BlockRegion.BOTTOMRIGHT, topleft);
		return map;
	}

	/**
	 * Generate models and blockstates for a thin,
	 * flat model that has 6 directions and 9 orientations.
	 */
	public void registerFlat9Direction(Block block) {
		generator.blockStateOutput.accept(MultiVariantGenerator
			.dispatch(block).with(createFlat9Direction(block)));
	}
}
