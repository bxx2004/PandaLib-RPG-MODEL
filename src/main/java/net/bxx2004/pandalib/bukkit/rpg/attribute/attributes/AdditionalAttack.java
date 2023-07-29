package net.bxx2004.pandalib.bukkit.rpg.attribute.attributes;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.bxx2004.pandalib.bukkit.item.PItemStack;
import net.bxx2004.pandalib.bukkit.rpg.attribute.AttributeHandler;
import net.bxx2004.pandalib.bukkit.rpg.attribute.EntityAttribute;
import net.bxx2004.pandalib.bukkit.rpg.attribute.ItemAttribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;

public class AdditionalAttack extends AttributeHandler implements EntityAttribute, ItemAttribute {
    public AdditionalAttack(){
        super();
    }
    @Override
    public String name() {
        return "额外攻击";
    }

    @Override
    public double power(LivingEntity entity) {
        return 10;
    }

    @Override
    public double onDamage(double origin, LivingEntity damaged, LivingEntity attacker, EntityEquipment entityEquipment) {
        attacker.sendMessage("额外的攻击 + 100");
        return origin + 100.00;
    }

    @Override
    public double chance(LivingEntity runner) {
        return 10;
    }

    @Override
    public void ifHave() {

    }

    @Override
    public boolean has(LivingEntity entity) {
        return true;
    }

    @Override
    public void onHeld(PItemStack stack) {

    }

    @Override
    public void onWear(PlayerArmorChangeEvent.SlotType slot, PItemStack stack) {

    }

    @Override
    public void inInventory(int slot, PItemStack stack) {

    }
}
