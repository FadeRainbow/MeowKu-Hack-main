package dev.lovelyneru.MeowKu.event.events;


import dev.lovelyneru.MeowKu.event.EventStage;
import net.minecraft.util.EnumHandSide;

public class TransformSideFirstPersonEvent extends EventStage {

    private final EnumHandSide enumHandSide;

    public TransformSideFirstPersonEvent(EnumHandSide enumHandSide) {
        this.enumHandSide = enumHandSide;
    }

    public EnumHandSide getEnumHandSide() {
        return this.enumHandSide;
    }
}