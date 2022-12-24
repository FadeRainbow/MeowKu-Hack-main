package dev.lovelyneru.MeowKu.features.modules.combat;


import java.util.Objects;

import dev.lovelyneru.MeowKu.event.events.PacketEvent;
import dev.lovelyneru.MeowKu.features.modules.Module;
import dev.lovelyneru.MeowKu.features.setting.Setting;
import dev.lovelyneru.MeowKu.util.EntityUtil;
import dev.lovelyneru.MeowKu.util.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Criticals extends Module {
  private final Setting<Integer> packets = register(new Setting("Packets", Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(5)));
  
  private Setting<Integer> desyncDelay = register(new Setting("DesyncDelay", Integer.valueOf(10), Integer.valueOf(0), Integer.valueOf(500)));
  
  private Setting<Boolean> onlySharp = register(new Setting("SwordOnly", Boolean.valueOf(true)));
  
  private final Timer timer = new Timer();
  
  private final boolean resetTimer = false;
  
  public Criticals() {
    super("Criticals", "Scores criticals for you", Module.Category.COMBAT, true, false, false);
  }
  
  @SubscribeEvent
  public void onPacketSend(PacketEvent.Send event) {
    CPacketUseEntity packet;
    if (event.getPacket() instanceof CPacketUseEntity && (packet = (CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK) {
      if (fullNullCheck())
        return; 
      if (((Boolean)this.onlySharp.getValue()).booleanValue() && !EntityUtil.holdingWeapon((EntityPlayer)mc.player))
        return; 
      getClass();
      if (!this.timer.passedMs(((Integer)this.desyncDelay.getValue()).intValue()))
        return; 
      if (mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown() && packet.getEntityFromWorld((World)mc.world) instanceof net.minecraft.entity.EntityLivingBase && !mc.player.isInWater() && !mc.player.isInLava()) {
        switch (((Integer)this.packets.getValue()).intValue()) {
          case 1:
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.10000000149011612D, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            break;
          case 2:
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0625101D, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.1E-5D, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            break;
          case 3:
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0625101D, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0125D, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            break;
          case 4:
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.05D, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.03D, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            break;
          case 5:
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1625D, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 4.0E-6D, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.0E-6D, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer());
            mc.player.onCriticalHit(Objects.<Entity>requireNonNull(packet.getEntityFromWorld((World)mc.world)));
            break;
        } 
        this.timer.reset();
      } 
    } 
  }
  
  public String getDisplayInfo() {
    return "Packet";
  }
}
