package dev.lovelyneru.MeowKu.event.events;

import dev.lovelyneru.MeowKu.event.EventStage;
import net.minecraft.entity.player.EntityPlayer;

public class DeathEvent
        extends EventStage {
    public EntityPlayer player;

    public DeathEvent(EntityPlayer player) {
        this.player = player;
    }
}

