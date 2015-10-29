package com.github.coolosity.superfurnace.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.github.coolosity.superfurnace.tileentity.TileEntitySuperFurnace;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	
	public static final int GUI_SUPERFURNACE = 0;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == GUI_SUPERFURNACE)
		{
			TileEntitySuperFurnace entity = (TileEntitySuperFurnace) world.getTileEntity(x, y, z);
			return new ContainerSuperFurnace(player.inventory, entity);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == GUI_SUPERFURNACE)
		{
			TileEntitySuperFurnace entity = (TileEntitySuperFurnace) world.getTileEntity(x, y, z);
			return new GuiSuperFurnace(player.inventory, entity);
		}
		return null;
	}

}
