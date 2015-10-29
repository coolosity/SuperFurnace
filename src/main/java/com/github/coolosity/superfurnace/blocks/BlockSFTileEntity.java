package com.github.coolosity.superfurnace.blocks;

import com.github.coolosity.superfurnace.Reference;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public abstract class BlockSFTileEntity extends BlockContainer
{
	public BlockSFTileEntity(String name, Material mat) {
		super(mat);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setBlockName(name);
		this.setBlockTextureName(Reference.MODID + ":" + name);
	}
	
	public BlockSFTileEntity(String name)
	{
		this(name, Material.rock);
	}

}
