package net.bxx2004.pandalib.bukkit.rpg.task.predefine;

import net.bxx2004.pandalib.bukkit.listener.PListener;
import net.bxx2004.pandalib.bukkit.rpg.task.TaskImpl;
import net.bxx2004.pandalib.bukkit.rpg.task.TaskState;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class KillMobTask extends TaskImpl {
    private MobCondition condition;
    private HashMap<Player,Integer> killAmount;
    public KillMobTask(String name,MobCondition condition) {
        super(name);
        this.condition = condition;
        killAmount = new HashMap<>();
    }

    @Override
    public PListener listener() {
        return new PListener(){
          @EventHandler
          public void onKill(EntityDeathEvent event){
              try {
                  Player player = event.getEntity().getKiller();
                  if (isApply(player)){
                      if (!isFinish(player)){
                          if (!condition.check(event.getEntity())){
                              return;
                          }
                          if (killAmount.containsKey(player)){
                              killAmount.put(player,killAmount.get(player) + 1);
                          }else {
                              killAmount.put(player,1);
                          }
                          if (killAmount.get(player) >= condition.amount){
                              finish(player,TaskState.SUCCESS);
                          }
                      }
                  }
              }catch (Exception e){}
          }
        };
    }

    @Override
    public void onStart(Player player) {

    }

    @Override
    public void onActive(Player player) {

    }

    @Override
    public void onStop(Player player) {

    }
    public static MobCondition condition(int amount, String mobName, EntityType type, World world){
        return new MobCondition(amount, mobName, type, world);
    }
    static class MobCondition{
        private int amount;
        private String mobName;
        private EntityType type;
        private World world;

        public MobCondition(int amount, String mobName, EntityType type, World world) {
            this.amount = amount;
            this.mobName = mobName;
            this.type = type;
            this.world = world;
        }

        public int getAmount() {
            return amount;
        }

        public MobCondition setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public String getMobName() {
            return mobName;
        }

        public MobCondition setMobName(String mobName) {
            this.mobName = mobName;
            return this;
        }

        public EntityType getType() {
            return type;
        }

        public MobCondition setType(EntityType type) {
            this.type = type;
            return this;
        }

        public World getWorld() {
            return world;
        }

        public MobCondition setWorld(World world) {
            this.world = world;
            return this;
        }
        public boolean check(Entity entity){
            boolean worldB = true;
            boolean nameB = true;
            boolean typeB = true;
            if (world != null){
                if (!world.getName().equals(entity.getWorld().getName())){
                    worldB = false;
                }
            }
            if (entity.getCustomName() != null){
                if (!mobName.equals(entity.getCustomName())){
                    worldB = false;
                }
            }
            if (type != null){
                if (!type.equals(entity.getType())){
                    typeB = false;
                }
            }
            return worldB && nameB && typeB;
        }
    }
}
