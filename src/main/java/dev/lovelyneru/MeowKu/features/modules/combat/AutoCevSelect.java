package dev.lovelyneru.MeowKu.features.modules.combat;

import dev.lovelyneru.MeowKu.MeowKu;
import dev.lovelyneru.MeowKu.features.setting.Setting;
import dev.lovelyneru.MeowKu.util.InventoryUtil;
import dev.lovelyneru.MeowKu.features.modules.Module;
import dev.lovelyneru.MeowKu.util.BlockUtil;
import dev.lovelyneru.MeowKu.util.EntityUtil;
import dev.lovelyneru.MeowKu.util.Timer;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.List;

public class AutoCevSelect extends Module {
    public static EntityPlayer target;
    private static AutoCevSelect INSTANCE = new AutoCevSelect();
    private final Setting<Float> range = this.register(new Setting<>("Range", 5.0f, 1.0f, 8.0f));
    private final Setting<Boolean> toggle = this.register(new Setting<>("AutoToggle", false));
    private final List godBlocks = Arrays.asList(Blocks.OBSIDIAN, Blocks.BEDROCK);
    private final Timer timer = new Timer();

    public AutoCevSelect() {
        super("AutoCevSelect", "All aspects AutoCity is good", Category.COMBAT, true, false, false);
        setInstance();
    }

    public static AutoCevSelect Instance() {
        if (INSTANCE == null)
            INSTANCE = new AutoCevSelect();
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    public void onTick() {
        if (fullNullCheck())
            return;
        target = getTarget(this.range.getValue());
        surroundMine();
        if (this.toggle.getValue())
            this.disable();
    }


    public String getDisplayInfo() {
        if (target != null) {
            return target.getName();
        }
        return null;
    }

    private void surroundMine() {
        if (target == null)
            return;
        int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        if (obbySlot == -1) {
            return;
        }
        int old = Flatten.mc.player.inventory.currentItem;
        BlockPos feet = new BlockPos(target.posX, target.posY, target.posZ);
        if (!detection(target)) {
            if (getBlock(feet.add(1, 2, 0)).getBlock() == Blocks.AIR && getBlock(feet.add(1, 3, 0)).getBlock() == Blocks.AIR && getBlock(feet.add(1, 1, 0)).getBlock() != Blocks.BEDROCK) {
                this.switchToSlot(obbySlot);
                BlockUtil.placeBlock(feet.add(1, 1, 0), EnumHand.MAIN_HAND, false, true, false);
                this.switchToSlot(old);
            } else {
                if (getBlock(feet.add(-1, 2, 0)).getBlock() == Blocks.AIR && getBlock(feet.add(-1, 3, 0)).getBlock() == Blocks.AIR && getBlock(feet.add(-1, 1, 0)).getBlock() != Blocks.BEDROCK) {
                    this.switchToSlot(obbySlot);
                    BlockUtil.placeBlock(feet.add(-1, 1, 0), EnumHand.MAIN_HAND, false, true, false);
                    this.switchToSlot(old);
                } else {
                    if (getBlock(feet.add(0, 2, 1)).getBlock() == Blocks.AIR && getBlock(feet.add(0, 3, 1)).getBlock() == Blocks.AIR && getBlock(feet.add(0, 1, 1)).getBlock() != Blocks.BEDROCK) {
                        this.switchToSlot(obbySlot);
                        BlockUtil.placeBlock(feet.add(0, 1, 1), EnumHand.MAIN_HAND, false, true, false);
                        this.switchToSlot(old);
                    } else {
                        if (getBlock(feet.add(0, 2, -1)).getBlock() == Blocks.AIR && getBlock(feet.add(0, 3, -1)).getBlock() == Blocks.AIR && getBlock(feet.add(0, 1, -1)).getBlock() != Blocks.BEDROCK) {
                            this.switchToSlot(obbySlot);
                            BlockUtil.placeBlock(feet.add(0, 1, -1), EnumHand.MAIN_HAND, false, true, false);
                            this.switchToSlot(old);
                        }
                    }
                }
            }
        }

        if (!detection(target)) {
            if (getBlock(feet.add(1, 2, 0)).getBlock() == Blocks.AIR && getBlock(feet.add(1, 3, 0)).getBlock() == Blocks.AIR && getBlock(feet.add(1, 1, 0)).getBlock() != Blocks.BEDROCK)
                surroundMine(feet.add(1, 1, 0));
            else if (getBlock(feet.add(-1, 2, 0)).getBlock() == Blocks.AIR && getBlock(feet.add(1, 3, 0)).getBlock() == Blocks.AIR && getBlock(feet.add(1, 1, 0)).getBlock() != Blocks.BEDROCK)
                surroundMine(feet.add(-1, 1, 0));
            else if (getBlock(feet.add(0, 2, 1)).getBlock() == Blocks.AIR && getBlock(feet.add(0, 3, 1)).getBlock() == Blocks.AIR && getBlock(feet.add(0, 1, 1)).getBlock() != Blocks.BEDROCK)
                surroundMine(feet.add(0, 1, 1));
            else if (getBlock(feet.add(0, 2, -1)).getBlock() == Blocks.AIR && getBlock(feet.add(0, 3, -1)).getBlock() == Blocks.AIR && getBlock(feet.add(0, 1, -1)).getBlock() != Blocks.BEDROCK)
                surroundMine(feet.add(0, 1, -1));
            else target = null;
        }

    }


    private void surroundMine(BlockPos position) {

        if (InstantMine.breakPos != null) {
            if (InstantMine.breakPos.equals(position))
                return;
            if (InstantMine.breakPos.equals(new BlockPos(target.posX, target.posY, target.posZ)) && mc.world.getBlockState(new BlockPos(target.posX, target.posY, target.posZ)).getBlock() != Blocks.AIR)
                return;
            if (InstantMine.breakPos.equals(new BlockPos(mc.player.posX, mc.player.posY + 2, mc.player.posZ)))
                return;
            if (MeowKu.moduleManager.isModuleEnabled("AutoCev"))
                return;
            if (InstantMine.breakPos.equals(new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ)))
                return;
            if (mc.player.rotationPitch <= 90 && mc.player.rotationPitch >= 80)
                return;
            if (mc.world.getBlockState(InstantMine.breakPos).getBlock() == Blocks.WEB)
                return;
        }
        mc.playerController.onPlayerDamageBlock(position, BlockUtil.getRayTraceFacing(position));
    }

