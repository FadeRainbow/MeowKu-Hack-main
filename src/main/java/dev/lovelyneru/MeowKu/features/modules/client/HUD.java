package dev.lovelyneru.MeowKu.features.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.lovelyneru.MeowKu.MeowKu;
import dev.lovelyneru.MeowKu.features.setting.Setting;
import dev.lovelyneru.MeowKu.event.events.ClientEvent;
import dev.lovelyneru.MeowKu.event.events.Render2DEvent;
import dev.lovelyneru.MeowKu.features.modules.Module;
import dev.lovelyneru.MeowKu.util.*;
import dev.lovelyneru.MeowKu.util.Timer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.SimpleDateFormat;
import java.util.*;

public class HUD extends Module {
    private static final ResourceLocation box = new ResourceLocation("textures/gui/container/shulker_box.png");
    private static final ItemStack totem = new ItemStack(Items.TOTEM_OF_UNDYING);
    private static final ItemStack Crystal = new ItemStack(Items.END_CRYSTAL);
    private static final ItemStack xp = new ItemStack(Items.EXPERIENCE_BOTTLE);
    private static final ItemStack ap = new ItemStack(Items.GOLDEN_APPLE);
    private static RenderItem itemRender;
    private static HUD INSTANCE = new HUD();
    private final Setting<Boolean> grayNess = register(new Setting("Gray", Boolean.valueOf(true)));
    private final Setting<Boolean> renderingUp = register(new Setting("RenderingUp", Boolean.valueOf(true), "Orientation of the HUD-Elements."));
    private final Setting<Boolean> waterMark = register(new Setting("Watermark", Boolean.valueOf(true), "displays watermark"));
    private final Setting<Boolean> waterMark2 = register(new Setting("Watermark2", Boolean.valueOf(false), "displays watermark"));
    private final Setting<Boolean> waterMark3 = register(new Setting("slolwatermark", Boolean.valueOf(false), "displays watermark"));
    public Setting<Integer> waterMarkY = register(new Setting("WatermarkPosY", Integer.valueOf(2), Integer.valueOf(0), Integer.valueOf(20), v -> ((Boolean)waterMark.getValue()).booleanValue()));
    public Setting<Integer> waterMark2Y = register(new Setting("Watermark2PosY", Integer.valueOf(2), Integer.valueOf(0), Integer.valueOf(100), v -> ((Boolean)waterMark2.getValue()).booleanValue()));
    public Setting<Integer> waterMark3Y = register(new Setting("slolwatermarkY", Integer.valueOf(2), Integer.valueOf(0), Integer.valueOf(100), v -> ((Boolean)waterMark3.getValue()).booleanValue()));
    private final Setting<Boolean> arrayList = register(new Setting("ActiveModules", Boolean.valueOf(true), "Lists the active modules."));
    private final Setting<Boolean> coords = register(new Setting("Coords", Boolean.valueOf(true), "Your current coordinates"));
    private final Setting<Boolean> direction = register(new Setting("Direction", Boolean.valueOf(true), "The Direction you are facing."));
    private final Setting<Boolean> armor = register(new Setting("Armor", Boolean.valueOf(true), "ArmorHUD"));
    private final Setting<Boolean> totems = register(new Setting("Totems", Boolean.valueOf(true), "TotemHUD"));
    private final Setting<Boolean> Crystalsss = register(new Setting("Crystal", Boolean.valueOf(true), "CrystalHUD"));
    private final Setting<Boolean> APSSS = register(new Setting("APPLE", Boolean.valueOf(true), "APPLEHUD"));
    private final Setting<Boolean> XPSSS = register(new Setting("XP", Boolean.valueOf(true), "XPHUD"));
    private Setting<Integer> insss = register(new Setting("distance", Integer.valueOf(16), Integer.valueOf(0), Integer.valueOf(100)));
    private final Setting<Boolean> greeter = register(new Setting("Welcomer", Boolean.valueOf(true), "The time"));
    private final Setting<Boolean> speed = register(new Setting("Speed", Boolean.valueOf(true), "Your Speed"));
    private final Setting<Boolean> potions = register(new Setting("Potions", Boolean.valueOf(true), "Your Speed"));
    private final Setting<Boolean> ping = register(new Setting("Ping", Boolean.valueOf(true), "Your response time to the server."));
    private final Setting<Boolean> tps = register(new Setting("TPS", Boolean.valueOf(true), "Ticks per second of the server."));
    private final Setting<Boolean> fps = register(new Setting("FPS", Boolean.valueOf(true), "Your frames per second."));
    private final Setting<Boolean> lag = register(new Setting("LagNotifier", Boolean.valueOf(true), "The time"));
    private final Setting<Boolean> Friends = register(new Setting("Friends", Boolean.valueOf(false), "Friends"));
    private final Setting<Boolean> customFont = register(new Setting("Custom Font", Boolean.valueOf(false),  v -> ((Boolean)Friends.getValue()).booleanValue()));
    public Setting<Integer> Friendsx = register(new Setting("Friendsx", Integer.valueOf(10), Integer.valueOf(0), Integer.valueOf(1000), v -> ((Boolean)Friends.getValue()).booleanValue()));
    public Setting<Integer> Friendsy = register(new Setting("Friendsy", Integer.valueOf(72), Integer.valueOf(0), Integer.valueOf(1000), v -> ((Boolean)Friends.getValue()).booleanValue()));
    private final Timer timer = new Timer();
    private final Map<String, Integer> players = new HashMap<>();
    public Setting<String> command = register(new Setting("Command", "MeowKu"));
    public Setting<TextUtil.Color> bracketColor = register(new Setting("BracketColor", TextUtil.Color.RED));
    public Setting<TextUtil.Color> commandColor = register(new Setting("NameColor", TextUtil.Color.GRAY));
    public Setting<Boolean> rainbowPrefix = this.register(new Setting<Boolean>("RainbowPrefix", true));
    public Setting<Integer> rainbowSpeed = this.register(new Setting<Object>("PrefixSpeed", Integer.valueOf(20), Integer.valueOf(0), Integer.valueOf(100), v -> this.rainbowPrefix.getValue()));
    public Setting<String> commandBracket = register(new Setting("Bracket", "[-"));
    public Setting<String> commandBracket2 = register(new Setting("Bracket2", "-]"));
    public Setting<Boolean> notifyToggles = register(new Setting("ChatNotify", Boolean.valueOf(true), "notifys in chat"));
    public Setting<Boolean> magenDavid = register(new Setting("MagenDavid", Boolean.valueOf(true), "draws magen david"));
    public Setting<Integer> animationHorizontalTime = register(new Setting("AnimationHTime", Integer.valueOf(500), Integer.valueOf(1), Integer.valueOf(1000), v -> arrayList.getValue().booleanValue()));
    public Setting<Integer> animationVerticalTime = register(new Setting("AnimationVTime", Integer.valueOf(50), Integer.valueOf(1), Integer.valueOf(500), v -> arrayList.getValue().booleanValue()));
    public Setting<RenderingMode> renderingMode = register(new Setting("Ordering", RenderingMode.Length));
    public Setting<Boolean> time = register(new Setting("Time", Boolean.valueOf(true), "The time"));
    public Setting<Integer> lagTime = register(new Setting("LagTime", Integer.valueOf(1000), Integer.valueOf(0), Integer.valueOf(2000)));

