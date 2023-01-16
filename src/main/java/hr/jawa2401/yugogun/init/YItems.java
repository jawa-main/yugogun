package hr.jawa2401.yugogun.init;

import hr.jawa2401.yugogun.Main;
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
