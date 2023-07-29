package net.bxx2004.pandalib.bukkit.rpg.task;

import org.bukkit.entity.Player;

public interface TaskListener {
    void onStart(Player player);
    void onActive(Player player);
    void onStop(Player player);
}
