package com.alrex.parcool.client.gui;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.ParCoolConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiCheckBox;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

@SideOnly(Side.CLIENT)
public class ParCoolGuideScreen extends GuiScreen {
	public static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation("parcool:textures/gui/book_background.png");

	private static final int PAGE_HOME = -1;
	private static final int PAGE_SETTINGS = -2;
	private static int currentPage = PAGE_HOME;
	private final List<String> pages = getPages();
	private final List<GuiButton> menuButtons = Arrays.asList(
			new GuiButton(0, 0, 0, 0, 0, "About This Mod"),
			new GuiButton(1, 0, 0, 0, 0, "Stamina"),
			new GuiButton(2, 0, 0, 0, 0, "CatLeap"),
			new GuiButton(3, 0, 0, 0, 0, "Crawl"),
			new GuiButton(4, 0, 0, 0, 0, "Dodge"),
			new GuiButton(5, 0, 0, 0, 0, "FastRunning"),
			new GuiButton(6, 0, 0, 0, 0, "GrabCliff"),
			new GuiButton(7, 0, 0, 0, 0, "Roll"),
			new GuiButton(8, 0, 0, 0, 0, "Vault"),
			new GuiButton(9, 0, 0, 0, 0, "WallJump"),
			new GuiButton(10, 0, 0, 0, 0, "Sliding"),
			new GuiButton(PAGE_SETTINGS, 0, 0, 0, 0, "Settings")
	);

	private final List<GuiCheckBox> settingButtons = Arrays.asList(
			new GuiCheckBox(0, 0, 0, "CatLeap", ParCoolConfig.client.canCatLeap),
			new GuiCheckBox(0, 0, 0, "Crawl", ParCoolConfig.client.canCrawl),
			new GuiCheckBox(0, 0, 0, "Dodge", ParCoolConfig.client.canDodge),
			new GuiCheckBox(0, 0, 0, "FastRunning", ParCoolConfig.client.canFastRunning),
			new GuiCheckBox(0, 0, 0, "FrontFlip", ParCoolConfig.client.canFrontFlip),
			new GuiCheckBox(0, 0, 0, "GrabCliff", ParCoolConfig.client.canGrabCliff),
			new GuiCheckBox(0, 0, 0, "Roll", ParCoolConfig.client.canRoll),
			new GuiCheckBox(0, 0, 0, "Vault", ParCoolConfig.client.canVault),
			new GuiCheckBox(0, 0, 0, "WallJump", ParCoolConfig.client.canWallJump)
	);

	private void syncSettings() {
		List<GuiCheckBox> b = settingButtons;
		assert b.size() == 9;
		ParCoolConfig.client.canCatLeap = (b.get(0).isChecked());
		ParCoolConfig.client.canCrawl = (b.get(1).isChecked());
		ParCoolConfig.client.canDodge = (b.get(2).isChecked());
		ParCoolConfig.client.canFastRunning = (b.get(3).isChecked());
		ParCoolConfig.client.canFrontFlip = (b.get(4).isChecked());
		ParCoolConfig.client.canGrabCliff = (b.get(5).isChecked());
		ParCoolConfig.client.canRoll = (b.get(6).isChecked());
		ParCoolConfig.client.canVault = (b.get(7).isChecked());
		ParCoolConfig.client.canWallJump = (b.get(8).isChecked());
	}

	public ParCoolGuideScreen() {
		super();
	}


	@Override
	protected void func_73728_b(int p_73728_1_, int p_73728_2_, int p_73728_3_, int p_73728_4_) {
		super.func_73728_b(p_73728_1_, p_73728_2_, p_73728_3_, p_73728_4_);
	}

	//render?
	@Override
	public void func_73863_a(int mouseX, int mouseY, float partialTick) {
		super.func_73863_a(mouseX, mouseY, partialTick);
		Minecraft mc = this.field_146297_k;
		mc.getTextureManager().bindTexture(BACKGROUND_LOCATION);
		ScaledResolution resolution = new ScaledResolution(mc);
		int width = 250;
		int height = (int) (width * 0.75);
		int offsetX = (resolution.func_78326_a() - width) / 2;
		int offsetY = (resolution.func_78328_b() - height) / 2;

		GuiUtils.drawTexturedModalRect(offsetX, offsetY, 256, 192, width, height, 0);
		renderContent(offsetX, offsetY, width / 2, height, mouseX, mouseY, partialTick);
		renderMenu(offsetX + width / 2, offsetY, width / 2, height, mouseX, mouseY, partialTick);
	}

	//keyPressed?
	@Override
	public void func_73869_a(char p_73869_1_, int keyCode) {
		try {
			super.func_73869_a(p_73869_1_, keyCode);
		} catch (IOException e) {
			return;
		}
		syncSettings();
		switch (keyCode) {
			case Keyboard.KEY_UP:
				changePage(currentPage - 1);
				break;
			case Keyboard.KEY_DOWN:
				changePage(currentPage + 1);
				break;
		}
	}

	//mouseClicked?
	@Override
	public void func_73864_a(int mouseX, int mouseY, int type) {//type:1->right 0->left
		if (type == 0) {
			if (currentPage == PAGE_SETTINGS) {
				settingButtons.stream().filter(button -> {
					int x = button.field_146128_h;
					int y = button.field_146129_i;
					int height = button.field_146121_g;
					int width = button.func_146117_b();
					return (x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height);
				}).findFirst().ifPresent(button -> button.func_146116_c(this.field_146297_k, mouseX, mouseY));
			}
			menuButtons.stream().filter(button -> {
				int x = button.field_146128_h;
				int y = button.field_146129_i;
				int height = button.field_146121_g;
				int width = button.func_146117_b();
				return (x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height);
			}).findFirst().ifPresent(this::onPress);
		}
	}

