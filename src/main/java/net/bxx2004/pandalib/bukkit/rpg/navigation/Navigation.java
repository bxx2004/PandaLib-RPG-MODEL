package net.bxx2004.pandalib.bukkit.rpg.navigation;

import net.bxx2004.pandalib.PandaLib;
import net.bxx2004.pandalib.bukkit.listener.PListener;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Navigation {
    private Location start;
    private Location end;
    private Consumer<PlayerMoveEvent> move = null;
    private List<Player> players = new ArrayList<Player>();
    private NavigationHandler handler;
    private BiConsumer<Double,Boolean> view;
    private boolean line = false;
    private boolean light = false;
    private ParticleBuilder lightp = new ParticleBuilder(ParticleEffect.VILLAGER_HAPPY);
    private ParticleBuilder linep = new ParticleBuilder(ParticleEffect.REDSTONE).setColor(Color.PINK);
    private Method method = Method.BLOCK;
    public Navigation start(Location start){
        this.start = start;
        return this;
    }
    public Navigation end(Location end){
        this.end = end;
        return this;
    }
    public Navigation view(BiConsumer<Double,Boolean> value){
        this.view = value;
        return this;
    }
    public Navigation move(Consumer<PlayerMoveEvent> value){
        this.move = value;
        return this;
    }
    public Navigation light(boolean a, ParticleBuilder particleBuilder){
        this.light = a;
        if (particleBuilder != null){
            this.lightp = particleBuilder;
        }
        return this;
    }
    public Navigation line(Method method, boolean a, ParticleBuilder particleBuilder){
        this.line = a;
        if (particleBuilder != null){
            this.linep = particleBuilder;
        }
        if (method != null){
            this.method = method;
        }
        return this;
    }
    public Navigation build(){
        handler = new NavigationHandler() {
            private PListener listener = new PListener(){
                @EventHandler
                public void onMove(PlayerMoveEvent event){
                    try{
                        if (players.contains(event.getPlayer())){
                            move(event);
                            Player player = event.getPlayer();
                            if (event.getTo().getWorld().getName().equals(end.getWorld().getName())){
                                if (player.getLocation().getBlock().getLocation().clone().add(0,-1,0).equals(end)){
                                    view(0.00,false);
                                    players.remove(player);
                                }else {
                                    if (light){
                                        Location location = end;
                                        for (int i = 0; i < 256; i++){
                                            lightp.setLocation(location);
                                            lightp.display(player);
                                            location = location.clone().add(0,0.5,0);
                                        }
                                    }
                                    if (line){
                                        getPath(method,player.getLocation().clone().add(0,1,0),end).forEach(l -> {
                                            linep.setLocation(l);
                                            linep.display(player);
                                        });
                                    }
                                    view(player.getLocation().distance(end),true);
                                }
                            }else {
                                view(-1.00,false);
                            }
                        }
                    }catch (Exception e){}
                }
            }.hook(PandaLib.initPlugin.getName());
            @Override
            public Location start() {
                return start;
            }

            @Override
            public Location end() {
                return end;
            }

            @Override
            public void view(double distance, boolean state) {
                view.accept(distance,state);
            }

            @Override
            public void move(PlayerMoveEvent event) {
                if (move != null){
                    move.accept(event);
                }
            }

            @Override
            public void destroy() {
                listener.unhook();
            }
        };
        return this;
    }
    public void use(Player... player){
        for (Player player1 : player) {
            players.add(player1);
        }
    }
    public void cancel(Consumer<Player[]> consumer,Player... player){
        consumer.accept(player);
        cancel(player);
    }
    public void cancel(Player... player){
        for (Player player1 : player) {
            players.remove(player1);
        }
    }
    public static boolean isEnd(Player player,Location location){
        if (player.getLocation().getBlock().getLocation().clone().add(0,-1,0).equals(location)){
            return true;
        }
        return false;
    }
    public static List<Location> getPath(Method method,Location start,Location end){
        List<Location> locations = new ArrayList<>();
        int length = (int) start.distance(end);
        Location n = start;
        for (int i = 0; i < length; i++) {
            locations.add(n);
            n = near(method,n,end);
        }
        return locations;
    }
    public static Location near(Method method,Location location,Location end){
        double distance = location.distance(end);
        if (method == Method.NEAR){
            HashMap<Double,Location> map = new HashMap<>();
            for (BlockFace face : BlockFace.values()){
                if (location.getBlock().getRelative(face).getLocation().distance(end) < distance){
                    map.put(location.getBlock().getRelative(face).getLocation().distance(end),location.getBlock().getRelative(face).getLocation());
                }
            }
            List<Double> key = new ArrayList<>();
            map.keySet().forEach( s -> {
                        key.add(s);
                    }
            );
            key.sort(((o1, o2) -> {
                if (o1 > o2){
                    return -1;
                }else {
                    return 1;
                }
            }));
            if (key.size() != 0){
                return map.get(key.get(key.size() - 1));
            }else {
                return end;
            }
        }else {
            for (BlockFace face : BlockFace.values()){
                if (location.getBlock().getRelative(face).getLocation().distance(end) < distance){
                    return location.getBlock().getRelative(face).getLocation();
                }
            }
            return null;
        }
    }
    public enum Method{
        BLOCK,
        NEAR
    }
}
