/*
 * Decompiled with CFR 0.151.
 *
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev.lovelyneru.MeowKu.features.modules.combat;

import dev.lovelyneru.MeowKu.features.setting.Bind;
import dev.lovelyneru.MeowKu.features.setting.Setting;
import dev.lovelyneru.MeowKu.util.*;
import dev.lovelyneru.MeowKu.event.PlayerDamageBlockEvent;
import dev.lovelyneru.MeowKu.event.events.PacketEvent;
import dev.lovelyneru.MeowKu.event.events.Render3DEvent;
import dev.lovelyneru.MeowKu.features.modules.Module;

import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class InstantMine extends Module {
    private final Timer breakSuccess = new Timer();
    private static InstantMine INSTANCE = new InstantMine();
    private Setting<Boolean> creativeMode = this.register(new Setting<Boolean>("CreativeMode", true));
    private Setting<Boolean> ghostHand = this.register(new Setting<Boolean>("GhostHand", Boolean.valueOf(true), v -> this.creativeMode.getValue()));
    private Setting<Boolean> render = this.register(new Setting<Boolean>("Render", true));
    private Setting<Boolean>DoubleBreak=this.register(new Setting<Boolean>("DoubleBreak", true)) ;
    private final Setting<Boolean> crystal = this.register(new Setting<Boolean>("Crystal", Boolean.FALSE));
    public final Setting<Boolean> attackcrystal = this.register(new Setting<Object>("Attack Crystal", Boolean.FALSE, v -> this.crystal.getValue()));
    public final Setting<Bind> bind = this.register(new Setting<Object>("ObsidianBind", new Bind(-1), v -> this.crystal.getValue()));
    private final List<Block> godBlocks = Arrays.asList(Blocks.AIR, Blocks.FLOWING_LAVA, Blocks.LAVA, Blocks.FLOWING_WATER, Blocks.WATER, Blocks.BEDROCK);
    private boolean cancelStart = false;
    private boolean empty = false;
    private EnumFacing facing;
    public static BlockPos breakPos;

    public InstantMine() {
        super("InstantMine", "InstantMine", Category.COMBAT, true, false, false);
        this.setInstance();
    }

    public static InstantMine getInstance() {
        if (INSTANCE != null) return INSTANCE;
        INSTANCE = new InstantMine();
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        if (InstantMine.fullNullCheck()) {
            return;
        }
        if (this.creativeMode.getValue() == false) return;
        if (!this.cancelStart) return;
        if (this.crystal.getValue().booleanValue() && this.attackcrystal.getValue().booleanValue() && InstantMine.mc.world.getBlockState(breakPos).getBlock() == Blocks.AIR) {
            InstantMine.attackcrystal();
        }
        if (this.bind.getValue().isDown() && this.crystal.getValue().booleanValue() && InventoryUtil.findHotbarBlock(BlockObsidian.class) != -1 && InstantMine.mc.world.getBlockState(breakPos).getBlock() == Blocks.AIR) {
            int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
            int old = InstantMine.mc.player.inventory.currentItem;
            this.switchToSlot(obbySlot);
            BlockUtil.placeBlock(breakPos, EnumHand.MAIN_HAND, false, true, false);
            this.switchToSlot(old);
        }
        if (InventoryUtil.getItemHotbar(Items.END_CRYSTAL) != -1 && this.crystal.getValue().booleanValue() && InstantMine.mc.world.getBlockState(breakPos).getBlock() == Blocks.OBSIDIAN && !this.check() && !breakPos.equals((Object) AntiBurrow.pos)) {
            BlockUtil.placeCrystalOnBlock(breakPos, EnumHand.MAIN_HAND, true, false, true);
        }
        if (this.godBlocks.contains(InstantMine.mc.world.getBlockState(this.breakPos).getBlock())) return;
        if (InstantMine.mc.world.getBlockState(this.breakPos).getBlock() != Blocks.WEB) {
        if (this.ghostHand.getValue().booleanValue() && (InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE) != -1) && InventoryUtil.getItemHotbars(Items.DIAMOND_PICKAXE) != -1) {
            int slotMain = InstantMine.mc.player.inventory.currentItem;
            if (InstantMine.mc.world.getBlockState(this.breakPos).getBlock() == Blocks.OBSIDIAN) {
                if (!this.breakSuccess.passedMs(1234L)) return;
                InstantMine.mc.player.inventory.currentItem = InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE);
                InstantMine.mc.playerController.updateController();
                InstantMine.mc.player.connection.sendPacket((Packet) new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.breakPos, this.facing));
                InstantMine.mc.player.inventory.currentItem = slotMain;
                InstantMine.mc.playerController.updateController();
                return;
            }
            InstantMine.mc.player.inventory.currentItem = InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE);
            InstantMine.mc.playerController.updateController();
            InstantMine.mc.player.connection.sendPacket((Packet) new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.breakPos, this.facing));
            InstantMine.mc.player.inventory.currentItem = slotMain;
            InstantMine.mc.playerController.updateController();
            return;
        }
    } else {
            if (this.DoubleBreak.getValue().booleanValue()||this.ghostHand.getValue().booleanValue() && (InventoryUtil.getItemHotbar(Items.DIAMOND_SWORD) != -1) && InventoryUtil.getItemHotbars(Items.DIAMOND_SWORD) != -1) {
                int slotMain = InstantMine.mc.player.inventory.currentItem;
                InstantMine.mc.player.inventory.currentItem = InventoryUtil.getItemHotbar(Items.DIAMOND_SWORD);
                InstantMine.mc.playerController.updateController();
                InstantMine.mc.player.connection.sendPacket((Packet) new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.breakPos, this.facing));
                InstantMine.mc.player.inventory.currentItem = slotMain;
                InstantMine.mc.playerController.updateController();
                return;
            }
        if (InstantMine.breakPos!=AntiBurrow.pos){
            AntiBurrow.nullCheck();
        }
        }
        InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.breakPos, this.facing));
    }



    double manxi=0;

    private void switchToSlot(int slot) {
        AutoFeet.mc.player.inventory.currentItem = slot;
        AutoFeet.mc.playerController.updateController();
    }
    @Override
    public void onRender3D(Render3DEvent event) {
        if (InstantMine.fullNullCheck()) {
            return;
        }
        if (this.render.getValue().booleanValue() && this.creativeMode.getValue().booleanValue() && this.cancelStart) {
            if (this.godBlocks.contains(InstantMine.mc.world.getBlockState(this.breakPos).getBlock())) {
                this.empty = true;
            }


            if (imerS.passedMs(11)){
                if(manxi<=10){
                    manxi=manxi+0.11;
                }
                imerS.reset();
            }
            AxisAlignedBB axisAlignedBB = mc.world.getBlockState(breakPos).getSelectedBoundingBox(mc.world, breakPos);
//            GSColor fillColor = new GSColor(new GSColor(this.empty ? 0 : 255, this.empty ? 255 : 0, 0, 255), 50);
//            GSColor outlineColor = new GSColor(new GSColor(this.empty ? 0 : 255, this.empty ? 255 : 0, 0, 255), 255);
            double centerX = axisAlignedBB.minX + ((axisAlignedBB.maxX - axisAlignedBB.minX) / 2);
            double centerY = axisAlignedBB.minY + ((axisAlignedBB.maxY - axisAlignedBB.minY) / 2);
            double centerZ = axisAlignedBB.minZ + ((axisAlignedBB.maxZ - axisAlignedBB.minZ) / 2);
            double progressValX = manxi * ((axisAlignedBB.maxX - centerX) / 10);
            double progressValY = manxi * ((axisAlignedBB.maxY - centerY) / 10);
            double progressValZ = manxi * ((axisAlignedBB.maxZ - centerZ) / 10);
            AxisAlignedBB axisAlignedBB1 = new AxisAlignedBB(centerX - progressValX, centerY - progressValY, centerZ - progressValZ, centerX + progressValX, centerY + progressValY, centerZ + progressValZ);

            RenderUtil.drawBBBox(axisAlignedBB1,new Colour(this.empty ? 0 : 255, this.empty ? 255 : 0, 44, 148),new Colour(this.empty ? 0 : 255, this.empty ? 255 : 0, 0, 148).getAlpha());
            return;
        }
        if (!this.cancelStart) return;
        if (this.render.getValue() == false) return;
        RenderUtil.drawBoxESP(this.breakPos, new Color (236, 235, 235), false, new Color(248, 248, 248, 148), 1.0f, true, true, 84, false);
    }

    public boolean check() {
        return breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX, InstantMine.mc.player.posY + 2.0, InstantMine.mc.player.posZ)) || breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX, InstantMine.mc.player.posY + 3.0, InstantMine.mc.player.posZ)) || breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX, InstantMine.mc.player.posY - 1.0, InstantMine.mc.player.posZ)) || breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX + 1.0, InstantMine.mc.player.posY, InstantMine.mc.player.posZ)) || breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX - 1.0, InstantMine.mc.player.posY, InstantMine.mc.player.posZ)) || breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX, InstantMine.mc.player.posY, InstantMine.mc.player.posZ + 1.0)) || breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX, InstantMine.mc.player.posY, InstantMine.mc.player.posZ - 1.0)) || breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX + 1.0, InstantMine.mc.player.posY + 1.0, InstantMine.mc.player.posZ)) || breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX - 1.0, InstantMine.mc.player.posY + 1.0, InstantMine.mc.player.posZ)) || breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX, InstantMine.mc.player.posY + 1.0, InstantMine.mc.player.posZ + 1.0)) || breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX, InstantMine.mc.player.posY + 1.0, InstantMine.mc.player.posZ - 1.0));
    }

    public final Timer imerS = new Timer();
    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (InstantMine.fullNullCheck()) {
            return;
        }
        if (!(event.getPacket() instanceof CPacketPlayerDigging)) return;
        CPacketPlayerDigging packet = (CPacketPlayerDigging)event.getPacket();
        if (packet.getAction() != CPacketPlayerDigging.Action.START_DESTROY_BLOCK) return;
        event.setCanceled(this.cancelStart);
    }

    public static void attackcrystal() {
        for (Entity crystal : InstantMine.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal && !e.isDead).sorted(Comparator.comparing(e -> Float.valueOf(InstantMine.mc.player.getDistance(e)))).collect(Collectors.toList())) {
            if (!(crystal instanceof EntityEnderCrystal) || !(crystal.getDistanceSq(breakPos) <= 2.0)) continue;
            InstantMine.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
            InstantMine.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        }
    }

    long times = 0;

    @SubscribeEvent
    public void onBlockEvent(PlayerDamageBlockEvent event) {
        if (InstantMine.fullNullCheck()) {
            return;
        }
        if (!BlockUtil.canBreak(event.pos)) return;
        if(breakPos!=null){
            if(breakPos.getX()==event.pos.getX()&&breakPos.getY()==event.pos.getY()&&breakPos.getZ()==event.pos.getZ()){

                return;
            }
        }


        manxi = 0;
        this.empty = false;
        this.cancelStart = false;
        this.breakPos = event.pos;
        this.breakSuccess.reset();
        this.facing = event.facing;
        if (this.breakPos == null) return;
        InstantMine.mc.player.swingArm(EnumHand.MAIN_HAND);
        InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.breakPos, this.facing));
        this.cancelStart = true;
        InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.breakPos, this.facing));
        event.setCanceled(true);
    }

    @Override
    public String getDisplayInfo() {
        if (this.ghostHand.getValue() == false) return "Normal";
        return "Ghost";
    }
}

