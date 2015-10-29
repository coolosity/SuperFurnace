package com.github.coolosity.superfurnace.items;

import com.github.coolosity.superfurnace.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemSF extends Item
{

	public ItemSF(String name)
	{
		super();
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setUnlocalizedName(name);
		this.setTextureName(Reference.MODID + ":" + name);
	}
	
}
