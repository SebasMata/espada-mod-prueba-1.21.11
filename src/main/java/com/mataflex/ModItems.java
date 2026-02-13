package com.mataflex;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.Level;

import java.util.function.Function;

public class ModItems {

    public static final Item SUSPICIOUS_SUBSTANCE = register("suspicious_substance", Item::new, new Item.Properties());

    public static final ToolMaterial MYSTICAL_TOOL_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            16384,
            20F,
            1.5F,
            15,
            ToolMaterial.DIAMOND.repairItems()
    );

    public static final Item MYSTICAL_SWORD = register(
            "mystical_sword",
            Item::new,
            new Item.Properties().sword(MYSTICAL_TOOL_MATERIAL, 17.5f, -2.4f)
    );

    public static final Item MYSTICAL_AXE = register (
            "mystical_axe",
            Item::new,
            new Item.Properties().axe(MYSTICAL_TOOL_MATERIAL, 24.5f, -3.0f)
    );

    public static final Item MYSTICAL_PICKAXE = register (
            "mystical_pickaxe",
            Item::new,
            new Item.Properties().pickaxe(MYSTICAL_TOOL_MATERIAL, 4f, -2.8f)
    );

    public static <GenericItem extends Item> GenericItem register(String name, Function<Item.Properties, GenericItem> itemFactory, Item.Properties settings) {
        // Create the item key.
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(EspadaModPrueba.MOD_ID, name));

        // Create the item instance.
        GenericItem item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static void initialize() {
        // Get the event for modifying entries in the ingredients group.
        // And register an event handler that adds our suspicious item to the ingredients group.
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS)
                .register((itemGroup) -> itemGroup.accept(ModItems.SUSPICIOUS_SUBSTANCE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT)
                .register((itemGroup) -> itemGroup.accept(ModItems.MYSTICAL_SWORD));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT)
                .register((itemGroup) -> itemGroup.accept(ModItems.MYSTICAL_AXE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register((itemGroup) -> itemGroup.accept(ModItems.MYSTICAL_PICKAXE));

    }

}