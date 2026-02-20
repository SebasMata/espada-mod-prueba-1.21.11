package com.mataflex.sound;

import com.mataflex.ValhallaEchoes;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class CustomSounds {

    private CustomSounds() {}

    public static final SoundEvent VIKING_GRUNT = registerSound("viking_grunt");
    public static final SoundEvent VIKING_DEATH = registerSound("viking_death");
    public static final SoundEvent VIKING_AMBIENT = registerSound("viking_ambient");
    public static final SoundEvent VIKING_ANGRY = registerSound("viking_angry");
    public static final SoundEvent VIKING_RETREAT = registerSound("viking_retreat");
    public static final SoundEvent VIKING_ADMIRING = registerSound("viking_admiring");
    public static final SoundEvent VIKING_CELEBRATE = registerSound("viking_celebrate");
    public static final SoundEvent VIKING_JEALOUS = registerSound("viking_jealous");


    private static SoundEvent registerSound(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(ValhallaEchoes.MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void initialize() {
        ValhallaEchoes.LOGGER.info("Registrando sonidos de vikingo...");
    }
}