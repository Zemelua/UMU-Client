package io.github.zemelua.umu_client.renderer.world;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import io.github.zemelua.umu_client.UMUClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class FallingStarRenderer {
	private static final ResourceLocation FALLING_STAR_LOCATION = UMUClient.resource("textures/environment/falling_star.png");

	private final Minecraft minecraft;
	private final List<FallingStar> fallingStars;

	public FallingStarRenderer(Minecraft minecraft) {
		this.minecraft = minecraft;
		this.fallingStars = new ArrayList<>();
	}

	public void onClientTick(final TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			this.tryCreateStar();
		}
	}

	private void tryCreateStar() {
		ClientLevel world = this.minecraft.level;
		if (world == null) return;

		if (world.getStarBrightness(0.0F) <= 0.0F && !this.fallingStars.isEmpty()) {
			this.fallingStars.clear();
			return;
		}

		long time = world.getGameTime();
		Random random = new Random(time);

		float x = random.nextFloat() * 2.0F - 1.0F;
		float y = random.nextFloat() * 2.0F - 1.0F;
		float z = random.nextFloat() * 2.0F - 1.0F;
		double d0 = x * x + y * y + z * z;

		for (int i = 0; i < 30; i++) {
			if (d0 < 1.0D && d0 > 0.01D && random.nextInt(20) > 0) {
				d0 = 1.0D / Math.sqrt(d0);
				x *= d0;
				y *= d0;
				z *= d0;

				this.fallingStars.add(new FallingStar(x, y, z));
			}
		}
	}

	public void renderFallingStar(Matrix4f matrix, BufferBuilder builder, float partialTicks) {
		RenderSystem.setShaderTexture(0, FALLING_STAR_LOCATION);
		builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		if (!this.fallingStars.isEmpty()) {
			for (Iterator<FallingStar> iterator = this.fallingStars.listIterator(); iterator.hasNext();) {
				FallingStar star = iterator.next();
				boolean result = star.draw(matrix, builder, partialTicks);
				if (result) iterator.remove();
			}
		}

		builder.end();
		BufferUploader.end(builder);
		RenderSystem.disableTexture();
	}

	private static class FallingStar {
		private final float drawX;
		private final float drawY;
		private final float drawZ;
		private final float size;

		private float ticks;

		private FallingStar(float drawX, float drawY, float drawZ) {
			this.drawX = drawX;
			this.drawY = drawY;
			this.drawZ = drawZ;
			this.size = 2.4F;
		}

		private boolean draw(Matrix4f matrix, BufferBuilder builder, float partialTicks) {
			this.ticks += partialTicks;

			int phase = (int) (this.ticks * 0.5F) % 12;

			UMUClient.LOGGER.info(this.ticks);

			int phaseU = phase % 4;
			int phaseV = phase / 4 % 3;
			float startU = (float) (phaseU) / 4.0F;
			float startV = (float) (phaseV) / 3.0F;
			float endU = (float) (phaseU + 1) / 4.0F;
			float endV = (float) (phaseV + 1) / 3.0F;

			float practicalX = this.drawX * 100.0F;
			float practicalY = this.drawY * 100.0F;
			float practicalZ = this.drawZ * 100.0F;
			double atan0 = Math.atan2(this.drawX, this.drawZ);
			double sin0 = Math.sin(atan0);
			double cos0 = Math.cos(atan0);
			double atan1 = Math.atan2(Math.sqrt(this.drawX * this.drawX + this.drawZ * this.drawZ), this.drawY);
			double sin1 = Math.sin(atan1);
			double cos1 = Math.cos(atan1);
			double radian = 2D / 8D * Math.PI * 2.0D;
			double sin2 = Math.sin(radian);
			double cos2 = Math.cos(radian);

			for (int i = 0; i < 4; ++i) {
				double d0 = (double) ((i & 2) - 1) * this.size;
				double d1 = (double) ((i + 1 & 2) - 1) * this.size;
				double d2 = d0 * cos2 - d1 * sin2;
				double d3 = d1 * cos2 + d0 * sin2;
				double d4 = 0.0D * sin1 - d2 * cos1;
				float f0 = (float) (d4 * sin0 - d3 * cos0);
				float f1 = (float) (d2 * sin1 + 0.0D * cos1);
				float f2 = (float) (d3 * sin0 + d4 * cos0);

				Vec2 uv = switch (i) {
					case 0 -> new Vec2(endU, endV);
					case 1 -> new Vec2(startU, endV);
					case 2 -> new Vec2(startU, startV);
					case 3 -> new Vec2(endU, startV);

					default -> throw new IllegalStateException("Unexpected value: " + i);
				};

				builder.vertex(matrix, practicalX + f0, practicalY + f1, practicalZ + f2).uv(uv.x, uv.y).endVertex();
			}

			return Math.floor((int) (this.ticks * 0.5F) / 12.0D) > 0;
		}
	}
}
