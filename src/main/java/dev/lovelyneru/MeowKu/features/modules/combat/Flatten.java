/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  net.minecraft.block.BlockEnderChest
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
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class Flatten
        extends Module {
    public EntityPlayer target;
    private final Setting<Float> range = this.register(new Setting<Float>("Range", Float.valueOf(5.0f), Float.valueOf(1.0f), Float.valueOf(6.0f)));
    private final Setting<Boolean> autoDisable = this.register(new Setting<Boolean>("AutoDisable", true));
    private final Setting<Boolean> chestplace = this.register(new Setting<Boolean>("Y Chest Place", true));
    private final Setting<Boolean> xzchestplace = this.register(new Setting<Boolean>("X|Z Chest Place", false));
    private final Setting<Boolean> negative = this.register(new Setting<Boolean>("-X|-Z Chest Place", false));

    public Flatten() {
        super("Flatten", "Automatic feetobsidian", Module.Category.COMBAT, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (Flatten.fullNullCheck()) {
            return;
        }
        this.target = this.getTarget(this.range.getValue().floatValue());
        if (this.target == null) {
            return;
        }
        BlockPos people = new BlockPos(this.target.posX, this.target.posY, this.target.posZ);
        int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        int chestSlot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
        if (obbySlot == -1) {
            return;
        }
        int old = Flatten.mc.player.inventory.currentItem;
        if (this.getBlock(people.add(0, -1, 0)).getBlock() == Blocks.AIR && this.getBlock(people.add(0, -2, 0)).getBlock() != Blocks.AIR) {
            if (this.chestplace.getValue().booleanValue() && InventoryUtil.findHotbarBlock(BlockEnderChest.class) != -1) {
                this.switchToSlot(chestSlot);
            } else {
                this.switchToSlot(obbySlot);
            }
            BlockUtil.placeBlock(people.add(0, -1, 0), EnumHand.MAIN_HAND, false, true, false);
            this.switchToSlot(old);
        }
        if (this.getBlock(people.add(0, -1, 0)).getBlock() != Blocks.AIR && this.getBlock(people.add(1, -1, 0)).getBlock() == Blocks.AIR && this.getBlock(people.add(1, 0, 0)).getBlock() == Blocks.AIR) {
            if (this.negative.getValue().booleanValue() && InventoryUtil.findHotbarBlock(BlockEnderChest.class) != -1) {
                this.switchToSlot(chestSlot);
            } else {
                this.switchToSlot(obbySlot);
            }
            BlockUtil.placeBlock(people.add(1, -1, 0), EnumHand.MAIN_HAND, false, true, false);
            this.switchToSlot(old);
        } else if (this.getBlock(people.add(0, -1, 0)).getBlock() != Blocks.AIR && this.getBlock(people.add(-1, -1, 0)).getBlock() == Blocks.AIR && this.getBlock(people.add(-1, 0, 0)).getBlock() == Blocks.AIR) {
            if (this.xzchestplace.getValue().booleanValue() && InventoryUtil.findHotbarBlock(BlockEnderChest.class) != -1) {
                this.switchToSlot(chestSlot);
            } else {
                this.switchToSlot(obbySlot);
            }
            BlockUtil.placeBlock(people.add(-1, -1, 0), EnumHand.MAIN_HAND, false, true, false);
            this.switchToSlot(old);
        } else if (this.getBlock(people.add(0, -1, 0)).getBlock() != Blocks.AIR && this.getBlock(people.add(0, -1, 1)).getBlock() == Blocks.AIR && this.getBlock(people.add(0, 0, 1)).getBlock() == Blocks.AIR) {
            if (this.negative.getValue().booleanValue() && InventoryUtil.findHotbarBlock(BlockEnderChest.class) != -1) {
                this.switchToSlot(chestSlot);
            } else {
                this.switchToSlot(obbySlot);
            }
            BlockUtil.placeBlock(people.add(0, -1, 1), EnumHand.MAIN_HAND, false, true, false);
            this.switchToSlot(old);
        } else if (this.getBlock(people.add(0, -1, 0)).getBlock() != Blocks.AIR && this.getBlock(people.add(0, -1, -1)).getBlock() == Blocks.AIR && this.getBlock(people.add(0, 0, -1)).getBlock() == Blocks.AIR) {
            if (this.xzchestplace.getValue().booleanValue() && InventoryUtil.findHotbarBlock(BlockEnderChest.class) != -1) {
                this.switchToSlot(chestSlot);
            } else {
                this.switchToSlot(obbySlot);
            }
            BlockUtil.placeBlock(people.add(0, -1, -1), EnumHand.MAIN_HAND, false, true, false);
            this.switchToSlot(old);
        }
        if (this.autoDisable.getValue().booleanValue()) {
            this.disable();
        }
    }

    private EntityPlayer getTarget(double range) {
        EntityPlayer target = null;
        double distance = range;
        for (EntityPlayer player : Flatten.mc.world.playerEntities) {
            if (EntityUtil.isntValid((Entity)player, range) || MeowKu.friendManager.isFriend(player.getName()) || Flatten.mc.player.posY - player.posY >= 5.0) continue;
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
        Flatten.mc.player.inventory.currentItem = slot;
        Flatten.mc.playerController.updateController();
    }

    private IBlockState getBlock(BlockPos block) {
        return Flatten.mc.world.getBlockState(block);
    }
}

