package hr.jawa2401.yugogun.util;

import net.minecraft.item.ItemStack;

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
}
