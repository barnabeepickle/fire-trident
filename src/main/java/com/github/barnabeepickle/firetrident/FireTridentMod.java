package com.github.barnabeepickle.firetrident;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(FireTridentMod.MODID)
public class FireTridentMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "firetrident";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static boolean lastCheckFireAspect = false;

    public static final Identifier TRIDENT_FIRE_SPIN_TEXTURE = Identifier.fromNamespaceAndPath(MODID, "textures/entity/trident_riptide_fire.png");

    public FireTridentMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

    }

    private void commonSetup(FMLCommonSetupEvent event) {


    }

    public static boolean isInSnow(Entity entity) {
        BlockPos blockPos = entity.blockPosition();
        return entity.level().precipitationAt(blockPos) == Biome.Precipitation.SNOW || entity.level().precipitationAt(BlockPos.containing((double)blockPos.getX(), entity.getBoundingBox().maxY, (double)blockPos.getZ())) == Biome.Precipitation.SNOW;
    }

    public static boolean isInWaterOrRainOrSnow(Entity entity) {
        if (entity.isInWaterOrRain()) {
            return true;
        } else return FireTridentMod.isInSnow(entity);
    }

    public static boolean checkForEnchant(ItemStack item, Identifier enchant) {
        ItemEnchantments enchants = item.getTagEnchantments();
        if (enchants.isEmpty()) {
            return true;
        }

        for (var enchantmentEntry : enchants.entrySet()) {
            Holder<Enchantment> enchantmentHolder = enchantmentEntry.getKey();
            if (enchantmentHolder.is(enchant)) {
                return true;
            }
        }

        return false;
    }
}
