package com.mataflex.entity;

import com.mataflex.CustomSounds;
import com.mataflex.ModItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MysticalVikingEntity extends ZombifiedPiglin {

    public MysticalVikingEntity(EntityType<? extends ZombifiedPiglin> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(randomSource.nextInt(20) == 0 ? ModItems.MYSTICAL_SWORD : ModItems.MYSTICAL_AXE));
    }

//    @Override
//    public void onAddedToWorld() {
//        super.onAddedToWorld();
//
//        if (!this.level().isClientSide()) {
//            this.playSound(CustomSounds.VIKING_GRUNT, 1.0f, 1.0f);
//        }
//    }

}