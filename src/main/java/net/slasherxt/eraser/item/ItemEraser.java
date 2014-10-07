package net.slasherxt.eraser.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.slasherxt.eraser.init.ModItems;
import net.slasherxt.eraser.reference.Reference;
import net.slasherxt.eraser.utility.LogHelper;

public class ItemEraser extends Item {
	
	private int[] tierStorage = {50, 450, 900, 1350, 5000};
	private int[] tierAddition = {1, 9, 18, 27, 100};
	private ItemEraser[] erasers = {ModItems.basicEraser, ModItems.smallEraser, ModItems.mediumEraser, ModItems.largeEraser, ModItems.megaEraser};
	
	public ItemEraser(int tier) {
		setCreativeTab(CreativeTabs.tabTools);
		setMaxStackSize(1);
		setUnlocalizedName(Reference.MOD_ID + ":eraser_" + tier);
		setTextureName(Reference.MOD_ID + ":eraser_" + tier);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("tier", getEraserTier(stack));
		}

		int count = eraseBlocks(world, player, x, y, z, stack.stackTagCompound.getInteger("tier"));
		
		if(stack.stackTagCompound.getInteger("shavingCount") < tierStorage[stack.stackTagCompound.getInteger("tier")]) {
			stack.stackTagCompound.setInteger("shavingCount", stack.stackTagCompound.getInteger("shavingCount") + count);
		} else {
			player.addChatMessage(player.func_145748_c_().appendText("Your eraser is full."));
		}
		
		if(stack.stackTagCompound.getInteger("shavingCount") > tierStorage[stack.stackTagCompound.getInteger("tier")]) {
			stack.stackTagCompound.setInteger("shavingCount", tierStorage[stack.stackTagCompound.getInteger("tier")]);
		}
		
		LogHelper.info("Count: " + stack.getTagCompound().getInteger("shavingCount"));
		LogHelper.info("Tier: " + stack.getTagCompound().getInteger("tier"));
		
        return false;
    }
	
	private int getEraserTier(ItemStack stack) {
		if(stack.getItem() == ModItems.basicEraser) {
			return 0;
		} else if(stack.getItem() == ModItems.smallEraser) {
			return 1;
		} else if(stack.getItem() == ModItems.mediumEraser) {
			return 2;
		} else if(stack.getItem() == ModItems.largeEraser) {
			return 3;
		} else if(stack.getItem() == ModItems.megaEraser) {
			return 4;
		} else {
			return 0;
		}
	}
	
	private int eraseBlocks(World world, EntityPlayer player, int x, int y, int z, int tier) {
		int counter = 0;
		
		if(tier == 0) {
			if(world.getBlock(x, y, z) != Blocks.bedrock && world.getBlock(x, y, z) != Blocks.air) {
				world.getBlock(x, y, z).removedByPlayer(world, player, x, y, z, false);
				counter++;
			}
		}
		
		if(tier == 1) {
			for(int x1=0;x1<3;x1++) {
				for(int y1=0;y1<1;y1++) {
					for(int z1=0;z1<3;z1++) {
						if(world.getBlock(x + x1, y - y1, z + z1) != Blocks.bedrock && world.getBlock(x + x1, y - y1, z + z1) != Blocks.air) {
							world.getBlock(x + x1, y - y1, z + z1).removedByPlayer(world, player, x + x1, y - y1, z + z1, false);
							counter++;
						}
					}
				}
			}
		}
		
		if(tier == 2) {
			for(int x1=0;x1<3;x1++) {
				for(int y1=0;y1<2;y1++) {
					for(int z1=0;z1<3;z1++) {
						if(world.getBlock(x + x1, y - y1, z + z1) != Blocks.bedrock && world.getBlock(x + x1, y - y1, z + z1) != Blocks.air) {
							world.getBlock(x + x1, y - y1, z + z1).removedByPlayer(world, player, x + x1, y - y1, z + z1, false);
							counter++;
						}
					}
				}
			}
		}
		
		if(tier == 3) {
			for(int x1=0;x1<3;x1++) {
				for(int y1=0;y1<3;y1++) {
					for(int z1=0;z1<3;z1++) {
						if(world.getBlock(x + x1, y - y1, z + z1) != Blocks.bedrock && world.getBlock(x + x1, y - y1, z + z1) != Blocks.air) {
							world.getBlock(x + x1, y - y1, z + z1).removedByPlayer(world, player, x + x1, y - y1, z + z1, false);
							counter++;
						}
					}
				}
			}
		}
		
		if(tier == 4) {
			for(int x1=0;x1<5;x1++) {
				for(int y1=0;y1<5;y1++) {
					for(int z1=0;z1<5;z1++) {
						if(world.getBlock(x + x1, y - y1, z + z1) != Blocks.bedrock && world.getBlock(x + x1, y - y1, z + z1) != Blocks.air) {
							world.getBlock(x + x1, y - y1, z + z1).removedByPlayer(world, player, x + x1, y - y1, z + z1, false);
							counter++;
						}	
					}
				}
			}
		}
		
		System.err.println(counter);
		return counter;
	}
}
