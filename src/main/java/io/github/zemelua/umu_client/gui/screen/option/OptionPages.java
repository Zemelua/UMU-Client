package io.github.zemelua.umu_client.gui.screen.option;

import com.google.common.collect.ImmutableList;
import io.github.zemelua.umu_client.option.IOption;
import io.github.zemelua.umu_client.option.ModOptions;
import io.github.zemelua.umu_client.option.vanilla.VanillaOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.ArrayList;
import java.util.List;

public final class OptionPages {
	public static final ImmutableList<Page> VIDEO;
	public static final ImmutableList<Page> SOUND;

	public static class Group {
		private final ImmutableList<IOption<?>> options;

		private Group(List<IOption<?>> options) {
			this.options = ImmutableList.copyOf(options);
		}

		public ImmutableList<IOption<?>> getOptions() {
			return this.options;
		}

		private static class Builder {
			private final List<IOption<?>> options = new ArrayList<>();

			private Builder() {
			}

			private Builder add(IOption<?> option) {
				this.options.add(option);
				return this;
			}

			private Group build() {
				return new Group(this.options);
			}
		}
	}

	public static class Page {
		private final Component title;
		private final ImmutableList<Group> groups;

		private Page(Component title, List<Group> groups) {
			this.title = title;
			this.groups = ImmutableList.copyOf(groups);
		}

		public Component getTitle() {
			return this.title;
		}

		public ImmutableList<Group> getGroups() {
			return this.groups;
		}

		private static class Builder {
			private final List<Group> groups = new ArrayList<>();

			private Builder() {
			}

			private Builder add(Group group) {
				this.groups.add(group);
				return this;
			}

			private Page build(String title) {
				return new Page(new TranslatableComponent("umu_client.screen.options.page." + title), this.groups);
			}
		}
	}

	static {
		VIDEO = ImmutableList.<Page>builder()
				.add(new Page.Builder()
						.add(new Group.Builder()
								.add(VanillaOptions.GRAPHICS).build()
						).add(new Group.Builder()
								.add(VanillaOptions.FRAMERATE_LIMIT)
								.add(VanillaOptions.ENABLE_VSYNC).build()
						).add(new Group.Builder()
								.add(ModOptions.THEME_COLOR).build()
						).add(new Group.Builder()
								.add(VanillaOptions.USE_FULLSCREEN).build()
						).build("video.general")
				).add(new Page.Builder()
						.add(new Group.Builder()
								.add(VanillaOptions.PRIORITIZE_CHUNK_UPDATES).build()
						).add(new Group.Builder()
								.add(VanillaOptions.RENDER_DISTANCE)
								.add(VanillaOptions.RENDER_CLOUDS)
								.add(VanillaOptions.PARTICLES)
								.add(VanillaOptions.ENTITY_SHADOWS)
								.add(VanillaOptions.ENTITY_DISTANCE_SCALING).build()
						).add(new Group.Builder()
								.add(VanillaOptions.AMBIENT_OCCLUSION)
								.add(VanillaOptions.GAMMA)
								.add(VanillaOptions.MIPMAP_LEVELS).build()
						).build("video.quality")
				).add(new Page.Builder()
						.add(new Group.Builder()
								.add(VanillaOptions.GUI_SCALE).build()
						).add(new Group.Builder()
								.add(VanillaOptions.ATTACK_INDICATOR)
								.add(VanillaOptions.SCREEN_EFFECTS_SCALE)
								.add(VanillaOptions.FOV_EFFECTS_SCALE).build()
						).add(new Group.Builder()
								.add(VanillaOptions.AUTOSAVE_INDICATOR).build()
						).build("video.gui")
				).add(new Page.Builder()
						.add(new Group.Builder()
								.add(VanillaOptions.VIEW_BOBBING).build()
						).add(new Group.Builder()
								.add(ModOptions.ENABLE_DYNAMIC_LIGHT).build()
						).build("video.custom")
				).build();
		SOUND = ImmutableList.<Page>builder()
				.add(new Page.Builder()
						.add(new Group.Builder()
								.build()
						).build("general")
				).build();
	}
}
