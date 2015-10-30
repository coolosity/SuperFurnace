package com.github.coolosity.superfurnace.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import com.github.coolosity.superfurnace.helpers.Utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntitySuperFurnace extends TileEntity implements ISidedInventory
{
	
	private static final int[] slots_top = new int[]{0,1,2};
	private static final int[] slots_bottom = new int[]{6,7,8, 3,4,5};
	private static final int[] slots_sides = new int[]{3,4,5};
	
	private ItemStack[] furnaceItemStacks = new ItemStack[9];
	
	public int furnaceBurnTime;
	public int currentBurnTime;
	public int furnaceCookTime;
	
	private String furnaceName;
	
	private boolean isInMultiblock;
	
	public void setIsInMultiblock(boolean isInMultiblock)
	{
		this.isInMultiblock = isInMultiblock;
	}
	
	public boolean isInMultiblock()
	{
		return isInMultiblock;
	}
	
	@Override
	public int getSizeInventory()
	{
		return furnaceItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.furnaceItemStacks[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		if(this.furnaceItemStacks[slot] != null)
		{
			ItemStack itemstack;
			if(this.furnaceItemStacks[slot].stackSize <= amt)
			{
				itemstack = this.furnaceItemStacks[slot];
				this.furnaceItemStacks[slot] = null;
				return itemstack;
			}
			else
			{
				itemstack = this.furnaceItemStacks[slot].splitStack(amt);
				
				if(this.furnaceItemStacks[slot].stackSize == 0)
				{
					this.furnaceItemStacks[slot] = null;
				}
				return itemstack;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if(this.furnaceItemStacks[slot] != null)
		{
			ItemStack itemstack = this.furnaceItemStacks[slot];
			this.furnaceItemStacks[slot] = null;
			return itemstack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.furnaceItemStacks[slot] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.furnaceName : "Super Furnace";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.furnaceName != null && this.furnaceName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		NBTTagList tagList = tagCompound.getTagList("Items", 10);
		this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];
		for(int i=0;i<tagList.tagCount();++i)
		{
			NBTTagCompound tabCompound1 = tagList.getCompoundTagAt(i);
			byte byte0 = tabCompound1.getByte("Slot");
			
			if(byte0 >= 0 && byte0 < this.furnaceItemStacks.length)
			{
				this.furnaceItemStacks[byte0] = ItemStack.loadItemStackFromNBT(tabCompound1);
			}
		}
		
		this.furnaceBurnTime = tagCompound.getShort("BurnTime");
		this.furnaceCookTime = tagCompound.getShort("CookTime");
		this.currentBurnTime = Utils.getItemBurnTime(this.furnaceItemStacks[3]);
		
		if(tagCompound.hasKey("CustomName", 8))
		{
			this.furnaceName = tagCompound.getString("CustomName");
		}
		
		this.isInMultiblock = tagCompound.getBoolean("IsMultiblock");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("BurnTime", (short) this.furnaceBurnTime);
		tagCompound.setShort("CookTime", (short) this.furnaceBurnTime);
		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < this.furnaceItemStacks.length; ++i) {
			if (this.furnaceItemStacks[i] != null) {
				NBTTagCompound tagCompound1 = new NBTTagCompound();
				tagCompound1.setByte("Slot", (byte) i);
				this.furnaceItemStacks[i].writeToNBT(tagCompound1);
				tagList.appendTag(tagCompound1);
			}
		}

		tagCompound.setTag("Items", tagList);
		tagCompound.setBoolean("IsMultiblock", isInMultiblock);

		if (this.hasCustomInventoryName()) {
			tagCompound.setString("CustomName", this.furnaceName);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1)
	{
		return this.furnaceCookTime * par1 / 200;
	}
	
	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1)
	{
		if(this.currentBurnTime == 0)
		{
			this.currentBurnTime = 200;
		}
		
		return this.furnaceBurnTime * par1 / this.currentBurnTime;
	}
	
	public boolean isBurning()
	{
		return this.furnaceBurnTime > 0;
	}
	
	@Override
	public void updateEntity()
	{
		if (this.furnaceBurnTime > 0) {
			this.furnaceBurnTime--;
		}
		if(this.furnaceBurnTime<0)
			this.furnaceBurnTime = 0;
		if (!this.worldObj.isRemote) {
			if (this.furnaceBurnTime == 0 && this.canSmelt()) {
				this.furnaceBurnTime = Utils.getItemBurnTime(this.furnaceItemStacks[3]);
				this.currentBurnTime = this.furnaceBurnTime;

				if (this.furnaceBurnTime > 0) {
					if (this.furnaceItemStacks[3] != null) {
						--this.furnaceItemStacks[3].stackSize;

						if (this.furnaceItemStacks[3].stackSize == 0) {
							this.furnaceItemStacks[3] = furnaceItemStacks[3].getItem().getContainerItem(this.furnaceItemStacks[3]);
						}
					}
				}
			}

			if (this.isBurning() && this.canSmelt()) {
				this.furnaceCookTime+=39;
				if (this.furnaceCookTime >= 200) {
					this.furnaceCookTime = 0;
					this.smeltItem();
				}
			} else {
				this.furnaceCookTime = 0;
			}
		}
	}
	
	private boolean canSmelt() {
		boolean hasOne = false;
		for(int i=0;i<3;i++)
		{
			if(this.furnaceItemStacks[i] != null)
				hasOne = true;
		}
		if(!hasOne)return false;
		
		hasOne = false;

		for(int i=0;i<3;i++)
		{
			if(this.furnaceItemStacks[i] != null)
			{
				ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[i]);
				if(itemstack != null)
				{
					if(this.furnaceItemStacks[i+6] == null)
						hasOne = true;
					else if(this.furnaceItemStacks[i+6].isItemEqual(itemstack))
					{
						int sum = this.furnaceItemStacks[i+6].stackSize + itemstack.stackSize;
						if(sum <= getInventoryStackLimit() && sum <= this.furnaceItemStacks[i+6].getMaxStackSize())
							hasOne = true;
					}
				}
			}
		}
		return hasOne;
	}

	public void smeltItem() {
		if (this.canSmelt()) {
			for(int i=0;i<3;i++)
			{
				if(this.furnaceItemStacks[i]!=null)
				{
					ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[i]);
					if(itemstack != null)
					{
						boolean removeOne = false;
						if (this.furnaceItemStacks[i+6] == null) {
							this.furnaceItemStacks[i+6] = itemstack.copy();
							removeOne = true;
						} else if (this.furnaceItemStacks[i+6].getItem() == itemstack.getItem()) {
							int sum = this.furnaceItemStacks[i+6].stackSize + itemstack.stackSize;
							if(sum <= getInventoryStackLimit() && sum <= this.furnaceItemStacks[i+6].getMaxStackSize())
							{
								this.furnaceItemStacks[i+6].stackSize += itemstack.stackSize;
								removeOne = true;
							}
						}
						if(removeOne)
						{
							this.furnaceItemStacks[i].stackSize--;
							if(this.furnaceItemStacks[i].stackSize == 0){
								this.furnaceItemStacks[i] = null;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack itemstack) {
		if(par1>=6 && par1<=8)
			return false;
		if(par1>=0 && par1<=2)
			return true;
		return Utils.isItemFuel(itemstack);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int par1) {
		return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return this.isItemValidForSlot(i, itemstack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return !(j>=0 && j<=2) || !(i>=3 && i<=5) || itemstack.getItem()==Items.bucket;
	}
}
