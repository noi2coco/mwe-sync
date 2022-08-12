package net.fabricmc.example.gui;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BoxScreen extends HandledScreen<ScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("minecraft", "textures/gui/container/dispenser.png");
    BoxScreenHandler screenHandler;

    public BoxScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        //we save a reference to the screenhandler so we can render the number from our propertyDelegate on screen
        screenHandler = (BoxScreenHandler) handler;

    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {/**/}

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        //We just render our synced number somewhere in the container, this is a demonstration after all
        //the last argument is a color code, making the font bright green
        textRenderer.draw(matrices, Integer.toString(screenHandler.getSyncedNumber()), 0, 0, 65280);
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}