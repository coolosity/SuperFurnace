package com.github.coolosity.superfurnace.init;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.github.coolosity.superfurnace.blocks.BlockFurnaceCore;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SFEvents {

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if(event.entityPlayer.worldObj.isRemote)
			return;
		World world = event.entity.worldObj;
		if(event.action == Action.RIGHT_CLICK_BLOCK)
		{
			int xx = event.x;
			int yy = event.y;
			int zz = event.z;
			switch(event.face)
			{
			case 0:
				yy--;
				break;
			case 1:
				yy++;
				break;
			case 2:
				zz--;
				break;
			case 3:
				zz++;
				break;
			case 4:
				xx--;
				break;
			case 5:
				xx++;
				break;
			}
			ItemStack inhand = event.entityPlayer.getHeldItem();
			if(inhand != null)
			{
				if(inhand.getItem() instanceof ItemBlock)
				{
					Block block = ((ItemBlock) inhand.getItem()).field_150939_a;
					if(block == Blocks.furnace || block == SFBlocks.furnaceCore)
					{
						for(int x=-2;x<=0;x++)
						{
							for(int y=-2;y<=0;y++)
							{
								for(int z=-2;z<=0;z++)
								{
									if(BlockFurnaceCore.isMultiblockStucture(event.entityPlayer.worldObj, xx+x, yy+y, zz+z, xx, yy, zz, block))
									{
										world.scheduleBlockUpdate(xx+x+1, yy+y+1, zz+z+1, SFBlocks.furnaceCore, 1);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
}
