package dev.lovelyneru.MeowKu.features.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.lovelyneru.MeowKu.MeowKu;
import dev.lovelyneru.MeowKu.features.setting.Setting;
import dev.lovelyneru.MeowKu.event.events.ClientEvent;
import dev.lovelyneru.MeowKu.features.command.Command;
import dev.lovelyneru.MeowKu.features.modules.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class FontMod extends Module {
    private static FontMod INSTANCE = new FontMod();
    public Setting<String> fontName = register(new Setting<String>("FontName", "Arial", "Name of the font."));
    public Setting<Boolean> antiAlias = register(new Setting<Boolean>("AntiAlias", Boolean.valueOf(true), "Smoother font."));
    public Setting<Boolean> fractionalMetrics = register(new Setting<Boolean>("Metrics", Boolean.valueOf(true), "Thinner font."));
    public Setting<Boolean> customAll = register(new Setting<Boolean>("CustomAll", Boolean.valueOf(true), "Renders font everywhere"));
    public Setting<Integer> fontSize = register(new Setting<Integer>("Size", Integer.valueOf(18), Integer.valueOf(12), Integer.valueOf(30), "Size of the font."));
    public Setting<Integer> fontStyle = register(new Setting<Integer>("Style", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(3), "Style of the font."));
    private boolean reloadFont = false;

    public FontMod() {
        super("CustomFont", "CustomFont for all of the clients text. Use the font command.", Module.Category.CLIENT, true, false, false);
        setInstance();
    }

    public static FontMod getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FontMod();
        }
        return INSTANCE;
    }

    public static boolean checkFont(String font, boolean message) {
        String[] fonts;
        for (String s : fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()) {
            if (!message && s.equals(font)) {
                return true;
            }
            if (!message) continue;
            Command.sendMessage(s);
        }
        return false;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @SubscribeEvent
    public void onSettingChange(ClientEvent event) {
        Setting setting;
        if (event.getStage() == 2 && (setting = event.getSetting()) != null && setting.getFeature().equals(this)) {
            if (setting.getName().equals("FontName") && !FontMod.checkFont(setting.getPlannedValue().toString(), false)) {
                Command.sendMessage(ChatFormatting.RED + "That font doesnt exist.");
                event.setCanceled(true);
                return;
            }
            reloadFont = true;
        }
    }

    @Override
    public void onTick() {
        if (reloadFont) {
            MeowKu.textManager.init(false);
            reloadFont = false;
        }
    }
}

