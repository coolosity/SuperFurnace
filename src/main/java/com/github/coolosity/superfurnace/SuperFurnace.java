package com.github.coolosity.superfurnace;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import com.github.coolosity.superfurnace.gui.GuiHandler;
import com.github.coolosity.superfurnace.helpers.RegisterHelper;
import com.github.coolosity.superfurnace.init.SFBlocks;
import com.github.coolosity.superfurnace.init.SFEvents;
import com.github.coolosity.superfurnace.init.SFItems;
import com.github.coolosity.superfurnace.init.SFRecipes;
import com.github.coolosity.superfurnace.proxies.CommonProxy;
import com.github.coolosity.superfurnace.tileentity.TileEntitySFWall;
import com.github.coolosity.superfurnace.tileentity.TileEntitySuperFurnace;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;


@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class SuperFurnace {
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
	public static CommonProxy proxy;
	
	@Instance(Reference.MODID)
	public static SuperFurnace instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		SFBlocks.registerBlocks();
		SFItems.registerItems();
		SFRecipes.registerRecipes();
		MinecraftForge.EVENT_BUS.register(new SFEvents());
		
		RegisterHelper.registerTileEntity(TileEntitySFWall.class);
		RegisterHelper.registerTileEntity(TileEntitySuperFurnace.class);
		
		GameRegistry.addRecipe(new ItemStack(SFBlocks.furnaceCore), "XMX","MZM","XMX", 'X', Blocks.furnace, 'M', Items.redstone, 'Z', Blocks.iron_block);
	
		GuiHandler guiHandler = new GuiHandler();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
}
