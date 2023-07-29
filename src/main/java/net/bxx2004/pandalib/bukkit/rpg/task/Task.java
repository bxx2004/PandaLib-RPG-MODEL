package net.bxx2004.pandalib.bukkit.rpg.task;

import org.bukkit.entity.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Task {
    public boolean isFinish(Player player);
    public boolean isApply(Player player);
    public void apply(Player player);
    public void finish(Player player, TaskState result);
    public void view(Consumer<Player> start, Consumer<Player> view, BiConsumer<Player,TaskState> end);
    public String name();
}
