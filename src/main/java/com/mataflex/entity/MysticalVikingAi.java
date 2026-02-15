package com.mataflex.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mataflex.ModItems;
import com.mojang.datafixers.util.Pair;
import com.mataflex.CustomSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import java.util.Optional;

public class MysticalVikingAi {

    // === CONSTRUCCIÓN DEL CEREBRO ===

    public static Brain<?> makeBrain(Brain<MysticalVikingEntity> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initFightActivity(brain);
        initCelebrateActivity(brain);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();

        return brain;
    }

    // === ACTIVIDADES ===

    private static void initCoreActivity(Brain<MysticalVikingEntity> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                InteractWithDoor.create(),
                StopAttackingIfTargetInvalid.create()
        ));
    }

    @SuppressWarnings("unchecked")
    private static <T extends BehaviorControl<?>> BehaviorControl<MysticalVikingEntity> cast(T behavior) {
        return (BehaviorControl<MysticalVikingEntity>) behavior;
    }

    private static void initIdleActivity(Brain<MysticalVikingEntity> brain) {
        brain.addActivity(Activity.IDLE, 10, ImmutableList.of(
                cast(SetEntityLookTarget.create(MysticalVikingAi::isPlayerHoldingValuableItem, 14.0F)),
                cast(StartAttacking.create(MysticalVikingAi::findNearestValidAttackTarget)),
                new RunOne<MysticalVikingEntity>(ImmutableList.of(
                        Pair.of(cast(SetEntityLookTarget.create(EntityType.PLAYER, 8.0F)), 1),
                        Pair.of(cast(SetEntityLookTarget.create(EntityType.PIGLIN, 8.0F)), 1),
                        Pair.of(cast(new DoNothing(30, 60)), 1)
                )),
                new RunOne<MysticalVikingEntity>(ImmutableList.of(
                        Pair.of(cast(RandomStroll.stroll(0.6F)), 2),
                        Pair.of(cast(new DoNothing(30, 60)), 1)
                ))
        ));
    }

    private static void initFightActivity(Brain<MysticalVikingEntity> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(
                Activity.FIGHT,
                10,
                ImmutableList.of(
                        StopAttackingIfTargetInvalid.create(),
                        SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.2F),
                        MeleeAttack.create(20)
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }

    private static void initCelebrateActivity(Brain<MysticalVikingEntity> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(
                Activity.CELEBRATE,
                10,
                ImmutableList.of(
                        SetEntityLookTarget.create(EntityType.PLAYER, 8.0F),
                        new RunOne<>(ImmutableList.of(
                                Pair.of(RandomStroll.stroll(0.6F, 2, 1), 1),
                                Pair.of(new DoNothing(10, 20), 1)
                        ))
                ),
                MemoryModuleType.CELEBRATE_LOCATION
        );
    }

    // === COMPORTAMIENTOS AUXILIARES ===

    private static BehaviorControl<? extends LivingEntity> createIdleLookBehaviors() {
        return new RunOne<>(ImmutableList.of(
                Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 8.0F), 1),
                Pair.of(SetEntityLookTarget.create(EntityType.PIGLIN, 8.0F), 1),
                Pair.of(new DoNothing(30, 60), 1)
        ));
    }

    private static BehaviorControl<? extends LivingEntity> createIdleMovementBehaviors() {
        return new RunOne<>(ImmutableList.of(
                Pair.of(RandomStroll.stroll(0.6F), 2),
                Pair.of(new DoNothing(30, 60), 1)
        ));
    }

    // === ACTUALIZACIÓN DE ACTIVIDAD ===

    public static void updateActivity(MysticalVikingEntity viking) {
        Brain<MysticalVikingEntity> brain = viking.getMysticalBrain();
        Activity currentActivity = brain.getActiveNonCoreActivity().orElse(null);

        brain.setActiveActivityToFirstValid(ImmutableList.of(
                Activity.FIGHT,
                Activity.CELEBRATE,
                Activity.IDLE
        ));

        Activity newActivity = brain.getActiveNonCoreActivity().orElse(null);

        if (currentActivity != newActivity) {
            getSoundForCurrentActivity(viking).ifPresent(viking::makeSound);
        }

        viking.setAggressive(brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
    }

    // === SELECCIÓN DE SONIDOS ===

    public static Optional<SoundEvent> getSoundForCurrentActivity(MysticalVikingEntity viking) {
        return viking.getMysticalBrain().getActiveNonCoreActivity()
                .map(activity -> getSoundForActivity(viking, activity));
    }

    private static SoundEvent getSoundForActivity(MysticalVikingEntity viking, Activity activity) {
        if (activity == Activity.FIGHT) {
            return CustomSounds.VIKING_ANGRY;
        } else if (activity == Activity.CELEBRATE) {
            return CustomSounds.VIKING_CELEBRATE;
        } else if (seesPlayerHoldingValuableItem(viking)) {
            return CustomSounds.VIKING_JEALOUS;
        } else {
            return CustomSounds.VIKING_AMBIENT;
        }
    }

    // === LÓGICA AUXILIAR ===

    private static boolean isPlayerHoldingValuableItem(LivingEntity entity) {
        if (entity instanceof Player player) {
            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();

            return mainHand.is(ModItems.MYSTICAL_SWORD) ||
                    offHand.is(ModItems.MYSTICAL_SWORD) ||
                    mainHand.is(ModItems.MYSTICAL_AXE) ||
                    offHand.is(ModItems.MYSTICAL_AXE);
        }
        return false;
    }

    private static boolean seesPlayerHoldingValuableItem(MysticalVikingEntity viking) {
        return viking.getMysticalBrain().hasMemoryValue(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM);
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(ServerLevel level, MysticalVikingEntity viking) {

        return viking.getMysticalBrain()
                .getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);

    }
}