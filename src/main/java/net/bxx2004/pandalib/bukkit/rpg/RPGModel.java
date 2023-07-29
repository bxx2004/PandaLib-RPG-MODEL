package net.bxx2004.pandalib.bukkit.rpg;

import net.bxx2004.pandalib.bukkit.item.PItemStack;

import java.util.function.Consumer;

public class RPGModel {
    public static final String VERSION = "3.0.0";
    public static void containLore(PItemStack stack, String id, Consumer<String> c){
        if (stack != null && stack.hasItemMeta() && stack.getItemMeta().hasLore()){
            stack.getItemMeta().getLore().forEach(
                    s -> {
                        if (s.contains(id)){
                            c.accept(s);
                        }
                    }
            );
        }
    }
}
