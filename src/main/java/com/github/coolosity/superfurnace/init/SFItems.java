package com.github.coolosity.superfurnace.init;

import net.minecraft.item.Item;

import com.github.coolosity.superfurnace.helpers.RegisterHelper;
import com.github.coolosity.superfurnace.items.ItemSF;

public class SFItems {

	public static Item test = new ItemSF("testItem");
	
	public static void registerItems()
	{
		RegisterHelper.registerItem(test);
	}
	
}
