package hr.jawa2401.yugogun.init.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import hr.jawa2401.yugogun.Main;
import hr.jawa2401.yugogun.init.container.IronExtractorContainer;
import hr.jawa2401.yugogun.init.container.StoneGeneratorContainer;
import hr.jawa2401.yugogun.init.tile_entities.IronExtractorTE;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.awt.*;

public class IronExtractorScreen extends ContainerScreen<IronExtractorContainer> {

    public IronExtractorTE ironExtractorTE;

    private final ResourceLocation GUI = new ResourceLocation(Main.MOD_ID, "textures/gui/iron_extractor_gui.png");

    public IronExtractorScreen(IronExtractorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        ironExtractorTE = screenContainer.tileEntity;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderHoveredTooltip(matrixStack, mouseX, mouseY);
//        font.drawString(matrixStack, String.format("%.1f%%", ironExtractorTE.ironGenerationProgress * 100), guiLeft + 80, guiTop + 31, Color.DARK_GRAY.getRGB());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {

        RenderSystem.color4f(1, 1, 1, 1);

        minecraft.getTextureManager().bindTexture(GUI);

        int i = guiLeft;
        int j = guiTop;

        blit(matrixStack, i, j, 0, 0, xSize, ySize);
    }

    @Override
    public void closeScreen() {
        System.out.println("closing screen " + getClass().getName());
        super.closeScreen();
    }

    @Override
    public void onClose() {
        System.out.println("onclose " + getClass().getName());
        super.onClose();
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
