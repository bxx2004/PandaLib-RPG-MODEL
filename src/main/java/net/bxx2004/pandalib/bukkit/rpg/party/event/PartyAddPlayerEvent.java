package net.bxx2004.pandalib.bukkit.rpg.party.event;

import net.bxx2004.pandalib.bukkit.listener.event.PandaLibExtendEvent;
import net.bxx2004.pandalib.bukkit.rpg.party.element.Party;
import org.bukkit.entity.Player;

public class PartyAddPlayerEvent extends PandaLibExtendEvent {
    private boolean c;
    private Player player;
    private Party party;
    @Override
    public void setCancelled(boolean bl) {
        this.c =bl;
    }

    @Override
    public boolean isCancelled() {
        return c;
    }
    public PartyAddPlayerEvent(Party party,Player player){
        this.party = party;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Party getParty() {
        return party;
    }
}
