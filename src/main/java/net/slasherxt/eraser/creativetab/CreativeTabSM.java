package net.slasherxt.eraser.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.slasherxt.eraser.reference.Reference;

public class CreativeTabSM {
	public static final CreativeTabs E_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase()) {
		@Override
		public Item getTabIconItem() {
			return Items.slime_ball;
		}
	};
}
