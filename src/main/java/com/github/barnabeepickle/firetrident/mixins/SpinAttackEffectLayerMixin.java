package com.github.barnabeepickle.firetrident.mixins;

import com.github.barnabeepickle.firetrident.FireTridentMod;

import net.minecraft.client.renderer.entity.layers.SpinAttackEffectLayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SpinAttackEffectLayer.class)
public class SpinAttackEffectLayerMixin {
    @Redirect(method = "render*",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;entityCutoutNoCull(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"))
    private RenderType replaceRenderType(ResourceLocation texture) {
        if (FireTridentMod.lastCheckFireAspect) {
            texture = FireTridentMod.TRIDENT_FIRE_SPIN_TEXTURE;
        }

        return RenderType.entityCutoutNoCull(texture);
    }
}
