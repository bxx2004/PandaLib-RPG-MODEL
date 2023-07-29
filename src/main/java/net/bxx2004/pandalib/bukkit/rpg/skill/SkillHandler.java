package net.bxx2004.pandalib.bukkit.rpg.skill;

import net.bxx2004.pandalib.bukkit.manager.Lang;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public abstract class SkillHandler {
    public abstract String name();
    public abstract void cast(LivingEntity caster, LivingEntity target);
    public abstract double cooling();
    public void use(LivingEntity caster){
        if (caster instanceof Player){
            if (!Skill.pCooldown.keySet().contains(name())){
                Skill.pCooldown.addKey(name());
            }
            if (Skill.pCooldown.is(name(), (OfflinePlayer) caster)){
                Lang.error("玩家技能冷却中",name(),String.valueOf(cooling()));
            }else {
                Skill.pCooldown.add(name(),(OfflinePlayer)caster,(float) cooling());
                cast(caster, (LivingEntity) caster.getTargetEntity(10));
                return;
            }
        }else {
            cast(caster, (LivingEntity) caster.getTargetEntity(10));
        }
    }
    public static void use(SkillHandler skill,LivingEntity caster){
        skill.use(caster);
    }
}
