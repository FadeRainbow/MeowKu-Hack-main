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
import dev.lovelyneru.MeowKu.util.*;
import dev.lovelyneru.MeowKu.features.modules.Module;

import net.minecraft.block.BlockObsidian;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;


public class SmartSurround
        extends Module {
    private final Setting<Boolean> rotate = this.register(new Setting<Boolean>("Rotate", false));
    public Setting<Boolean> packet = this.register(new Setting<Boolean>("Packet", true));
    private int obsidian = -1;

    private final Timer timer = new Timer();

    private final Timer retryTimer = new Timer();

    private float yaw = 0.0f;
    private float pitch = 0.0f;
    private boolean rotating = false;
    private boolean isSneaking;
    private final Setting<Boolean> center = this.register(new Setting<Boolean>("TPCenter", true));
    private BlockPos startPos;

    public SmartSurround() {
        super("SmartSurround", "SmartSurround", Module.Category.COMBAT, true, false, false);
    }

    @Override
    public void onEnable() {
        this.startPos = EntityUtil.getRoundedBlockPos((Entity)Surround.mc.player);
        if (this.center.getValue().booleanValue()) {
            MeowKu.positionManager.setPositionPacket((double)this.startPos.getX() + 0.5, this.startPos.getY(), (double)this.startPos.getZ() + 0.5, true, true, true);
        }
    }

    @Override
    public void onTick() {
        if (!this.startPos.equals((Object)EntityUtil.getRoundedBlockPos((Entity)Surround.mc.player))) {
            this.toggle();
        }
    }

    @Override
    public void onUpdate() {
        Vec3d a = AntiCev.mc.player.getPositionVector();
        this.obsidian = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
        if (this.obsidian == -1) {
            return;
        }
        BlockPos pos = new BlockPos(SmartSurround.mc.player.posX, SmartSurround.mc.player.posY, SmartSurround.mc.player.posZ);
        if (pos == null) {
            return;
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 1, 1)) != null) {
            EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 1, 1)), true);
            this.place(a, EntityUtil.getVarOffsets(0, 1, 1));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 1, -1)) != null) {
            EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 1, -1)), true);
            this.place(a, EntityUtil.getVarOffsets(0, 1, -1));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(1, 1, 0)) != null) {
            EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(1, 1, 0)), true);
            this.place(a, EntityUtil.getVarOffsets(1, 1, 0));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 1, 0)) != null) {
            EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 1, 0)), true);
            this.place(a, EntityUtil.getVarOffsets(-1, 1, 0));
        }

        if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 1)) != null) {
            EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 1)), true);
            this.place(a, EntityUtil.getVarOffsets(0, 0, 1));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 1, -1)) != null) {
            EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -1)), true);
            this.place(a, EntityUtil.getVarOffsets(0, 0, -1));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 0)) != null) {
            EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 0)), true);
            this.place(a, EntityUtil.getVarOffsets(1, 0, 0));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 0)) != null) {
            EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 0)), true);
            this.place(a, EntityUtil.getVarOffsets(-1, 0, 0));
        }

        if (this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(2, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(2, 1, 0)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(2, 1, 0)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(2, 1, 0)), true);
            }
            this.perform(pos.add(2, 1, 0));
        }
        if (this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-2, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-2, 1, 0)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(-2, 1, 0)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(-2, 1, 0)), true);
            }
            this.perform(pos.add(-2, 1, 0));
        }
        if (this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, 2)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 1, 2)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 1, 2)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 1, 2)), true);
            }
            this.perform(pos.add(0, 1, 2));
        }
        if (this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, -2)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 1, -2)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 1, -2)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 1, -2)), true);
            }
            this.perform(pos.add(0, 1, -2));
        }

        if (this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, -1, 1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 1)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 1)), true);
            }
            this.perform(pos.add(0, -1, 1));
            this.perform(pos.add(0, 0, 1));
        }
        if (this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, -1, -1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -1)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -1)), true);
            }
            this.perform(pos.add(0, -1, -1));
            this.perform(pos.add(0, 0, -1));
        }
        if (this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, -1, 0)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 0)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 0)), true);
            }
            this.perform(pos.add(1, -1, 0));
            this.perform(pos.add(1, 0, 0));
        }
        if (this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, -1, 0)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 0)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 0)), true);
            }
            this.perform(pos.add(-1, -1, 0));
            this.perform(pos.add(-1, 0, 0));
        }

        if (this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, 2)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 2)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 2)), true);
            }
            this.perform(pos.add(0, 0, 1));
            this.perform(pos.add(0, 0, 2));
            this.perform(pos.add(0, -1, 2));
        }
        if (this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, -2)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -2)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -2)), true);
            }
            this.perform(pos.add(0, 0, -1));
            this.perform(pos.add(0, 0, -2));
            this.perform(pos.add(0, -1, -2));
        }
        if (this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(2, 0, 0)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(2, 0, 0)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(2, 0, 0)), true);
            }
            this.perform(pos.add(1, 0, 0));
            this.perform(pos.add(2, 0, 0));
            this.perform(pos.add(2, -1, 0));
        }
        if (this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-2, 0, 0)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(-2, 0, 0)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(-2, 0, 0)), true);
            }
            this.perform(pos.add(-1, 0, 0));
            this.perform(pos.add(-2, 0, 0));
            this.perform(pos.add(-2, -1, 0));
        }

        if (this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 1, 1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(0, 0, 1));
            this.perform(pos.add(0, 1, 1));
            this.perform(pos.add(0, 1, 2));
        }
        if (this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 1, -1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(0, 0, -1));
            this.perform(pos.add(0, 1, -1));
            this.perform(pos.add(0, 1, -2));
        }
        if (this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 1, 0)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(1, 0, 0));
            this.perform(pos.add(1, 1, 0));
            this.perform(pos.add(2, 1, 0));
        }
        if (this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 1, 0)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(-1, 0, 0));
            this.perform(pos.add(-1, 1, 0));
            this.perform(pos.add(-2, 1, 0));
        }

        if (this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, 1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 1)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 1)), true);
            }
            this.perform(pos.add(1, 0, 0));
            this.perform(pos.add(1, 0, 1));
        }
        if (this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, 1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 1)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 1)), true);
            }
            this.perform(pos.add(0, 0, 1));
            this.perform(pos.add(1, 0, 1));
        }

        if (this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, -1)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, -1)), true);
            }
            this.perform(pos.add(-1, 0, 0));
            this.perform(pos.add(-1, 0, -1));
        }
        if (this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, -1)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, -1)), true);
            }
            this.perform(pos.add(0, 0, -1));
            this.perform(pos.add(-1, 0, -1));
        }

        if (this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 1)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 1)), true);
            }
            this.perform(pos.add(-1, 0, 0));
            this.perform(pos.add(-1, 0, 1));
        }
        if (this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 1)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 1)), true);
            }
            this.perform(pos.add(0, 0, 1));
            this.perform(pos.add(-1, 0, 1));
        }

        if (this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, -1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, -1)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, -1)), true);
            }
            this.perform(pos.add(1, 0, 0));
            this.perform(pos.add(1, 0, -1));
        }
        if (this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, -1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, -1)) != null) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, -1)), true);
            }
            this.perform(pos.add(0, 0, -1));
            this.perform(pos.add(1, 0, -1));
        }


        if (this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 1, 1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(1, 0, 0));
            this.perform(pos.add(1, 0, 1));
            this.perform(pos.add(1, 1, 1));
        }
        if (this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 1, 1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(0, 0, 1));
            this.perform(pos.add(1, 0, 1));
            this.perform(pos.add(1, 1, 1));
        }

        if (this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 1, -1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(-1, 0, 0));
            this.perform(pos.add(-1, 0, -1));
            this.perform(pos.add(-1, 1, -1));
        }
        if (this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 1, -1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(0, 0, -1));
            this.perform(pos.add(-1, 0, -1));
            this.perform(pos.add(-1, 1, -1));
        }

        if (this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 1, 1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(-1, 0, 0));
            this.perform(pos.add(-1, 0, 1));
            this.perform(pos.add(-1, 1, 1));
        }
        if (this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 1, 1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(0, 0, 1));
            this.perform(pos.add(-1, 0, 1));
            this.perform(pos.add(-1, 1, 1));
        }

        if (this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 1, -1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(1, 0, 0));
            this.perform(pos.add(1, 0, -1));
            this.perform(pos.add(1, 1, -1));
        }
        if (this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 1, -1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(0, 0, -1));
            this.perform(pos.add(1, 0, -1));
            this.perform(pos.add(1, 1, -1));
        }


    }
    private void switchToSlot(int slot) {
        SmartSurround.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        SmartSurround.mc.player.inventory.currentItem = slot;
        SmartSurround.mc.playerController.updateController();
    }

    private void place(Vec3d pos, Vec3d[] list) {
        Vec3d[] var3 = list;
        int var4 = list.length;
        for (int var5 = 0; var5 < var4; ++var5) {
            Vec3d vec3d = var3[var5];
            BlockPos position = new BlockPos(pos).add(vec3d.x, vec3d.y, vec3d.z);
            int a = AntiCev.mc.player.inventory.currentItem;
            AntiCev.mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(BlockObsidian.class);
            AntiCev.mc.playerController.updateController();
            this.isSneaking = BlockUtil.placeBlock(position, EnumHand.MAIN_HAND, false, this.packet.getValue(), true);
            AntiCev.mc.player.inventory.currentItem = a;
            AntiCev.mc.playerController.updateController();
        }
    }

    Entity checkCrystal(Vec3d pos, Vec3d[] list) {
        Entity crystal = null;
        Vec3d[] var4 = list;
        int var5 = list.length;
        for (int var6 = 0; var6 < var5; ++var6) {
            Vec3d vec3d = var4[var6];
            BlockPos position = new BlockPos(pos).add(vec3d.x, vec3d.y, vec3d.z);
            for (Entity entity : AntiCev.mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(position))) {
                if (!(entity instanceof EntityEnderCrystal) || crystal != null) continue;
                crystal = entity;
            }
        }
        return crystal;
    }

    private IBlockState getBlock(BlockPos block) {
        return SmartSurround.mc.world.getBlockState(block);
    }

    private void perform(BlockPos pos) {
        int old = SmartSurround.mc.player.inventory.currentItem;
        this.switchToSlot(this.obsidian);
        BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, this.rotate.getValue(), true, false);
        this.switchToSlot(old);
    }
}

