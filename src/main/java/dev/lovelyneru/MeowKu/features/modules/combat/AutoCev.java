package dev.lovelyneru.MeowKu.features.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.lovelyneru.MeowKu.MeowKu;
import dev.lovelyneru.MeowKu.features.setting.Setting;
import dev.lovelyneru.MeowKu.util.InventoryUtil;
import dev.lovelyneru.MeowKu.features.command.Command;
import dev.lovelyneru.MeowKu.features.modules.Module;
import dev.lovelyneru.MeowKu.util.BlockUtil;
import dev.lovelyneru.MeowKu.util.EntityUtil;
import dev.lovelyneru.MeowKu.util.HoleUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoCev extends Module {
    public AutoCev() {
        super("AutoCev","AutoCev" ,Module.Category.COMBAT, true, false, false);
    }
    private final Setting<Boolean> rotate = register(new Setting<Boolean>("Rotate", true));
    private final Setting<Boolean> noGhost = register(new Setting<Boolean>("Packet", false));

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.player.isDead) return;
        if (nullCheck()) return;

        if (InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN) == -1) {
            Command.sendMessage("<" + getDisplayName() + "> " + ChatFormatting.RED + "No Obsidian in hotbar disabling...");

            this.disable();
            return;
        }
//        if (InventoryUtil.findItemInHotbar(Items.END_CRYSTAL) == -1) {
//            Command.sendMessage("<" + getDisplayName() + "> " + ChatFormatting.RED + "You don't have a manuscript");
//
//            this.disable();
//            return;
//        }

        if(InventoryUtil.findItemInHotbar(Items.END_CRYSTAL) == -1){
            Command.sendMessage("<" + getDisplayName() + "> " + ChatFormatting.RED + "You don't have a manuscript");
            this.disable();
            return;
        }
        if (InventoryUtil.findItemInHotbar(Items.DIAMOND_PICKAXE) == -1) {

            Command.sendMessage("<" + getDisplayName() + "> " + ChatFormatting.RED + "You don't have crystal!!");
            this.disable();
            return;
        }

        EntityPlayer targets = getTarget(10.0, true);
        if (targets == null) {
            Command.sendMessage("<" + getDisplayName() + "> " + ChatFormatting.RED + "No enemy coordinates obtained!!");

            this.disable();
            return;
        }

        if (HoleUtil.isHole(new BlockPos(targets.posX,targets.posY,targets.posZ) )) {
            trap(targets);

            if(mc.world.getBlockState(new BlockPos(targets.posX+1,targets.posY+1,targets.posZ)) .getBlock()==Blocks.AIR&&mc.world.getBlockState(new BlockPos(targets.posX+1,targets.posY+2,targets.posZ)) .getBlock()==Blocks.AIR){
                int imp =mc.player.inventory.currentItem;
                mc.player.inventory.currentItem=InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
                BlockUtil.placeBlock(new BlockPos(targets.posX+1,targets.posY+1,targets.posZ),  EnumHand.MAIN_HAND, rotate.getValue(), noGhost.getValue(), true);
                mc.player.inventory.currentItem = imp;
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(new BlockPos(targets.posX+1,targets.posY+2,targets.posZ), EnumFacing.UP, mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                AutoCrystal.mc.player.connection.sendPacket(new CPacketUseEntity());

            }
//            if(mc.world.getBlockState(new BlockPos(targets.posX+1,targets.posY+1,targets.posZ)) .getBlock()==Blocks.AIR&&mc.world.getBlockState(new BlockPos(targets.posX+1,targets.posY+2,targets.posZ)) .getBlock()==Blocks.){
//                if(mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL){
//                    int imp =mc.player.inventory.currentItem;
//                    mc.player.inventory.currentItem=InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
//                    BlockUtil.placeBlock(new BlockPos(targets.posX+1,targets.posY+1,targets.posZ),  EnumHand.OFF_HAND, rotate.getValue(), noGhost.getValue(), true);
//                    mc.player.inventory.currentItem = imp;
//                }
//
//            }

        } else {

        }
    }

    public void trap(EntityPlayer target) {
        int obsidianSlot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
        for (Vec3d autoTrapBox : Trap) {
            if (target != null) {
                IBlockState blockState2 = mc.world.getBlockState(new BlockPos(autoTrapBox.add(target.getPositionVector())));
                if (blockState2.getBlock()==Blocks.AIR) {
                    int imp =mc.player.inventory.currentItem;

                    if (obsidianSlot != -1)
                        mc.player.inventory.currentItem=InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
                    BlockUtil.placeBlock(new BlockPos(autoTrapBox.add(target.getPositionVector())), EnumHand.MAIN_HAND, rotate.getValue(), noGhost.getValue(), false);
                    mc.player.inventory.currentItem = imp;



                }
            }
        }
    }
    List<Vec3d> Trap = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, -1),
            new Vec3d(1, -1, 0),
            new Vec3d(0, -1, 1),
            new Vec3d(-1, -1, 0),
            new Vec3d(0, 0, -1),
            new Vec3d(1, 0, 0),
            new Vec3d(0, 0, 1),
            new Vec3d(-1, 0, 0),
            new Vec3d(0, 1, -1),
            new Vec3d(0, 1, 1),
            new Vec3d(-1, 1, 0),
            new Vec3d(0, 2, -1),
            new Vec3d(0, 2, 1),
            new Vec3d(-1, 2, 0),
            new Vec3d(0, 2, 0)

    ));

    private EntityPlayer getTarget(double range, boolean trapped) {
        EntityPlayer target = null;
        double distance = Math.pow(range, 2.0) + 1.0;
        for (EntityPlayer player : AutoTrap.mc.world.playerEntities) {
            if (EntityUtil.isntValid(player, range) || trapped && EntityUtil.isTrapped(player,true, false, false, false, false) || MeowKu.speedManager.getPlayerSpeed(player) > 10.0)
                continue;
            if (target == null) {
                target = player;
                distance = AutoTrap.mc.player.getDistanceSq(player);
                continue;
            }
            if (!(AutoTrap.mc.player.getDistanceSq(player) < distance)) continue;
            target = player;
            distance = AutoTrap.mc.player.getDistanceSq(player);
        }
        return target;
    }

}
