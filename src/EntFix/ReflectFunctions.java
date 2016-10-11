package EntFix;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import org.bukkit.Material;

public class ReflectFunctions {
	public static boolean checkAttributes(ItemStack item){
		if(item==null || item.getType()==Material.AIR || item.getAmount()<1 || item.getAmount()>64) {
			return false;
		}

		try {
			
			//Без рефлексии. Чуть больше производительности, но требует компиляцию под конкретную версию nms пакета.
			/*
			net.minecraft.server.v1_10_R1.NBTTagCompound tag = new net.minecraft.server.v1_10_R1.NBTTagCompound();
			String NBTS = org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack.asNMSCopy(item).save(tag).toString();
			*/
			
			//Использование рефлексии, делает плагин рабочим почти на всех версиях.
			Class<?> nbtTagCompoundClass = getNmsClass("NBTTagCompound");
			Object nbtTagCompound = nbtTagCompoundClass.getConstructor().newInstance();
			Object nmsItemStack = getNmsCraftClass("inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
			String NBTS = getNmsClass("ItemStack").getMethod("save", nbtTagCompoundClass).invoke(nmsItemStack, nbtTagCompound).toString();
			
			if ((NBTS.contains("BlockEntityTag:") && NBTS.contains("Items:")) || NBTS.contains("CustomPotionEffects:") || NBTS.contains("AttributeModifiers:") || NBTS.contains("Unbreakable:") || NBTS.contains("ClickEvent") || NBTS.contains("run_command") || NBTS.contains("open_file") || NBTS.contains("open_url") || NBTS.contains("suggest_command") || NBTS.contains("CustomName:")) {
				return true;
			}
			if(NBTS.contains("minecraft:skull") && NBTS.contains("SkullOwner:") && NBTS.contains("Properties:") && NBTS.contains("textures:") && NBTS.contains("Value:")) {
				String value = new String(Base64.decodeBase64(NBTS.split("Value:")[1].split("}]},")[0]));
				if(!value.contains("timestamp") && !value.contains("profileId") && !value.contains("profileName") && !value.contains("textures")) {
					return true;
				}
			}
			if(NBTS.contains("minecraft:spawn_egg") && (NBTS.contains("Size:") ||  NBTS.contains("DeathLootTable:") || NBTS.contains("ActiveEffects:") || NBTS.contains("Profession:") || NBTS.contains("powered:") || NBTS.contains("Type:"))) {
				return true;
			}
			if(NBTS.contains("minecraft:structure_block")) {
				return true;
			}
			if(NBTS.contains("minecraft:structure_void")) {
				return true;
			}
			if(NBTS.contains("minecraft:barrier")) {
				return true;
			}
			if(NBTS.contains("minecraft:armor_stand") && NBTS.contains("EntityTag:")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Class<?> getNmsClass(String nmsClassName) throws ClassNotFoundException {
		return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + nmsClassName);
	}

	public static Class<?> getNmsCraftClass(String nmsClassName) throws ClassNotFoundException {
		return Class.forName("org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + nmsClassName);
	}
}