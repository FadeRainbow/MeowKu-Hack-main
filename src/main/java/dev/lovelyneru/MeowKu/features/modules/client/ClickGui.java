package dev.lovelyneru.MeowKu.features.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.lovelyneru.MeowKu.MeowKu;
import dev.lovelyneru.MeowKu.features.setting.Setting;
import dev.lovelyneru.MeowKu.event.events.ClientEvent;
import dev.lovelyneru.MeowKu.features.command.Command;
import dev.lovelyneru.MeowKu.features.gui.MeowKuGui;
import dev.lovelyneru.MeowKu.features.modules.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClickGui extends Module {
    private static ClickGui INSTANCE = new ClickGui();
    public Setting<String> prefix = register(new Setting<String>("Prefix", "."));

    public Setting<Integer> red = register(new Setting<Integer>("Red", 0, 0, 255));
    public Setting<Integer> green = register(new Setting<Integer>("Green", 0, 0, 255));
    public Setting<Integer> blue = register(new Setting<Integer>("Blue", 255, 0, 255));
    public Setting<Integer> hoverAlpha = register(new Setting<Integer>("Alpha", 180, 0, 255));
    public Setting<Integer> topRed = register(new Setting<Integer>("SecondRed", 0, 0, 255));
    public Setting<Integer> topGreen = register(new Setting<Integer>("SecondGreen", 0, 0, 255));
    public Setting<Integer> topBlue = register(new Setting<Integer>("SecondBlue", 150, 0, 255));
    public Setting<Integer> alpha = register(new Setting<Integer>("HoverAlpha", 240, 0, 255));
    public Setting<Boolean> rainbow = register(new Setting<Boolean>("Rainbow", true));
    public Setting<rainbowMode> rainbowModeHud = register(new Setting<Object>("HRainbowMode", rainbowMode.Static, v -> rainbow.getValue()));
    public Setting<rainbowModeArray> rainbowModeA = register(new Setting<Object>("ARainbowMode", rainbowModeArray.Static, v -> rainbow.getValue()));
    public Setting<Integer> rainbowHue = register(new Setting<Object>("Delay", Integer.valueOf(240), Integer.valueOf(0), Integer.valueOf(600), v -> rainbow.getValue()));
    public Setting<Float> rainbowBrightness = register(new Setting<Object>("Brightness ", Float.valueOf(150.0f), Float.valueOf(1.0f), Float.valueOf(255.0f), v -> rainbow.getValue()));
    public Setting<Float> rainbowSaturation = register(new Setting<Object>("Saturation", Float.valueOf(150.0f), Float.valueOf(1.0f), Float.valueOf(255.0f), v -> rainbow.getValue()));
    private MeowKuGui click;
    public enum ModeS {
        GUI1,
        GUI2,
        GUI3,
        GUI4,
        GUI5,
        GUI6,
        GUI7,
        GUI8,
        GUI9,
        GUI10,
    }
    public ClickGui() {
        super("ClickGui", "Opens the ClickGui", Module.Category.CLIENT, true, false, false);
        setInstance();
    }

    public static ClickGui getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClickGui();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {

    }

    @SubscribeEvent
    public void onSettingChange(ClientEvent event) {
        if (event.getStage() == 2 && event.getSetting().getFeature().equals(this)) {
            if (event.getSetting().equals(prefix)) {
                MeowKu.commandManager.setPrefix(prefix.getPlannedValue());
                Command.sendMessage("Prefix set to " + ChatFormatting.DARK_GRAY + MeowKu.commandManager.getPrefix());
            }
            MeowKu.colorManager.setColor(red.getPlannedValue(), green.getPlannedValue(), blue.getPlannedValue(), hoverAlpha.getPlannedValue());
        }
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(MeowKuGui.getClickGui());
    }

    @Override
    public void onLoad() {
        MeowKu.colorManager.setColor(red.getValue(), green.getValue(), blue.getValue(), hoverAlpha.getValue());
        MeowKu.commandManager.setPrefix(prefix.getValue());
    }

    @Override
    public void onTick() {
        if (!(ClickGui.mc.currentScreen instanceof MeowKuGui)) {
            disable();
        }
    }

    public enum rainbowModeArray {
        Static,
        Up

    }

    public enum rainbowMode {
        Static,
        Sideway

    }
}

