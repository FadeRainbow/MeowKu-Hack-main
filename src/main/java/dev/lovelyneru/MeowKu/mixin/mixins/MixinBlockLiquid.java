package dev.lovelyneru.MeowKu.mixin.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {BlockLiquid.class})
public class MixinBlockLiquid
        extends Block {
    protected MixinBlockLiquid(Material materialIn) {
        super(materialIn);
    }
}

