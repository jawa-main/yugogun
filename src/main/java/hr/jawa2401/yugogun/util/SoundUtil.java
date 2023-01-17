package hr.jawa2401.yugogun.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class SoundUtil {
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
            player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, volume, 1);
        }
    }
}
