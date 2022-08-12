package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.block.BoxBlock;
import net.fabricmc.example.block.BoxBlockEntity;
import net.fabricmc.example.gui.BoxScreenHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("modid");
    public static final ScreenHandlerType<BoxScreenHandler> BOX_SCREEN_HANDLER;

    public static final Block BOX_BLOCK;
    public static final BlockItem BOX_BLOCK_ITEM;
    public static final BlockEntityType<BoxBlockEntity> BOX_BLOCK_ENTITY;

    public static final String MOD_ID = "testmod";
    // a public identifier for multiple parts of our bigger chest
    public static final Identifier BOX = new Identifier(MOD_ID, "box_block");

    static {
        BOX_BLOCK = Registry.register(Registry.BLOCK, BOX, new BoxBlock(FabricBlockSettings.copyOf(Blocks.CHEST)));
        BOX_BLOCK_ITEM = Registry.register(Registry.ITEM, BOX,
                new BlockItem(BOX_BLOCK, new Item.Settings().group(ItemGroup.MISC)));

        //The parameter of build at the very end is always null, do not worry about it
        // pre-1.17
        // BOX_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, BOX,
        //         BlockEntityType.Builder.create(BoxBlockEntity::new, BOX_BLOCK).build(null));
        // In 1.17 use FabricBlockEntityTypeBuilder instead of BlockEntityType.Builder
        BOX_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, BOX,
                FabricBlockEntityTypeBuilder.create(BoxBlockEntity::new, BOX_BLOCK).build(null));
        BOX_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(BOX, BoxScreenHandler::new);
    }

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Hello Fabric world!");
    }
}
