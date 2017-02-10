package EntFix;

import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;

public class Functions {
	public static boolean removeEnt(ItemStack item) {
		boolean b = false;
		if(item==null) {
			return false;
		}
		for(Entry<Enchantment, Integer> ench : item.getEnchantments().entrySet()) {
			Enchantment Enchant = (Enchantment) ench.getKey();
			if(ench.getValue() > Enchant.getMaxLevel() || ench.getValue() < 0) {
			ItemMeta meta = item.getItemMeta();
			meta.removeEnchant(Enchant);
			item.setItemMeta(meta);
				b=true;
			}
		}
		return b;
	}

	public static HashMap<String, Object> chech (ItemStack[] item, Player p, boolean a) {
		HashMap<String, Object> obj = new HashMap<String, Object>();
		boolean b = false;
		int i=-1;
		for (ItemStack it : item) {
			i++;
			if(checkAttributes(it) || removeEnt(it)) {
				b = true;
				if(a) {
					p.getInventory().remove(it);
				} else {
					item[i] = new ItemStack(Material.AIR, 0);
				}
				new Kick(p).runTaskLater(EntFix.getPlugin(EntFix.class), 5);
			}
		}
		obj.put("b", b);
		obj.put("item", item);
		return obj;
	}
	
	public static boolean checkAttributes(ItemStack item){
		if(item==null || item.getType()==Material.AIR) {
			return false;
		}
		
		if(item.getAmount()<1 || item.getAmount()>64) {
			return true;
		}

		try {
			NbtCompound tag = (NbtCompound) NbtFactory.fromItemTag(item);
			String NBTS = tag.toString().replace("\"", "");
			if ((NBTS.contains("BlockEntityTag:") && NBTS.contains("Items:")) || NBTS.contains("CustomPotionEffects:") || NBTS.contains("AttributeModifiers:") || NBTS.contains("Unbreakable:") || NBTS.contains("ClickEvent") || NBTS.contains("run_command") || NBTS.contains("open_file") || NBTS.contains("open_url") || NBTS.contains("suggest_command") || NBTS.contains("CustomName:")) {
				return true;
			}
			if(NBTS.contains("SkullOwner:") && NBTS.contains("Properties:") && NBTS.contains("textures:") && NBTS.contains("Value:")) {
				String value = new String(Base64.decodeBase64(NBTS.split("Value:")[1].split("}]},")[0]));
				if(!value.contains("timestamp") && !value.contains("profileId") && !value.contains("profileName") && !value.contains("textures")) {
					return true;
				}
			}
			if((item.getType() == Material.MONSTER_EGG && NBTS.contains("Size:") ||  NBTS.contains("DeathLootTable:") || NBTS.contains("ActiveEffects:") || NBTS.contains("Profession:") || NBTS.contains("powered:") || NBTS.contains("Type:"))) {
				return true;
			}
			if(item.getType() == Material.matchMaterial("STRUCTURE_BLOCK") || item.getType() == Material.matchMaterial("BARRIER") || item.getType() == Material.matchMaterial("STRUCTURE_VOID")) {
				return true;
			}
			if(item.getType() == Material.matchMaterial("ARMOR_STAND") && NBTS.contains("EntityTag:")) {
				return true;
			}
			if(item.getType() == Material.matchMaterial("ENDER_PEARL") || item.getType() == Material.matchMaterial("SNOW_BALL") || item.getType() == Material.matchMaterial("NAME_TAG")) {
                if (NBTS.length() > 400 || NBTS.contains("NaNd")) {
                    return true;
                }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}