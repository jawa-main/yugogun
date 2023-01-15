package io.github.jawa2401.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.jawa2401.YConfig;
import io.github.jawa2401.blocks_items.custom.MortarBlock;
import io.github.jawa2401.util.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.text.ITextComponent;

public class MortarScreen extends Screen {
    private final MortarBlock block;

    public int _w;
    public int _h;

    public MortarScreen(MortarBlock block) {
        super(ITextComponent.getTextComponentOrEmpty(""));
        this.block = block;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void wh() {
        _w = width;
        _h = height;
    }

    public void renderBG(MatrixStack matrixStack, java.awt.Color color) {
        wh();

        int x = (_w / 4);
        int y = (_h / 4);
        int w = _w - (x * 2);
        int h = _h - (y * 2);

        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int a = color.getAlpha();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        minecraft.getTextureManager().bindTexture(BACKGROUND_LOCATION);

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(x, y + h, 0).tex(0.0F, h / f).color(r, g, b, a).endVertex();
        bufferbuilder.pos(x + w, y + h, 0).tex(w / f, h / f).color(r, g, b, a).endVertex();
        bufferbuilder.pos(x + w, y, 0).tex(w / f, 0).color(r, g, b, a).endVertex();
        bufferbuilder.pos(x, y, 0).tex(0.0F, 0).color(r, g, b, a).endVertex();
        tessellator.draw();
        super.renderBackground(matrixStack);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBG(matrixStack, java.awt.Color.lightGray);

        int x = (_w / 2) - 10;
        int y = (_h / 2) + 10;

        addButton(new Button(x, y, 20, 20, ITextComponent.getTextComponentOrEmpty("+"), p_onPress_1_ -> {
            block.range += 0.1;
        }));
        addButton(new Button(x, y - 40, 20, 20, ITextComponent.getTextComponentOrEmpty("-"), p_onPress_1_ -> {
            block.range -= 0.1;
        }));

        if (block.range < 0) block.range = 0;
        if (block.range > YConfig.maxMortarRange) block.range = YConfig.maxMortarRange;



        String text = String.format("domet:%.1f", block.range);
        int textWidth = font.getStringWidth(text);

        font.drawTextWithShadow(matrixStack, ITextComponent.getTextComponentOrEmpty(text), (_w / 2F) - (textWidth / 2F), y - 15, Color.WHITE);

        String hint = "desni klik sa raketom da ju lansiras";
        int hintW = font.getStringWidth(hint);

        font.drawTextWithShadow(matrixStack, ITextComponent.getTextComponentOrEmpty(hint), (_w / 2F) - (hintW / 2F), (_h + y) / 2.0F - 20, Color.YELLOW);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
