package io.github.zemelua.umu_client.gui.screen.option;

import com.google.common.collect.ImmutableList;
import io.github.zemelua.umu_client.option.IOption;
import io.github.zemelua.umu_client.option.ModOptions;
import io.github.zemelua.umu_client.option.vanilla.VanillaOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.ArrayList;
import java.util.List;

public class ModOptionPages {
	public static final ImmutableList<Page> VIDEO = ImmutableList.<Page>builder()
			.add(new Page.Builder()
					.add(new Group.Builder()
							.add(VanillaOptions.GRAPHICS).build()
					).add(new Group.Builder()
							.add(VanillaOptions.FRAMERATE_LIMIT)
							.add(VanillaOptions.VSYNC_ENABLE).build()
					).add(new Group.Builder()
							.add(VanillaOptions.FULLSCREEN_ENABLE).build()
					).add(new Group.Builder()
							.add(ModOptions.THEME_COLOR).build()
					).build("general")
			).add(new Page.Builder()
					.add(new Group.Builder()
							.add(VanillaOptions.RENDER_DISTANCE).build()
					).add(new Group.Builder()
							.add(VanillaOptions.GAMMA)
							.add(VanillaOptions.AMBIENT_OCCLUSION).build()
					).add(new Group.Builder()
							.add(VanillaOptions.MIPMAP_LEVELS).build()
					).add(new Group.Builder()
							.add(VanillaOptions.ENTITY_SHADOWS)
							.add(VanillaOptions.ENTITY_DISTANCE_SCALING).build()
					).add(new Group.Builder()
							.add(VanillaOptions.PARTICLES).build()
					).add(new Group.Builder()
							.add(VanillaOptions.FOV_EFFECTS_SCALE)
							.add(VanillaOptions.SCREEN_EFFECTS_SCALE).build()
					).build("world")
			).add(new Page.Builder()
					.add(new Group.Builder()
							.add(VanillaOptions.GUI_SCALE)
							.add(VanillaOptions.ATTACK_INDICATOR)
							.add(VanillaOptions.VIEW_BOBBING).build()
					).build("gui")
			).add(new Page.Builder()
					.add(new Group.Builder()
							.add(ModOptions.DYNAMIC_LIGHT)
							.add(ModOptions.DYNAMIC_LIGHT_BRIGHTNESS).build()
					).build("dynamic_light")
			).build();

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
}
