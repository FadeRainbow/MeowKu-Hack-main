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

public class SurroundRender2
        extends Module {
    public EntityPlayer target;
    private final Setting<Integer> range = this.register(new Setting<Integer>("Range", 30, 1, 30));

    public SurroundRender2() {
        super("Anti Y CityESP", "CityESP", Module.Category.RENDER, true, false, false);
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        if (SurroundRender2.fullNullCheck()) {
            return;
        }
        this.target = this.getTarget(this.range.getValue().intValue());
        this.SurroundRender2();
    }


    private void SurroundRender2() {
        if (this.target == null) return;
        Vec3d a = this.target.getPositionVector();
        if (SurroundRender2.mc.world.getBlockState(new BlockPos(a)).getBlock() == Blocks.OBSIDIAN || SurroundRender2.mc.world.getBlockState(new BlockPos(a)).getBlock() == Blocks.ENDER_CHEST) {
            RenderUtil.drawBoxESP(new BlockPos(a), new Color(255, 255, 0), false, new Color(255, 255, 0), 2.0f, false, true, 42, true);
        }
        if (EntityUtil.getSurroundWeakness(a, -1, 1)) {
            this.SurroundRender2(a, -2.0, 1.0, 0.0, true);
        }
        if (EntityUtil.getSurroundWeakness(a, -1, 2)) {
            this.SurroundRender2(a, 2.0, 1.0, 0.0, true);
        }
        if (EntityUtil.getSurroundWeakness(a, -1, 3)) {
            this.SurroundRender2(a, 0.0, 1.0, -2.0, true);
        }
        if (EntityUtil.getSurroundWeakness(a, -1, 4)) {
            this.SurroundRender2(a, 0.0, 1.0, 2.0, true);
        }
        if (EntityUtil.getSurroundWeakness(a, -1, 5)) {
            this.SurroundRender2(a, -2.0, 1.0, 0.0, false);
        }
        if (EntityUtil.getSurroundWeakness(a, -1, 6)) {
            this.SurroundRender2(a, 2.0, 1.0, 0.0, false);
        }
        if (EntityUtil.getSurroundWeakness(a, -1, 7)) {
            this.SurroundRender2(a, 0.0, 1.0, -2.0, false);
        }
        if (!EntityUtil.getSurroundWeakness(a, -1, 8)) return;
        this.SurroundRender2(a, 0.0, 1.0, 2.0, false);
    }

    private void SurroundRender2(Vec3d pos, double x, double y, double z, boolean red) {
        BlockPos position = new BlockPos(pos).add(x, y, z);
        if (SurroundRender2.mc.world.getBlockState(position).getBlock() == Blocks.AIR) return;
        if (SurroundRender2.mc.world.getBlockState(position).getBlock() == Blocks.FIRE) {
            return;
        }
        if (red) {
            RenderUtil.drawBoxESP(position, new Color(255, 0, 0), false, new Color(255, 0, 0), 2.0f, false, true, 42, true);
            return;
        }
        RenderUtil.drawBoxESP(position, new Color(0, 0, 255), false, new Color(0, 0, 255), 2.0f, false, true, 42, true);
    }

    private EntityPlayer getTarget(double range) {
        EntityPlayer target = null;
        double distance = range;
        Iterator iterator = SurroundRender2.mc.world.playerEntities.iterator();
        while (iterator.hasNext()) {
            EntityPlayer player = (EntityPlayer)iterator.next();
            if (EntityUtil.isntValid((Entity)player, range) || !EntityUtil.isInHole((Entity)player)) continue;
            if (target == null) {
                target = player;
                distance = SurroundRender2.mc.player.getDistanceSq((Entity)player);
                continue;
            }
            if (!(SurroundRender2.mc.player.getDistanceSq((Entity)player) < distance)) continue;
            target = player;
            distance = SurroundRender2.mc.player.getDistanceSq((Entity)player);
        }
        return target;
    }
}

