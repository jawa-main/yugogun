package io.github.jawa2401;

import com.mrcrayfish.guns.entity.DamageSourceProjectile;
import com.mrcrayfish.guns.entity.MissileEntity;
import com.mrcrayfish.guns.entity.ProjectileEntity;
import com.mrcrayfish.guns.event.GunProjectileHitEvent;
import de.Whitedraco.switchbow.arrow.entity.EntityArrowTNT;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class YEvents {
    @SubscribeEvent
    public void onArrowLand(ProjectileImpactEvent event) {
        Entity e = event.getEntity();
        if (e instanceof EntityArrowTNT) {
            BlockPos pos = e.getPosition();
            World w = e.getEntityWorld();

            echoExplosion(w.getServer(), pos);
        }
    }

    @SubscribeEvent
    public void onMissleHit(GunProjectileHitEvent event) {
        ProjectileEntity e = event.getProjectile();
        BlockPos pos = e.getPosition();
        World w = e.world;
        RayTraceResult.Type t = event.getRayTrace().getType();
        if (t == RayTraceResult.Type.MISS) return;
        if (e instanceof MissileEntity) {
            echoExplosion(w.getServer(), pos);
        }
    }

    public static void echoExplosion(MinecraftServer server, BlockPos sourcePos) {
        if (server == null) {
            return;
        }

        for (PlayerEntity player : server.getPlayerList().getPlayers()) {
//            BlockPos playerPos = player.getPosition();

            float distance = (float) Math.sqrt(player.getPosition().distanceSq(sourcePos));
            float volume = 1;

            if (distance >= 15) {
                volume = 240f / (distance - 15);
            }
            System.out.println("vol:" + volume + "dis:" + distance);
            player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, volume, 1);
        }
    }
}
