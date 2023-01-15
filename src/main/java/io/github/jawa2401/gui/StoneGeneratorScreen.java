package io.github.jawa2401.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.jawa2401.Main;
import io.github.jawa2401.TE.StoneGeneratorTE;
import io.github.jawa2401.container.StoneGeneratorContainer;
import io.github.jawa2401.util.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class StoneGeneratorScreen extends ContainerScreen<StoneGeneratorContainer> {

    public StoneGeneratorTE stoneGeneratorBlock;

    private final ResourceLocation GUI = new ResourceLocation(Main.MOD_ID, "textures/gui/stone_generator_gui.png");

    public StoneGeneratorScreen(StoneGeneratorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        stoneGeneratorBlock = screenContainer.stoneGeneratorBlock;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderHoveredTooltip(matrixStack, mouseX, mouseY);

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {

        RenderSystem.color4f(1, 1, 1, 1);

        minecraft.getTextureManager().bindTexture(GUI);

        int i = guiLeft;
        int j = guiTop;

        blit(matrixStack, i, j, 0, 0, xSize, ySize);
    }
}
