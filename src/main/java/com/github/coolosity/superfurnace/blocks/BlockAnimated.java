package com.github.coolosity.superfurnace.blocks;

import java.util.HashMap;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.github.coolosity.superfurnace.Names;
import com.github.coolosity.superfurnace.Reference;
import com.github.coolosity.superfurnace.helpers.Utils;

public class BlockAnimated extends BlockSF
{
	private static final String ITOP = "top";
	private static final String IFRONT = "front";
	private static final String ISIDE = "side";
	private static final String IBOTTOM = "bottom";
	private static final String[] ICON_LIST = {ITOP, IFRONT, ISIDE, IBOTTOM};
	
	private HashMap<String,IIcon> icons;
	
	public BlockAnimated()
	{
		super(Names.Blocks.ANIMATED);
		icons = new HashMap<String,IIcon>();
	}

	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		for(String s : ICON_LIST)
		{
			icons.put(s, reg.registerIcon(Reference.MODID + ":" + Names.Blocks.ANIMATED + "_" + s));
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack)
    {
		int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
        	world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }

        if (l == 1)
        {
        	world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }

        if (l == 2)
        {
        	world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        if (l == 3)
        {
        	world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
    }
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		switch(side)
		{
		case Utils.Side.TOP:
			return icons.get(ITOP);
		case Utils.Side.BOTTOM:
			return icons.get(IBOTTOM);
		default:
			return (side == meta ? icons.get(IFRONT) : icons.get(ISIDE));
		}
	}
}
