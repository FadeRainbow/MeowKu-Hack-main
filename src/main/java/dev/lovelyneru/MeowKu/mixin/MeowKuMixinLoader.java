package dev.lovelyneru.MeowKu.mixin;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;
@IFMLLoadingPlugin.Name(value="MeowKu")
public class MeowKuMixinLoader
        implements IFMLLoadingPlugin {
    private static boolean isObfuscatedEnvironment = false;

    public MeowKuMixinLoader() {
        System.out.println("Loading mixins ...");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.MeowKu.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        System.out.println(MixinEnvironment.getDefaultEnvironment().getObfuscationContext());
    }

    public String[] getASMTransformerClass() {
        return new String[0];
    }

    public String getModContainerClass() {
        return null;
    }

    public String getSetupClass() {
        return null;
    }

    public void injectData(Map<String, Object> data) {
        isObfuscatedEnvironment = (Boolean) data.get("runtimeDeobfuscationEnabled");
    }

    public String getAccessTransformerClass() {
        return null;
    }
}

