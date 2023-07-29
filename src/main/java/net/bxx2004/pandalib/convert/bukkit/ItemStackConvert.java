package net.bxx2004.pandalib.convert.bukkit;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.bxx2004.pandalib.convert.Convert;
import net.bxx2004.pandalib.convert.ConvertImpl;
import net.bxx2004.pandalib.convert.ConvertOption;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemStackConvert extends ConvertImpl implements Convert<ItemStack> {
    public ItemStackConvert(ConvertOption option) {
        super(option);
    }

    @Override
    public ItemStack parseObject(String s) {
        JSONObject object = JSONObject.parseObject(s);
        ItemStack stack = new ItemStack(Objects.requireNonNull(Material.getMaterial(object.getString("material"))),object.getInteger("amount"));
        if (object.getJSONObject("meta") != null){
            JSONObject meta = object.getJSONObject("meta");
            ItemMeta imeta = stack.getItemMeta();
            if (meta.getString("displayname") != null){
                imeta.setDisplayName(meta.getString("displayname"));
            }
            if (meta.getJSONArray("lore") != null){
                List<String> list = new ArrayList<>();
                meta.getJSONArray("lore").forEach(sa -> list.add(sa.toString()));
                imeta.setLore(list);
            }
            if (meta.getJSONObject("enchantments") != null){
                meta.getJSONObject("enchantments").forEach((name,level) -> imeta.addEnchant(Enchantment.getByName(name),Integer.parseInt(level.toString()),true));
            }
            if (meta.getJSONArray("flags") != null){
                meta.getJSONArray("flags").forEach(fs -> imeta.addItemFlags(ItemFlag.valueOf(fs.toString())));
            }
            if (meta.getJSONArray("attributes") != null){
                final int[] i = {0};
                meta.getJSONArray("attributes").forEach(
                        (att) -> {
                            JSONObject object1 = meta.getJSONArray("attributes").getJSONObject(i[0]);
                            Attribute attribute = Attribute.valueOf(object1.getString("attribute"));
                            AttributeModifier attributeModifier = new AttributeModifier(UUID.fromString(object1.getString("uuid")
                            ), object1.getString("name"), object1.getDouble("amount"), AttributeModifier.Operation.valueOf(object1.getString("operation")), EquipmentSlot.valueOf(object1.getString("slot")));
                            imeta.addAttributeModifier(attribute,attributeModifier);
                            i[0] += 1;
                        }
                );
            }
            if (meta.get("unbreakable") != null){
                imeta.setUnbreakable(meta.getBoolean("unbreakable"));
            }
            stack.setItemMeta(imeta);
        }
        return stack;
    }

    @Override
    public String parseString(ItemStack stack) {
        JSONObject iobject = JSONObject.parseObject("{}");
        iobject.put("material",stack.getType().toString());
        iobject.put("amount",stack.getAmount());
        JSONObject metaObject;
        if (stack.hasItemMeta()){
            metaObject = JSONObject.parseObject("{}");
            ItemMeta meta = stack.getItemMeta();
            if (meta.hasDisplayName()){
                metaObject.put("displayname",meta.getDisplayName());
            }
            if (meta.hasLore()){
                metaObject.put("lore",meta.getLore());
            }
            if (meta.hasEnchants()){
                HashMap<String,Integer> h = new HashMap<>();
                meta.getEnchants().forEach((k,v) -> h.put(k.getName(),v));
                metaObject.put("enchantments",h);
            }
            if (meta.getItemFlags() != null && meta.getItemFlags().size() != 0){
                metaObject.put("flags",meta.getItemFlags().stream().map(Enum::toString).toArray());
            }
            if (meta.hasCustomModelData()){
                metaObject.put("custommodeldata",meta.getCustomModelData());
            }
            if (meta.hasAttributeModifiers()){
                JSONArray array = JSONArray.parseArray("[]");
                meta.getAttributeModifiers().forEach(
                        (attribute, attributeModifier) ->{
                            JSONObject attr = JSONObject.parseObject("{}");
                            attr.put("attribute",attribute.name());
                            attr.put("name",attributeModifier.getName());
                            attr.put("slot",attributeModifier.getSlot().toString());
                            attr.put("amount",attributeModifier.getAmount());
                            attr.put("operation",attributeModifier.getOperation());
                            attr.put("uuid",attributeModifier.getUniqueId().toString());
                            array.add(attr);
                        }
                );
                metaObject.put("attributes",array);
            }
            metaObject.put("unbreakable",meta.isUnbreakable());
            iobject.put("meta",metaObject);
        }
        return iobject.toJSONString();
    }

    @Override
    public boolean equal(ItemStack stack, ItemStack t1) {
        return false;
    }

    @Override
    public boolean equal(String s, String s1) {
        return false;
    }
}
