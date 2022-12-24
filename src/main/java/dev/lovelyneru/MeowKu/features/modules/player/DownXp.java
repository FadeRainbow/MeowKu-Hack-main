/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityXPOrb
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.NonNullList
 */
package dev.lovelyneru.MeowKu.features.modules.player;

import dev.lovelyneru.MeowKu.features.setting.Bind;
import dev.lovelyneru.MeowKu.features.setting.Setting;
import dev.lovelyneru.MeowKu.util.InventoryUtil;
import dev.lovelyneru.MeowKu.features.modules.Module;
import dev.lovelyneru.MeowKu.util.Timer;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class DownXp
        extends Module {
    public static DownXp INSTANCE;
    private final Timer timer = new Timer();
    private final Setting<Bind> bind = this.register(new Setting<Bind>("PacketBind", new Bind(-1)));
    char toMend = '\u0000';


    public DownXp() {
        super("DownXp", "DownXp", Module.Category.PLAYER, true, false, false);
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        if (DownXp.fullNullCheck()) {
            return;
        }
        if (this.bind.getValue().isDown() && mc.currentScreen == null) {
            this.mendArmor();
        }
    }

    private void mendArmor() {
        int a = InventoryUtil.getItemHotbar(Items.EXPERIENCE_BOTTLE);
        int b = DownXp.mc.player.inventory.currentItem;
        if (a == -1) {
            return;
        }
        DownXp.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(DownXp.mc.player.rotationYaw, 90.0f, true));
        DownXp.mc.player.inventory.currentItem = a;
        DownXp.mc.playerController.updateController();
        DownXp.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        DownXp.mc.player.inventory.currentItem = b;
        DownXp.mc.playerController.updateController();
    }
}

