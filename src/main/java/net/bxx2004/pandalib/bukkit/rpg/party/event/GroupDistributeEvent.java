package net.bxx2004.pandalib.bukkit.rpg.party.event;

import net.bxx2004.pandalib.bukkit.listener.event.PandaLibExtendEvent;
import net.bxx2004.pandalib.bukkit.rpg.party.Group;

public class GroupDistributeEvent extends PandaLibExtendEvent {
    private boolean c;
    private Group group;
    @Override
    public void setCancelled(boolean bl) {
        this.c =bl;
    }

    @Override
    public boolean isCancelled() {
        return c;
    }
    public GroupDistributeEvent(Group group){
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }
}
