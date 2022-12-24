/*
 * Decompiled with CFR 0.151.
 */
package dev.lovelyneru.MeowKu.features.modules.player;


import dev.lovelyneru.MeowKu.features.modules.Module;

public class  PacketEat
extends Module {
    private static PacketEat INSTANCE = new PacketEat();

    public PacketEat() {
        super("PacketEat", "PacketEat", Category.PLAYER, true, false, false);
        this.setInstance();
    }

    public static PacketEat getInstance() {
        if (INSTANCE != null) return INSTANCE;
        INSTANCE = new PacketEat();
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }
}

