package net.bxx2004.pandalib.bukkit.rpg.task;

import net.bxx2004.pandalib.PandaLib;
import net.bxx2004.pandalib.bukkit.listener.PListener;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class TaskImpl implements Task,TaskListener{
    private HashMap<Player,TaskState> states;
    private String name;
    private Consumer<Player> view;
    private Consumer<Player> start;
    private BiConsumer<Player,TaskState> end;
    public TaskImpl(String name){
        this.name = name;
        states = new HashMap<>();
        if (listener() != null){
            listener().hook(PandaLib.initPlugin.getName());
        }
    }
    @Override
    public boolean isFinish(Player player) {
        if (states.containsKey(player)){
           if (states.get(player) == TaskState.DOING){
               return false;
           } else {
               return true;
           }
        }else {
            return true;
        }
    }

    @Override
    public boolean isApply(Player player) {
        return states.containsKey(player);
    }

    @Override
    public void apply(Player player) {
        states.put(player,TaskState.DOING);
        onStart(player);
        start.accept(player);
        net.bxx2004.pandalib.bukkit.task.Task.submitAsynchronously(0,20, runnable -> {
            if (isFinish(player)){
                runnable.cancel();
            }else {
                view.accept(player);
                onActive(player);
            }
        });
    }

    @Override
    public void view(Consumer<Player> start, Consumer<Player> view, BiConsumer<Player,TaskState> end) {
        this.start = start;
        this.view = view;
        this.end = end;
    }

    @Override
    public void finish(Player player, TaskState result) {
        states.put(player,TaskState.SUCCESS);
        end.accept(player,result);
        onStop(player);
    }

    @Override
    public String name() {
        return this.name;
    }
    public abstract PListener listener();
}
