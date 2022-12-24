/*
 * Decompiled with CFR 0.151.
 *
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.entity.MoverType
 *  net.minecraft.stats.RecipeBook
 *  net.minecraft.stats.StatisticsManager
 *  net.minecraft.world.World
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package dev.lovelyneru.MeowKu.mixin.mixins;


import dev.lovelyneru.MeowKu.event.events.ChatEvent;
import dev.lovelyneru.MeowKu.event.events.MoveEvent;
import dev.lovelyneru.MeowKu.event.events.PushEvent;
import dev.lovelyneru.MeowKu.event.events.UpdateWalkingPlayerEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.MoverType;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={EntityPlayerSP.class}, priority=0x7FFFFFFF)
public abstract class MixinEntityPlayerSP
        extends AbstractClientPlayer {
    public MixinEntityPlayerSP(Minecraft p_i47378_1_, World p_i47378_2_, NetHandlerPlayClient p_i47378_3_, StatisticsManager p_i47378_4_, RecipeBook p_i47378_5_) {
        super(p_i47378_2_, p_i47378_3_.getGameProfile());
    }

    @Inject(method={"sendChatMessage"}, at={@At(value="HEAD")}, cancellable=true)
    public void sendChatMessage(String message, CallbackInfo ci) {
        ChatEvent chatEvent = new ChatEvent(message);
        MinecraftForge.EVENT_BUS.post((Event)chatEvent);
    }

    @Inject(method={"onUpdateWalkingPlayer"}, at={@At(value="HEAD")}, cancellable=true)
    private void preMotion(CallbackInfo ci) {
        UpdateWalkingPlayerEvent event = new UpdateWalkingPlayerEvent(0);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCanceled()) return;
        ci.cancel();
    }

    @Inject(method={"onUpdateWalkingPlayer"}, at={@At(value="RETURN")}, cancellable=true)
    private void postMotion(CallbackInfo ci) {
        UpdateWalkingPlayerEvent event = new UpdateWalkingPlayerEvent(1);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCanceled()) return;
        ci.cancel();
    }

    @Inject(method={"pushOutOfBlocks"}, at={@At(value="HEAD")}, cancellable=true)
    private void pushOutOfBlocksHook(double x, double y, double z, CallbackInfoReturnable<Boolean> ci) {
        PushEvent event = new PushEvent(1);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCanceled()) return;
        ci.setReturnValue(false);
    }

    @Redirect(method={"onLivingUpdate"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/entity/EntityPlayerSP;setSprinting(Z)V", ordinal=2))
    public void onLivingUpdate(EntityPlayerSP entityPlayerSP, boolean sprinting) {
        entityPlayerSP.setSprinting(sprinting);
    }

    @Inject(method={"move"}, at={@At(value="HEAD")}, cancellable=true)
    public void move(MoverType moverType, double n, double n2, double n3, CallbackInfo ci) {
        MoveEvent event = new MoveEvent( moverType, n, n2, n3);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCanceled()) return;
        super.move(moverType, event.getX(), event.getY(), event.getZ());
        ci.cancel();
    }
}

