package net.bxx2004.pandalib.bukkit.rpg.task.predefine;

import net.bxx2004.pandalib.bukkit.listener.PListener;
import net.bxx2004.pandalib.bukkit.rpg.task.TaskImpl;
import net.bxx2004.pandalib.bukkit.rpg.task.TaskState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.function.Consumer;

public class SearchNPCTask extends TaskImpl {
    private Entity entity;
    public SearchNPCTask(String name,Entity entity) {
        super(name);
        this.entity= entity;
    }

    @Override
    public void onStart(Player player) {
    }

    @Override
    public void onActive(Player player) {
    }

    @Override
    public void onStop(Player player) {}

    @Override
    public PListener listener() {
        return new PListener(){
          @EventHandler
          public void onInteract(PlayerInteractEntityEvent event){
              if (isApply(event.getPlayer())){
                  if (!isFinish(event.getPlayer())){
                      if (event.getRightClicked().getUniqueId().equals(entity.getUniqueId())){
                          finish(event.getPlayer(), TaskState.SUCCESS);
                      }
                  }
              }
          }
        };
    }
}
