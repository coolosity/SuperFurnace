package com.github.coolosity.superfurnace.blocks;

import com.github.coolosity.superfurnace.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockSF extends Block
{
	public BlockSF(String name, Material mat)
	{
		super(mat);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setBlockName(name);
		this.setBlockTextureName(Reference.MODID + ":" + name);
	}
	
	public BlockSF(String name)
	{
		this(name, Material.rock);
	}
	
}
