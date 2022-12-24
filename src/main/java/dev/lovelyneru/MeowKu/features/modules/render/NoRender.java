/*
 * Decompiled with CFR 0.151.
 *
 * Could not load the following classes:
 *  net.minecraft.init.MobEffects
 *  net.minecraftforge.client.event.RenderBlockOverlayEvent
 *  net.minecraftforge.client.event.RenderBlockOverlayEvent$OverlayType
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev.lovelyneru.MeowKu.features.modules.render;


import dev.lovelyneru.MeowKu.features.setting.Setting;
import dev.lovelyneru.MeowKu.event.events.NoRenderEvent;
import dev.lovelyneru.MeowKu.features.modules.Module;
import net.minecraft.init.MobEffects;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoRender
        extends Module {

    public static NoRender INSTANCE = new NoRender();
    public Setting<Boolean> armor = this.register(new Setting<Boolean>("Armor", true));
    public Setting<Boolean> fire = this.register(new Setting<Boolean>("Frie", true));
    public Setting<Boolean> blind = this.register(new Setting<Boolean>("Blind", true));
    public Setting<Boolean> nausea = this.register(new Setting<Boolean>("Nausea", true));

    public Setting<Boolean> fog = this.register(new Setting<Boolean>("Fog", true));
    public Setting<Boolean> hurtCam = this.register(new Setting<Boolean>("HurtCam", true));
    public Setting<Boolean> enchantmentTables = this.register(new Setting<Boolean>("enchantmentTables", true));
    public NoRender() {
        super("NoRender", "No Render", Module.Category.RENDER, true, false, false);
        this.setInstance();
    }

    public static NoRender getInstance() {
        if (INSTANCE != null) return INSTANCE;
        INSTANCE = new NoRender();
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        if (this.blind.getValue().booleanValue() && NoRender.mc.player.isPotionActive(MobEffects.BLINDNESS)) {
            NoRender.mc.player.removePotionEffect(MobEffects.BLINDNESS);
        }
        if (this.nausea.getValue() == false) return;
        if (!NoRender.mc.player.isPotionActive(MobEffects.NAUSEA)) return;
        NoRender.mc.player.removePotionEffect(MobEffects.NAUSEA);
    }

    @SubscribeEvent
    public void NoRenderEventListener(NoRenderEvent event) {
        if (event.getStage() == 0 && this.armor.getValue().booleanValue()) {
            event.setCanceled(true);
            return;
        }
        if (event.getStage() != 1) return;
        if (this.hurtCam.getValue() == false) return;
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void fog_density(final EntityViewRenderEvent.FogDensity event) {
        if (!this.fog.getValue()) {
            event.setDensity(0.0f);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void blockOverlayEventListener(RenderBlockOverlayEvent event) {
        if (this.fire.getValue() == false) return;
        if (event.getOverlayType() != RenderBlockOverlayEvent.OverlayType.FIRE) return;
        event.setCanceled(true);
    }
}

