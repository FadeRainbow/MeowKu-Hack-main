package dev.lovelyneru.MeowKu.util.math.deneb;

import dev.lovelyneru.MeowKu.event.events.PacketEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.LogWrapper;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.EventListener;

public class LagCompensator
implements EventListener {
    public static LagCompensator INSTANCE;
    private final float[] tickRates;
    private int nextIndex = 0;
    private long timeLastTimeUpdate;

    public LagCompensator() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.tickRates = new float[20];
        this.reset();
    }

    public static int globalInfoPingValue() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.getConnection() == null) {
            return 1;
        }
        if (mc.player == null) {
            return -1;
        }
        try {
            return mc.getConnection().getPlayerInfo(mc.player.getUniqueID()).getResponseTime();
        }
        catch (NullPointerException npe) {
            LogWrapper.info((String)"Caught NPE l25 CalcPing.java", (Object[])new Object[0]);
            return -1;
        }
    }

    @SubscribeEvent
    public void onPacketEvent(PacketEvents event) {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            INSTANCE.onTimeUpdate();
        }
    }

    public void reset() {
        this.nextIndex = 0;
        this.timeLastTimeUpdate = -1L;
        Arrays.fill(this.tickRates, 0.0f);
    }

    public float getTickRate() {
        float numTicks = 0.0f;
        float sumTickRates = 0.0f;
        for (float tickRate : this.tickRates) {
            if (!(tickRate > 0.0f)) continue;
            sumTickRates += tickRate;
            numTicks += 1.0f;
        }
        return MathHelper.clamp((float)(sumTickRates / numTicks), (float)0.0f, (float)20.0f);
    }

    public void onTimeUpdate() {
        if (this.timeLastTimeUpdate != -1L) {
            float timeElapsed = (float)(System.currentTimeMillis() - this.timeLastTimeUpdate) / 1000.0f;
            this.tickRates[this.nextIndex % this.tickRates.length] = MathHelper.clamp((float)(20.0f / timeElapsed), (float)0.0f, (float)20.0f);
            ++this.nextIndex;
        }
        this.timeLastTimeUpdate = System.currentTimeMillis();
    }
}

