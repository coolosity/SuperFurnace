package com.github.coolosity.superfurnace.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.github.coolosity.superfurnace.Reference;
import com.github.coolosity.superfurnace.tileentity.TileEntitySuperFurnace;

public class GuiSuperFurnace extends GuiContainer
{

	public static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/gui/superFurnace.png");
	
	private TileEntitySuperFurnace superFurnace;
	
	public GuiSuperFurnace(InventoryPlayer inventoryPlayer, TileEntitySuperFurnace entity) {
		super(new ContainerSuperFurnace(inventoryPlayer, entity));
		this.superFurnace = entity;
		
		//this.xSize = 176;
		//this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String string = this.superFurnace.hasCustomInventoryName() ? this.superFurnace.getInventoryName() : I18n.format(this.superFurnace.getInventoryName(), new Object[0]);
		this.fontRendererObj.drawString(string, this.xSize / 2 - this.fontRendererObj.getStringWidth(string), 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 94, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1;

        if (this.superFurnace.isBurning())
        {
            i1 = this.superFurnace.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }

        i1 = this.superFurnace.getCookProgressScaled(24);
        this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	}
}
