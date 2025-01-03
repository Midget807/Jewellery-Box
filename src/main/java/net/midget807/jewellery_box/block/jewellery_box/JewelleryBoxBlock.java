package net.midget807.jewellery_box.block.jewellery_box;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.midget807.jewellery_box.JewelleryBoxMain;
import net.midget807.jewellery_box.block.entity.ModBlockEntities;
import net.midget807.jewellery_box.block.entity.jewellery_box.JewelleryBoxBlockEntity;
import net.midget807.jewellery_box.screen.jewellery_box.JewelleryBoxScreenHandler;
import net.midget807.jewellery_box.stat.ModStats;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class JewelleryBoxBlock extends AbstractChestBlock<JewelleryBoxBlockEntity> implements Waterloggable {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public VoxelShape FULL_SHAPE = Block.createCuboidShape(1, 0, 1, 15, 8, 15);
    public VoxelShape Z_AXIS_HALF_SHAPE = Block.createCuboidShape(1, 0, 4.5, 15, 8, 11.5);
    public VoxelShape X_AXIS_HALF_SHAPE = Block.createCuboidShape(4.5, 0, 1, 11.5, 8, 15);
    public VoxelShape QUARTER_SHAPE = Block.createCuboidShape(4.5, 0, 4.5, 11.5, 8, 11.5);
    public final int size;
    public JewelleryBoxBlock(Settings settings, int size) {
        super(settings, () -> ModBlockEntities.JEWELLERY_BOX_BLOCK_ENTITY);
        this.size = size;
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> getBlockEntitySource(BlockState state, World world, BlockPos pos, boolean ignoreBlocked) {
        return DoubleBlockProperties.PropertyRetriever::getFallback;
    }

    public int getSize() {
        return size;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        return switch (size) {
            case 2 -> QUARTER_SHAPE;
            case 4 -> direction.getAxis() == Direction.Axis.X ? X_AXIS_HALF_SHAPE : Z_AXIS_HALF_SHAPE;
            default -> FULL_SHAPE;
        };
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            NamedScreenHandlerFactory namedScreenHandlerFactory = (JewelleryBoxBlockEntity) world.getBlockEntity(pos);
            if (namedScreenHandlerFactory != null) {
                player.openHandledScreen(namedScreenHandlerFactory);
                player.incrementStat(this.getOpenStat());
                PiglinBrain.onGuardedBlockInteracted(player, true);
            }

            return ActionResult.CONSUME;
        }
    }
    protected Stat<Identifier> getOpenStat() {
        return Stats.CUSTOM.getOrCreateStat(ModStats.OPEN_JEWELLERY_BOX);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        JewelleryBoxBlockEntity jewelleryBoxBlockEntity = new JewelleryBoxBlockEntity(pos, state);
        jewelleryBoxBlockEntity.size = this.size;
        jewelleryBoxBlockEntity.inventory = DefaultedList.ofSize(this.size, ItemStack.EMPTY);
        return jewelleryBoxBlockEntity;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? checkType(type, ModBlockEntities.JEWELLERY_BOX_BLOCK_ENTITY, JewelleryBoxBlockEntity::clientTick) : null;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof JewelleryBoxBlockEntity) {
            ((JewelleryBoxBlockEntity)blockEntity).onScheduledTick();
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (itemStack.hasCustomName()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof JewelleryBoxBlockEntity jewelleryBoxBlockEntity) {
                jewelleryBoxBlockEntity.setCustomName(itemStack.getName());
            }
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof JewelleryBoxBlockEntity) {
                ItemScatterer.spawn(world, pos, ((JewelleryBoxBlockEntity) blockEntity));
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }
    public static DoubleBlockProperties.PropertyRetriever<JewelleryBoxBlockEntity, Float2FloatFunction> getAnimationProgressRetriever(LidOpenable progress) {
        return new DoubleBlockProperties.PropertyRetriever<>() {
            @Override
            public Float2FloatFunction getFromBoth(JewelleryBoxBlockEntity first, JewelleryBoxBlockEntity second) {
                return tickDelta -> Math.max(first.getAnimationProgress(tickDelta), second.getAnimationProgress(tickDelta));
            }

            @Override
            public Float2FloatFunction getFrom(JewelleryBoxBlockEntity single) {
                return single::getAnimationProgress;
            }

            @Override
            public Float2FloatFunction getFallback() {
                return progress::getAnimationProgress;
            }
        };
    }
}
