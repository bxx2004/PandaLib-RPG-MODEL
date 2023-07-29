package net.bxx2004.pandalib.bukkit.rpg.skill;

import net.bxx2004.pandalib.PandaLib;
import net.bxx2004.pandalib.bukkit.util.PCooldown;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class Skill {
    public static PCooldown pCooldown = new PCooldown(PandaLib.initPlugin);
    private String name;
    private double d;
    private SkillHandler skillHandler;

    public Skill name(String name){
        this.name = name;
        return this;
    }
    public Skill cooling(double d){
        this.d = d;
        return this;
    }
    public Skill cast(BiConsumer<LivingEntity,LivingEntity> consumer){
         skillHandler = new SkillHandler() {
            @Override
            public String name() {
                return name();
            }

            @Override
            public void cast(LivingEntity caster, LivingEntity target) {
                consumer.accept(caster,target);
            }

            @Override
            public double cooling() {
                return cooling();
            }
        };
        return this;
    }
    public SkillHandler build(){
        return skillHandler;
    }
}
