package net.bxx2004.pandalib.bukkit.rpg.party.event;


import net.bxx2004.pandalib.bukkit.listener.event.PandaLibExtendEvent;
import net.bxx2004.pandalib.bukkit.rpg.party.Group;
import org.bukkit.entity.Player;

public class GroupAddPlayerEvent extends PandaLibExtendEvent {
    private Player player;
    private Group group;
    private boolean cancel;
    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean bl) {
        this.cancel = cancel;
    }
    public GroupAddPlayerEvent(Player player, Group group){
        this.group = group;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Group getGroup() {
        return group;
    }
}
