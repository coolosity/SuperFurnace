package com.github.coolosity.superfurnace.init;

import java.lang.reflect.Field;

import net.minecraft.block.Block;

import com.github.coolosity.superfurnace.blocks.BlockAnimated;
import com.github.coolosity.superfurnace.blocks.BlockSFWall;
import com.github.coolosity.superfurnace.blocks.BlockFurnaceCore;
import com.github.coolosity.superfurnace.helpers.RegisterHelper;

public class SFBlocks {

	public static Block furnaceCore = new BlockFurnaceCore();
	public static Block sfWall = new BlockSFWall();
	public static Block animated = new BlockAnimated();
	
	public static void registerBlocks()
	{
		for(Field field : SFBlocks.class.getFields())
		{
			if(field.getType() == Block.class)
			{
				try {
					RegisterHelper.registerBlock((Block) field.get(null));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
