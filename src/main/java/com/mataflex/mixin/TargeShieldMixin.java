package com.mataflex.mixin;

import com.mataflex.item.TargeShieldItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class) // Cambiamos a LivingEntity para capturar el daño mejor
public abstract class TargeShieldMixin {

	@Unique
    private void onHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if ((Object) this instanceof Player player) {

			if (player.isUsingItem() && player.getUseItem().getItem() instanceof TargeShieldItem) {

				int useTicks = player.getTicksUsingItem();

				if (useTicks <= 10) {
					// --- PERFECT TIMED PARRY ---
					player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
							SoundEvents.MACE_SMASH_GROUND, SoundSource.PLAYERS, 1.0F, 1.0F);

					if (source.getDirectEntity() instanceof LivingEntity attacker) {
						attacker.knockback(0.5D, player.getX() - attacker.getX(), player.getZ() - attacker.getZ());
					}

					cir.setReturnValue(false);

				} else {
					// --- COMMON BLOCK ---
					player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
							SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0F, 1.0F);

					EquipmentSlot slot = player.getUsedItemHand() == net.minecraft.world.InteractionHand.MAIN_HAND
							? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;

					player.getUseItem().hurtAndBreak((int) amount, player, slot);

					cir.setReturnValue(false);
				}
			}
		}
	}
}