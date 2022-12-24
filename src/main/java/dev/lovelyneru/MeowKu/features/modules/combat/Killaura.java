package dev.lovelyneru.MeowKu.features.modules.combat;


import dev.lovelyneru.MeowKu.event.events.UpdateWalkingPlayerEvent;
import dev.lovelyneru.MeowKu.features.modules.Module;
import dev.lovelyneru.MeowKu.features.setting.Setting;
import dev.lovelyneru.MeowKu.util.DamageUtil;
import dev.lovelyneru.MeowKu.util.EntityUtil;
import dev.lovelyneru.MeowKu.util.MathUtil;
import dev.lovelyneru.MeowKu.util.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static dev.lovelyneru.MeowKu.MeowKu.rotationManager;
import static dev.lovelyneru.MeowKu.MeowKu.serverManager;

public class Killaura extends Module {
  public static Entity target;
  
  private final Timer timer = new Timer();
  
  public Setting<Float> range = register(new Setting("Range", Float.valueOf(6.0F), Float.valueOf(0.1F), Float.valueOf(7.0F)));
  
  public Setting<Boolean> delay = register(new Setting("HitDelay", Boolean.valueOf(true)));
  
  public Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(true)));
  
  public Setting<Boolean> onlySharp = register(new Setting("SwordOnly", Boolean.valueOf(true)));
  
  public Setting<Float> raytrace = register(new Setting("Raytrace", Float.valueOf(6.0F), Float.valueOf(0.1F), Float.valueOf(7.0F), "Wall Range."));
  
  public Setting<Boolean> players = register(new Setting("Players", Boolean.valueOf(true)));
  
  public Setting<Boolean> mobs = register(new Setting("Mobs", Boolean.valueOf(false)));
  
  public Setting<Boolean> animals = register(new Setting("Animals", Boolean.valueOf(false)));
  
  public Setting<Boolean> vehicles = register(new Setting("Entities", Boolean.valueOf(false)));
  
  public Setting<Boolean> projectiles = register(new Setting("Projectiles", Boolean.valueOf(false)));
  
  public Setting<Boolean> tps = register(new Setting("TpsSync", Boolean.valueOf(true)));
  
  public Setting<Boolean> packet = register(new Setting("Packet", Boolean.valueOf(false)));
  
  public Killaura() {
    super("Kills aura", "Kills aura.", Module.Category.COMBAT, true, false, false);
  }
  
  public void onTick() {
    if (!((Boolean)this.rotate.getValue()).booleanValue())
      doKillaura(); 
  }
  
  @SubscribeEvent
  public void onUpdateWalkingPlayerEvent(UpdateWalkingPlayerEvent event) {
    if (event.getStage() == 0 && ((Boolean)this.rotate.getValue()).booleanValue())
      doKillaura(); 
  }
  
  private void doKillaura() {
    if (((Boolean)this.onlySharp.getValue()).booleanValue() && !EntityUtil.holdingWeapon((EntityPlayer)mc.player)) {
      target = null;
      return;
    } 
    int wait = !((Boolean)this.delay.getValue()).booleanValue() ? 0 : (int)(DamageUtil.getCooldownByWeapon((EntityPlayer)mc.player) * (((Boolean)this.tps.getValue()).booleanValue() ? serverManager.getTpsFactor() : 1.0F));
    if (!this.timer.passedMs(wait))
      return; 
    target = getTarget();
    if (target == null)
      return; 
    if (((Boolean)this.rotate.getValue()).booleanValue())
     rotationManager.lookAtEntity(target);
    EntityUtil.attackEntity(target, ((Boolean)this.packet.getValue()).booleanValue(), true);
    this.timer.reset();
  }
  
  private Entity getTarget() {
    Entity target = null;
    double distance = ((Float)this.range.getValue()).floatValue();
    double maxHealth = 36.0D;
    for (Entity entity : mc.world.playerEntities) {
      if (((!((Boolean)this.players.getValue()).booleanValue() || !(entity instanceof EntityPlayer)) && (!((Boolean)this.animals.getValue()).booleanValue() || !EntityUtil.isPassive(entity)) && (!((Boolean)this.mobs.getValue()).booleanValue() || !EntityUtil.isMobAggressive(entity)) && (!((Boolean)this.vehicles.getValue()).booleanValue() || !EntityUtil.isVehicle(entity)) && (!((Boolean)this.projectiles.getValue()).booleanValue() || !EntityUtil.isProjectile(entity))) || (entity instanceof net.minecraft.entity.EntityLivingBase && 
        EntityUtil.isntValid(entity, distance)))
        continue; 
      if (!mc.player.canEntityBeSeen(entity) && !EntityUtil.canEntityFeetBeSeen(entity) && mc.player.getDistanceSq(entity) > MathUtil.square(((Float)this.raytrace.getValue()).floatValue()))
        continue; 
      if (target == null) {
        target = entity;
        distance = mc.player.getDistanceSq(entity);
        maxHealth = EntityUtil.getHealth(entity);
        continue;
      } 
      if (entity instanceof EntityPlayer && DamageUtil.isArmorLow((EntityPlayer)entity, 18)) {
        target = entity;
        break;
      } 
      if (mc.player.getDistanceSq(entity) < distance) {
        target = entity;
        distance = mc.player.getDistanceSq(entity);
        maxHealth = EntityUtil.getHealth(entity);
      } 
      if (EntityUtil.getHealth(entity) < maxHealth) {
        target = entity;
        distance = mc.player.getDistanceSq(entity);
        maxHealth = EntityUtil.getHealth(entity);
      } 
    } 
    return target;
  }
  
  public String getDisplayInfo() {
    if (target instanceof EntityPlayer)
      return target.getName(); 
    return null;
  }
}
