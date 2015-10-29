package com.github.coolosity.superfurnace.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySFWall extends TileEntity
{
	private int masterX, masterY, masterZ;
	private boolean broken;
	
	public TileEntitySFWall() {}
	
	public void setMasterLocation(int masterX, int masterY, int masterZ)
	{
		this.masterX = masterX;
		this.masterY = masterY;
		this.masterZ = masterZ;
	}
	
	public int getMasterX()
	{
		return masterX;
	}
	
	public int getMasterY()
	{
		return masterY;
	}
	
	public int getMasterZ()
	{
		return masterZ;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("masterX", masterX);
		nbt.setInteger("masterY", masterY);
		nbt.setInteger("masterZ", masterZ);
		nbt.setBoolean("broken", broken);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		masterX = nbt.getInteger("masterX");
		masterY = nbt.getInteger("masterY");
		masterZ = nbt.getInteger("masterZ");
		broken = nbt.getBoolean("broken");
	}
	
	public boolean isBroken()
	{
		return broken;
	}
	
	public void setBroken(boolean broken)
	{
		this.broken = broken;
	}
}
