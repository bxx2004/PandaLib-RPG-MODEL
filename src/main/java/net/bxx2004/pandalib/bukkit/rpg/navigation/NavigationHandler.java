package net.bxx2004.pandalib.bukkit.rpg.navigation;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

public abstract class NavigationHandler {
    public abstract Location start();
    public abstract Location end();
    public abstract void view(double distance,boolean state);
    public abstract void move(PlayerMoveEvent event);
    public abstract void destroy();
}
