package net.bxx2004.pandalib.bukkit.rpg.attribute;

import org.bukkit.entity.LivingEntity;

public interface EntityAttribute {
    public void ifHave();
    public boolean has(LivingEntity entity);
}
