package gg.moonflower.pollen.core.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import gg.moonflower.pollen.core.Pollen;
import gg.moonflower.pollen.core.client.entitlement.EntitlementManager;
import gg.moonflower.pollen.core.client.profile.ProfileConnection;
import gg.moonflower.pollen.core.client.profile.ProfileData;
import gg.moonflower.pollen.core.client.profile.ProfileManager;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.HttpUtil;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class MoonflowerServerDownScreen extends Screen {

    private final Screen previous;
    private static final Component TITLE = new TranslatableComponent("screen." + Pollen.MOD_ID + ".moonflowerServerDown.header").withStyle(ChatFormatting.BOLD);
    private static final Component CONTENT = new TranslatableComponent("screen." + Pollen.MOD_ID + ".moonflowerServerDown.message");
    private static final Component NARRATION = TITLE.copy().append("\n").append(CONTENT);
    private MultiLineLabel message = MultiLineLabel.EMPTY;

    public MoonflowerServerDownScreen(Screen screen) {
        super(NarratorChatListener.NO_TITLE);
        this.previous = screen;
    }

    @Override
    protected void init() {
        super.init();
        this.message = MultiLineLabel.create(this.font, CONTENT, this.width - 50);
        int i = (this.message.getLineCount() + 1) * 9 * 2;
        this.addButton(new Button(this.width / 2 - 100, 100 + i, 200, 20, CommonComponents.GUI_DONE, arg -> this.minecraft.setScreen(this.previous)));
    }

    @Override
    public String getNarrationMessage() {
        return NARRATION.getString();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderDirtBackground(0);
        drawCenteredString(poseStack, this.font, TITLE, this.width / 2, 30, 16777215);
        this.message.renderCentered(poseStack, this.width / 2, 70, 9 * 2, 16777215);
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }
}
