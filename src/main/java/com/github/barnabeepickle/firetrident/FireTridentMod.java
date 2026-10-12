package com.github.barnabeepickle.firetrident;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FireTridentMod implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger(Tags.MODID);

	@Override
	public void onInitialize() {

	}

	public static boolean lastCheckFireAspect = false;

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

	/*
	public static boolean isTridentUsable(Entity entity) {
		if (!entity.level().isClientSide()) {
			if (entity.getY() > FireTridentMod.getRiptideTriedentFlightCeiling((ServerLevel) entity.level())) {
				return false;
			}
		}
		return FireTridentMod.isInSnow(entity);
	}
	 */

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

	/*
	public static final GameRule<Integer> RIPTIDE_TRIDENT_FLIGHT_CEILING = GameRuleBuilder
			.forInteger(600)
			.category(GameRuleCategory.MISC)
			.buildAndRegister(Identifier.fromNamespaceAndPath(Tags.MODID, "riptide_trident_flight_ceiling"));

	public static int getRiptideTriedentFlightCeiling(ServerLevel serverLevel) {
		return serverLevel.getGameRules().get(FireTridentMod.RIPTIDE_TRIDENT_FLIGHT_CEILING);
	}
	 */
}