package hr.jawa2401.yugogun.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ItemUtil {
    public static ItemStack extract(ItemStack stack, int amount)
    {
        int count = stack.getCount();
        if (amount > count) {
            return ItemStack.EMPTY;
        }
        stack.setCount(count - amount);
        ItemStack copy = stack.copy();
        copy.setCount(amount);
        return copy;
    }
    public static boolean same(ItemStack sample, String id)
    {
        ResourceLocation resourceLocation = sample.getItem().getRegistryName();
        if (resourceLocation == null) return false;
        return resourceLocation.getPath().equals(id);
    }
    public static String id(ItemStack stack)
    {
        return id(stack.getItem());
    }
    public static String id(Item item)
    {
        ResourceLocation loc = item.getRegistryName();
        if (loc == null) return "";
        return loc.getPath();
    }
}
