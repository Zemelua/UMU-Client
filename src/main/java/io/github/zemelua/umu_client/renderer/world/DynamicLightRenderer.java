package io.github.zemelua.umu_client.renderer.world;

import io.github.zemelua.umu_client.option.ModOptions;
import io.github.zemelua.umu_client.option.enums.DynamicLightMode;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraftforge.event.TickEvent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;

public class DynamicLightRenderer {
	private static final List<ToIntFunction<Entity>> ENTITY_BRIGHTNESSES;
	private static final List<ToIntBiFunction<Entity, ItemStack>> ITEM_BRIGHTNESSES;

	private final Minecraft minecraft;
	private final Map<BlockPos, Integer> sources = new ConcurrentHashMap<>();

	public DynamicLightRenderer(Minecraft minecraft) {
		this.minecraft = minecraft;
	}

	public void onClientTick(final TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			this.updateLights();
		}
	}

	public void updateLights() {
		Level world = this.minecraft.level;
		Player player = this.minecraft.player;
		if (world == null || player == null) return;

		this.sources.replaceAll((pos, shouldOff) -> 0);

		world.getEntities((Entity) null, player.getBoundingBox().inflate(128), this::canBright).forEach(entity -> {
			int brightness = DynamicLightRenderer.getBrightness(entity);
			if (brightness > 0) {
				this.sources.put(entity.blockPosition(), brightness);
			}
		});

		this.sources.forEach((pos, brightness) -> world.getChunkSource().getLightEngine().checkBlock(pos));
		this.sources.entrySet().removeIf(entry -> entry.getValue() <= 0);
	}

	private boolean canBright(Entity entity) {
		return ModOptions.DYNAMIC_LIGHT_MODE.getValue() == DynamicLightMode.ALL || entity == this.minecraft.player;
	}

	public int getBrightness(BlockPos pos) {
		Integer brightness = this.sources.get(pos);
		if (brightness == null) brightness = 0;

		return brightness;
	}

	private static int getBrightness(Entity entity) {
		return Mth.clamp(0, 15, DynamicLightRenderer.ENTITY_BRIGHTNESSES.stream()
				.mapToInt(function -> function.applyAsInt(entity))
				.sum());
	}

	private static int getBrightness(Entity owner, ItemStack itemStack) {
		return Mth.clamp(0, 15, DynamicLightRenderer.ITEM_BRIGHTNESSES.stream()
				.mapToInt(biFunction -> biFunction.applyAsInt(owner, itemStack))
				.sum());
	}

	static {
		ENTITY_BRIGHTNESSES = List.of(
				(entity -> entity.getType() == EntityType.GLOW_SQUID ? 15 : 0),
				(entity -> entity.isOnFire() ? 15 : 0),
				(entity -> entity.isCurrentlyGlowing() ? 3 : 0),
				(entity -> {
					int brightness = 0;

					if (entity instanceof LivingEntity entityLiving) {
						for (InteractionHand hand : InteractionHand.values()) {
							brightness += DynamicLightRenderer.getBrightness(entityLiving, entityLiving.getItemInHand(hand));
						}
					} else if (entity instanceof ItemEntity entityItem) {
						brightness += DynamicLightRenderer.getBrightness(entityItem, entityItem.getItem());
					}

					return brightness;
				})
		);
		ITEM_BRIGHTNESSES = List.of(
				((entity, itemStack) -> itemStack.is(Items.TORCH) && !entity.isInWater() ? 14 : 0),
				((entity, itemStack) -> itemStack.is(Items.LANTERN) ? 15 : 0),
				((entity, itemStack) -> itemStack.is(Items.SOUL_LANTERN) ? 10 : 0),
				((entity, itemStack) -> itemStack.is(Items.CAMPFIRE) && !entity.isInWater() ? 15 : 0),
				((entity, itemStack) -> itemStack.is(Items.SOUL_CAMPFIRE) && !entity.isInWater() ? 10 : 0),
				((entity, itemStack) -> itemStack.is(Items.CANDLE) && !entity.isInWater() ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.WHITE_CANDLE) && !entity.isInWater() ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.ORANGE_CANDLE) && !entity.isInWater() ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.MAGENTA_CANDLE) && !entity.isInWater() ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.LIGHT_BLUE_CANDLE) && !entity.isInWater() ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.YELLOW_CANDLE) && !entity.isInWater() ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.LIME_CANDLE) && !entity.isInWater() ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.PINK_CANDLE) && !entity.isInWater() ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.GRAY_CANDLE) && !entity.isInWater() ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.LIGHT_GRAY_CANDLE) && !entity.isInWater() ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.CYAN_CANDLE) && !entity.isInWater() ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.PURPLE_CANDLE) && !entity.isInWater() ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.BLUE_CANDLE) && !entity.isInWater() ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.BROWN_CANDLE) && !entity.isInWater() ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.GREEN_CANDLE) && !entity.isInWater() ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.RED_CANDLE) && !entity.isInWater() ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.BLACK_CANDLE) && !entity.isInWater() ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.LAVA_BUCKET) ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.SEA_PICKLE) && entity.isInWater() ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.END_ROD) ? 14 : 0),
				((entity, itemStack) -> itemStack.is(Items.JACK_O_LANTERN) ? 15 : 0),
				((entity, itemStack) -> itemStack.is(Items.GLOW_LICHEN) ? 7 : 0),
				((entity, itemStack) -> itemStack.is(Items.ENCHANTING_TABLE) ? 7 : 0),
				((entity, itemStack) -> itemStack.is(Items.DRAGON_EGG) ? 1 : 0),
				((entity, itemStack) -> itemStack.is(Items.ENDER_CHEST) ? 7 : 0),
				((entity, itemStack) -> itemStack.is(Items.BEACON) ? Mth.clamp(0, 15, BeaconBlockEntity.updateBase(
						entity.level, entity.getBlockX(), entity.getBlockY(), entity.getBlockZ()) * 15 / 4) : 0),
				((entity, itemStack) -> itemStack.is(Items.SEA_LANTERN) ? 15 : 0),
				((entity, itemStack) -> itemStack.is(Items.MAGMA_BLOCK) ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.CONDUIT) && entity.isInWater() ? 15 : 0),
				((entity, itemStack) -> itemStack.is(Items.REDSTONE_TORCH) && entity.isInWater() ? 7 : 0),
				((entity, itemStack) -> itemStack.is(Items.ENCHANTED_GOLDEN_APPLE) ? 1 : 0),
				((entity, itemStack) -> itemStack.is(Items.GLOWSTONE_DUST) ? 10 : 0),
				((entity, itemStack) -> itemStack.is(Items.BLAZE_ROD) ? 10 : 0),
				((entity, itemStack) -> itemStack.is(Items.BLAZE_POWDER) ? 6 : 0),
				((entity, itemStack) -> itemStack.is(Items.MAGMA_BLOCK) ? 6 : 0),
				((entity, itemStack) -> itemStack.is(Items.GLOW_SQUID_SPAWN_EGG) ? 5 : 0),
				((entity, itemStack) -> itemStack.is(Items.NETHER_STAR) ? 5 : 0),
				((entity, itemStack) -> itemStack.is(Items.SPECTRAL_ARROW) ? 3 : 0),
				((entity, itemStack) -> itemStack.is(Items.GLOW_BERRIES) ? 14 : 0),
				((entity, itemStack) -> itemStack.is(Items.SHROOMLIGHT) ? 15 : 0),
				((entity, itemStack) -> itemStack.is(Items.RESPAWN_ANCHOR) ? 3 : 0)
		);
	}
}
