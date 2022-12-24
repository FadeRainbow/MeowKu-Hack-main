/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
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
import dev.lovelyneru.MeowKu.util.InventoryUtil;
import dev.lovelyneru.MeowKu.features.modules.Module;
import dev.lovelyneru.MeowKu.util.BlockUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.Comparator;
import java.util.stream.Collectors;

public class TrapSelf
        extends Module {
    private final Setting<Boolean> rotate = this.register(new Setting<Boolean>("Rotate", false));
    private final Setting<Boolean> cev = this.register(new Setting<Boolean>("AntiCev", false));
    private final Setting<Boolean> civ = this.register(new Setting<Boolean>("AntiCev", false));
    private final Setting<Boolean> cev2 = this.register(new Setting<Boolean>("AntiCev2", false));
    private final Setting<Boolean> head = this.register(new Setting<Boolean>("TrapHead", false));

    private final Setting<Boolean> headb = this.register(new Setting<Boolean>("HeadButton", false));
    private int obsidian = -1;

    public TrapSelf() {
        super("TrapSelf", "One Self Trap", Module.Category.COMBAT, true, false, false);
    }

    @Override
    public void onEnable() {
        Surround.breakcrystal();
    }

    @Override
    public void onTick() {
        if (TrapSelf.fullNullCheck()) {
            return;
        }
        this.obsidian = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
        if (this.obsidian == -1) {
            return;
        }
        BlockPos pos = new BlockPos(TrapSelf.mc.player.posX, TrapSelf.mc.player.posY, TrapSelf.mc.player.posZ);
        if (this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR) {
            this.place(pos.add(1, 0, 0));
        }
        if (this.getBlock(pos.add(1, 1, 0)).getBlock() == Blocks.AIR) {
            this.place(pos.add(1, 1, 0));
        }
        if (this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR) {
            this.place(pos.add(-1, 0, 0));
        }
        if (this.getBlock(pos.add(-1, 1, 0)).getBlock() == Blocks.AIR) {
            this.place(pos.add(-1, 1, 0));
        }
        if (this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR) {
            this.place(pos.add(0, 0, 1));
        }
        if (this.getBlock(pos.add(0, 1, 1)).getBlock() == Blocks.AIR) {
            this.place(pos.add(0, 1, 1));
        }
        if (this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR) {
            this.place(pos.add(0, 0, -1));
        }
        if (this.getBlock(pos.add(0, 1, -1)).getBlock() == Blocks.AIR) {
            this.place(pos.add(0, 1, -1));
        }
        if (this.cev.getValue().booleanValue()) {
            if (this.getBlock(pos.add(0, 2, -1)).getBlock() == Blocks.AIR) {
                this.place(pos.add(0, 2, -1));
            }
            if (this.getBlock(pos.add(0, 3, -1)).getBlock() == Blocks.AIR) {
                this.place(pos.add(0, 3, -1));
            }
            if (this.getBlock(pos.add(0, 3, 0)).getBlock() == Blocks.AIR) {
                this.place(pos.add(0, 3, 0));
            }
        }
        if (this.cev2.getValue().booleanValue()) {
            if (this.getBlock(pos.add(0, 3, -1)).getBlock() == Blocks.AIR) {
                this.place(pos.add(0, 3, -1));
            }
            if (this.getBlock(pos.add(0, 4, -1)).getBlock() == Blocks.AIR) {
                this.place(pos.add(0, 4, -1));
            }
            if (this.getBlock(pos.add(0, 4, 0)).getBlock() == Blocks.AIR) {
                this.place(pos.add(0, 4, 0));
            }
        }
        if (this.head.getValue().booleanValue()) {
            if (this.headb.getValue().booleanValue()) {
                this.obsidian = InventoryUtil.findHotbarBlock(Blocks.WOODEN_BUTTON);
                if (this.obsidian == -1) {
                    this.obsidian = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
                }
            }
            if (this.getBlock(pos.add(0, 2, -1)).getBlock() == Blocks.AIR) {
                this.place(pos.add(0, 2, -1));
            }
            if (this.getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.AIR) {
                this.place(pos.add(0, 2, 0));
            }
            if (this.headb.getValue().booleanValue()) {
                this.obsidian = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
                if (this.obsidian == -1) {
                    return;
                }
            }
        }
        if (this.civ.getValue().booleanValue()) {
            if (this.getBlock(pos.add(0, 2, -1)).getBlock() == Blocks.AIR) {
                this.place(pos.add(0, 2, -1));
            }
            if (this.getBlock(pos.add(0, 2, 1)).getBlock() == Blocks.AIR) {
                this.place(pos.add(0, 2, 1));
            }
            if (this.getBlock(pos.add(1, 2, 0)).getBlock() == Blocks.AIR) {
                this.place(pos.add(1, 2, 0));
            }
            if (this.getBlock(pos.add(-1, 2, 0)).getBlock() == Blocks.AIR) {
                this.place(pos.add(-1, 2, 0));
            }
        }
    }

    private void switchToSlot(int slot) {
        TrapSelf.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        TrapSelf.mc.player.inventory.currentItem = slot;
        TrapSelf.mc.playerController.updateController();
    }

    private void place(BlockPos pos) {
        int old = TrapSelf.mc.player.inventory.currentItem;
        this.switchToSlot(this.obsidian);
        BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, this.rotate.getValue(), true, false);
        this.switchToSlot(old);
    }

    private IBlockState getBlock(BlockPos block) {
        return TrapSelf.mc.world.getBlockState(block);
    }

    public static void breakcrystal() {
        for (Entity crystal : Surround.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal && !e.isDead).sorted(Comparator.comparing(e -> Float.valueOf(Surround.mc.player.getDistance(e)))).collect(Collectors.toList())) {
            if (!(crystal instanceof EntityEnderCrystal) || !(Surround.mc.player.getDistance(crystal) <= 4.0f)) continue;
            Surround.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
            Surround.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.OFF_HAND));
        }
    }

    private boolean isBurrowed(EntityPlayer entityPlayer) {
        BlockPos blockPos = new BlockPos(Math.floor(entityPlayer.posX), Math.floor(entityPlayer.posY + 0.2), Math.floor(entityPlayer.posZ));
        return TrapSelf.mc.world.getBlockState(blockPos).getBlock() == Blocks.ENDER_CHEST || TrapSelf.mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN || TrapSelf.mc.world.getBlockState(blockPos).getBlock() == Blocks.CHEST;
    }
}

