// Decompiled with: Procyon 0.6.0
// Class Version: 8
package dev.lovelyneru.MeowKu.features.modules.combat;

import java.util.ArrayList;
import java.util.stream.Collectors;

import dev.lovelyneru.MeowKu.MeowKu;
import dev.lovelyneru.MeowKu.features.setting.Setting;
import dev.lovelyneru.MeowKu.util.*;

import net.minecraft.util.NonNullList;

import java.awt.Color;

import dev.lovelyneru.MeowKu.features.modules.client.ClickGui;
import dev.lovelyneru.MeowKu.event.events.Render3DEvent;

import java.util.List;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.Packet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import dev.lovelyneru.MeowKu.event.events.PacketEvent;
import net.minecraft.util.math.BlockPos;
import dev.lovelyneru.MeowKu.features.modules.Module;

public class HoleFill extends Module
{
    private static BlockPos PlayerPos;
    private static boolean togglePitch;
    private static boolean isSpoofingAngles;
    private static double yaw;
    private static double pitch;
    private static HoleFill INSTANCE;
    private final Setting<Double> range;
    private final Setting<Boolean> smart;
    private final Setting<Double> smartRange;
    private final Setting<Boolean> web;
    private final Setting<Boolean> announceUsage;
    private final Setting<Boolean> rainbow;
    private final Setting<Integer> red;
    private final Setting<Integer> green;
    private final Setting<Integer> blue;
    private final Setting<Integer> alpha;
    private final Setting<Integer> outlineAlpha;
    private final long systemTime;
    private final boolean switchCooldown;
    private final boolean isAttacking;
    double d;
    private BlockPos render;
    private Entity renderEnt;
    private EntityPlayer closestTarget;
    private boolean caOn;
    private int newSlot;

    public HoleFill() {
        super("HoleFill", "Fills holes around you.", Category.COMBAT, true, false, true);
        this.range = this.register(new Setting("Range", 4.5, 0.1, 6.0));
        this.smart = this.register(new Setting("Smart", true));
        this.smartRange = this.register(new Setting("Smart Range", 3.0, 0.1, 6.0));
        this.web = this.register(new Setting("WEB", false));
        this.announceUsage = this.register(new Setting("Announce Usage", false));
        this.rainbow = this.register(new Setting("Rainbow", false));
        this.red = this.register(new Setting("Red", 0, 0, 255, v -> !this.rainbow.getValue()));
        this.green = this.register(new Setting("Green", 255, 0, 255, v -> !this.rainbow.getValue()));
        this.blue = this.register(new Setting("Blue", 0, 0, 255, v -> !this.rainbow.getValue()));
        this.alpha = this.register(new Setting("Alpha", 0, 0, 255, v -> !this.rainbow.getValue()));
        this.outlineAlpha = this.register(new Setting("OL-Alpha", 0, 0, 255, v -> !this.rainbow.getValue()));
        this.systemTime = -1L;
        this.switchCooldown = false;
        this.isAttacking = false;
        this.setInstance();
        final Packet[] packet = { null };
    }