    private boolean detection(EntityPlayer player) {
        return mc.world.getBlockState(new BlockPos(player.posX + 1.2, player.posY, player.posZ)).getBlock() == Blocks.AIR || mc.world.getBlockState(new BlockPos(player.posX - 1.2, player.posY, player.posZ)).getBlock() == Blocks.AIR ||
                mc.world.getBlockState(new BlockPos(player.posX, player.posY, player.posZ + 1.2)).getBlock() == Blocks.AIR || mc.world.getBlockState(new BlockPos(player.posX, player.posY, player.posZ - 1.2)).getBlock() == Blocks.AIR;
    }

    private void switchToSlot(int slot) {
        Flatten.mc.player.inventory.currentItem = slot;
        Flatten.mc.playerController.updateController();
    }

    private boolean detection2(EntityPlayer player) {
        return (mc.world.getBlockState(new BlockPos(player.posX + 1.2, player.posY, player.posZ)).getBlock() == Blocks.AIR && mc.world.getBlockState(new BlockPos(player.posX + 1.2, player.posY + 1, player.posZ)).getBlock() == Blocks.AIR) || (mc.world.getBlockState(new BlockPos(player.posX - 1.2, player.posY, player.posZ)).getBlock() == Blocks.AIR && mc.world.getBlockState(new BlockPos(player.posX - 1.2, player.posY + 1, player.posZ)).getBlock() == Blocks.AIR) ||
                (mc.world.getBlockState(new BlockPos(player.posX, player.posY, player.posZ + 1.2)).getBlock() == Blocks.AIR && mc.world.getBlockState(new BlockPos(player.posX, player.posY + 1, player.posZ + 1.2)).getBlock() == Blocks.AIR) || (mc.world.getBlockState(new BlockPos(player.posX, player.posY, player.posZ - 1.2)).getBlock() == Blocks.AIR && mc.world.getBlockState(new BlockPos(player.posX, player.posY + 1, player.posZ - 1.2)).getBlock() == Blocks.AIR);
    }


    private EntityPlayer getTarget(double range) {
        EntityPlayer target = null;
        double distance = range;
        for (EntityPlayer player : mc.world.playerEntities) {
            if (EntityUtil.isntValid(player, range))
                continue;
            if (detection2(player))
                continue;
            if (target == null) {
                target = player;
                distance = EntityUtil.mc.player.getDistanceSq(player);
                continue;
            }
            if (EntityUtil.mc.player.getDistanceSq(player) >= distance)
                continue;
            target = player;
            distance = EntityUtil.mc.player.getDistanceSq(player);
        }
        return target;
    }

    private IBlockState getBlock(BlockPos block) {
        return mc.world.getBlockState(block);
    }

}


