/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.Item
 */
package dev.lovelyneru.MeowKu.features.modules.combat;

import dev.lovelyneru.MeowKu.features.setting.Setting;
import dev.lovelyneru.MeowKu.features.modules.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;

public class AutoTotem
        extends Module {
    private final Setting<Boolean> strict = this.register(new Setting<Boolean>("Strict", true));
    private final Setting<Integer> health = this.register(new Setting<Integer>("Health", Integer.valueOf(13), Integer.valueOf(0), Integer.valueOf(36), v -> this.strict.getValue()));

    public AutoTotem() {
        super("AutoTotem", "Main Hand AutoTotem", Module.Category.COMBAT, true, false, false);
    }

    public static int getItemSlot(Item item) {
        int itemSlot = -1;
        for (int i = 45; i > 0; --i) {
            if (!AutoTotem.mc.player.inventory.getStackInSlot(i).getItem().equals(item)) continue;
            itemSlot = i;
            break;
        }
        return itemSlot;
    }

    @Override
    public void onUpdate() {
        int slot;
        if (AutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            return;
        }
        if (AutoTotem.mc.player.getHeldItemMainhand().getItem() == Items.TOTEM_OF_UNDYING) {
            return;
        }
        int hotBarSlot = -1;
        int totemSlot = AutoTotem.getItemSlot(Items.TOTEM_OF_UNDYING);
        for (int l = 0; l < 9; ++l) {
            if (AutoTotem.mc.player.inventory.getStackInSlot(l).getItem() != Items.TOTEM_OF_UNDYING) continue;
            hotBarSlot = l;
            break;
        }
        int n = slot = totemSlot < 9 ? totemSlot + 36 : totemSlot;
        if (totemSlot != -1) {
            AutoTotem.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
            AutoTotem.mc.playerController.windowClick(0, 36, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
            AutoTotem.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
            if (hotBarSlot != -1 && AutoTotem.mc.player.getHealth() <= (float)this.health.getValue().intValue() && this.strict.getValue() != false || hotBarSlot != -1 && !this.strict.getValue().booleanValue()) {
                AutoTotem.mc.player.inventory.currentItem = hotBarSlot;
            }
        }
    }
}

