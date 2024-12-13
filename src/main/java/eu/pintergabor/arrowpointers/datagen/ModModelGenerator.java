package eu.pintergabor.arrowpointers.datagen;

import eu.pintergabor.arrowpointers.Global;
import eu.pintergabor.arrowpointers.blocks.ArrowMarkBlock;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Optional;

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
            BlockStateVariantMap.DoubleProperty<Direction, Integer> map,
            int orientation, Identifier modelId) {
        map.register(Direction.DOWN, orientation, BlockStateVariant.create()
                .put(VariantSettings.MODEL, modelId)
                .put(VariantSettings.X, VariantSettings.Rotation.R180)
        );
        map.register(Direction.UP, orientation, BlockStateVariant.create()
                .put(VariantSettings.MODEL, modelId)
        );
        map.register(Direction.NORTH, orientation, BlockStateVariant.create()
                .put(VariantSettings.MODEL, modelId)
                .put(VariantSettings.X, VariantSettings.Rotation.R270)
                .put(VariantSettings.Y, VariantSettings.Rotation.R180)
        );
        map.register(Direction.SOUTH, orientation, BlockStateVariant.create()
                .put(VariantSettings.MODEL, modelId)
                .put(VariantSettings.X, VariantSettings.Rotation.R270)
        );
        map.register(Direction.WEST, orientation, BlockStateVariant.create()
                .put(VariantSettings.MODEL, modelId)
                .put(VariantSettings.X, VariantSettings.Rotation.R270)
                .put(VariantSettings.Y, VariantSettings.Rotation.R90)
        );
        map.register(Direction.EAST, orientation, BlockStateVariant.create()
                .put(VariantSettings.MODEL, modelId)
                .put(VariantSettings.X, VariantSettings.Rotation.R270)
                .put(VariantSettings.Y, VariantSettings.Rotation.R270)
        );
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
            BlockStateVariantMap.DoubleProperty<Direction, Integer> map,
            int orientation, Identifier modelId) {
        map.register(Direction.DOWN, orientation, BlockStateVariant.create()
                .put(VariantSettings.MODEL, modelId)
                .put(VariantSettings.X, VariantSettings.Rotation.R180)
                .put(VariantSettings.Y, VariantSettings.Rotation.R180)
        );
        map.register(Direction.UP, orientation, BlockStateVariant.create()
                .put(VariantSettings.MODEL, modelId)
                .put(VariantSettings.Y, VariantSettings.Rotation.R180)
        );
        map.register(Direction.NORTH, orientation, BlockStateVariant.create()
                .put(VariantSettings.MODEL, modelId)
                .put(VariantSettings.X, VariantSettings.Rotation.R90)
        );
        map.register(Direction.SOUTH, orientation, BlockStateVariant.create()
                .put(VariantSettings.MODEL, modelId)
                .put(VariantSettings.X, VariantSettings.Rotation.R90)
                .put(VariantSettings.Y, VariantSettings.Rotation.R180)
        );
        map.register(Direction.WEST, orientation, BlockStateVariant.create()
                .put(VariantSettings.MODEL, modelId)
                .put(VariantSettings.X, VariantSettings.Rotation.R90)
                .put(VariantSettings.Y, VariantSettings.Rotation.R270)
        );
        map.register(Direction.EAST, orientation, BlockStateVariant.create()
                .put(VariantSettings.MODEL, modelId)
                .put(VariantSettings.X, VariantSettings.Rotation.R90)
                .put(VariantSettings.Y, VariantSettings.Rotation.R90)
        );
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
    public BlockStateVariantMap createFlat9Direction(Block block) {
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
                .create(Properties.FACING, ArrowMarkBlock.ORIENTATION);
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
        generator.blockStateCollector.accept(VariantsBlockStateSupplier
                .create(block).coordinate(createFlat9Direction(block)));
    }
}
