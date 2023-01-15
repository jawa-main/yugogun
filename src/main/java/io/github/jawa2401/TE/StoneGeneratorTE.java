package io.github.jawa2401.TE;

import io.github.jawa2401.YConfig;
import io.github.jawa2401.container.StoneGeneratorContainer;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StoneGeneratorTE extends TileEntity implements ITickableTileEntity {

    public static final int SLOT_WATER_IN = 0;
    public static final int SLOT_ROCK_OUT = 1;
    public static final int SLOT_LAVA_IN = 2;

    public float rockGenerationProgress = 0;

    public StoneGeneratorContainer container;

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public StoneGeneratorTE(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public StoneGeneratorTE() {
        this(YTEs.STONE_GENERATOR_TILE.get());
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(3) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
//                switch (slot) {
//                    case SLOT_WATER_IN:
//                        return stack.getItem() == Items.WATER_BUCKET;
//                    case SLOT_LAVA_IN:
//                        return stack.getItem() == Items.LAVA_BUCKET;
//                    default:
//                        return false;
//                }
                return true;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }

//            @Nonnull
//            @Override
//            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
//                if (!isItemValid(slot, stack)) return stack;
//                return super.insertItem(slot, stack, simulate);
//            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.DOWN) {
                // set output slots to 1
//                System.out.println("dir == down");
                ItemStackHandler _h = new ItemStackHandler(3) {
                    @Nonnull
                    @Override
                    public ItemStack extractItem(int slot, int amount, boolean simulate) {
                        if (slot != SLOT_ROCK_OUT) return ItemStack.EMPTY;
                        if (!simulate) return ItemStack.EMPTY;
                        ItemStack stack = itemHandler.getStackInSlot(slot);
                        int count = stack.getCount();
                        if (amount > count)
                        {
                            return ItemStack.EMPTY;
                        }
                        stack.setCount(count - amount);
                        ItemStack copy = stack.copy();
                        copy.setCount(amount);
                        return copy;
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