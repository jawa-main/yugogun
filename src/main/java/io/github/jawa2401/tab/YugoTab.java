package io.github.jawa2401.tab;

import com.mrcrayfish.guns.init.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class YugoTab extends ItemGroup {

    public static YugoTab TAB = new YugoTab("Yugo");

    public YugoTab(String tabId) {
        super(tabId);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModItems.BASIC_BULLET.get());
    }
}
