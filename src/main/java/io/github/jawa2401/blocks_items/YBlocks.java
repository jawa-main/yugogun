package io.github.jawa2401.blocks_items;

import io.github.jawa2401.Main;
import io.github.jawa2401.blocks_items.custom.MineBlock;
import io.github.jawa2401.blocks_items.custom.StoneGeneratorBlock;
import io.github.jawa2401.tab.YugoTab;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class YBlocks {
    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);
    public static final RegistryObject<MineBlock> MINE_BLOCK = MineBlock.create();
    public static final RegistryObject<StoneGeneratorBlock> STONE_GENERATOR_BLOCK = StoneGeneratorBlock.create();
//    public static final RegistryObject<MortarBlock> MORTAR_BLOCK = MortarBlock.create();

    public static RegistryObject<Block> add(Material material, String id, Item.Properties properties) {
        Item.Properties props = properties;
        if (props == null) props = new Item.Properties();
        RegistryObject<Block> blockReg = BLOCKS.register(id, () -> new Block(AbstractBlock.Properties.create(material)));
        Item.Properties finalProps = props;
        YItems.ITEMS.register(id, () -> new BlockItem(blockReg.get(), finalProps.group(YugoTab.TAB)));
        return blockReg;
    }
}
