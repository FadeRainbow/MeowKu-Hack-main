package dev.lovelyneru.MeowKu.features.modules.combat;


import com.jcraft.jogg.Packet;
import dev.lovelyneru.MeowKu.features.modules.Module;
import dev.lovelyneru.MeowKu.util.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class GhostGap extends Module {
    public GhostGap() {
        super("GhostGap", "GhostGap" , Module.Category.COMBAT, true, false, false);
    }

    @Override
    public void onUpdate() {
        final int gapHand = InventoryUtil.findItemInHotbar(Items.GOLDEN_APPLE);
        if (InventoryUtil.findItemInHotbar(Items.GOLDEN_APPLE) != -1) {
            GhostGap.mc.player.connection.sendPacket( new CPacketHeldItemChange(gapHand));
            GhostGap.mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        }
    }
}
