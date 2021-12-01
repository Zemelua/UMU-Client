package io.github.zemelua.umu_client.renderer.world;

import com.google.common.collect.ImmutableMap;
import io.github.zemelua.umu_client.option.ModOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicLightRenderer {
	private static final ImmutableMap<Item, Integer> ITEM_BRIGHTNESSES = ImmutableMap.<Item, Integer>builder()
			.put(Items.TORCH, 14)
			.put(Items.LANTERN, 15)
			.put(Items.SOUL_LANTERN, 10)
			.put(Items.CAMPFIRE, 15)
			.put(Items.SOUL_CAMPFIRE, 10)
			.put(Items.CANDLE, 3)
			.put(Items.WHITE_CANDLE, 3)
			.put(Items.ORANGE_CANDLE, 3)
			.put(Items.MAGENTA_CANDLE, 3)
			.put(Items.LIGHT_BLUE_CANDLE, 3)
			.put(Items.YELLOW_CANDLE, 3)
			.put(Items.LIME_CANDLE, 3)
			.put(Items.PINK_CANDLE, 3)
			.put(Items.GRAY_CANDLE, 3)
			.put(Items.LIGHT_GRAY_CANDLE, 3)
			.put(Items.CYAN_CANDLE, 3)
			.put(Items.PURPLE_CANDLE, 3)
			.put(Items.BLUE_CANDLE, 3)
			.put(Items.BROWN_CANDLE, 3)
			.put(Items.GREEN_CANDLE, 3)
			.put(Items.RED_CANDLE, 3)
			.put(Items.BLACK_CANDLE, 3)
			.put(Items.LAVA_BUCKET, 15)
			.build();

	private final Minecraft minecraft;
	private final Map<BlockPos, Integer> sources = new ConcurrentHashMap<>();

	public DynamicLightRenderer(Minecraft minecraft) {
		this.minecraft = minecraft;
	}

	public void tick() {
		Level world = this.minecraft.level;
		Player player = this.minecraft.player;
		if (world == null || player == null) return;

		this.sources.replaceAll((pos, shouldOff) -> 0);

		world.getEntities(null, player.getBoundingBox().inflate(128)).forEach(entity -> {
			int brightness = this.getBrightness(entity);
			if (brightness > 0) {
				this.sources.put(entity.blockPosition(), brightness);
			}
		});

		this.sources.forEach((pos, brightness) -> world.getChunkSource().getLightEngine().checkBlock(pos));
		this.sources.entrySet().removeIf(entry -> entry.getValue() <= 0);
	}

	public int getBrightness(BlockPos pos) {
		Integer brightness = this.sources.get(pos);
		if (brightness == null) brightness = 0;

		return brightness;
	}

	private int getBrightness(Entity entity) {
		int brightness = 0;
		if (ModOptions.DYNAMIC_LIGHT_MODE.getValue().renderOnlySelf() && this.minecraft.player != entity) return brightness;

		if (entity instanceof LivingEntity entityLiving) {
			for (InteractionHand hand : InteractionHand.values()) {
				brightness += this.getBrightness(entityLiving.getItemInHand(hand));
			}

			if (ModOptions.DYNAMIC_LIGHT_MODE.getValue().renderEntities() && entityLiving.getType() == EntityType.GLOW_SQUID) {
				brightness += 5;
			}
		} else if (entity instanceof ItemEntity entityItem) {
			brightness += this.getBrightness(entityItem.getItem());
		}

		return brightness;
	}

	private int getBrightness(ItemStack itemStack) {
		Integer brightness = ITEM_BRIGHTNESSES.get(itemStack.getItem());
		if (brightness == null) brightness = 0;

		return brightness;
	}
}
