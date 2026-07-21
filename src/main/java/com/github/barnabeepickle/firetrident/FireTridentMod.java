package com.github.barnabeepickle.firetrident;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.biome.Biome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FireTridentMod implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger(Tags.MODID);

	@Override
	public void onInitialize() {

	}

	public static boolean lastCheckFireAspect = false;

	public static final Identifier FIRE_ASPECT = Identifier.withDefaultNamespace("fire_aspect");

	public static final Identifier TRIDENT_SPIN_TEXTURE = Identifier.withDefaultNamespace("textures/entity/trident_riptide.png");
	public static final Identifier TRIDENT_FIRE_SPIN_TEXTURE = Identifier.fromNamespaceAndPath(Tags.MODID, "textures/entity/trident_riptide_fire.png");

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
		ItemEnchantments enchants = item.getEnchantments();
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

	public static boolean replacementCheck(Player instance) {
		if (instance.isInWaterOrRain()) {
			return true;
		} else if (FireTridentMod.isInSnow(instance)) {
			ItemStack item = instance.getActiveItem();
			if (item.getItem() instanceof TridentItem) {
				boolean fireAspect = FireTridentMod.checkForEnchant(item, FireTridentMod.FIRE_ASPECT);
				FireTridentMod.lastCheckFireAspect = fireAspect;
				return fireAspect;
			} else {
				FireTridentMod.LOGGER.warn("Didn't get an instance of TridentItem back for the fire aspect check");
				FireTridentMod.lastCheckFireAspect = false;
			}
		}

		return false;
	}
}