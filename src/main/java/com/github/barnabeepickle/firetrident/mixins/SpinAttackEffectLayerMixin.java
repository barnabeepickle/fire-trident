package com.github.barnabeepickle.firetrident.mixins;

import com.github.barnabeepickle.firetrident.FireTridentMod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.SpinAttackEffectLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpinAttackEffectLayer.class)
public class SpinAttackEffectLayerMixin {
    @Inject(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/AvatarRenderState;FF)V", at = @At("HEAD"))
    private void submit(
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            int i,
            AvatarRenderState avatarRenderState,
            float f,
            float g,
            CallbackInfo ci
    ) {
        SpinAttackEffectLayer.TEXTURE = FireTridentMod.TRIDENT_SPIN_TEXTURE;
        if (FireTridentMod.lastCheckFireAspect) {
            if (Minecraft.getInstance().level.precipitationAt(new BlockPos((int) avatarRenderState.x, (int) avatarRenderState.y, (int) avatarRenderState.z)) == Biome.Precipitation.SNOW) {
                SpinAttackEffectLayer.TEXTURE = FireTridentMod.TRIDENT_FIRE_SPIN_TEXTURE;
            }
        }
    }
}
