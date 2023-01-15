package io.github.jawa2401.blocks_items.custom;

import com.mrcrayfish.guns.Config;
import com.mrcrayfish.guns.init.ModItems;
import com.mrcrayfish.guns.init.ModSounds;
import io.github.jawa2401.blocks_items.YBlocks;
import io.github.jawa2401.blocks_items.YItems;
import io.github.jawa2401.gui.MortarScreen;
import io.github.jawa2401.tab.YugoTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.RegistryObject;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class MortarBlock extends HorizontalBlock {

    public double range = 2;

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public MortarBlock() {
        super(Properties.create(Material.ROCK).notSolid());
    }

    public static RegistryObject<MortarBlock> create() {
        MortarBlock b = new MortarBlock();
        RegistryObject<MortarBlock> blockReg = YBlocks.BLOCKS.register("mortar", () -> b);
        Item.Properties finalProps = new Item.Properties().group(YugoTab.TAB);
        YItems.ITEMS.register("mortar", () -> new BlockItem(blockReg.get(), finalProps.group(YugoTab.TAB)));
        return blockReg;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {

        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {

        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        if (handIn != Hand.MAIN_HAND) return ActionResultType.PASS;
        MortarBlock block = (MortarBlock) worldIn.getBlockState(pos).getBlock();
//        if (block != YBlocks.MORTAR_BLOCK.get()) return ActionResultType.PASS;

        if (player.getHeldItemMainhand().getItem() == Items.AIR) {
            if (worldIn.isRemote()) {
                Minecraft.getInstance().displayGuiScreen(new MortarScreen(block));
            }
        }
        if (player.getHeldItemMainhand().getItem() == ModItems.MISSILE.get()) {
            if (!worldIn.isRemote()) {
                launchRocket(worldIn, pos);
            }
        }
        return ActionResultType.PASS;
    }

    public Vector3d getMotion(World w, BlockPos p) {
        BlockState state = w.getBlockState(p);
        Vector3i bsRot = state.get(FACING).getDirectionVec();
        return new Vector3d(-bsRot.getX(), bsRot.getY(), -bsRot.getZ()).mul(range, range, range);
    }

    public void launchRocket(World _world, BlockPos pos) {
        ServerWorld world = (ServerWorld) _world;
        System.out.println("launchin rocka");
        // play sound
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), ModSounds.ITEM_BAZOOKA_FIRE.get(), SoundCategory.BLOCKS, 16, 1, false);

        // launch rocket
        ProjectileEntity arrow = new AbstractArrowEntity(EntityType.ARROW, pos.getX(), pos.getY() + 1, pos.getZ(), world) {
            @Override
            protected void onEntityHit(EntityRayTraceResult result) {
                world.createExplosion(this, pos.getX(), pos.getY(), pos.getZ(), Config.COMMON.missiles.explosionRadius.get().floatValue(), Explosion.Mode.DESTROY);
                super.onEntityHit(result);
                remove();
            }

            @Override
            protected void onImpact(RayTraceResult result) {
                world.createExplosion(this, pos.getX(), pos.getY(), pos.getZ(), Config.COMMON.missiles.explosionRadius.get().floatValue(), Explosion.Mode.DESTROY);
                super.onImpact(result);
                remove();
            }

            @Override
            protected ItemStack getArrowStack() {
                return ModItems.MISSILE.get().getDefaultInstance();
            }

        };


        Vector3d fwd = getMotion(world, pos);
        System.out.println("fwd:" + fwd);
        arrow.setVelocity(fwd.x, fwd.y, fwd.z);
        world.addEntity(arrow);
    }
}
