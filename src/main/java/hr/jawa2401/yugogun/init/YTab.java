package hr.jawa2401.yugogun.init;

import com.mrcrayfish.guns.init.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class YTab extends ItemGroup {

    public static YTab TAB = new YTab("Yugo");

    public YTab(String tabId) {
        super(tabId);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModItems.ASSAULT_RIFLE.get());
    }
}
