package io.github.zemelua.umu_client.renderer.world;

import io.github.zemelua.umu_client.option.vanilla.VanillaOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.EntityViewRenderEvent;

@SuppressWarnings("ClassCanBeRecord")
public class ViewTiltRenderer {
	private final Minecraft minecraft;

	public ViewTiltRenderer(Minecraft minecraft) {
		this.minecraft = minecraft;
	}

	public void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
		Player player = this.minecraft.player;

		if (player != null && player.isFallFlying() && VanillaOptions.VIEW_BOBBING.getValue()) {
			event.setRoll((float) (this.calculateRoll(player)));
		}
	}

	private double calculateRoll(Player player) {
		Vec3 lookVec = new Vec3(player.getLookAngle().x(), 0, player.getLookAngle().z());
		Vec3 moveVec = new Vec3(player.getDeltaMovement().x(), 0, player.getDeltaMovement().z()).normalize();

		return lookVec.cross(moveVec).y() * 10;
	}
}
