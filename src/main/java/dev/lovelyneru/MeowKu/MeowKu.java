package dev.lovelyneru.MeowKu;

import dev.lovelyneru.MeowKu.event.events.Render3DEvent;
import dev.lovelyneru.MeowKu.features.gui.font.CustomFont;
import dev.lovelyneru.MeowKu.manager.*;

import dev.lovelyneru.MeowKu.util.Enemy;
import dev.lovelyneru.MeowKu.util.Title;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = MeowKu.MOD_ID,
        name = MeowKu.MOD_NAME,
        version = MeowKu.VERSION
)
public class MeowKu {
    public static final String MOD_ID = "meowku";
    public static final String MOD_NAME = "MeowKu";
    public static final String VERSION = "1.12.2";
    public static final String ID = "0.1";
    public static final Logger LOGGER = LogManager.getLogger("MeowKu");
    public static TimerManager timerManager;
    public static CommandManager commandManager;
    public static FriendManager friendManager;
    public static ModuleManager moduleManager;
    public static PacketManager packetManager;
    public static ColorManager colorManager;
    public static HoleManager holeManager;
    public static InventoryManager inventoryManager;
    public static PotionManager potionManager;
    public static RotationManager rotationManager;
    public static PositionManager positionManager;
    public static SpeedManager speedManager;
    public static ReloadManager reloadManager;
    public static FileManager fileManager;
    public static ConfigManager configManager;
    public static ServerManager serverManager;
    public static EventManager eventManager;
    public static TextManager textManager;
    public static CustomFont fontRenderer;
    public static Render3DEvent render3DEvent;
    public static Enemy enemy;
    public static final EventBus EVENT_BUS = new EventBus();

    public static MenuFont MENU_FONT_MANAGER= new MenuFont();
    public static GuiFont GUI_FONT_MANAGER= new GuiFont();
    public static DonatorFont DONATOR_FONT_MANAGER= new DonatorFont();
    public static SongManager SONG_MANAGER= new SongManager();
    @Mod.Instance
    public static MeowKu INSTANCE;
    private static boolean unloaded;

    static {
        unloaded = false;
    }
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        System.out.println("Mod preinit");
    }



    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        System.out.println("Mod postinit");
    }
    public static void load() {


        unloaded = false;
        if (reloadManager != null) {
            reloadManager.unload();
            reloadManager = null;
        }
        textManager = new TextManager();
        commandManager = new CommandManager();
        friendManager = new FriendManager();
        moduleManager = new ModuleManager();
        rotationManager = new RotationManager();
        packetManager = new PacketManager();
        eventManager = new EventManager();
        speedManager = new SpeedManager();
        potionManager = new PotionManager();
        inventoryManager = new InventoryManager();
        serverManager = new ServerManager();
        fileManager = new FileManager();
        colorManager = new ColorManager();
        positionManager = new PositionManager();
        configManager = new ConfigManager();
        holeManager = new HoleManager();
        LOGGER.info("Managers loaded.");
        moduleManager.init();
        LOGGER.info("Modules loaded.");
        configManager.init();
        eventManager.init();
        LOGGER.info("EventManager loaded.");
        textManager.init(true);
        moduleManager.onLoad();

    }

    public static void unload(boolean unload) {
        if (unload) {
            reloadManager = new ReloadManager();
            reloadManager.init(commandManager != null ? commandManager.getPrefix() : ".");
        }
        MeowKu.onUnload();
        eventManager = null;
        friendManager = null;
        speedManager = null;
        holeManager = null;
        positionManager = null;
        rotationManager = null;
        configManager = null;
        commandManager = null;
        colorManager = null;
        serverManager = null;
        fileManager = null;
        potionManager = null;
        inventoryManager = null;
        moduleManager = null;
        textManager = null;

    }

    public static void reload() {
        MeowKu.unload(false);
        MeowKu.load();
    }

    public static void onUnload() {
        if (!unloaded) {
            eventManager.onUnload();
            moduleManager.onUnload();
            configManager.saveConfig(MeowKu.configManager.config.replaceFirst("MeowKu/", ""));
            moduleManager.onUnloadPost();
            unloaded = true;
        }
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new Title());
        MeowKu.load();
    }
}

