package com.github.coolosity.superfurnace.helpers;

import com.github.coolosity.superfurnace.Reference;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.registry.GameRegistry;

public class RegisterHelper {

	public static void registerBlock(Block block)
	{
		GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
	}
	
	public static void registerItem(Item item)
	{
		GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
	}
	
	public static void registerTileEntity(Class<? extends TileEntity> tileEntity)
	{
		GameRegistry.registerTileEntity(tileEntity, Reference.MODID+"_"+tileEntity.getName());
	}
}
