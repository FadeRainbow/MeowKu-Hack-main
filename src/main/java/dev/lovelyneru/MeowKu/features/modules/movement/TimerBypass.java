package dev.lovelyneru.MeowKu.features.modules.movement;

import dev.lovelyneru.MeowKu.features.setting.Setting;
import dev.lovelyneru.MeowKu.event.events.PacketEvents;
import dev.lovelyneru.MeowKu.features.modules.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.CopyOnWriteArrayList;

public class TimerBypass extends Module {
    public static CopyOnWriteArrayList<Packet<?>> packetList = new CopyOnWriteArrayList();
    private final Setting<Float> tickNormal = register(new Setting<Float>("Speed", 1.2f, 1.0f, 10.0f));
    private final Setting<Float> ISFM = register(new Setting<Float>("SpeedValue", 50F, (float) 0, 100F));
    public int i = 0;
    public int x = 0;

    public TimerBypass() {
        super("TimerBypass", "NT", Category.MOVEMENT, true, false, false);
    }

    @SubscribeEvent
    public void onPacket(PacketEvents.Send event) {
        if (TimerBypass.fullNullCheck()) {
            return;
        }
        if (event.getPacket() instanceof CPacketPlayer && !packetList.contains(event.getPacket())) {
            packetList.add((Packet<?>)event.getPacket());
        }
    }

    @Override
    public void onDisable() {
        if (TimerBypass.fullNullCheck()) {
            return;
        }
        mc.timer.tickLength = 50.0f;
        packetList.clear();
    }

    @Override
    public void onEnable() {
        if (TimerBypass.fullNullCheck()) {
            return;
        }
        TimerBypass.mc.timer.tickLength = 50.0f;
        packetList.clear();
    }

    @Override
    public void onUpdate() {
        if (TimerBypass.fullNullCheck()) {
            return;
        }
            if (this.i <= this.ISFM.getValue()) {
                ++this.i;
                TimerBypass.mc.timer.tickLength = 50.0f / this.tickNormal.getValue().floatValue();
                this.x = 0;
            } else if (this.x <= this.ISFM.getValue() - this.ISFM.getValue() / 2 / 2) {
                ++this.x;
                TimerBypass.mc.timer.tickLength = 50.0f;
            } else {
                this.i = 0;
            }
    }
}