    public static HoleFill getInstance() {
        if (HoleFill.INSTANCE == null) {
            HoleFill.INSTANCE = new HoleFill();
        }
        return HoleFill.INSTANCE;
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(HoleFill.mc.player.posX), Math.floor(HoleFill.mc.player.posY), Math.floor(HoleFill.mc.player.posZ));
    }

    private static void setYawAndPitch(final float yaw1, final float pitch1) {
        HoleFill.yaw = yaw1;
        HoleFill.pitch = pitch1;
        HoleFill.isSpoofingAngles = true;
    }

    private static void resetRotation() {
        if (HoleFill.isSpoofingAngles) {
            HoleFill.yaw = HoleFill.mc.player.rotationYaw;
            HoleFill.pitch = HoleFill.mc.player.rotationPitch;
            HoleFill.isSpoofingAngles = false;
        }
    }

    private void setInstance() {
        HoleFill.INSTANCE = this;
    }

    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        final Packet packet = event.getPacket();
        if (packet instanceof CPacketPlayer && HoleFill.isSpoofingAngles) {
            ((CPacketPlayer)packet).yaw = (float)HoleFill.yaw;
            ((CPacketPlayer)packet).pitch = (float)HoleFill.pitch;
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onUpdate() {
        if (HoleFill.mc.world == null) {
            return;
        }
        if (this.smart.getValue()) {
            this.findClosestTarget();
        }
        final List<BlockPos> blocks = this.findCrystalBlocks();
        BlockPos q = null;
        final double dist = 0.0;
        final double prevDist = 0.0;
        int obsidianSlot;
        final int n = obsidianSlot = ((HoleFill.mc.player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(Blocks.OBSIDIAN)) ? HoleFill.mc.player.inventory.currentItem : -1);
        if (obsidianSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (HoleFill.mc.player.inventory.getStackInSlot(l).getItem() == Item.getItemFromBlock(Blocks.OBSIDIAN)) {
                    obsidianSlot = l;
                    break;
                }
            }
        }
        if (this.web.getValue().booleanValue()) {
            obsidianSlot = ((HoleFill.mc.player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(Blocks.WEB)) ? HoleFill.mc.player.inventory.currentItem : -1);
            if (obsidianSlot == -1) {
                for (int l = 0; l < 9; ++l) {
                    if (HoleFill.mc.player.inventory.getStackInSlot(l).getItem() == Item.getItemFromBlock(Blocks.WEB)) {
                        obsidianSlot = l;
                        break;
                    }
                }
            }
        }
        if (obsidianSlot == -1) {
            if (this.web.getValue().booleanValue()) {
                obsidianSlot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
                if (obsidianSlot == -1) {
                    return;
                }
            } else {
                return;
            }
        }
        for (final BlockPos blockPos : blocks) {
            if (!HoleFill.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(blockPos)).isEmpty()) {
                continue;
            }
            if (this.smart.getValue() && this.isInRange(blockPos)) {
                q = blockPos;
            }
            else {
                q = blockPos;
            }
        }
        this.render = q;
        if (q != null && HoleFill.mc.player.onGround) {
            final int oldSlot = HoleFill.mc.player.inventory.currentItem;
            if (HoleFill.mc.player.inventory.currentItem != obsidianSlot) {
                HoleFill.mc.player.inventory.currentItem = obsidianSlot;
            }
            this.lookAtPacket(q.getX() + 0.5, q.getY() - 0.5, q.getZ() + 0.5, (EntityPlayer)HoleFill.mc.player);
            BlockUtil.placeBlockScaffold(this.render);
            HoleFill.mc.player.swingArm(EnumHand.MAIN_HAND);
            HoleFill.mc.player.inventory.currentItem = oldSlot;
            resetRotation();
        }
    }

    @Override
    public void onRender3D(final Render3DEvent event) {
        if (this.render != null) {
            RenderUtil.drawBoxESP(this.render, ((boolean)this.rainbow.getValue()) ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()) : new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.outlineAlpha.getValue()), 3.5f, true, true, this.alpha.getValue());
        }
    }

    private double getDistanceToBlockPos(final BlockPos pos1, final BlockPos pos2) {
        final double x = pos1.getX() - pos2.getX();
        final double y = pos1.getY() - pos2.getY();
        final double z = pos1.getZ() - pos2.getZ();
        return Math.sqrt(x * x + y * y + z * z);
    }

    private void lookAtPacket(final double px, final double py, final double pz, final EntityPlayer me) {
        final double[] v = EntityUtil.calculateLookAt(px, py, pz, me);
        setYawAndPitch((float)v[0], (float)v[1]);
    }

    public boolean IsHole(final BlockPos blockPos) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 0, 0);
        final BlockPos boost3 = blockPos.add(0, 0, -1);
        final BlockPos boost4 = blockPos.add(1, 0, 0);
        final BlockPos boost5 = blockPos.add(-1, 0, 0);
        final BlockPos boost6 = blockPos.add(0, 0, 1);
        final BlockPos boost7 = blockPos.add(0, 2, 0);
        final BlockPos boost8 = blockPos.add(0.5, 0.5, 0.5);
        final BlockPos boost9 = blockPos.add(0, -1, 0);
        return HoleFill.mc.world.getBlockState(boost).getBlock() == Blocks.AIR && HoleFill.mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && HoleFill.mc.world.getBlockState(boost7).getBlock() == Blocks.AIR && (HoleFill.mc.world.getBlockState(boost3).getBlock() == Blocks.OBSIDIAN || HoleFill.mc.world.getBlockState(boost3).getBlock() == Blocks.BEDROCK) && (HoleFill.mc.world.getBlockState(boost4).getBlock() == Blocks.OBSIDIAN || HoleFill.mc.world.getBlockState(boost4).getBlock() == Blocks.BEDROCK) && (HoleFill.mc.world.getBlockState(boost5).getBlock() == Blocks.OBSIDIAN || HoleFill.mc.world.getBlockState(boost5).getBlock() == Blocks.BEDROCK) && (HoleFill.mc.world.getBlockState(boost6).getBlock() == Blocks.OBSIDIAN || HoleFill.mc.world.getBlockState(boost6).getBlock() == Blocks.BEDROCK) && HoleFill.mc.world.getBlockState(boost8).getBlock() == Blocks.AIR && (HoleFill.mc.world.getBlockState(boost9).getBlock() == Blocks.OBSIDIAN || HoleFill.mc.world.getBlockState(boost9).getBlock() == Blocks.BEDROCK);
    }

    public BlockPos getClosestTargetPos() {
        if (this.closestTarget != null) {
            return new BlockPos(Math.floor(this.closestTarget.posX), Math.floor(this.closestTarget.posY), Math.floor(this.closestTarget.posZ));
        }
        return null;
    }

    private void findClosestTarget() {
        final List<EntityPlayer> playerList = HoleFill.mc.world.playerEntities;
        this.closestTarget = null;
        for (final EntityPlayer target : playerList) {
            if (target != HoleFill.mc.player && !MeowKu.friendManager.isFriend(target.getName()) && EntityUtil.isLiving((Entity)target)) {
                if (target.getHealth() <= 0.0f) {
                    continue;
                }
                if (this.closestTarget == null) {
                    this.closestTarget = target;
                }
                else {
                    if (HoleFill.mc.player.getDistance((Entity)target) >= HoleFill.mc.player.getDistance((Entity)this.closestTarget)) {
                        continue;
                    }
                    this.closestTarget = target;
                }
            }
        }
    }

    private boolean isInRange(final BlockPos blockPos) {
        final NonNullList<BlockPos> positions = NonNullList.create();
        positions.addAll(this.getSphere(HoleFill.getPlayerPos(), this.range.getValue().floatValue(), this.range.getValue().intValue(), false, true, 0).stream().filter(this::IsHole).collect(Collectors.toList()));
        return positions.contains((Object)blockPos);
    }

    private List<BlockPos> findCrystalBlocks() {
        final NonNullList<BlockPos> positions = NonNullList.create();
        if (this.smart.getValue() && this.closestTarget != null) {
            positions.addAll(this.getSphere(this.getClosestTargetPos(), this.smartRange.getValue().floatValue(), this.range.getValue().intValue(), false, true, 0).stream().filter(this::IsHole).filter(this::isInRange).collect(Collectors.toList()));
        }
        else if (!this.smart.getValue()) {
            positions.addAll(this.getSphere(HoleFill.getPlayerPos(), this.range.getValue().floatValue(), this.range.getValue().intValue(), false, true, 0).stream().filter(this::IsHole).collect(Collectors.toList()));
        }
        return (List<BlockPos>)positions;
    }

    public List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                int y = sphere ? (cy - (int)r) : cy;
                while (true) {
                    final float f = (float)y;
                    final float f2 = sphere ? (cy + r) : ((float)(cy + h));
                    if (f >= f2) {
                        break;
                    }
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                }
            }
        }
        return circleblocks;
    }

    @Override
    public void onDisable() {
        this.closestTarget = null;
        this.render = null;
        resetRotation();
        super.onDisable();
    }

    static {
        HoleFill.INSTANCE = new HoleFill();
        HoleFill.togglePitch = false;
    }
}
 