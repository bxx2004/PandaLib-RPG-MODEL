package net.bxx2004.pandalib.bukkit.rpg.attribute;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.bxx2004.pandalib.PandaLib;
import net.bxx2004.pandalib.bukkit.item.PItemStack;
import net.bxx2004.pandalib.bukkit.listener.PListener;
import net.bxx2004.pandalib.bukkit.rpg.RPGModel;
import net.bxx2004.pandalib.bukkit.util.PMath;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.regex.Pattern;

public abstract class AttributeHandler {
    public AttributeHandler(){
        if (this instanceof EntityAttribute){
            EntityAttribute ea = (EntityAttribute) this;
            new BukkitRunnable(){
                @Override
                public void run() {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if (ea.has(player)){
                            ea.ifHave();
                        }
                    });
                }
            }.runTaskTimerAsynchronously(PandaLib.initPlugin,0,20);
        }
        if (this instanceof ItemAttribute){
            ItemAttribute ea = (ItemAttribute) AttributeHandler.this;
            new BukkitRunnable(){
                @Override
                public void run() {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.getInventory().forEach(item -> {
                            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()){
                                new PItemStack(item).searchLore(name(), s -> {
                                    ea.inInventory(player.getInventory().first(item),new PItemStack(item));
                                });
                            }
                        });
                    });
                }
            }.runTaskTimerAsynchronously(PandaLib.initPlugin,0,20);
        }
        new PListener(){
            @EventHandler
            public void dam(EntityDamageByEntityEvent event){
                LivingEntity entity = (LivingEntity) event.getDamager();
                PItemStack stack = new PItemStack(entity.getEquipment().getItemInMainHand());
                if (stack.hasItemMeta() && stack.getItemMeta().hasLore()){
                    stack.searchLore(name(), s -> {
                        if (chance((LivingEntity)event.getDamager()) > PMath.getRandomAsInt(1,100)){
                            event.setDamage(onDamage(event.getDamage(),(
                                    LivingEntity)event.getEntity(),(
                                    LivingEntity)event.getDamager(),entity.getEquipment()));
                        }
                    });
                }
                PItemStack sstack = new PItemStack(entity.getEquipment().getItemInOffHand());
                if (sstack.hasItemMeta() && sstack.getItemMeta().hasLore()){
                    sstack.searchLore(name(), s -> {
                        if (chance((LivingEntity)event.getDamager()) > PMath.getRandomAsInt(1,100)){
                            event.setDamage(onDamage(event.getDamage(),(
                                    LivingEntity)event.getEntity(),(
                                    LivingEntity)event.getDamager(),entity.getEquipment()));
                        }
                    });
                }
            }
            @EventHandler
            public void held(PlayerItemHeldEvent event){
                if (AttributeHandler.this instanceof ItemAttribute){
                    ItemAttribute ea = (ItemAttribute) AttributeHandler.this;
                    try {
                        new PItemStack(event.getPlayer().getInventory().getItem(event.getNewSlot())).searchLore(name(), s -> {
                            ea.onHeld(new PItemStack(event.getPlayer().getInventory().getItem(event.getNewSlot())));
                        });
                    }catch (Exception e){}
                }
            }
            @EventHandler
            public void wear(PlayerArmorChangeEvent event){
                if (AttributeHandler.this instanceof ItemAttribute){
                    ItemAttribute ea = (ItemAttribute) AttributeHandler.this;
                    try {
                        new PItemStack(event.getNewItem()).searchLore(name(), s -> {
                            ea.onWear(event.getSlotType(),new PItemStack(event.getNewItem()));
                        });
                    }catch (Exception e){}
                }
            }
        }.hook(PandaLib.initPlugin.getName());
    }
    abstract public String name();
    abstract public double onDamage(double origin, LivingEntity damaged, LivingEntity attacker, EntityEquipment stack);
    abstract public double chance(LivingEntity runner);
    public double power(PItemStack stack){
        final double[] a = new double[1];
        if (this instanceof ItemAttribute){
            RPGModel.containLore(stack,name(), s -> {
                PMath.regx("([1-9]\\d*\\.?\\d+)|(0\\.\\d*[1-9])|(\\d+)",s, value -> {
                    a[0] = Double.parseDouble(value);
                });
            });
            return a[0];
        }
        return 0.00;
    }
    public String lore(PItemStack stack){
        final String[] a = new String[1];
        if (this instanceof ItemAttribute){
            RPGModel.containLore(stack,name(), s -> {
                a[0] = s;
            });
            return a[0];
        }
        return "";
    }
    public abstract double power(LivingEntity entity);
    public double allPower(PItemStack stack,LivingEntity entity){
        return power(stack) + power(entity);
    }
}
