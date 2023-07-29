package net.bxx2004.pandalib.bukkit.rpg.view;

import de.tr7zw.changeme.nbtapi.NBTEntity;
import net.bxx2004.pandalib.PandaLib;
import net.bxx2004.pandalib.bukkit.entity.PDisplay;
import net.bxx2004.pandalib.bukkit.listener.PListener;
import net.bxx2004.pandalib.bukkit.rpg.hook.PlaceHolderAPI;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.BiConsumer;

public class Hologram {
    private PDisplay display;
    private String id;
    public Hologram(String id){
        this.id = id;
        this.display = new PDisplay(id);
    }
    public Hologram content(String... content){
        this.display.inputContent(content);
        return this;
    }
    public Hologram content(boolean placeholderAPI, OfflinePlayer player,String... content){
        if (!placeholderAPI){
            content(content);
        }else {
            content(PlaceHolderAPI.replace(player,content));
        }
        display.refresh();
        return this;
    }
    public Hologram distance(double distance){
        this.display.setDistance(distance);
        return this;
    }
    public Hologram clear(){
        this.display.clear();
        return this;
    }
    public Hologram replace(int line,String c){
        this.display.replace(line,c);
        return this;
    }
    public Hologram build(Location location){
        this.display.spawn(location);
        return this;
    }
    public Hologram build(int time,Location location){
        this.display.spawn(location);
        new BukkitRunnable(){
            @Override
            public void run() {
                remove();
                cancel();
            }
        }.runTaskLater(PandaLib.initPlugin,time * 20);
        return this;
    }
    public void teleport(Location location){
        this.display.teleport(location);
    }
    public void remove(){
        this.display.remove();
    }
    public void refresh(){
        this.display.refresh();
    }
    public void click(BiConsumer<String, Player> consumer){
        new PListener(){
            @EventHandler
            public void onClick(PlayerInteractEntityEvent event){
                if (event.getRightClicked().getScoreboardTags().contains("pdisplay")){
                    consumer.accept(id,event.getPlayer());
                }
                unhook();
            }
        }.hook(PandaLib.initPlugin.getName());
    }
}
