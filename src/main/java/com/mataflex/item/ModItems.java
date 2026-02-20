package com.mataflex.item;

import com.mataflex.ValhallaEchoes;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import java.util.function.Function;

public class ModItems {

    // ITEMS
    public static final Item SUSPICIOUS_SUBSTANCE = register("suspicious_substance", Item::new, new Item.Properties());

    public static final Consumable REGENARATION_FOOD_CONSUMABLE_COMPONENT = Consumables.defaultFood()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 180 * 20, 1), 1.0f))
            .build();

    public static final FoodProperties REGENERATION_FOOD_COMPONENT = new FoodProperties.Builder()
            .alwaysEdible()
            .build();

    public static final Item MYSTICAL_ESSENCE = register("mystical_essence", Item::new,
            new Item.Properties().food(REGENERATION_FOOD_COMPONENT, REGENARATION_FOOD_CONSUMABLE_COMPONENT));

    public static final Item MYSTICAL_INGOT = register("mystical_ingot", Item::new, new Item.Properties());

    public static final Item OBSIDIAN_ROD = register("obsidian_rod", Item::new, new Item.Properties());

    public static final Item RUNIC_GEM = register("runic_gem", Item::new, new Item.Properties());

    public static final SpawnEggItem VIKING_SPAWN_EGG = register(
            "viking_spawn_egg",
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(ValhallaEchoes.MYSTICAL_VIKING)
    );

    // MYSTICAL TOOL MATERIAL
    public static final ToolMaterial MYSTICAL_TOOL_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            16384,
            20F,
            1.5F,
            15,
            ToolMaterial.DIAMOND.repairItems()
    );

    // MYSTICAL TOOLS
    public static final Item MYSTICAL_SWORD = register(
            "mystical_sword",
            Item::new,
            new Item.Properties().sword(MYSTICAL_TOOL_MATERIAL, 15f, -2.0f)
    );

    public static final Item MYSTICAL_AXE = register (
            "mystical_axe",
            Item::new,
            new Item.Properties().axe(MYSTICAL_TOOL_MATERIAL, 18.5f, -3.0f)
    );

    public static final Item MYSTICAL_PICKAXE = register (
            "mystical_pickaxe",
            Item::new,
            new Item.Properties().pickaxe(MYSTICAL_TOOL_MATERIAL, 4.5f, -2.8f)
    );

    public static final Item MYSTICAL_SHOVEL = register (
            "mystical_shovel",
            Item::new,
            new Item.Properties().shovel(MYSTICAL_TOOL_MATERIAL, 4.5f, -3.0f)
    );

    public static <GenericItem extends Item> GenericItem register(String name, Function<Item.Properties, GenericItem> itemFactory, Item.Properties settings) {
        // Create the item key.
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ValhallaEchoes.MOD_ID, name));

        // Create the item instance.
        GenericItem item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    // Targe shield
    public static final Item TARGE_SHIELD = register(
            "targe_shield",
            TargeShieldItem::new,
            new Item.Properties()
                    .durability(500)
    );

    public static void initialize() {

        // ITEMS
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS)
                .register((itemGroup) -> itemGroup.accept(ModItems.SUSPICIOUS_SUBSTANCE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(ModItems.MYSTICAL_ESSENCE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS)
                .register((itemGroup) -> itemGroup.accept(ModItems.MYSTICAL_INGOT));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS)
                .register((itemGroup) -> itemGroup.accept(ModItems.OBSIDIAN_ROD));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS)
                .register(itemGroup -> itemGroup.accept(ModItems.VIKING_SPAWN_EGG));

        // TOOLS
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT)
                .register((itemGroup) -> itemGroup.accept(ModItems.MYSTICAL_SWORD));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT)
                .register((itemGroup) -> itemGroup.accept(ModItems.MYSTICAL_AXE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register((itemGroup) -> itemGroup.accept(ModItems.MYSTICAL_PICKAXE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register((itemGroup) -> itemGroup.accept(ModItems.MYSTICAL_SHOVEL));
    }

}