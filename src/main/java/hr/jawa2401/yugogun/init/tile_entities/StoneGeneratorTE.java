package hr.jawa2401.yugogun.init.tile_entities;

import hr.jawa2401.yugogun.YConfig;
import hr.jawa2401.yugogun.base.YTileEntityWithContainer;
import hr.jawa2401.yugogun.init.YTileEntities;
import hr.jawa2401.yugogun.init.container.StoneGeneratorContainer;
import hr.jawa2401.yugogun.util.ItemUtil;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StoneGeneratorTE extends YTileEntityWithContainer<StoneGeneratorContainer> implements ITickableTileEntity {

    public static final int SLOT_WATER_IN = 0;
    public static final int SLOT_ROCK_OUT = 1;
    public static final int SLOT_LAVA_IN = 2;

    public static final int SLOT_COUNT = 3;

    public float rockGenerationProgress = 0;

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public StoneGeneratorTE(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public StoneGeneratorTE() {
        this(YTileEntities.STONE_GENERATOR_TILE.get());
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(SLOT_COUNT) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.DOWN) {
                // set output slots to 1
//                System.out.println("dir == down");
                ItemStackHandler _h = new ItemStackHandler(SLOT_COUNT) {
                    @Nonnull
                    @Override
                    public ItemStack extractItem(int slot, int amount, boolean simulate) {
                        if (!simulate) return ItemStack.EMPTY;
                        if (slot != SLOT_ROCK_OUT) return ItemStack.EMPTY;
                        return ItemUtil.extract(itemHandler.getStackInSlot(slot), amount);
                    }
                };

                LazyOptional<IItemHandler> lazyOptional = LazyOptional.of(() -> _h);
                return lazyOptional.cast();
            }
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        return super.write(compound);
    }

    public void generateRock() {
        if (itemHandler.getStackInSlot(SLOT_LAVA_IN).getItem() != Items.LAVA_BUCKET) return;
        if (itemHandler.getStackInSlot(SLOT_WATER_IN).getItem() != Items.WATER_BUCKET) return;
        if (itemHandler.getStackInSlot(SLOT_ROCK_OUT).getCount() == 64) return;

        // called every tick
        if (rockGenerationProgress < 1) {
            rockGenerationProgress += YConfig.rockGenerateInterval;
        } else {
            // generate rock
            ItemStack rock = itemHandler.getStackInSlot(SLOT_ROCK_OUT);
            if (rock.getItem() == Items.AIR) {
                rock = new ItemStack(Items.COBBLESTONE);
                rock.setCount(0);
            }
            rock.setCount(rock.getCount() + 1);

            itemHandler.setStackInSlot(SLOT_ROCK_OUT, rock);
            rockGenerationProgress = 0;
        }
    }

    @Override
    public void tick() {
        generateRock();
    }
}