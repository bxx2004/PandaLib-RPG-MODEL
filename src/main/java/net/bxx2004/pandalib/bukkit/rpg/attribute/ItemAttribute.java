package net.bxx2004.pandalib.bukkit.rpg.attribute;


import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.bxx2004.pandalib.bukkit.item.PItemStack;

public interface ItemAttribute {
    public void onHeld(PItemStack stack);
    public void onWear(PlayerArmorChangeEvent.SlotType slot, PItemStack stack);
    public void inInventory(int slot,PItemStack stack);
}
