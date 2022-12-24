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
import net.minecraft.block.BlockWeb;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class AutoFeet
        extends Module {
    public EntityPlayer target;
    private final Setting<Float> range = this.register(new Setting<Float>("Range", Float.valueOf(5.0f), Float.valueOf(1.0f), Float.valueOf(6.0f)));

    private final Setting<Boolean> feet2 = this.register(new Setting<Boolean>("Feet+", false));

    private final Setting<Boolean> WEB = this.register(new Setting<Boolean>("WEB", false));

    public AutoFeet() {
        super("AutoFeet", "Automatic feetobsidian", Module.Category.COMBAT, true, false, false);

    }

    @Override
    public void onUpdate() {
        if (AutoFeet.fullNullCheck()) {
            return;
        }
        this.target = this.getTarget(this.range.getValue().floatValue());
        if (this.target == null) {
            return;
        }
        int obbySlot = InventoryUtil.findItemInHotbar(Items.REDSTONE);
        if (obbySlot == -1) {
            return;
        }
        BlockPos people = new BlockPos(this.target.posX, this.target.posY, this.target.posZ);
        if (this.WEB.getValue().booleanValue()) {
            obbySlot = InventoryUtil.findHotbarBlock(BlockWeb.class);
            if (obbySlot == -1) {
                return;
            }
        }
        int old = AutoFeet.mc.player.inventory.currentItem;
        if (this.getBlock(people.add(0, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(people.add(0, -1, 0)).getBlock() != Blocks.AIR) {
            this.switchToSlot(obbySlot);
            BlockUtil.placeBlock(people.add(0, 0, 0), EnumHand.MAIN_HAND, false, true, false);
            this.switchToSlot(old);
        }
        if (!this.WEB.getValue().booleanValue()) {
            if (this.feet2.getValue().booleanValue()) {
                if (this.getBlock(people.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(people.add(1, -1, 0)).getBlock() != Blocks.AIR) {
                    this.switchToSlot(obbySlot);
                    BlockUtil.placeBlock(people.add(1, 0, 0), EnumHand.MAIN_HAND, false, true, false);
                    this.switchToSlot(old);
                }
                if (this.getBlock(people.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(people.add(-1, -1, 0)).getBlock() != Blocks.AIR) {
                    this.switchToSlot(obbySlot);
                    BlockUtil.placeBlock(people.add(-1, 0, 0), EnumHand.MAIN_HAND, false, true, false);
                    this.switchToSlot(old);
                }

                if (this.getBlock(people.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(people.add(0, -1, 1)).getBlock() != Blocks.AIR) {
                    this.switchToSlot(obbySlot);
                    BlockUtil.placeBlock(people.add(0, 0, 1), EnumHand.MAIN_HAND, false, true, false);
                    this.switchToSlot(old);
                }
                if (this.getBlock(people.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(people.add(0, -1, -1)).getBlock() != Blocks.AIR) {
                    this.switchToSlot(obbySlot);
                    BlockUtil.placeBlock(people.add(0, 0, -1), EnumHand.MAIN_HAND, false, true, false);
                    this.switchToSlot(old);
                }
            }
        }
    }

    private EntityPlayer getTarget(double range) {
        EntityPlayer target = null;
        double distance = range;
        for (EntityPlayer player : AutoFeet.mc.world.playerEntities) {
            if (EntityUtil.isntValid((Entity)player, range) || MeowKu.friendManager.isFriend(player.getName()) || AutoFeet.mc.player.posY - player.posY >= 5.0) continue;
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
        AutoFeet.mc.player.inventory.currentItem = slot;
        AutoFeet.mc.playerController.updateController();
    }

    private IBlockState getBlock(BlockPos block) {
        return AutoFeet.mc.world.getBlockState(block);
    }
}

