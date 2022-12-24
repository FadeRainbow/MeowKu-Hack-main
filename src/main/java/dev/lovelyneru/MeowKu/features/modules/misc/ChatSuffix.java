package dev.lovelyneru.MeowKu.features.modules.misc;

import dev.lovelyneru.MeowKu.event.events.PacketEvent;
import dev.lovelyneru.MeowKu.manager.ModuleManager;
import dev.lovelyneru.MeowKu.features.modules.Module;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatSuffix
        extends Module {
    public ChatSuffix() {
        super("ChatSuffix", "suffix", Module.Category.MISC, true, false, false);
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        CPacketChatMessage packet;
        if(ModuleManager.getModuleByName("AutoQueue").isEnabled()) return;
        String message;
        if (event.getStage() == 0 && event.getPacket() instanceof CPacketChatMessage && !(message = (packet = (CPacketChatMessage)event.getPacket()).getMessage()).startsWith("/")) {
            String Chat = message + " | MeowKu <Ovo>";
            packet.message = Chat;
        }
    }
}

