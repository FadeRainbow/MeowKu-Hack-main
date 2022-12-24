package dev.lovelyneru.MeowKu.event.events;

import dev.lovelyneru.MeowKu.event.EventStage;
import net.minecraft.util.math.BlockPos;

public class BlockDestructionEvent extends EventStage {
    BlockPos nigger;
    public BlockDestructionEvent(BlockPos nigger){
        super();
        nigger = nigger;
    }

    public BlockPos getBlockPos(){
        return nigger;
    }
}
