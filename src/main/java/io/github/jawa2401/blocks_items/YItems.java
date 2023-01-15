package io.github.jawa2401.blocks_items;

import io.github.jawa2401.Main;
import io.github.jawa2401.tab.YugoTab;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class YItems {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static RegistryObject<Item> add(String id, Item.Properties properties)
    {
        return ITEMS.register(id, ()->new Item(properties));
    }
}
