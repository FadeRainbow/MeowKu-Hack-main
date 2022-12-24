package dev.lovelyneru.MeowKu.event.events;

import dev.lovelyneru.MeowKu.event.EventStage;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class PacketEvents
extends EventStage {
    private final Packet<?> packet;

    public PacketEvents(int stage, Packet<?> packet) {
        super(stage);
        this.packet = packet;
    }

    public <T extends Packet<?>> T getPacket() {
        return (T)this.packet;
    }

    @Cancelable
    public static class Receive
    extends PacketEvents {
        public Receive(int stage, Packet<?> packet) {
            super(stage, packet);
        }
    }

    @Cancelable
    public static class Send
    extends PacketEvents {
        public Send(int stage, Packet<?> packet) {
            super(stage, packet);
        }
    }
}

