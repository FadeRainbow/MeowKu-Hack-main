package dev.lovelyneru.MeowKu.features.modules.render;


import dev.lovelyneru.MeowKu.event.events.Render3DEvent;
import dev.lovelyneru.MeowKu.features.modules.Module;
import dev.lovelyneru.MeowKu.features.setting.Setting;
import dev.lovelyneru.MeowKu.util.EntityUtil;
import dev.lovelyneru.MeowKu.util.RenderUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.Iterator;

public class AntiCityESP
        extends Module {
    public EntityPlayer target;
    private final Setting<Integer> range = this.register(new Setting<Integer>("Range", 30, 1, 30));

    public AntiCityESP() {
        super("AntiCityESP", "AntiCityESP", Module.Category.RENDER, true, false, false);
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        if (AntiCityESP.fullNullCheck()) {
            return;
        }
        this.target = this.getTarget(this.range.getValue().intValue());
        this.surroundRender();
    }


    private void surroundRender() {
        if (this.target == null) return;
        Vec3d a = this.target.getPositionVector();
        if (AntiCityESP.mc.world.getBlockState(new BlockPos(a)).getBlock() == Blocks.OBSIDIAN || AntiCityESP.mc.world.getBlockState(new BlockPos(a)).getBlock() == Blocks.ENDER_CHEST) {
            RenderUtil.drawBoxESP(new BlockPos(a), new Color(255, 255, 0), false, new Color(255, 255, 0), 2.0f, false, true, 42, true);
        }
        if (EntityUtil.getSurroundWeakness(a, -1, 1)) {
            this.surroundRender(a, -2.0, 0.0, 0.0, true);
        }
        if (EntityUtil.getSurroundWeakness(a, -1, 2)) {
            this.surroundRender(a, 2.0, 0.0, 0.0, true);
        }
        if (EntityUtil.getSurroundWeakness(a, -1, 3)) {
            this.surroundRender(a, 0.0, 0.0, -2.0, true);
        }
        if (EntityUtil.getSurroundWeakness(a, -1, 4)) {
            this.surroundRender(a, 0.0, 0.0, 2.0, true);
        }
        if (EntityUtil.getSurroundWeakness(a, -1, 5)) {
            this.surroundRender(a, -2.0, 0.0, 0.0, false);
        }
        if (EntityUtil.getSurroundWeakness(a, -1, 6)) {
            this.surroundRender(a, 2.0, 0.0, 0.0, false);
        }
        if (EntityUtil.getSurroundWeakness(a, -1, 7)) {
            this.surroundRender(a, 0.0, 0.0, -2.0, false);
        }
        if (!EntityUtil.getSurroundWeakness(a, -1, 8)) return;
        this.surroundRender(a, 0.0, 0.0, 2.0, false);
    }

    private void surroundRender(Vec3d pos, double x, double y, double z, boolean red) {
        BlockPos position = new BlockPos(pos).add(x, y, z);
        if (AntiCityESP.mc.world.getBlockState(position).getBlock() == Blocks.AIR) return;
        if (AntiCityESP.mc.world.getBlockState(position).getBlock() == Blocks.BEDROCK)
            return;
            if (AntiCityESP.mc.world.getBlockState(position).getBlock() == Blocks.FIRE) {


            }
            if (red) {
                RenderUtil.drawBoxESP(position, new Color(255, 0, 0), false, new Color(255, 0, 0), 2.0f, false, true, 42, true);
                return;
            }
            RenderUtil.drawBoxESP(position, new Color(0, 0, 255), false, new Color(0, 0, 255), 2.0f, false, true, 42, true);
        }

        private EntityPlayer getTarget ( double range){
            EntityPlayer target = null;
            double distance = range;
            Iterator iterator = AntiCityESP.mc.world.playerEntities.iterator();
            while (iterator.hasNext()) {
                EntityPlayer player = (EntityPlayer) iterator.next();
                if (EntityUtil.isntValid((Entity) player, range) || !EntityUtil.isInHole((Entity) player)) continue;
                if (target == null) {
                    target = player;
                    distance = AntiCityESP.mc.player.getDistanceSq((Entity) player);
                    continue;
                }
                if (!(AntiCityESP.mc.player.getDistanceSq((Entity) player) < distance)) continue;
                target = player;
                distance = AntiCityESP.mc.player.getDistanceSq((Entity) player);
            }
            return target;
        }
    }

