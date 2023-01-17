package hr.jawa2401.yugogun.init;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class YTab extends ItemGroup {

    public static YTab TAB = new YTab("Yugo");

    public YTab(String tabId) {
        super(tabId);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(YBlocks.IRON_EXTRACTOR_BLOCK.get().asItem());
    }
}
