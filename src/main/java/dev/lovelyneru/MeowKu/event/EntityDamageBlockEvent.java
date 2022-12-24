package dev.lovelyneru.MeowKu.event;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public class EntityDamageBlockEvent extends EventStage {
    private int entityId;

    private BlockPos pos;

    public EntityDamageBlockEvent(int stage, int entityId, BlockPos pos) {

        this.entityId = entityId;
        this.pos = pos;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public Entity getEntity() {
        return (Minecraft.getMinecraft()).world.getEntityByID(this.entityId);
    }
}
