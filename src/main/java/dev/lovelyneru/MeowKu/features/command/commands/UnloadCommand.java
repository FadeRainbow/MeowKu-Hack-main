package dev.lovelyneru.MeowKu.features.command.commands;

import dev.lovelyneru.MeowKu.MeowKu;
import dev.lovelyneru.MeowKu.features.command.Command;

public class UnloadCommand
        extends Command {
    public UnloadCommand() {
        super("unload", new String[0]);
    }

    @Override
    public void execute(String[] commands) {
        MeowKu.unload(true);
    }
}

