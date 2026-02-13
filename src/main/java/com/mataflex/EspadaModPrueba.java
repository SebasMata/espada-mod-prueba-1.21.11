package com.mataflex;

import com.mataflex.entity.MysticalVikingEntity;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EspadaModPrueba implements ModInitializer {
	public static final String MOD_ID = "espada-mod-prueba";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final EntityType<MysticalVikingEntity> MYSTICAL_VIKING =
			Registry.register(
					BuiltInRegistries.ENTITY_TYPE,
					Identifier.fromNamespaceAndPath(MOD_ID, "mystical_viking"),
					EntityType.Builder.of(
									MysticalVikingEntity::new,
									MobCategory.MONSTER
							)
							.sized(0.6f, 1.95f)
							.build(ResourceKey.create(
									Registries.ENTITY_TYPE,
									Identifier.fromNamespaceAndPath(MOD_ID, "mystical_viking")
							))
			);

	@Override
	public void onInitialize() {

		LOGGER.info("Hello Fabric world!");
		ModItems.initialize();

		FabricDefaultAttributeRegistry.register(
				MYSTICAL_VIKING,
				ZombifiedPiglin.createAttributes()
		);

		CustomSounds.initialize();

	}

}