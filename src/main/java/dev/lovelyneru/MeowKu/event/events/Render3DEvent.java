package dev.lovelyneru.MeowKu.event.events;

import dev.lovelyneru.MeowKu.event.EventStage;

public class Render3DEvent extends EventStage
{
    private float partialTicks;

    public Render3DEvent(final float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}

