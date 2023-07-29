package net.bxx2004.pandalib.bukkit.rpg.hook;

import me.clip.placeholderapi.PlaceholderAPI;
import net.bxx2004.pandalib.bukkit.util.PMath;
import org.bukkit.OfflinePlayer;

import java.util.List;

public class PlaceHolderAPI {
    public static List<String> replace(OfflinePlayer player, List<String> list){
        return PlaceholderAPI.setPlaceholders(player, list);
    }
    public static String[] replace(OfflinePlayer player, String[] list){
        return PMath.toStringArray(PlaceholderAPI.setPlaceholders(player, PMath.toStringList(list)));
    }
    public static String replace(OfflinePlayer player,String s){
        return PlaceholderAPI.setPlaceholders(player, s);
    }
}
