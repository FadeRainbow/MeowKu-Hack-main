/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  net.minecraft.block.BlockObsidian
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 */
package dev.lovelyneru.MeowKu.features.modules.combat;

import dev.lovelyneru.MeowKu.MeowKu;
import dev.lovelyneru.MeowKu.features.setting.Setting;
import dev.lovelyneru.MeowKu.util.InventoryUtil;
import dev.lovelyneru.MeowKu.features.modules.Module;
import dev.lovelyneru.MeowKu.util.BlockUtil;
import dev.lovelyneru.MeowKu.util.EntityUtil;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockWeb;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class TrapHead
        extends Module {
    public EntityPlayer target;
    private final Setting<Float> range = this.register(new Setting<Float>("Range", Float.valueOf(5.0f), Float.valueOf(1.0f), Float.valueOf(6.0f)));

    public TrapHead() {
        super("TrapHead", "Trap Head", Module.Category.COMBAT, true, false, false);
    }

    private final Setting<Boolean> web = this.register(new Setting<Boolean>("WEB", false));

    @Override
    public void onEnable() {
        Surround.breakcrystal();
    }

    @Override
    public void onTick() {
        if (TrapHead.fullNullCheck()) {
            return;
        }
        this.target = this.getTarget(this.range.getValue().floatValue());
        if (this.target == null) {
            return;
        }
        BlockPos people = new BlockPos(this.target.posX, this.target.posY, this.target.posZ);
        int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        if (obbySlot == -1) {
            return;
        }
        int webSlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        if (webSlot == -1) {
            return;
        }
        if (this.web.getValue().booleanValue()) {
            webSlot = InventoryUtil.findHotbarBlock(BlockWeb.class);
            if (webSlot == -1) {
                return;
            }
        }
        int old = TrapHead.mc.player.inventory.currentItem;
        if (this.getBlock(people.add(0, 2, 0)).getBlock() == Blocks.AIR) {
            if (this.getBlock(people.add(1, 2, 0)).getBlock() != Blocks.AIR || this.getBlock(people.add(0, 2, 1)).getBlock() != Blocks.AIR || this.getBlock(people.add(-1, 2, 0)).getBlock() != Blocks.AIR || this.getBlock(people.add(0, 2, -1)).getBlock() != Blocks.AIR) {
                this.switchToSlot(webSlot);
                BlockUtil.placeBlock(people.add(0, 2, 0), EnumHand.MAIN_HAND, false, true, false);
                this.switchToSlot(old);
            } else if (this.getBlock(people.add(1, 1, 0)).getBlock() != Blocks.AIR) {
                this.switchToSlot(obbySlot);
                BlockUtil.placeBlock(people.add(1, 2, 0), EnumHand.MAIN_HAND, false, true, false);
                this.switchToSlot(old);
            } else if (this.getBlock(people.add(-1, 1, 0)).getBlock() != Blocks.AIR) {
                this.switchToSlot(obbySlot);
                BlockUtil.placeBlock(people.add(-1, 2, 0), EnumHand.MAIN_HAND, false, true, false);
                this.switchToSlot(old);
            } else if (this.getBlock(people.add(0, 1, 1)).getBlock() != Blocks.AIR) {
                this.switchToSlot(obbySlot);
                BlockUtil.placeBlock(people.add(0, 2, 1), EnumHand.MAIN_HAND, false, true, false);
                this.switchToSlot(old);
            } else if (this.getBlock(people.add(0, 1, -1)).getBlock() != Blocks.AIR) {
                this.switchToSlot(obbySlot);
                BlockUtil.placeBlock(people.add(0, 2, -1), EnumHand.MAIN_HAND, false, true, false);
                this.switchToSlot(old);
            }
        }
    }

    private EntityPlayer getTarget(double range) {
        EntityPlayer target = null;
        double distance = range;
        for (EntityPlayer player : TrapHead.mc.world.playerEntities) {
            if (EntityUtil.isntValid((Entity)player, range) || MeowKu.friendManager.isFriend(player.getName()) || TrapHead.mc.player.posY - player.posY >= 5.0) continue;
            if (target == null) {
                target = player;
                distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
                continue;
            }
            if (EntityUtil.mc.player.getDistanceSq((Entity)player) >= distance) continue;
            target = player;
            distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
        }
        return target;
    }

    private void switchToSlot(int slot) {
        TrapHead.mc.player.inventory.currentItem = slot;
        TrapHead.mc.playerController.updateController();
    }

    private IBlockState getBlock(BlockPos block) {
        return TrapHead.mc.world.getBlockState(block);
    }
}

