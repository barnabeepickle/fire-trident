package com.github.barnabeepickle.firetrident.mixins;

import com.github.barnabeepickle.firetrident.FireTridentMod;
import net.minecraft.client.model.effects.SpinAttackEffectModel;
import net.minecraft.client.renderer.entity.layers.SpinAttackEffectLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SpinAttackEffectLayer.class)
public class SpinAttackEffectLayerMixin {
    @Redirect(method = "submit*",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/effects/SpinAttackEffectModel;renderType(Lnet/minecraft/resources/Identifier;)Lnet/minecraft/client/renderer/rendertype/RenderType;"))
    private RenderType replaceRenderType(SpinAttackEffectModel instance, Identifier texture) {
        if (FireTridentMod.lastCheckFireAspect) {
            texture = FireTridentMod.TRIDENT_FIRE_SPIN_TEXTURE;
        }

        return instance.renderType(texture);
    }
}
