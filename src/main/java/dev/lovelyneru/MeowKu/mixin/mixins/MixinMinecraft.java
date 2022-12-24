package dev.lovelyneru.MeowKu.mixin.mixins;

import dev.lovelyneru.MeowKu.MeowKu;
import dev.lovelyneru.MeowKu.features.modules.client.ClickGui;
import dev.lovelyneru.MeowKu.event.events.KeyEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.crash.CrashReport;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(value = {Minecraft.class})
public abstract class MixinMinecraft {
    @Inject(method = {"shutdownMinecraftApplet"}, at = {@At(value = "HEAD")})
    private void stopClient(CallbackInfo callbackInfo) {
        this.unload();
    }

    @Redirect(method = {"run"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayCrashReport(Lnet/minecraft/crash/CrashReport;)V"))
    public void displayCrashReport(Minecraft minecraft, CrashReport crashReport) {
        this.unload();
    }

    @Inject(method = {"runTickKeyboard"}, at = {@At(value = "INVOKE", remap = false, target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", ordinal = 0, shift = At.Shift.BEFORE)})
    private void onKeyboard(CallbackInfo callbackInfo) {
        int i;
        int n = i = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
        if (Keyboard.getEventKeyState()) {
            KeyEvent event = new KeyEvent(i);
            MinecraftForge.EVENT_BUS.post(event);
        }
    }
    @Shadow
    public abstract void displayGuiScreen(@Nullable GuiScreen var1);
    @Inject(method={"displayGuiScreen"}, at={@At(value="HEAD")})
    private void displayGuiScreen(GuiScreen screen, CallbackInfo ci) {
        ClickGui ClickGui = (ClickGui) MeowKu.moduleManager.getModuleByDisplayName("ClickGui");
    }
    @Inject(method={"runTick()V"}, at={@At(value="RETURN")})
    private void runTick(CallbackInfo callbackInfo) {
        ClickGui ClickGui = (ClickGui) MeowKu.moduleManager.getModuleByDisplayName("ClickGui");
    }
    private void unload() {
        MeowKu.LOGGER.info("Initiated client shutdown.");
        MeowKu.onUnload();
        MeowKu.LOGGER.info("Finished client shutdown.");
    }
}

