package io.github.zemelua.umu_client.util;

public class Rect2i extends net.minecraft.client.renderer.Rect2i {
	public Rect2i(int posX, int posY, int sizeX, int sizeY) {
		super(posX, posY, sizeX, sizeY);
	}

	public int getLimitX() {
		return this.getX() + this.getWidth();
	}

	public int getLimitY() {
		return this.getY() + this.getHeight();
	}

	public int getMiddleX() {
		return this.getX() + this.getWidth() / 2;
	}

	public int getMiddleY() {
		return this.getY() + this.getHeight() / 2;
	}
}
