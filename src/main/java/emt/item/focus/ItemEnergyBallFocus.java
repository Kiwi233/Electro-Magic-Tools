package emt.item.focus;

import emt.entity.EntityEnergyBall;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.ItemFocusBasic;

public class ItemEnergyBallFocus extends ItemBaseFocus {

	public ItemEnergyBallFocus() {
		super("energyBall");
	}

	@Override
	public int getFocusColor(ItemStack stack) {
		return 0x0000FF;
	}

	@Override
	public String getSortingHelper(ItemStack itemstack) {
		return "ENERGYBALL";
	}

	@Override
	public ItemStack onFocusRightClick(ItemStack stack, World world, EntityPlayer player, MovingObjectPosition mop) {
		if (!world.isRemote) {
			float rotX = (-MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI)) * 100;
			float rotY = (-MathHelper.sin(player.rotationPitch / 180.0F * (float) Math.PI)) * 100;
			float rotZ = (MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI)) * 100;
			world.spawnEntityInWorld(new EntityEnergyBall(world, player, rotX, rotY, rotZ));
		}
		return stack;
	}

}