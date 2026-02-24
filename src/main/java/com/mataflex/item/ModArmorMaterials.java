package com.mataflex.item;

import com.mataflex.ValhallaEchoes;
import net.minecraft.util.Util;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier; // Tu archivo usa Identifier en lugar de ResourceLocation
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.core.registries.Registries;

import java.util.EnumMap;

public class ModArmorMaterials {

    public static final TagKey<Item> REPAIRS_MYSTICAL_ARMOR = TagKey.create(
            Registries.ITEM,
            Identifier.fromNamespaceAndPath(ValhallaEchoes.MOD_ID, "repairs_mystical_armor")
    );

    public static final ArmorMaterial MYSTICAL = new ArmorMaterial(
            33,
            Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 3);
                map.put(ArmorType.LEGGINGS, 6);
                map.put(ArmorType.CHESTPLATE, 8);
                map.put(ArmorType.HELMET, 3);
                map.put(ArmorType.BODY, 11);
            }),
            15,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            3.0F,
            0.2F,
            REPAIRS_MYSTICAL_ARMOR,

            ResourceKey.create(ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset")),
                    Identifier.fromNamespaceAndPath(ValhallaEchoes.MOD_ID, "mystical"))
    );

    public static void load() {}
}