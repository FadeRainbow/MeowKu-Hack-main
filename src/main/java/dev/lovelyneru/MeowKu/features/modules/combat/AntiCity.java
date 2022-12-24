/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketHeldItemChange
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
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class AntiCity
        extends Module {
    private final Setting<Boolean> rotate = this.register(new Setting<Boolean>("Rotate", false));
    private final Setting<Boolean> toggle = this.register(new Setting<Boolean>("Toggle", false));
    private final Setting<Double> range = this.register(new Setting<Double>("Range", 5.0, 1.0, 10.0));
    private int obsidian = -1;

    public AntiCity() {
        super("AntiHoleMine", "AntiCity", Module.Category.COMBAT, true, false, false);
    }

    public static boolean isHard(Block block) {
        return block == Blocks.BEDROCK;
    }

    @Override
    public void onEnable() {
        Surround.breakcrystal();
    }

    @Override
    public void onUpdate() {
        if (AntiCity.mc.player == null || AntiCity.mc.world == null) {
            return;
        }
        if (!MeowKu.moduleManager.isModuleEnabled("Surround")) {
            return;
        }
        this.obsidian = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
        if (this.obsidian == -1) {
            return;
        }
        BlockPos pos = new BlockPos(AntiCity.mc.player.posX, AntiCity.mc.player.posY, AntiCity.mc.player.posZ);
        if (pos == null) {
            return;
        }
        if (this.getTarget(this.range.getValue()) == null) {
            return;
        }
        if (!AntiCity.isHard(this.getBlock(pos.add(1, 0, 0)).getBlock())) {
            if (this.getBlock(pos.add(2, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(2, 0, 0));
            }
            if (this.getBlock(pos.add(1, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(1, 0, 1));
            }
            if (this.getBlock(pos.add(1, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(1, 0, -1));
            }
            if (this.getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.OBSIDIAN && this.getBlock(pos.add(2, 1, 0)).getBlock() == Blocks.AIR) {
                this.perform(pos.add(1, 1, 0));
                this.perform(pos.add(2, 1, 0));
            }
        }
        if (!AntiCity.isHard(this.getBlock(pos.add(-1, 0, 0)).getBlock())) {
            if (this.getBlock(pos.add(-2, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(-2, 0, 0));
            }
            if (this.getBlock(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(-1, 0, 1));
            }
            if (this.getBlock(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(-1, 0, -1));
            }
            if (this.getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.OBSIDIAN && this.getBlock(pos.add(-2, 1, 0)).getBlock() == Blocks.AIR) {
                this.perform(pos.add(-1, 1, 0));
                this.perform(pos.add(-2, 1, 0));
            }
        }
        if (!AntiCity.isHard(this.getBlock(pos.add(0, 0, 1)).getBlock())) {
            if (this.getBlock(pos.add(0, 0, 2)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(0, 0, 2));
            }
            if (this.getBlock(pos.add(1, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(1, 0, 1));
            }
            if (this.getBlock(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(-1, 0, 1));
            }
            if (this.getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.OBSIDIAN && this.getBlock(pos.add(0, 1, 2)).getBlock() == Blocks.AIR) {
                this.perform(pos.add(0, 1, 1));
                this.perform(pos.add(0, 1, 2));
            }
        }
        if (!AntiCity.isHard(this.getBlock(pos.add(0, 0, -1)).getBlock())) {
            if (this.getBlock(pos.add(0, 0, -2)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(0, 0, -2));
            }
            if (this.getBlock(pos.add(1, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(1, 0, -1));
            }
            if (this.getBlock(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(-1, 0, -1));
            }
            if (this.getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.OBSIDIAN && this.getBlock(pos.add(0, 1, -2)).getBlock() == Blocks.AIR) {
                this.perform(pos.add(0, 1, -1));
                this.perform(pos.add(0, 1, -2));
            }
        }
        if (this.toggle.getValue().booleanValue()) {
            this.toggle();
        }
    }

    private void switchToSlot(int slot) {
        AntiCity.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        AntiCity.mc.player.inventory.currentItem = slot;
        AntiCity.mc.playerController.updateController();
    }

    private IBlockState getBlock(BlockPos block) {
        return AntiCity.mc.world.getBlockState(block);
    }

    private void perform(BlockPos pos) {
        int old = AntiCity.mc.player.inventory.currentItem;
        this.switchToSlot(this.obsidian);
        BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, this.rotate.getValue(), true, false);
        this.switchToSlot(old);
    }

    private EntityPlayer getTarget(double range) {
        EntityPlayer target = null;
        double distance = Math.pow(range, 2.0) + 1.0;
        for (EntityPlayer player : AutoTrap.mc.world.playerEntities) {
            if (EntityUtil.isntValid((Entity)player, range) || MeowKu.speedManager.getPlayerSpeed(player) > 10.0) continue;
            if (target == null) {
                target = player;
                distance = AutoTrap.mc.player.getDistanceSq((Entity)player);
                continue;
            }
            if (AutoTrap.mc.player.getDistanceSq((Entity)player) >= distance) continue;
            target = player;
            distance = AutoTrap.mc.player.getDistanceSq((Entity)player);
        }
        return target;
    }
}

