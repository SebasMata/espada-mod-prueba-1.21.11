package com.mataflex;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class CustomSounds {

    private CustomSounds() {}

    public static final SoundEvent VIKING_GRUNT = registerSound("viking_grunt");

    private static SoundEvent registerSound(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(EspadaModPrueba.MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void initialize() {
        EspadaModPrueba.LOGGER.info("Registrando sonidos de vikingo...");
    }
}