    private int color;
    public float hue;
    private boolean shouldIncrement;
    private int hitMarkerTimer;

    public HUD() {
        super("HUD", "HUD Elements rendered on your screen", Module.Category.CLIENT, true, false, false);
        setInstance();
    }

    public static HUD getInstance() {
        if (INSTANCE == null)
            INSTANCE = new HUD();
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    public void onUpdate() {
        if (shouldIncrement)
            hitMarkerTimer++;
        if (hitMarkerTimer == 10) {
            hitMarkerTimer = 0;
            shouldIncrement = false;
        }
    }
    private void drawString(String string, int x, int y) {
        if (this.customFont.getValue()) {
           MeowKu.GUI_FONT_MANAGER.drawStringWithShadow(string, x, y, ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB());
        } else {
            mc.fontRenderer.drawStringWithShadow(string, x, y, ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB());
        }
    }


    private void renderFriends() {
        List<String> friends = new ArrayList<>();
        for (EntityPlayer player : mc.world.playerEntities) {
            if(MeowKu.friendManager.isFriend(player.getName())) {
                friends.add(player.getName());
            }
        }
        int y = Friendsy.getValue();
        if (friends.isEmpty()) {
            drawString(ChatFormatting.BOLD + "You have no friends on this server", Friendsx.getValue(), y);
        } else {
            drawString(ChatFormatting.BOLD + "The_Friends_list", Friendsx.getValue(), y);
            y += 12;
            for (String friend : friends) {
                drawString(friend, Friendsx.getValue(), y);
                y += 12;
            }
        }
    }
    public void onRender2D(Render2DEvent event) {
        if (fullNullCheck())
            return;
        int width = renderer.scaledWidth;
        int height = renderer.scaledHeight;
        color = ColorUtil.toRGBA(((Integer)(ClickGui.getInstance()).red.getValue()).intValue(), ((Integer)(ClickGui.getInstance()).green.getValue()).intValue(), ((Integer)(ClickGui.getInstance()).blue.getValue()).intValue());
        if (((Boolean)waterMark.getValue()).booleanValue()) {
            String string = (String)command.getPlannedValue() + " "+ MeowKu.ID ;
            if (((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue()) {
                if ((ClickGui.getInstance()).rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                    renderer.drawString(string, 2.0F, ((Integer)waterMarkY.getValue()).intValue(), ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB(), true);
                } else {
                    int[] arrayOfInt = { 1 };
                    char[] stringToCharArray = string.toCharArray();
                    float f = 0.0F;
                    for (char c : stringToCharArray) {
                        renderer.drawString(String.valueOf(c), 2.0F + f, ((Integer)waterMarkY.getValue()).intValue(), ColorUtil.rainbow(arrayOfInt[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB(), true);
                        f += renderer.getStringWidth(String.valueOf(c));
                        arrayOfInt[0] = arrayOfInt[0] + 1;
                    }
                }
            } else {
                renderer.drawString(string, 2.0F, ((Integer)waterMarkY.getValue()).intValue(), color, true);
            }
        }
        if (Friends.getValue()) {
            renderFriends();
        }
        if (((Boolean)waterMark2.getValue()).booleanValue()) {
            String string = (String)"MeowKu cool person edition";
            if (((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue()) {
                if ((ClickGui.getInstance()).rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                    renderer.drawString(string, 2.0F, ((Integer)waterMark2Y.getValue()).intValue(), ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB(), true);
                } else {
                    int[] arrayOfInt = { 1 };
                    char[] stringToCharArray = string.toCharArray();
                    float f = 0.0F;
                    for (char c : stringToCharArray) {
                        renderer.drawString(String.valueOf(c), 2.0F + f, ((Integer)waterMark2Y.getValue()).intValue(), ColorUtil.rainbow(arrayOfInt[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB(), true);
                        f += renderer.getStringWidth(String.valueOf(c));
                        arrayOfInt[0] = arrayOfInt[0] + 1;
                    }
                }
            } else {
                renderer.drawString(string, 2.0F, ((Integer)waterMark2Y.getValue()).intValue(), color, true);
            }
        }

        if (((Boolean)waterMark3.getValue()).booleanValue()) {
            String string = (String)"Bright in China";
            if (((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue()) {
                if ((ClickGui.getInstance()).rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                    renderer.drawString(string, 2.0F, ((Integer)waterMark3Y.getValue()).intValue(), ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB(), true);
                } else {
                    int[] arrayOfInt = { 1 };
                    char[] stringToCharArray = string.toCharArray();
                    float f = 0.0F;
                    for (char c : stringToCharArray) {
                        renderer.drawString(String.valueOf(c), 2.0F + f, ((Integer)waterMark3Y.getValue()).intValue(), ColorUtil.rainbow(arrayOfInt[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB(), true);
                        f += renderer.getStringWidth(String.valueOf(c));
                        arrayOfInt[0] = arrayOfInt[0] + 1;
                    }
                }
            } else {
                renderer.drawString(string, 2.0F, ((Integer)waterMark3Y.getValue()).intValue(), color, true);
            }
        }

        int[] counter1 = {1};
        int j = (mc.currentScreen instanceof net.minecraft.client.gui.GuiChat && !renderingUp.getValue().booleanValue()) ? 14 : 0;
        if (arrayList.getValue().booleanValue())
            if (renderingUp.getValue().booleanValue()) {
                if (renderingMode.getValue() == RenderingMode.ABC) {
                    for (int k = 0; k < MeowKu.moduleManager.sortedModulesABC.size(); k++) {
                        String str = MeowKu.moduleManager.sortedModulesABC.get(k);
                        renderer.drawString(str, (width - 2 - renderer.getStringWidth(str)), (2 + j * 10), (ClickGui.getInstance()).rainbow.getValue().booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB()) : color, true);
                        j++;
                        counter1[0] = counter1[0] + 1;
                    }
                } else {
                    for (int k = 0; k < MeowKu.moduleManager.sortedModules.size(); k++) {
                        Module module = MeowKu.moduleManager.sortedModules.get(k);
                        String str = module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
                        renderer.drawString(str, (width - 2 - renderer.getStringWidth(str)), (2 + j * 10), (ClickGui.getInstance()).rainbow.getValue().booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB()) : color, true);
                        j++;
                        counter1[0] = counter1[0] + 1;
                    }
                }
            } else if (renderingMode.getValue() == RenderingMode.ABC) {
                for (int k = 0; k < MeowKu.moduleManager.sortedModulesABC.size(); k++) {
                    String str = MeowKu.moduleManager.sortedModulesABC.get(k);
                    j += 10;
                    renderer.drawString(str, (width - 2 - renderer.getStringWidth(str)), (height - j), (ClickGui.getInstance()).rainbow.getValue().booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB()) : color, true);
                    counter1[0] = counter1[0] + 1;
                }
            } else {
                for (int k = 0; k < MeowKu.moduleManager.sortedModules.size(); k++) {
                    Module module = MeowKu.moduleManager.sortedModules.get(k);
                    String str = module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
                    j += 10;
                    renderer.drawString(str, (width - 2 - renderer.getStringWidth(str)), (height - j), (ClickGui.getInstance()).rainbow.getValue().booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB()) : color, true);
                    counter1[0] = counter1[0] + 1;
                }
            }
        String grayString = grayNess.getValue().booleanValue() ? String.valueOf(ChatFormatting.GRAY) : "";
        int i = (mc.currentScreen instanceof net.minecraft.client.gui.GuiChat && renderingUp.getValue().booleanValue()) ? 13 : (renderingUp.getValue().booleanValue() ? -2 : 0);
        if (renderingUp.getValue().booleanValue()) {
            if (potions.getValue().booleanValue()) {
                List<PotionEffect> effects = new ArrayList<>((Minecraft.getMinecraft()).player.getActivePotionEffects());
                for (PotionEffect potionEffect : effects) {
                    String str = MeowKu.potionManager.getColoredPotionString(potionEffect);
                    i += 10;
                    renderer.drawString(str, (width - renderer.getStringWidth(str) - 2), (height - 2 - i), potionEffect.getPotion().getLiquidColor(), true);
                }
            }
            if (speed.getValue().booleanValue()) {
                String str = grayString + "Speed " + ChatFormatting.WHITE + MeowKu.speedManager.getSpeedKpH() + " km/h";
                i += 10;
                renderer.drawString(str, (width - renderer.getStringWidth(str) - 2), (height - 2 - i), (ClickGui.getInstance()).rainbow.getValue().booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB()) : color, true);
                counter1[0] = counter1[0] + 1;
            }
            if (time.getValue().booleanValue()) {
                String str = grayString + "Time " + ChatFormatting.WHITE + (new SimpleDateFormat("h:mm a")).format(new Date());
                i += 10;
                renderer.drawString(str, (width - renderer.getStringWidth(str) - 2), (height - 2 - i), (ClickGui.getInstance()).rainbow.getValue().booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB()) : color, true);
                counter1[0] = counter1[0] + 1;
            }
            if (tps.getValue().booleanValue()) {
                String str = grayString + "TPS " + ChatFormatting.WHITE + MeowKu.serverManager.getTPS();
                i += 10;
                renderer.drawString(str, (width - renderer.getStringWidth(str) - 2), (height - 2 - i), (ClickGui.getInstance()).rainbow.getValue().booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB()) : color, true);
                counter1[0] = counter1[0] + 1;
            }
            String fpsText = grayString + "FPS " + ChatFormatting.WHITE + Minecraft.debugFPS;
            String str1 = grayString + "Ping " + ChatFormatting.WHITE + MeowKu.serverManager.getPing();
            if (renderer.getStringWidth(str1) > renderer.getStringWidth(fpsText)) {
                if (ping.getValue().booleanValue()) {
                    i += 10;
                    renderer.drawString(str1, (width - renderer.getStringWidth(str1) - 2), (height - 2 - i), (ClickGui.getInstance()).rainbow.getValue().booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB()) : color, true);
                    counter1[0] = counter1[0] + 1;
                }
                if (fps.getValue().booleanValue()) {
                    i += 10;
                    renderer.drawString(fpsText, (width - renderer.getStringWidth(fpsText) - 2), (height - 2 - i), (ClickGui.getInstance()).rainbow.getValue().booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB()) : color, true);
                    counter1[0] = counter1[0] + 1;
                }
            } else {
                if (fps.getValue().booleanValue()) {
                    i += 10;
                    renderer.drawString(fpsText, (width - renderer.getStringWidth(fpsText) - 2), (height - 2 - i), (ClickGui.getInstance()).rainbow.getValue().booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB()) : color, true);
                    counter1[0] = counter1[0] + 1;
                }
                if (ping.getValue().booleanValue()) {
                    i += 10;
                    renderer.drawString(str1, (width - renderer.getStringWidth(str1) - 2), (height - 2 - i), (ClickGui.getInstance()).rainbow.getValue().booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB()) : color, true);
                    counter1[0] = counter1[0] + 1;
                }
            }
        } else {
            if (potions.getValue().booleanValue()) {
                List<PotionEffect> effects = new ArrayList<>((Minecraft.getMinecraft()).player.getActivePotionEffects());
                for (PotionEffect potionEffect : effects) {
                    String str = MeowKu.potionManager.getColoredPotionString(potionEffect);
                    renderer.drawString(str, (width - renderer.getStringWidth(str) - 2), (2 + i++ * 10), potionEffect.getPotion().getLiquidColor(), true);
                }
            }
            if (speed.getValue().booleanValue()) {
                String str = grayString + "Speed " + ChatFormatting.WHITE + MeowKu.speedManager.getSpeedKpH() + " km/h";
                renderer.drawString(str, (width - renderer.getStringWidth(str) - 2), (2 + i++ * 10), (ClickGui.getInstance()).rainbow.getValue().booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB()) : color, true);
                counter1[0] = counter1[0] + 1;
            }
            if (time.getValue().booleanValue()) {
                String str = grayString + "Time " + ChatFormatting.WHITE + (new SimpleDateFormat("h:mm a")).format(new Date());
                renderer.drawString(str, (width - renderer.getStringWidth(str) - 2), (2 + i++ * 10), (ClickGui.getInstance()).rainbow.getValue().booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB()) : color, true);
                counter1[0] = counter1[0] + 1;
            }
            if (tps.getValue().booleanValue()) {
                String str = grayString + "TPS " + ChatFormatting.WHITE + MeowKu.serverManager.getTPS();
                renderer.drawString(str, (width - renderer.getStringWidth(str) - 2), (2 + i++ * 10), (ClickGui.getInstance()).rainbow.getValue().booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB()) : color, true);
                counter1[0] = counter1[0] + 1;
            }
            String fpsText = grayString + "FPS " + ChatFormatting.WHITE + Minecraft.debugFPS;
            String str1 = grayString + "Ping " + ChatFormatting.WHITE + MeowKu.serverManager.getPing();
            if (renderer.getStringWidth(str1) > renderer.getStringWidth(fpsText)) {
                if (ping.getValue().booleanValue()) {
                    renderer.drawString(str1, (width - renderer.getStringWidth(str1) - 2), (2 + i++ * 10), (ClickGui.getInstance()).rainbow.getValue().booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB()) : color, true);
                    counter1[0] = counter1[0] + 1;
                }
                if (fps.getValue().booleanValue()) {
                    renderer.drawString(fpsText, (width - renderer.getStringWidth(fpsText) - 2), (2 + i++ * 10), (ClickGui.getInstance()).rainbow.getValue().booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB()) : color, true);
                    counter1[0] = counter1[0] + 1;
                }
            } else {
                if (fps.getValue().booleanValue()) {
                    renderer.drawString(fpsText, (width - renderer.getStringWidth(fpsText) - 2), (2 + i++ * 10), (ClickGui.getInstance()).rainbow.getValue().booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB()) : color, true);
                    counter1[0] = counter1[0] + 1;
                }
                if (ping.getValue().booleanValue()) {
                    renderer.drawString(str1, (width - renderer.getStringWidth(str1) - 2), (2 + i++ * 10), (ClickGui.getInstance()).rainbow.getValue().booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB()) : color, true);
                    counter1[0] = counter1[0] + 1;
                }

            }
        }
        boolean inHell = mc.world.getBiome(mc.player.getPosition()).getBiomeName().equals("Hell");
        int posX = (int) mc.player.posX;
        int posY = (int) mc.player.posY;
        int posZ = (int) mc.player.posZ;
        float nether = !inHell ? 0.125F : 8.0F;
        int hposX = (int) (mc.player.posX * nether);
        int hposZ = (int) (mc.player.posZ * nether);
        i = (mc.currentScreen instanceof net.minecraft.client.gui.GuiChat) ? 14 : 0;
        String coordinates = ChatFormatting.WHITE + "XYZ " + ChatFormatting.RESET + (inHell ? (posX + ", " + posY + ", " + posZ + ChatFormatting.WHITE + " [" + ChatFormatting.RESET + hposX + ", " + hposZ + ChatFormatting.WHITE + "]" + ChatFormatting.RESET) : (posX + ", " + posY + ", " + posZ + ChatFormatting.WHITE + " [" + ChatFormatting.RESET + hposX + ", " + hposZ + ChatFormatting.WHITE + "]"));
        String direction = this.direction.getValue().booleanValue() ? MeowKu.rotationManager.getDirection4D(false) : "";
        String coords = this.coords.getValue().booleanValue() ? coordinates : "";
        i += 10;
        if ((ClickGui.getInstance()).rainbow.getValue().booleanValue()) {
            String rainbowCoords = this.coords.getValue().booleanValue() ? ("XYZ " + (inHell ? (posX + ", " + posY + ", " + posZ + " [" + hposX + ", " + hposZ + "]") : (posX + ", " + posY + ", " + posZ + " [" + hposX + ", " + hposZ + "]"))) : "";
            if ((ClickGui.getInstance()).rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                renderer.drawString(direction, 2.0F, (height - i - 11), ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB(), true);
                renderer.drawString(rainbowCoords, 2.0F, (height - i), ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB(), true);
            } else {
                int[] counter2 = {1};
                char[] stringToCharArray = direction.toCharArray();
                float s = 0.0F;
                for (char c : stringToCharArray) {
                    renderer.drawString(String.valueOf(c), 2.0F + s, (height - i - 11), ColorUtil.rainbow(counter2[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB(), true);
                    s += renderer.getStringWidth(String.valueOf(c));
                    counter2[0] = counter2[0] + 1;
                }
                int[] counter3 = {1};
                char[] stringToCharArray2 = rainbowCoords.toCharArray();
                float u = 0.0F;
                for (char c : stringToCharArray2) {
                    renderer.drawString(String.valueOf(c), 2.0F + u, (height - i), ColorUtil.rainbow(counter3[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB(), true);
                    u += renderer.getStringWidth(String.valueOf(c));
                    counter3[0] = counter3[0] + 1;
                }
            }
        } else {
            renderer.drawString(direction, 2.0F, (height - i - 11), color, true);
            renderer.drawString(coords, 2.0F, (height - i), color, true);
        }
        if (armor.getValue().booleanValue())
            renderArmorHUD(true);
        if (totems.getValue().booleanValue())
            renderTotemHUD();
        if (greeter.getValue().booleanValue())
            renderGreeter();
        if (lag.getValue().booleanValue())
            renderLag();
        if (Crystalsss.getValue().booleanValue())
             renderCrystalHUD();
        if (XPSSS.getValue().booleanValue())
            renderxpHUD();
        if (APSSS.getValue().booleanValue())
            renderAPHUD();
    }

    public Map<String, Integer> getTextRadarPlayers() {
        return EntityUtil.getTextRadarPlayers();
    }
    public void renderGreeter() {
        int width = renderer.scaledWidth;
        String text = "Welcome, ";
        if (greeter.getValue().booleanValue())
            text = text + mc.player.getDisplayNameString();
        if ((ClickGui.getInstance()).rainbow.getValue().booleanValue()) {
            if ((ClickGui.getInstance()).rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                renderer.drawString(text, width / 2.0F - renderer.getStringWidth(text) / 2.0F + 2.0F, 2.0F, ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB(), true);
            } else {
                int[] counter1 = {1};
                char[] stringToCharArray = text.toCharArray();
                float i = 0.0F;
                for (char c : stringToCharArray) {
                    renderer.drawString(String.valueOf(c), width / 2.0F - renderer.getStringWidth(text) / 2.0F + 2.0F + i, 2.0F, ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue().intValue()).getRGB(), true);
                    i += renderer.getStringWidth(String.valueOf(c));
                    counter1[0] = counter1[0] + 1;
                }
            }
        } else {
            renderer.drawString(text, width / 2.0F - renderer.getStringWidth(text) / 2.0F + 2.0F, 2.0F, color, true);
        }
    }

    public void renderLag() {
        int width = renderer.scaledWidth;
        if (MeowKu.serverManager.isServerNotResponding()) {
            String text = ChatFormatting.RED + "Server lagging for " + MathUtil.round((float) MeowKu.serverManager.serverRespondingTime() / 1000.0F, 1) + "s.";
            renderer.drawString(text, width / 2.0F - renderer.getStringWidth(text) / 2.0F + 2.0F, 20.0F, color, true);
        }
    }

    public void renderTotemHUD() {
        int width = renderer.scaledWidth;
        int height = renderer.scaledHeight;
        int totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.TOTEM_OF_UNDYING)).mapToInt(ItemStack::getCount).sum();
        if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING)
            totems += mc.player.getHeldItemOffhand().getCount();
        if (totems > 0) {
            GlStateManager.enableTexture2D();
            int i = width / 2;
            int iteration = 0;
            int y = height - 55 - ((mc.player.isInWater() && mc.playerController.gameIsSurvivalOrAdventure()) ? 10 : 0);
            int x = i - 189 + 180 + 2;
            GlStateManager.enableDepth();
            RenderUtil.itemRender.zLevel = 200.0F;
            RenderUtil.itemRender.renderItemAndEffectIntoGUI(totem, x, y);
            RenderUtil.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, totem, x, y, "");
            RenderUtil.itemRender.zLevel = 0.0F;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            renderer.drawStringWithShadow(totems + "", (x + 19 - 2 - renderer.getStringWidth(totems + "")), (y + 9), 16777215);
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }
    }
    public void renderCrystalHUD() {
        int width = renderer.scaledWidth;
        int height = renderer.scaledHeight;
        int Crystals = mc.player.inventory.mainInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.END_CRYSTAL)).mapToInt(ItemStack::getCount).sum();
        if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL)
            Crystals += mc.player.getHeldItemOffhand().getCount();
        if (Crystals > 0) {
            GlStateManager.enableTexture2D();
            int i = width / 2;
            int iteration = 0;
            int y = height - 55 - ((mc.player.isInWater() && mc.playerController.gameIsSurvivalOrAdventure()) ? 10 : 0);
            int x = i - 189 + 180 + 2;
            if(totems.getValue()){
                y=y-insss.getValue();
            }
            GlStateManager.enableDepth();
            RenderUtil.itemRender.zLevel = 200.0F;
            RenderUtil.itemRender.renderItemAndEffectIntoGUI(Crystal, x, y);
            RenderUtil.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, Crystal, x, y, "");
            RenderUtil.itemRender.zLevel = 0.0F;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            renderer.drawStringWithShadow(Crystals + "", (x + 19 - 2 - renderer.getStringWidth(Crystals + "")), (y + 9), 16777215);
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }
    }
    public void renderxpHUD() {
        int width = renderer.scaledWidth;
        int height = renderer.scaledHeight;
        int Crystals = mc.player.inventory.mainInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.EXPERIENCE_BOTTLE)).mapToInt(ItemStack::getCount).sum();
        if (mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE)
            Crystals += mc.player.getHeldItemOffhand().getCount();
        if (Crystals > 0) {
            GlStateManager.enableTexture2D();
            int i = width / 2;
            int iteration = 0;
            int y = height - 55 - ((mc.player.isInWater() && mc.playerController.gameIsSurvivalOrAdventure()) ? 10 : 0);
            int x = i - 189 + 180 + 2;
            if(totems.getValue()){
                y=y-insss.getValue();
            }
            if(Crystalsss.getValue()){
                y=y-insss.getValue();
            }
            GlStateManager.enableDepth();
            RenderUtil.itemRender.zLevel = 200.0F;
            RenderUtil.itemRender.renderItemAndEffectIntoGUI(xp, x, y);
            RenderUtil.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, xp, x, y, "");
            RenderUtil.itemRender.zLevel = 0.0F;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            renderer.drawStringWithShadow(Crystals + "", (x + 19 - 2 - renderer.getStringWidth(Crystals + "")), (y + 9), 16777215);
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }
    }
    public void renderAPHUD() {
        int width = renderer.scaledWidth;
        int height = renderer.scaledHeight;
        int Crystals = mc.player.inventory.mainInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.GOLDEN_APPLE)).mapToInt(ItemStack::getCount).sum();
        if (mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE)
            Crystals += mc.player.getHeldItemOffhand().getCount();
        if (Crystals > 0) {
            GlStateManager.enableTexture2D();
            int i = width / 2;
            int iteration = 0;
            int y = height - 55 - ((mc.player.isInWater() && mc.playerController.gameIsSurvivalOrAdventure()) ? 10 : 0);
            int x = i - 189 + 180 + 2;
            if(totems.getValue()){
                y=y-insss.getValue();
            }
            if(Crystalsss.getValue()){
                y=y-insss.getValue();
            }
            if(XPSSS.getValue()){
                y=y-insss.getValue();
            }

            GlStateManager.enableDepth();
            RenderUtil.itemRender.zLevel = 200.0F;
            RenderUtil.itemRender.renderItemAndEffectIntoGUI(ap, x, y);
            RenderUtil.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, ap, x, y, "");
            RenderUtil.itemRender.zLevel = 0.0F;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            renderer.drawStringWithShadow(Crystals + "", (x + 19 - 2 - renderer.getStringWidth(Crystals + "")), (y + 9), 16777215);
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }
    }
    public void renderArmorHUD(boolean percent) {
        int width = renderer.scaledWidth;
        int height = renderer.scaledHeight;
        GlStateManager.enableTexture2D();
        int i = width / 2;
        int iteration = 0;
        int y = height - 55 - (mc.player.isInWater() && mc.playerController.gameIsSurvivalOrAdventure() ? 10 : 0);
        for (ItemStack is : mc.player.inventory.armorInventory) {
            iteration++;
            if (is.isEmpty()) continue;
            int x = i - 90 + (9 - iteration) * 20 + 2;
            GlStateManager.enableDepth();
            RenderUtil.itemRender.zLevel = 200F;
            RenderUtil.itemRender.renderItemAndEffectIntoGUI(is, x, y);
            RenderUtil.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, is, x, y, "");
            RenderUtil.itemRender.zLevel = 0F;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            String s = is.getCount() > 1 ? is.getCount() + "" : "";
            renderer.drawStringWithShadow(s, x + 19 - 2 - renderer.getStringWidth(s), y + 9, 0xffffff);
            //mc.fontRenderer.drawStringWithShadow(s, x + 19 - 2 - mc.fontRenderer.getStringWidth(s), y + 9, 0xffffff);

            if (percent) {
                int dmg = 0;
                int itemDurability = is.getMaxDamage() - is.getItemDamage();
                float green = ((float) is.getMaxDamage() - (float) is.getItemDamage()) / (float) is.getMaxDamage();
                float red = 1 - green;
                if (percent) {
                    dmg = 100 - (int) (red * 100);
                } else {
                    dmg = itemDurability;
                }
                renderer.drawStringWithShadow(dmg + "", x + 8 - renderer.getStringWidth(dmg + "") / 2, y - 11, ColorUtil.toRGBA((int) (red * 255), (int) (green * 255), 0));
            }
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }

    @SubscribeEvent
    public void onUpdateWalkingPlayer(AttackEntityEvent event) {
        shouldIncrement = true;
    }

    public void onLoad() {
        MeowKu.commandManager.setClientMessage(getCommandMessage());
    }

    @SubscribeEvent
    public void onSettingChange(ClientEvent event) {
        if (event.getStage() == 2 &&
                equals(event.getSetting().getFeature()))
            MeowKu.commandManager.setClientMessage(getCommandMessage());
    }


    public String getCommandMessage() {
        if (this.rainbowPrefix.getPlannedValue().booleanValue()) {
            StringBuilder stringBuilder = new StringBuilder(this.getRawCommandMessage());
            stringBuilder.insert(0, "\u00a7+");
            stringBuilder.append("\u00a7r");
            return stringBuilder.toString();
        }
        return TextUtil.coloredString(this.commandBracket.getPlannedValue(), this.bracketColor.getPlannedValue()) + TextUtil.coloredString(this.command.getPlannedValue(), this.commandColor.getPlannedValue()) + TextUtil.coloredString(this.commandBracket2.getPlannedValue(), this.bracketColor.getPlannedValue());
    }

    public String getRainbowCommandMessage() {
        StringBuilder stringBuilder = new StringBuilder(this.getRawCommandMessage());
        stringBuilder.insert(0, "\u00a7+");
        stringBuilder.append("\u00a7r");
        return stringBuilder.toString();
    }

    public String getRawCommandMessage() {
        return this.commandBracket.getValue() + this.command.getValue() + this.commandBracket2.getValue();
    }

    public void drawTextRadar(int yOffset) {
        if (!players.isEmpty()) {
            int y = renderer.getFontHeight() + 7 + yOffset;
            for (Map.Entry<String, Integer> player : players.entrySet()) {
                String text = player.getKey() + " ";
                int textheight = renderer.getFontHeight() + 1;
                renderer.drawString(text, 2.0F, y, color, true);
                y += textheight;
            }
        }
    }

    public enum RenderingMode {
        Length, ABC
    }
}
