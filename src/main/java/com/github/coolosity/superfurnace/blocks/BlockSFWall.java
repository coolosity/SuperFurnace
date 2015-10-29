package com.github.coolosity.superfurnace.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.github.coolosity.superfurnace.Names;
import com.github.coolosity.superfurnace.SuperFurnace;
import com.github.coolosity.superfurnace.gui.GuiHandler;
import com.github.coolosity.superfurnace.init.SFBlocks;
import com.github.coolosity.superfurnace.tileentity.TileEntitySFWall;

public class BlockSFWall extends BlockSFTileEntity
{
	public BlockSFWall() {
		super(Names.Blocks.SFWALL);
		this.setHardness(3F);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		if(!world.isRemote)
		{
			TileEntitySFWall te = (TileEntitySFWall) world.getTileEntity(x, y, z);
			if(!te.isBroken())
			{
				int xx = te.getMasterX();
				int yy = te.getMasterY();
				int zz = te.getMasterZ();
				for(int xxx=-1;xxx<=1;xxx++)
				{
					for(int yyy=-1;yyy<=1;yyy++)
					{
						for(int zzz=-1;zzz<=1;zzz++)
						{
							if(!(xxx==0 && yyy==0 && zzz==0))
							{
								Block blk = world.getBlock(xx+xxx, yy+yyy, zz+zzz);
								if(blk == SFBlocks.sfWall)
									((TileEntitySFWall)world.getTileEntity(xx+xxx, yy+yyy, zz+zzz)).setBroken(true);
								world.setBlock(xx+xxx, yy+yyy, zz+zzz, Blocks.furnace);
							}
						}
					}
				}
				world.setBlockToAir(x, y, z);
			}
		}
		super.breakBlock(world, x, y, z, block, metadata);
	}
	
	@Override
	public Item getItemDropped(int metadata, Random random, int fortune)
	{
		return Item.getItemFromBlock(Blocks.furnace);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntitySFWall();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
		if(!world.isRemote)
		{
			TileEntitySFWall te = (TileEntitySFWall) world.getTileEntity(x, y, z);
			player.openGui(SuperFurnace.instance, GuiHandler.GUI_SUPERFURNACE, world, te.getMasterX(), te.getMasterY(), te.getMasterZ());
		}
        return true;
    }
}
