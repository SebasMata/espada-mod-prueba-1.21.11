package com.mataflex.entity;

import com.mataflex.sound.CustomSounds;
import com.mataflex.item.ModItems;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class MysticalVikingEntity extends Piglin {

    private Brain<MysticalVikingEntity> mysticalVikingBrain;

    public MysticalVikingEntity(EntityType<? extends Piglin> entityType, Level level) {
        super(entityType, level);
        this.setImmuneToZombification(false);
    }

    @Override
    public void populateDefaultEquipmentSlots(RandomSource randomSource, @NonNull DifficultyInstance difficultyInstance) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(randomSource.nextInt(20) == 0 ? ModItems.MYSTICAL_SWORD : ModItems.MYSTICAL_AXE));
    }

    @Override
    public void checkDespawn() {
        super.checkDespawn();
        if (!this.level().isClientSide() && !this.isImmuneToZombification()) {
            this.setImmuneToZombification(true);
        }
    }

    @Override
    protected void dropCustomDeathLoot(@NonNull ServerLevel serverLevel, @NonNull DamageSource damageSource, boolean bl) {
        super.dropCustomDeathLoot(serverLevel, damageSource, bl);

        if (this.random.nextFloat() < 0.10f) {
            this.spawnAtLocation(serverLevel, new ItemStack(ModItems.MYSTICAL_ESSENCE, 1));
        } else if (this.random.nextFloat() < 0.65f) {
            this.spawnAtLocation(serverLevel, new ItemStack(Items.BONE, 3));
        } else {
            this.spawnAtLocation(serverLevel, new ItemStack(Items.IRON_INGOT, 1));
        }
    }

    @Override
    protected @NonNull Brain<?> makeBrain(@NonNull Dynamic<?> dynamic) {
        Brain<Piglin> piglinBrain = this.brainProvider().makeBrain(dynamic);

        @SuppressWarnings("unchecked")
        Brain<MysticalVikingEntity> vikingBrain = (Brain<MysticalVikingEntity>) (Brain<?>) piglinBrain;

        // GUARDAR PARA DESPUÉS
        this.mysticalVikingBrain = vikingBrain;

        return MysticalVikingAi.makeBrain(vikingBrain);
    }

    @Override
    protected void customServerAiStep(@NonNull ServerLevel serverLevel) {
        ProfilerFiller profilerFiller = Profiler.get();
        profilerFiller.push("mysticalVikingBrain");

        this.getMysticalBrain().tick(serverLevel, this);  // CAMBIADO

        profilerFiller.pop();

        MysticalVikingAi.updateActivity(this);

        super.customServerAiStep(serverLevel);
    }

    // Versión simplificada de getAmbientSound
    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        if (this.level().isClientSide()) return null;

        // Delegar en nuestra AI para todos los sonidos ambientales
        return MysticalVikingAi.getSoundForCurrentActivity(this).orElse(null);
    }

    @Override
    protected @NonNull SoundEvent getHurtSound(@NonNull DamageSource damageSource) {
        return CustomSounds.VIKING_GRUNT;
    }

    @Override
    protected @NonNull SoundEvent getDeathSound() {
        return CustomSounds.VIKING_DEATH;
    }

    @Override
    protected void playStepSound(@NonNull BlockPos pos, @NonNull BlockState state) {
        this.playSound(SoundEvents.PIGLIN_STEP, 0.3F, 0.7F);
    }

    public Brain<MysticalVikingEntity> getMysticalBrain() {
        if (this.mysticalVikingBrain == null) {
            @SuppressWarnings("unchecked")
            Brain<MysticalVikingEntity> brain = (Brain<MysticalVikingEntity>) (Brain<?>) super.getBrain();
            this.mysticalVikingBrain = brain;
        }
        return this.mysticalVikingBrain;
    }

}