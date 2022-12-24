package dev.lovelyneru.MeowKu.features.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.lovelyneru.MeowKu.MeowKu;
import dev.lovelyneru.MeowKu.features.command.Command;

public class HelpCommand
        extends Command {
    public HelpCommand() {
        super("help");
    }

    @Override
    public void execute(String[] commands) {
        HelpCommand.sendMessage("Commands: ");
        for (Command command : MeowKu.commandManager.getCommands()) {
            HelpCommand.sendMessage(ChatFormatting.GRAY + MeowKu.commandManager.getPrefix() + command.getName());
        }
    }
}

