package dev.lovelyneru.MeowKu.features.modules.render;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import dev.lovelyneru.MeowKu.features.modules.Module;
import dev.lovelyneru.MeowKu.features.setting.Setting;
import dev.lovelyneru.MeowKu.util.EntityUtil;
import dev.lovelyneru.MeowKu.util.RenderUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import static dev.lovelyneru.MeowKu.MeowKu.friendManager;


public class BurrowESP extends Module {
   private final Setting boxRed = this.register(new Setting("BoxRed", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
   private final Setting outlineGreen = this.register(new Setting("OutlineGreen", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), (v) -> {
      return ((Boolean)this.cOutline.getValue()).booleanValue();
   }));
   private final Setting boxGreen = this.register(new Setting("BoxGreen", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
   private final Setting box = new Setting("Box", true);
   private final Setting cOutline = this.register(new Setting("CustomOutline", false, (v) -> {
      return ((Boolean)this.outline.getValue()).booleanValue();
   }));
   private final Setting outlineBlue = this.register(new Setting("OutlineBlue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), (v) -> {
      return ((Boolean)this.cOutline.getValue()).booleanValue();
   }));
   private final Setting name = this.register(new Setting("Name", false));
   private final Setting boxAlpha = this.register(new Setting("BoxAlpha", Integer.valueOf(125), Integer.valueOf(0), Integer.valueOf(255)));
   private final Setting outlineWidth = this.register(new Setting("OutlineWidth", 1.0F, 0.0F, 5.0F, (v) -> {
      return ((Boolean)this.outline.getValue()).booleanValue();
   }));
   private final Setting outlineRed = this.register(new Setting("OutlineRed", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), (v) -> {
      return ((Boolean)this.cOutline.getValue()).booleanValue();
   }));
   private final Setting outline = this.register(new Setting("Outline", true));
   private final Setting boxBlue = this.register(new Setting("BoxBlue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
   private final Map burrowedPlayers = new HashMap();
   private final Setting outlineAlpha = this.register(new Setting("OutlineAlpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), (v) -> {
      return ((Boolean)this.cOutline.getValue()).booleanValue();
   }));

   public BurrowESP() {
      super("BurrowESP", "Show burrow players .", Module.Category.RENDER, true, false, false);
   }

   private void getPlayers() {
      for(EntityPlayer entityPlayer : mc.world.playerEntities) {
         if (entityPlayer != mc.player && !friendManager.isFriend(entityPlayer.getName()) && EntityUtil.isLiving(entityPlayer) && this.isBurrowed(entityPlayer)) {
            this.burrowedPlayers.put(entityPlayer, new BlockPos(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ));
         }
      }

   }

   public void onEnable() {
      this.burrowedPlayers.clear();
   }

   private void lambda$onRender3D$8(Entry entry) {
      this.renderBurrowedBlock((BlockPos)entry.getValue());
      if (((Boolean)this.name.getValue()).booleanValue()) {
         RenderUtil.drawText((BlockPos)entry.getValue(), ((EntityPlayer)entry.getKey()).getGameProfile().getName());
      }

   }

   private boolean isBurrowed(EntityPlayer entityPlayer) {
      BlockPos blockPos = new BlockPos(Math.floor(entityPlayer.posX), Math.floor(entityPlayer.posY + 0.2D), Math.floor(entityPlayer.posZ));
      return mc.world.getBlockState(blockPos).getBlock() == Blocks.ENDER_CHEST || mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(blockPos).getBlock() == Blocks.CHEST;
   }

   public void onUpdate() {
      if (!fullNullCheck()) {
         this.burrowedPlayers.clear();
         this.getPlayers();
      }
   }

   private void renderBurrowedBlock(BlockPos blockPos) {
      RenderUtil.drawBoxESP(blockPos, new Color(((Integer)this.boxRed.getValue()).intValue(), ((Integer)this.boxGreen.getValue()).intValue(), ((Integer)this.boxBlue.getValue()).intValue(), ((Integer)this.boxAlpha.getValue()).intValue()), true, new Color(((Integer)this.outlineRed.getValue()).intValue(), ((Integer)this.outlineGreen.getValue()).intValue(), ((Integer)this.outlineBlue.getValue()).intValue(), ((Integer)this.outlineAlpha.getValue()).intValue()), ((Float)this.outlineWidth.getValue()).floatValue(), ((Boolean)this.outline.getValue()).booleanValue(), ((Boolean)this.box.getValue()).booleanValue(), ((Integer)this.boxAlpha.getValue()).intValue(), true);
   }


      }



