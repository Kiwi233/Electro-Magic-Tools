package emt.gthandler.common.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class EMT_GT_Block extends Block {

    public EMT_GT_Block(String name, String[] texture) {
        super(Material.anvil);
        this.setHardness(15.0F);
        this.setResistance(30.0F);
        this.name = name;
        this.textureNames=texture;
        this.setCreativeTab(EMT.TAB);
    }

    @SideOnly(Side.CLIENT)
    private IIcon[] texture;
    @SideOnly(Side.SERVER)
    private String[] textureNames;
    private String name;

    @Override
    public int damageDropped(final int meta) {
        return meta;
    }

    @Override
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < textureNames.length; i ++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return meta < texture.length ? texture[meta] : texture[1];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister){
        texture = new IIcon[textureNames.length];
        for (int i = 0; i < textureNames.length; i++) {
            texture[i] = par1IconRegister.registerIcon(textureNames[i]);
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public String getUnlocalizedName(){
        return name;
    }
}
