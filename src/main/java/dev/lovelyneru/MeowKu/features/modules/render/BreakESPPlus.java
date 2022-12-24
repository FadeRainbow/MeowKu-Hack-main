package dev.lovelyneru.MeowKu.features.modules.render;



import dev.lovelyneru.MeowKu.event.EntityDamageBlockEvent;
import dev.lovelyneru.MeowKu.event.events.RenderEvent;
import dev.lovelyneru.MeowKu.features.gui.CFontRenderer;
import dev.lovelyneru.MeowKu.features.gui.font.CFont;
import dev.lovelyneru.MeowKu.features.gui.font.CustomFont;
import dev.lovelyneru.MeowKu.features.modules.Module;
import dev.lovelyneru.MeowKu.features.setting.Setting;

import dev.lovelyneru.MeowKu.util.MelonTessellator;
import dev.lovelyneru.MeowKu.util.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;




public class BreakESPPlus extends Module {
//    public Setting<Integer> red = (Setting<Integer>)("Red", 255, 0, 255);
//
//    public Setting<Integer> green = (Setting<Integer>)isetting("Green", 255, 0, 255);
//
//    public Setting<Integer> blue = (Setting<Integer>)isetting("Blue", 0, 0, 255);
//
//    public Setting<Integer> alpha = (Setting<Integer>)isetting("Alpha", 120, 1, 255);
//
//    public Setting<Float> lineWidth = (Setting<Float>)fsetting("LineWidth", 3.0F, 0.0F, 3.0F);
private Setting<Integer> red = register(new Setting("Red", 255, 0, 255));
    private Setting<Integer> green = register(new Setting("Green", 255, 0, 255));
    private Setting<Integer> blue = register(new Setting("Blue", 255, 0, 255));
    private Setting<Integer> alpha = register(new Setting("alpha", 120, 1, 255));
    private Setting<Integer> LineWidth = register(new Setting("LineWidth", 3.0F, 0.0F, 3.0F));
    private final Setting name = this.register(new Setting("Name", true));


    private Map<BlockPos, BlockStatus> blocks = new HashMap<>();

    private Map<net.minecraft.entity.Entity, BlockPos> entityLocking = new HashMap<net.minecraft.entity.Entity, BlockPos>();

    public BreakESPPlus() {
        super("BreakESP+","code by trdyun",Category.RENDER,true,false,false);
    }

    @SubscribeEvent
    public void onBreaking(EntityDamageBlockEvent event) {
        if (this.blocks.containsKey(event.getPos())) {
            ((BlockStatus)this.blocks.get(event.getPos())).update(event.getStage());
        } else {
            this.blocks.put(event.getPos(), new BlockStatus(event.getStage(), event.getPos(), event.getEntity()));
        }

        this.entityLocking.put(event.getEntity(), event.getPos());
    }

    public void onWorldRender(RenderEvent event) {
        this.entityLocking.forEach((entity, blockPos) -> {
            if (((BlockStatus)this.blocks.get(blockPos)).isDanger()) {
                AxisAlignedBB aabb = new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), (blockPos.getX() + 1), (blockPos.getY() + 1), (blockPos.getZ() + 1));
                MelonTessellator.drawBBBox(aabb, new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue()), ((Integer)this.alpha.getValue()).intValue(), 3.0F, true);
                GlStateManager.pushMatrix();
                MelonTessellator.glBillboardDistanceScaled((float)(aabb.getCenter()).x, (float)(aabb.getCenter()).y, (float)(aabb.getCenter()).z, (EntityPlayer)mc.player, 1.0F);
                GlStateManager.disableDepth();
              //  GlStateManager.translate(-(CustomFont.gayStringWidth(entity.getName()) / 2.0D), 0.0D, 0.0D);
               // CustomFont.gaydrawStringWithShadow(entity.getName(), 0.0D, 0.0D, (new Color(255, 255, 255)).getRGB());
                GlStateManager.popMatrix();
            }
            if (((Boolean)this.name.getValue()).booleanValue()) {

            }
        });
    }

    public void onEnable() {
        this.blocks = new HashMap<>();
    }

    class BlockStatus {
        public int s;

        private int s2 = 0;

        public BlockPos pos;

        public Entity creator;

        public BlockStatus(int s1, BlockPos pos, Entity creator) {
            this.s = this.s;
            this.pos = pos;
            this.creator = creator;
        }



        public BlockStatus update(int s) {
            this.s2 = this.s;
            this.s = s;
            return this;
        }

        public boolean isDanger() {
            return (this.s == 255 && this.s2 == 255);
        }
    }
}
