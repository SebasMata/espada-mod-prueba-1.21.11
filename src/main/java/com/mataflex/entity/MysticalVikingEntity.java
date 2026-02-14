package com.mataflex.entity;

import com.mataflex.CustomSounds;
import com.mataflex.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class MysticalVikingEntity extends Piglin {

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
    protected @Nullable SoundEvent getAmbientSound() {
        if (this.level().isClientSide()) return null;

        if (this.random.nextFloat() < 0.8f) {
            return CustomSounds.VIKING_AMBIENT;
        }
        return CustomSounds.VIKING_AMBIENT_2;
    }

    @Override
    protected @NonNull SoundEvent getHurtSound(DamageSource damageSource) {
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

}