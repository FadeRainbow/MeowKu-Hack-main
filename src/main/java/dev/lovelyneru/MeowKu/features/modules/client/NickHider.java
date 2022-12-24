package dev.lovelyneru.MeowKu.features.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.lovelyneru.MeowKu.features.setting.Setting;
import dev.lovelyneru.MeowKu.features.command.Command;
import dev.lovelyneru.MeowKu.features.modules.Module;

public class NickHider extends Module {
    public final Setting<String> NameString = register(new Setting<Object>("Name", "New Name Here..."));
    private static NickHider instance;

    public NickHider() {
        super("NickHider", "Changes name", Module.Category.CLIENT, false, false, false);
        instance = this;
    }

    @Override
    public void onEnable() {
        Command.sendMessage(ChatFormatting.GRAY + "Success! Name succesfully changed to " + ChatFormatting.GREEN + NameString.getValue());
    }

    public static NickHider getInstance() {
        if (instance == null) {
            instance = new NickHider();
        }
        return instance;
    }
}