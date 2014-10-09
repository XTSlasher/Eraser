package net.slasherxt.eraser.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.slasherxt.eraser.Eraser;
import net.slasherxt.eraser.reference.Reference;
import net.slasherxt.eraser.tileentity.TileEntityConverter;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockConverter extends BlockContainer {
	public BlockConverter() {
		super(Material.rock);
		setBlockName("eraserConverter");
		setCreativeTab(CreativeTabs.tabRedstone);
		setHardness(5F);
		setResistance(10F);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon top;
	@SideOnly(Side.CLIENT)
	public IIcon side;
	@SideOnly(Side.CLIENT)
	public IIcon front;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister icon) {
		top = icon.registerIcon(Reference.MOD_ID + ":converter_Top");
		side = icon.registerIcon(Reference.MOD_ID + ":converter_Side");
		front = icon.registerIcon(Reference.MOD_ID + ":converter_Front");
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if(side == 0 || side == 1) {
			return top;
		} else if(side != meta ) {
			return this.side;
		} else {
			return front;
		}
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		setDefaultDirection(world, x, y, z);
	}
	
	private void setDefaultDirection(World world, int x, int y, int z) {
		if(!world.isRemote) {
			Block block1 = world.getBlock(x, y, z - 1);
			Block block2 = world.getBlock(x, y, z + 1);
			Block block3 = world.getBlock(x - 1, y, z);
			Block block4 = world.getBlock(x + 1, y, z);
			byte meta = 3;
			
			if(block3.isOpaqueCube() && !block4.isOpaqueCube()) {
				meta = 5;
			}
			if(block4.isOpaqueCube() && !block3.isOpaqueCube()) {
				meta = 4;
			}
			if(block1.isOpaqueCube() && !block2.isOpaqueCube()) {
				meta = 3;
			}
			if(block2.isOpaqueCube() && !block1.isOpaqueCube()) {
				meta = 2;
			}
			
			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		int rotation = MathHelper.floor_double((double)(entity.rotationYaw * 4F / 360F) + 0.5D) & 3;

		if(rotation == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(rotation == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(rotation == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(rotation == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			FMLNetworkHandler.openGui(player, Eraser.instance, 0, world, x, y, z);
		}
		
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityConverter();
	}
}
