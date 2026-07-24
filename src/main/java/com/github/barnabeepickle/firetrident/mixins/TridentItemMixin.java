package com.github.barnabeepickle.firetrident.mixins;

import com.github.barnabeepickle.firetrident.FireTridentMod;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@SuppressWarnings("unused")
@Mixin(TridentItem.class)
abstract class TridentItemMixin {
    @Unique
    private static final Identifier FIRE_ASPECT = Identifier.withDefaultNamespace("fire_aspect");

    @Unique
    private static boolean snowCheck(Player instance) {
        if (instance.isInWaterOrRain()) {
            FireTridentMod.lastCheckFireAspect = false;
            return true;
        } else if (FireTridentMod.isInSnow(instance)) {
            ItemStack item = instance.getActiveItem();
            if (item.getItem() instanceof TridentItem) {
                boolean fireAspect = FireTridentMod.checkForEnchant(item, FIRE_ASPECT);
                FireTridentMod.lastCheckFireAspect = fireAspect;
                return fireAspect;
            } else {
                FireTridentMod.LOGGER.warn("Didn't get an instance of TridentItem back for the fire aspect check");
            }
        }

        FireTridentMod.lastCheckFireAspect = false;
        return false;
    }

    @Redirect(method = "use",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInWaterOrRain()Z"))
    private boolean replaceUse(Player instance) {
        return snowCheck(instance);
    }

    @Redirect(method = "releaseUsing",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInWaterOrRain()Z"))
    private boolean replaceReleaseUsing(Player instance) {
        return snowCheck(instance);
    }
}
