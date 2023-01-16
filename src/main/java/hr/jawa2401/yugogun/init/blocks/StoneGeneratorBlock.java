package hr.jawa2401.yugogun.init.blocks;

import hr.jawa2401.yugogun.init.YBlocks;
import hr.jawa2401.yugogun.init.YItems;
import hr.jawa2401.yugogun.init.YTab;
import hr.jawa2401.yugogun.init.YTileEntities;
import hr.jawa2401.yugogun.init.container.StoneGeneratorContainer;
import hr.jawa2401.yugogun.init.tile_entities.StoneGeneratorTE;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class StoneGeneratorBlock extends Block {
    public StoneGeneratorBlock() {
        super(AbstractBlock.Properties.create(Material.IRON));
    }

    public static RegistryObject<StoneGeneratorBlock> create() {
        String id = "stone_generator";
        StoneGeneratorBlock b = new StoneGeneratorBlock();
        RegistryObject<StoneGeneratorBlock> blockReg = YBlocks.BLOCKS.register(id, () -> b);
        Item.Properties finalProps = new Item.Properties();
        YItems.ITEMS.register(id, () -> new BlockItem(blockReg.get(), finalProps.group(YTab.TAB)));
        return blockReg;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return YTileEntities.STONE_GENERATOR_TILE.get().create();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote()) {
            return ActionResultType.FAIL;
        }

        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof StoneGeneratorTE) {
            INamedContainerProvider containerProvider = createContainerProvider(worldIn, pos);
            NetworkHooks.openGui(((ServerPlayerEntity) player), containerProvider, tileEntity.getPos());
        }

        return ActionResultType.SUCCESS;
    }

    private INamedContainerProvider createContainerProvider(World worldIn, BlockPos pos) {
        return new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.yugogun.stone_generator");
            }

            @Nullable
            @Override
            public Container createMenu(int i, PlayerInventory inv, PlayerEntity plr) {
                return new StoneGeneratorContainer(i, worldIn, pos, inv, plr);
            }
        };
    }
}
