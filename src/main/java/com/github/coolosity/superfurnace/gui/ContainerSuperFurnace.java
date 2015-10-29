package com.github.coolosity.superfurnace.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import com.github.coolosity.superfurnace.helpers.Utils;
import com.github.coolosity.superfurnace.tileentity.TileEntitySuperFurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerSuperFurnace extends Container
{
	
	private TileEntitySuperFurnace superFurnace;
	private int lastCookTime;
	private int lastBurnTime;
	private int lastItemBurnTime;	
	
	public ContainerSuperFurnace(InventoryPlayer inventory, TileEntitySuperFurnace tileentity)
	{
		this.superFurnace = tileentity;
		
		this.addSlotToContainer(new Slot(tileentity, 0, 38, 17));
		this.addSlotToContainer(new Slot(tileentity, 1, 56, 17));
		this.addSlotToContainer(new Slot(tileentity, 2, 74, 17));
		this.addSlotToContainer(new Slot(tileentity, 3, 38, 53));
		this.addSlotToContainer(new Slot(tileentity, 4, 56, 53));
		this.addSlotToContainer(new Slot(tileentity, 5, 74, 53));
		this.addSlotToContainer(new SlotFurnace(inventory.player, tileentity, 6, 116, 35));
		this.addSlotToContainer(new SlotFurnace(inventory.player, tileentity, 7, 116+18, 35));
		this.addSlotToContainer(new SlotFurnace(inventory.player, tileentity, 8, 116+36, 35));
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<9;j++)
			{
				this.addSlotToContainer(new Slot(inventory, j+i*9+9, 8+j*18, 84+i*18));
			}
		}
		
		for(int i=0;i<9;i++)
		{
			this.addSlotToContainer(new Slot(inventory, i, 8+i*18, 142));
		}
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting icrafting)
	{
		super.addCraftingToCrafters(icrafting);
		icrafting.sendProgressBarUpdate(this, 0, this.superFurnace.furnaceCookTime);
		icrafting.sendProgressBarUpdate(this, 3, this.superFurnace.furnaceBurnTime);
		icrafting.sendProgressBarUpdate(this, 6, this.superFurnace.currentBurnTime);
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int i = 0; i < this.crafters.size(); ++i){
			ICrafting craft = (ICrafting) this.crafters.get(i);
			
			if(this.lastCookTime != this.superFurnace.furnaceCookTime){
				craft.sendProgressBarUpdate(this, 0, this.superFurnace.furnaceCookTime);
			}
			
			if(this.lastBurnTime != this.superFurnace.furnaceBurnTime){
				craft.sendProgressBarUpdate(this, 1, this.superFurnace.furnaceBurnTime);
			}
			
			if(this.lastItemBurnTime != this.superFurnace.currentBurnTime){
				craft.sendProgressBarUpdate(this, 2, this.superFurnace.currentBurnTime);
			}
		}
		
		this.lastBurnTime = this.superFurnace.furnaceBurnTime;
		this.lastCookTime = this.superFurnace.furnaceCookTime;
		this.lastItemBurnTime = this.superFurnace.currentBurnTime;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int slot, int newValue)
	{
		if(slot==0)this.superFurnace.furnaceCookTime = newValue;
		if(slot==1)this.superFurnace.furnaceBurnTime = newValue;
		if(slot==2)this.superFurnace.currentBurnTime = newValue;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);
		
		if(slot != null && slot.getHasStack()){
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if(par2 == 2){
				if(!this.mergeItemStack(itemstack1, 3, 39, true)){
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			}else if(par2 != 1 && par2 != 0){
				if(FurnaceRecipes.smelting().getSmeltingResult(itemstack1) != null){
					if(!this.mergeItemStack(itemstack1, 0, 1, false)){
						return null;
					}
				}else if(Utils.isItemFuel(itemstack1)){
					if(!this.mergeItemStack(itemstack1, 1, 2, false)){
						return null;
					}
				}else if(par2 >=3 && par2 < 30){
					if(!this.mergeItemStack(itemstack1, 30, 39, false)){
						return null;
					}
				}else if(par2 >= 30 && par2 < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)){
					return null;
				}
			}else if(!this.mergeItemStack(itemstack1, 3, 39, false)){
				return null;
			}
			if(itemstack1.stackSize == 0){
				slot.putStack((ItemStack)null);
			}else{
				slot.onSlotChanged();
			}
			if(itemstack1.stackSize == itemstack.stackSize){
				return null;
			}
			slot.onPickupFromSlot(player, itemstack1);
		}
		return itemstack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.superFurnace.isUseableByPlayer(player);
	}

}