	private void renderContent(int left, int top, int width, int height, int mouseX, int mouseY, float n) {
		switch (currentPage) {
			case PAGE_HOME:
				renderHome(left, top, width, height, mouseX, mouseY, n);
				break;
			case PAGE_SETTINGS:
				renderSettings(left, top, width, height, mouseX, mouseY, n);
				break;
			default:
				renderContentText(left, top, width, height, mouseX, mouseY, n);
				break;
		}
	}

	private void renderHome(int left, int top, int width, int height, int mouseX, int mouseY, float n) {
		Minecraft mc = this.field_146297_k;
		FontRenderer fontRenderer = this.field_146289_q;
		final int offsetY = 20;
		final int center = left + width / 2;
		String textTitle = "ParCool!";
		String textSubtitle = "Guide Book";
		drawCenteredText(textTitle, center, top + offsetY + 10, getColorCodeFromARGB(0xFF, 0x55, 0x55, 0xFF));
		drawCenteredText(textSubtitle, center, top + offsetY + 15 + fontRenderer.FONT_HEIGHT, getColorCodeFromARGB(0xFF, 0x44, 0x44, 0xBB));
	}

	private void renderContentText(int left, int top, int width, int height, int mouseX, int mouseY, float n) {
		Minecraft mc = this.field_146297_k;
		FontRenderer fontRenderer = this.field_146289_q;
		final int offsetY = 20;
		final int offsetX = 10;
		if (currentPage < 0 || pages.size() <= currentPage) return;
		String text = pages.get(currentPage);
		List<String> wrappedLine = fontRenderer.listFormattedStringToWidth(text, width - offsetX * 2);
		for (int i = 0; i < wrappedLine.size(); i++) {
			fontRenderer.func_78276_b(wrappedLine.get(i), left + offsetX, top + offsetY + i * (fontRenderer.FONT_HEIGHT) + 3, getColorCodeFromARGB(0xFF, 0, 0, 0));
		}
	}

	private void renderMenu(int left, int top, int width, int height, int mouseX, int mouseY, float n) {
		Minecraft mc = this.field_146297_k;
		FontRenderer fontRenderer = this.field_146289_q;
		int offsetY = 20;
		int offsetX = 20;
		int buttonWidth = width - offsetX * 2;
		int y = (int) (top + offsetY * 1.5);
		drawCenteredText("Index", left + width / 2, top + offsetY, getColorCodeFromARGB(0xFF, 0x66, 0x66, 0xFF));
		for (GuiButton button : menuButtons) {
			button.func_175211_a(buttonWidth);//width
			button.field_146121_g = (fontRenderer.FONT_HEIGHT + 2);
			button.field_146128_h = left + offsetX;//x
			button.field_146129_i = y;//y
			button.func_191745_a(mc, mouseX, mouseY, n);
			y += button.field_146121_g;
		}
	}

	private void renderSettings(int left, int top, int width, int height, int mouseX, int mouseY, float n) {
		Minecraft mc = this.field_146297_k;
		FontRenderer fontRenderer = this.field_146289_q;
		int offsetY = 20;
		int offsetX = 20;
		int buttonWidth = width - offsetX * 2;
		int y = (int) (top + offsetY * 1.5);
		drawCenteredText("Enabled Actions", left + width / 2, top + offsetY, getColorCodeFromARGB(0xFF, 0x66, 0x66, 0xFF));
		for (GuiCheckBox button : settingButtons) {
			button.func_175211_a(buttonWidth);
			button.field_146121_g = (fontRenderer.FONT_HEIGHT + 6);
			button.field_146128_h = left + offsetX;
			button.field_146129_i = y;
			button.func_191745_a(mc, mouseX, mouseY, n);
			y += button.field_146121_g;
		}
	}

	private void drawCenteredText(String text, int x, int y, int color) {
		FontRenderer fontRenderer = this.field_146289_q;
		int width = fontRenderer.getStringWidth(text);
		fontRenderer.func_78276_b(text, x - (width >> 1), y - (fontRenderer.FONT_HEIGHT >> 1), color);
	}

	private static int getColorCodeFromARGB(int a, int r, int g, int b) {
		return a * 0x1000000 + r * 0x10000 + g * 0x100 + b;
	}

	private static List<String> getPages() {
		final String path = "/assets/parcool/book/parcool_guide_content.txt";
		BufferedReader reader = new BufferedReader(new InputStreamReader(ParCool.class.getResourceAsStream(path), StandardCharsets.UTF_8));
		ArrayList<String> texts = new ArrayList<>();
		//=======
		// replace division line -> \\n and set to list
		Iterator<String> iterator = reader.lines().iterator();
		Pattern division = Pattern.compile("===+");
		AtomicReference<StringBuilder> builder = new AtomicReference<>(new StringBuilder());
		iterator.forEachRemaining((line -> {
			if (!division.matcher(line).matches()) {
				builder.get().append(line).append("\n");
			} else {//division line
				texts.add(builder.toString());
				if (iterator.hasNext()) builder.set(new StringBuilder());
			}
		}));
		//=======
		return texts;
	}

	private void changePage(int i) {
		if (i != PAGE_HOME && i != PAGE_SETTINGS && (i < 0 || pages.size() <= i)) return;
		currentPage = i;
	}

	private void onPress(GuiButton button) {
		changePage(button.field_146127_k);
	}

	private void openSetting(GuiButton button) {
		changePage(PAGE_SETTINGS);
	}
}
