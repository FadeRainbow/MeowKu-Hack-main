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

import dev.lovelyneru.MeowKu.features.setting.Setting;
import dev.lovelyneru.MeowKu.util.BlockUtil;
import dev.lovelyneru.MeowKu.util.InventoryUtil;
import dev.lovelyneru.MeowKu.features.modules.Module;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;


public class AntiHoleKick
        extends Module {
    private final Setting<Boolean> rotate = this.register(new Setting<Boolean>("Rotate", false));

    private final Setting<Boolean> head = this.register(new Setting<Boolean>("TrapHead", false));
    private int obsidian = -1;

    public AntiHoleKick() {
        super("AntiHoleKick", "AntiHoleKick", Module.Category.COMBAT, true, false, false);
    }

    @Override
    public void onUpdate() {
        this.obsidian = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
        if (this.obsidian == -1) {
            return;
        }
        BlockPos pos = new BlockPos(AntiHoleKick.mc.player.posX, AntiHoleKick.mc.player.posY, AntiHoleKick.mc.player.posZ);
        if (pos == null) {
            return;
        }
        if (this.getBlock(pos.add(0, 1, 1)).getBlock() == Blocks.PISTON) {
            if (this.getBlock(pos.add(0, 1, -1)).getBlock() == Blocks.AIR) {
                this.perform(pos.add(0, 1, -1));
            }
            mc.playerController.onPlayerDamageBlock(pos.add(0, 1, 1), BlockUtil.getRayTraceFacing(pos.add(0, 1, 1)));
            if (this.getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.AIR) {
                if (this.head.getValue().booleanValue()) {
                    this.perform(pos.add(0, 2, -1));
                    this.perform(pos.add(0, 2, 0));
                }
            }
        }
        if (this.getBlock(pos.add(0, 1, -1)).getBlock() == Blocks.PISTON) {
            if (this.getBlock(pos.add(0, 1, 1)).getBlock() == Blocks.AIR) {
                this.perform(pos.add(0, 1, 1));
            }
            mc.playerController.onPlayerDamageBlock(pos.add(0, 1, -1), BlockUtil.getRayTraceFacing(pos.add(0, 1, 1)));
            if (this.getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.AIR) {
                if (this.head.getValue().booleanValue()) {
                    this.perform(pos.add(0, 2, 1));
                    this.perform(pos.add(0, 2, 0));
                }
            }
        }
        if (this.getBlock(pos.add(1, 1, 0)).getBlock() == Blocks.PISTON) {
            if (this.getBlock(pos.add(-1, 1, 0)).getBlock() == Blocks.AIR) {
                this.perform(pos.add(-1, 1, 0));
            }
            mc.playerController.onPlayerDamageBlock(pos.add(1, 1, 0), BlockUtil.getRayTraceFacing(pos.add(0, 1, 1)));
            if (this.getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.AIR) {
                if (this.head.getValue().booleanValue()) {
                    this.perform(pos.add(-1, 2, 0));
                    this.perform(pos.add(0, 2, 0));
                }
            }
        }
        if (this.getBlock(pos.add(-1, 1, 0)).getBlock() == Blocks.PISTON) {
            if (this.getBlock(pos.add(1, 1, 0)).getBlock() == Blocks.AIR) {
                this.perform(pos.add(1, 1, 0));
            }
            mc.playerController.onPlayerDamageBlock(pos.add(-1, 1, 0), BlockUtil.getRayTraceFacing(pos.add(0, 1, 1)));
            if (this.getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.AIR) {
                if (this.head.getValue().booleanValue()) {
                    this.perform(pos.add(1, 2, 0));
                    this.perform(pos.add(0, 2, 0));
                }
            }
        }
    }
    private void switchToSlot(int slot) {
        AntiHoleKick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        AntiHoleKick.mc.player.inventory.currentItem = slot;
        AntiHoleKick.mc.playerController.updateController();
    }


    private IBlockState getBlock(BlockPos block) {
        return AntiHoleKick.mc.world.getBlockState(block);
    }

    private void perform(BlockPos pos) {
        int old = AntiHoleKick.mc.player.inventory.currentItem;
        this.switchToSlot(this.obsidian);
        BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, this.rotate.getValue(), true, false);
        this.switchToSlot(old);
    }
}

