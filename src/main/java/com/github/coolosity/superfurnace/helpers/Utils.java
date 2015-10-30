package com.github.coolosity.superfurnace.helpers;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class Utils
{
	
	public class Side
	{
		public static final int BOTTOM = 0;
		public static final int TOP = 1;
		public static final int NORTH = 2;
		public static final int SOUTH = 3;
		public static final int WEST = 4;
		public static final int EAST = 5;
	}

	public static boolean isItemFuel(ItemStack itemstack)
	{
		return getItemBurnTime(itemstack) > 0;
	}
	
	public static int getItemBurnTime(ItemStack itemstack)
	{
		if(itemstack==null)
			return 0;
		
		Item item = itemstack.getItem();
		if(item instanceof ItemBlock)
		{
			Block block = ((ItemBlock)item).field_150939_a;
			if(block == Blocks.cobblestone)
			{
				return 200;
			}
		}
		else
		{
			if(item == Items.coal)
			{
				return 200*8;
			}
		}
		
		return GameRegistry.getFuelValue(itemstack);
	}
}
