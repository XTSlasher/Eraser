package net.slasherxt.eraser.item.crafting;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.slasherxt.eraser.utility.LogHelper;

public class ConverterRecipes {
	private static final ConverterRecipes base = new ConverterRecipes();
	private Map<ItemStack, Integer> convertingList = new HashMap<ItemStack, Integer>();
	
	public static ConverterRecipes shaping() {
		return base;
	}
	
	private ConverterRecipes() {		
		this.storeBlock(Blocks.dirt, 1);
		this.storeItem(Items.redstone, 273);
	}
	
	public void storeBlock(Block block, int requiredShavings)
    {
        this.storeItem(Item.getItemFromBlock(block), requiredShavings);
    }

    public void storeItem(Item item, int requiredShavings)
    {
        this.storeItemStack(new ItemStack(item, 1, 32767), requiredShavings);
    }
    
	public void storeItemStack(ItemStack stack, int requiredShavings)
    {
		LogHelper.info("Crafting: " + stack.getDisplayName() + " usings " + requiredShavings + " shavings!");
        this.convertingList.put(stack, requiredShavings);
    }
    
	public ItemStack getShapingResult(ItemStack stack)
	{
		return new ItemStack(stack.getItem(), 2);
    }
	
	private boolean keyExists(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getItemDamage() == 32767 || stack2.getItemDamage() == stack1.getItemDamage());
    }
	
	@SuppressWarnings("rawtypes")
	public int getRequiredShaving(ItemStack stack) {
		Iterator it = this.convertingList.entrySet().iterator();
		Entry entry;
		
		do {
			if(!it.hasNext()) {
				return 0;
			}
			
			entry = (Entry)it.next();
		}
		while(!this.keyExists(stack, (ItemStack)entry.getKey()));
		
		return Integer.parseInt(entry.getValue().toString());
	}
	
	@SuppressWarnings("rawtypes")
	public Map getShapingList() {
		return this.convertingList;
	}
}
