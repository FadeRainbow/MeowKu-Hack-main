package dev.lovelyneru.MeowKu.manager;


import Kotlin.Today.Meowku.Modules.PenShen;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.lovelyneru.MeowKu.MeowKu;
import dev.lovelyneru.MeowKu.features.Feature;
import dev.lovelyneru.MeowKu.features.gui.MeowKuGui;
import dev.lovelyneru.MeowKu.features.modules.Module;
import dev.lovelyneru.MeowKu.features.modules.client.*;
import dev.lovelyneru.MeowKu.features.modules.combat.*;
import dev.lovelyneru.MeowKu.features.modules.misc.*;
import dev.lovelyneru.MeowKu.features.modules.movement.*;
import dev.lovelyneru.MeowKu.features.modules.player.*;
import dev.lovelyneru.MeowKu.features.modules.render.*;
import dev.lovelyneru.MeowKu.event.events.Render2DEvent;
import dev.lovelyneru.MeowKu.event.events.Render3DEvent;

import dev.lovelyneru.MeowKu.features.modules.combat.InstantMine;

import dev.lovelyneru.MeowKu.util.Util;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ModuleManager
        extends Feature {
    public static ArrayList<Module> modules = new ArrayList();
    public List<Module> sortedModules = new ArrayList<Module>();
    public List<String> sortedModulesABC = new ArrayList<String>();
    public Animation animationThread;

    public void init() {

        this.modules.add(new ClickGui());
        this.modules.add(new FontMod());
        this.modules.add(new GUIBlur());
        this.modules.add(new HUD());
        this.modules.add(new HudComponents());
        this.modules.add(new NickHider());
        this.modules.add(new NameTags());
        this.modules.add(new Surround());
        this.modules.add(new SkyColor());
        this.modules.add(new PlayerTweaks());
        this.modules.add(new AutoTotem());
        this.modules.add(new NoFall());
        this.modules.add(new Strafe());
        this.modules.add(new DownXp());
        this.modules.add(new Burrow());
        this.modules.add(new AutoCrystal());
        this.modules.add(new AutoHoleMine());
        this.modules.add(new AutoTrap());
        this.modules.add(new AutoFeet());
        this.modules.add(new NoRender());
        this.modules.add(new TrapHead());
        this.modules.add(new AntiCity());
        this.modules.add(new SmartSurround());
        this.modules.add(new InstantMine());
        this.modules.add(new AntiBurrow());
        this.modules.add(new AntiCev());
        this.modules.add(new AutoCevSelect());
        this.modules.add(new AutoCev());
        this.modules.add(new Offhand());
        this.modules.add(new TrapSelf());
        this.modules.add(new AntiHoleKick());
        this.modules.add(new HoleFill());
        this.modules.add(new Flatten());
        this.modules.add(new HoleRedstoneFill());
        this.modules.add(new TimerBypass());
        this.modules.add(new Replenish());
        this.modules.add(new FakePlayer());
        this.modules.add(new Reach());
        this.modules.add(new MCP());
        this.modules.add(new GodModule());
        this.modules.add(new PacketEat());
        this.modules.add(new TNTHead());
        this.modules.add(new MCF());
        this.modules.add(new AutoQueue());
        this.modules.add(new PacketXP());
        this.modules.add(new XCarry());
        this.modules.add(new Speed());
        this.modules.add(new Step());
        this.modules.add(new ChatSuffix());
        this.modules.add(new BurrowElse());
        this.modules.add(new PopChams());
        this.modules.add(new AntiCityESP());
        this.modules.add(new SurroundRender2());
        this.modules.add(new BurrowESP());
        this.modules.add(new FPSBypass());
        this.modules.add(new Killaura());
        this.modules.add(new Criticals());
        this.modules.add(new AutoWeak());
        this.modules.add(new GhostGap());
        this.modules.add(new Csgo());
        this.modules.add(new AutoMmaaa());
        this.modules.add(new PistonCrystal());
        this.modules.add(new Blocker());
        this.modules.add(new BreakESPPlus());
        this.modules.add(new PenShen());


    }

    public static Module getModuleByName(String name) {
        for (Module module : modules) {
            if (!module.getName().equalsIgnoreCase(name)) continue;
            return module;
        }
        return null;
    }

    public static  <T extends Module> T getModuleByClass(Class<T> clazz) {
        for (Module module : modules) {
            if (!clazz.isInstance(module)) continue;
            return (T) module;
        }
        return null;
    }

    public void enableModule(Class<Module> clazz) {
        Module module = this.getModuleByClass(clazz);
        if (module != null) {
            module.enable();
        }
    }

    public void disableModule(Class<Module> clazz) {
        Module module = this.getModuleByClass(clazz);
        if (module != null) {
            module.disable();
        }
    }

    public void enableModule(String name) {
        Module module = this.getModuleByName(name);
        if (module != null) {
            module.enable();
        }
    }

    public void disableModule(String name) {
        Module module = this.getModuleByName(name);
        if (module != null) {
            module.disable();
        }
    }

    public boolean isModuleEnabled(String name) {
        Module module = this.getModuleByName(name);
        return module != null && module.isOn();
    }

    public boolean isModuleEnabled(Class<Module> clazz) {
        Module module = this.getModuleByClass(clazz);
        return module != null && module.isOn();
    }

    public Module getModuleByDisplayName(String displayName) {
        for (Module module : this.modules) {
            if (!module.getDisplayName().equalsIgnoreCase(displayName)) continue;
            return module;
        }
        return null;
    }

    public ArrayList<Module> getEnabledModules() {
        ArrayList<Module> enabledModules = new ArrayList<Module>();
        for (Module module : this.modules) {
            if (!module.isEnabled()) continue;
            enabledModules.add(module);
        }
        return enabledModules;
    }

    public ArrayList<String> getEnabledModulesName() {
        ArrayList<String> enabledModules = new ArrayList<String>();
        for (Module module : this.modules) {
            if (!module.isEnabled() || !module.isDrawn()) continue;
            enabledModules.add(module.getFullArrayString());
        }
        return enabledModules;
    }

    public ArrayList<Module> getModulesByCategory(Module.Category category) {
        ArrayList<Module> modulesCategory = new ArrayList<Module>();
        this.modules.forEach(module -> {
            if (module.getCategory() == category) {
                modulesCategory.add(module);
            }
        });
        return modulesCategory;
    }

    public List<Module.Category> getCategories() {
        return Arrays.asList(Module.Category.values());
    }

    public void onLoad() {
        this.modules.stream().filter(Module::listening).forEach(((EventBus) MinecraftForge.EVENT_BUS)::register);
        this.modules.forEach(Module::onLoad);
    }

    public void onUpdate() {
        this.modules.stream().filter(Feature::isEnabled).forEach(Module::onUpdate);
    }

    public void onTick() {
        this.modules.stream().filter(Feature::isEnabled).forEach(Module::onTick);
    }

    public void onRender2D(Render2DEvent event) {
        this.modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender2D(event));
    }

    public void onRender3D(Render3DEvent event) {
        this.modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender3D(event));
    }

    public <T extends Module> T getModuleT(Class<T> clazz) {
        return modules.stream().filter(module -> module.getClass() == clazz).map(module -> (T) module).findFirst().orElse(null);
    }

    public void sortModules(boolean reverse) {
        this.sortedModules = this.getEnabledModules().stream().filter(Module::isDrawn).sorted(Comparator.comparing(module -> this.renderer.getStringWidth(module.getFullArrayString()) * (reverse ? -1 : 1))).collect(Collectors.toList());
    }

    public void sortModulesABC() {
        this.sortedModulesABC = new ArrayList<String>(this.getEnabledModulesName());
        this.sortedModulesABC.sort(String.CASE_INSENSITIVE_ORDER);
    }

    public void onLogout() {
        this.modules.forEach(Module::onLogout);
    }

    public void onLogin() {
        this.modules.forEach(Module::onLogin);
    }

    public void onUnload() {
        this.modules.forEach(MinecraftForge.EVENT_BUS::unregister);
        this.modules.forEach(Module::onUnload);
    }

    public void onUnloadPost() {
        for (Module module : this.modules) {
            module.enabled.setValue(false);
        }
    }

    public void onKeyPressed(int eventKey) {
        if (eventKey == 0 || !Keyboard.getEventKeyState() || ModuleManager.mc.currentScreen instanceof MeowKuGui) {
            return;
        }
        this.modules.forEach(module -> {
            if (module.getBind().getKey() == eventKey) {
                module.toggle();
            }
        });
    }

    private class Animation
            extends Thread {
        public Module module;
        public float offset;
        public float vOffset;
        ScheduledExecutorService service;

        public Animation() {
            super("Animation");
            this.service = Executors.newSingleThreadScheduledExecutor();
        }

        @Override
        public void run() {
            if (HUD.getInstance().renderingMode.getValue() == HUD.RenderingMode.Length) {
                for (Module module : ModuleManager.this.sortedModules) {
                    String text = module.getDisplayName() + ChatFormatting.GRAY + (module.getDisplayInfo() != null ? " [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]" : "");
                    module.offset = (float) ModuleManager.this.renderer.getStringWidth(text) / HUD.getInstance().animationHorizontalTime.getValue().floatValue();
                    module.vOffset = (float) ModuleManager.this.renderer.getFontHeight() / HUD.getInstance().animationVerticalTime.getValue().floatValue();
                    if (module.isEnabled() && HUD.getInstance().animationHorizontalTime.getValue() != 1) {
                        if (!(module.arrayListOffset > module.offset) || Util.mc.world == null) continue;
                        module.arrayListOffset -= module.offset;
                        module.sliding = true;
                        continue;
                    }
                    if (!module.isDisabled() || HUD.getInstance().animationHorizontalTime.getValue() == 1) continue;
                    if (module.arrayListOffset < (float) ModuleManager.this.renderer.getStringWidth(text) && Util.mc.world != null) {
                        module.arrayListOffset += module.offset;
                        module.sliding = true;
                        continue;
                    }
                    module.sliding = false;
                }
            } else {
                for (String e : ModuleManager.this.sortedModulesABC) {
                    Module module = MeowKu.moduleManager.getModuleByName(e);
                    String text = module.getDisplayName() + ChatFormatting.GRAY + (module.getDisplayInfo() != null ? " [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]" : "");
                    module.offset = (float) ModuleManager.this.renderer.getStringWidth(text) / HUD.getInstance().animationHorizontalTime.getValue().floatValue();
                    module.vOffset = (float) ModuleManager.this.renderer.getFontHeight() / HUD.getInstance().animationVerticalTime.getValue().floatValue();
                    if (module.isEnabled() && HUD.getInstance().animationHorizontalTime.getValue() != 1) {
                        if (!(module.arrayListOffset > module.offset) || Util.mc.world == null) continue;
                        module.arrayListOffset -= module.offset;
                        module.sliding = true;
                        continue;
                    }
                    if (!module.isDisabled() || HUD.getInstance().animationHorizontalTime.getValue() == 1) continue;
                    if (module.arrayListOffset < (float) ModuleManager.this.renderer.getStringWidth(text) && Util.mc.world != null) {
                        module.arrayListOffset += module.offset;
                        module.sliding = true;
                        continue;
                    }
                    module.sliding = false;
                }
            }
        }

        @Override
        public void start() {
            System.out.println("Starting animation thread.");
            this.service.scheduleAtFixedRate(this, 0L, 1L, TimeUnit.MILLISECONDS);
        }
    }

    public static ArrayList<Module> nigger;

    public static ArrayList<Module> getModules() {
        return nigger;
    }

    public static boolean isModuleEnablednigger(String name) {
        Module modulenigger = getModules().stream().filter(mm->mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        return modulenigger.isEnabled();
    }

    public static boolean isModuleEnablednigger(Module modulenigger) {
        return modulenigger.isEnabled();
    }

}

