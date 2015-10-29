package com.github.coolosity.superfurnace.init;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class SFRecipes {

	public static void registerRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(SFBlocks.furnaceCore), "XX", "XX", 'X', Blocks.dirt);
	}
	
}
