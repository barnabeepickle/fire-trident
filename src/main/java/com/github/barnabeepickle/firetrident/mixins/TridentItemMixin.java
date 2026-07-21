package com.github.barnabeepickle.firetrident.mixins;

import com.github.barnabeepickle.firetrident.FireTridentMod;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@SuppressWarnings("unused")
@Mixin(TridentItem.class)
abstract class TridentItemMixin {
    @Redirect(method = "use",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInWaterOrRain()Z"))
    private boolean replaceUse(Player instance) {
        return FireTridentMod.replacementCheck(instance);
    }

    @Redirect(method = "releaseUsing",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInWaterOrRain()Z"))
    private boolean replaceReleaseUsing(Player instance) {
        return FireTridentMod.replacementCheck(instance);
    }
}
