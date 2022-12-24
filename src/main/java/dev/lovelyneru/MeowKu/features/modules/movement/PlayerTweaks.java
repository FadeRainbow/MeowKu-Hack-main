/*
 * Decompiled with CFR 0.151.
 *
 * Could not load the following classes:
 *  net.minecraft.network.play.server.SPacketEntityVelocity
 *  net.minecraft.network.play.server.SPacketExplosion
 *  net.minecraftforge.client.event.InputUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev.lovelyneru.MeowKu.features.modules.movement;


import dev.lovelyneru.MeowKu.event.events.PacketEvent;
import dev.lovelyneru.MeowKu.event.events.PushEvent;
import dev.lovelyneru.MeowKu.features.modules.Module;
import dev.lovelyneru.MeowKu.features.setting.Setting;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerTweaks
        extends Module {
    public static PlayerTweaks INSTANCE = new PlayerTweaks();
    public Setting<Boolean> noSlow = this.register(new Setting<Boolean>("No Slow", true));
    public Setting<Boolean> antiKnockBack = this.register(new Setting<Boolean>("Velocity", true));
    public Setting<Boolean> noEntityPush = this.register(new Setting<Boolean>("No PlayerPush", true));
    public Setting<Boolean> noBlockPush = this.register(new Setting<Boolean>("No BlockPush", true));
    public Setting<Boolean> noWaterPush = this.register(new Setting<Boolean>("No LiquidPush", true));
    public Setting<Boolean> Sprint = this.register(new Setting<Boolean>("Sprint", true));
    public Setting<Boolean> guiMove = this.register(new Setting<Boolean>("Gui Move", true));

    public PlayerTweaks() {
        super("PlayerTweaks", "tweaks", Module.Category.MOVEMENT, true, false, false);
        this.setInstance();
    }

    public static PlayerTweaks getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerTweaks();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @SubscribeEvent
    public void Slow(InputUpdateEvent event) {
        if (this.noSlow.getValue().booleanValue() && PlayerTweaks.mc.player.isHandActive() && !PlayerTweaks.mc.player.isRiding()) {
            event.getMovementInput().moveStrafe *= 5.0f;
            event.getMovementInput().moveForward *= 5.0f;
        }
    }

    @SubscribeEvent
    public void onPacketReceived(PacketEvent.Receive event) {
        if (PlayerTweaks.fullNullCheck()) {
            return;
        }
        if (this.antiKnockBack.getValue().booleanValue()) {
            if (event.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)event.getPacket()).getEntityID() == PlayerTweaks.mc.player.getEntityId()) {
                event.setCanceled(true);
            }
            if (event.getPacket() instanceof SPacketExplosion) {
                event.setCanceled(true);
            }
        }
    }

    @Override
    public void onTick() {
        if (this.Sprint.getValue().booleanValue()) {
            if (mc.player.moveForward != 0.0f || mc.player.moveStrafing != 0.0f) {
                //Sprint
                if (!mc.player.isSprinting()) {
                    mc.player.setSprinting(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPush(PushEvent event) {
        if (PlayerTweaks.fullNullCheck()) {
            return;
        }
        if (event.getStage() == 0 && this.noEntityPush.getValue().booleanValue() && event.entity.equals((Object)PlayerTweaks.mc.player)) {
            event.x = -event.x * 0.0;
            event.y = -event.y * 0.0;
            event.z = -event.z * 0.0;
        } else if (event.getStage() == 1 && this.noBlockPush.getValue().booleanValue()) {
            event.setCanceled(true);
        } else if (event.getStage() == 2 && this.noWaterPush.getValue().booleanValue() && PlayerTweaks.mc.player != null && PlayerTweaks.mc.player.equals((Object)event.entity)) {
            event.setCanceled(true);
        }
    }
}

