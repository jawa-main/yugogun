package hr.jawa2401.yugogun.init.blocks;

import hr.jawa2401.yugogun.init.YBlocks;
import hr.jawa2401.yugogun.init.YItems;
import hr.jawa2401.yugogun.init.YTab;
import hr.jawa2401.yugogun.util.SoundUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class MineBlock extends Block {
    public MineBlock() {
        super(AbstractBlock.Properties.create(Material.IRON).notSolid());
    }

    public static RegistryObject<MineBlock> create() {
        String id = "mine";
        MineBlock b = new MineBlock();
        RegistryObject<MineBlock> blockReg = YBlocks.BLOCKS.register(id, () -> b);
        Item.Properties finalProps = new Item.Properties().group(YTab.TAB);
        YItems.ITEMS.register(id, () -> new BlockItem(blockReg.get(), finalProps.group(YTab.TAB)));
        return blockReg;
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if (!(entityIn instanceof LivingEntity)) return;
        if (worldIn.isRemote()) return;

        MinecraftServer server = worldIn.getServer();

        if (server == null) return;

        for (PlayerEntity p : server.getPlayerList().getPlayers()) {
            p.sendStatusMessage(ITextComponent.getTextComponentOrEmpty(TextFormatting.RED + entityIn.getName().getString() + " je stao na minu"), true);
        }

        worldIn.createExplosion(entityIn, pos.getX(), pos.getY(), pos.getZ(), 5, Explosion.Mode.DESTROY);

        SoundUtil.echoExplosion(server, pos);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return VoxelShapes.create(0, 0, 0, 1, 4.0 / 16.0, 1);
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return getCollisionShape(state, worldIn, pos);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return getCollisionShape(state, worldIn, pos);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return getCollisionShape(state, worldIn, pos);
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return getCollisionShape(state, worldIn, pos);
    }

}
