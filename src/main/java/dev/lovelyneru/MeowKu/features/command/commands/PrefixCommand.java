package dev.lovelyneru.MeowKu.features.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.lovelyneru.MeowKu.MeowKu;
import dev.lovelyneru.MeowKu.features.command.Command;

public class PrefixCommand
        extends Command {
    public PrefixCommand() {
        super("prefix", new String[]{"<char>"});
    }

    @Override
    public void execute(String[] commands) {
        if (commands.length == 1) {
            Command.sendMessage(ChatFormatting.GREEN + "Current prefix is " + MeowKu.commandManager.getPrefix());
            return;
        }
        MeowKu.commandManager.setPrefix(commands[0]);
        Command.sendMessage("Prefix changed to " + ChatFormatting.GRAY + commands[0]);
    }
}

