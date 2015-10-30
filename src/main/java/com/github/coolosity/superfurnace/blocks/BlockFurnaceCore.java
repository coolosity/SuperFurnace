package com.github.coolosity.superfurnace.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.github.coolosity.superfurnace.Names;
import com.github.coolosity.superfurnace.init.SFBlocks;
import com.github.coolosity.superfurnace.tileentity.TileEntitySFWall;
import com.github.coolosity.superfurnace.tileentity.TileEntitySuperFurnace;

public class BlockFurnaceCore extends BlockSFTileEntity
{
	
	public BlockFurnaceCore() {
		super(Names.Blocks.FURNACECORE);
		this.setHardness(5F);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		TileEntitySuperFurnace superFurnace = (TileEntitySuperFurnace) world.getTileEntity(x, y, z);
		if(isMultiblockStucture(world, x-1, y-1, z-1) && !superFurnace.isInMultiblock())
		{
			superFurnace.setIsInMultiblock(true);
			//Turn into multiblock
			for(int xx=0;xx<3;xx++)
			{
				for(int yy=0;yy<3;yy++)
				{
					for(int zz=0;zz<3;zz++)
					{
						if(xx*yy*zz!=1)
						{
							world.setBlockToAir(x+xx-1, y+yy-1, z+zz-1);
							world.setBlock(x+xx-1, y+yy-1, z+zz-1, SFBlocks.sfWall, 0, 1);
							TileEntitySFWall te = (TileEntitySFWall) world.getTileEntity(x+xx-1, y+yy-1, z+zz-1);
							te.setMasterLocation(x, y, z);
						}
					}
				}
			}
		}
	}
	
	public static boolean isMultiblockStucture(World world, int x, int y, int z)
	{
		return isMultiblockStucture(world,x,y,z,0,0,0,null);
	}
	
	public static boolean isMultiblockStucture(World world, int x, int y, int z, int x1, int y1, int z1, Block newBlock)
	{
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				for(int k=0;k<3;k++)
				{
					Block b = world.getBlock(x+i, y+j, z+k);
					if(x+i==x1 && y+j==y1 && z+k==z1 && newBlock != null)
						b = newBlock;
					if(i*j*k==1)
					{
						if(b != SFBlocks.furnaceCore)
							return false;
					}
					else if(b != Blocks.furnace)
						return false;
				}
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntitySuperFurnace();
	}
}
