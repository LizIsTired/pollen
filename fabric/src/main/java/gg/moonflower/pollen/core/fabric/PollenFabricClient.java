package gg.moonflower.pollen.core.fabric;

import gg.moonflower.pollen.api.event.events.client.render.ReloadRendersEvent;
import gg.moonflower.pollen.api.event.events.lifecycle.TickEvents;
import gg.moonflower.pollen.api.event.events.network.ClientNetworkEvents;
import gg.moonflower.pollen.api.event.events.registry.client.RegisterAtlasSpriteEvent;
import gg.moonflower.pollen.api.event.events.world.ChunkEvents;
import gg.moonflower.pollen.api.fluid.PollinatedFluid;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.InvalidateRenderStateCallback;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class PollenFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> TickEvents.CLIENT_PRE.invoker().tick());
        ClientTickEvents.END_CLIENT_TICK.register(client -> TickEvents.CLIENT_POST.invoker().tick());
        ClientTickEvents.START_WORLD_TICK.register(TickEvents.LEVEL_PRE.invoker()::tick);
        ClientTickEvents.END_WORLD_TICK.register(TickEvents.LEVEL_POST.invoker()::tick);

        ClientChunkEvents.CHUNK_LOAD.register(ChunkEvents.LOAD.invoker()::load);
        ClientChunkEvents.CHUNK_UNLOAD.register(ChunkEvents.UNLOAD.invoker()::unload);

        InvalidateRenderStateCallback.EVENT.register(() -> ReloadRendersEvent.EVENT.invoker().reloadRenders());

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> ClientNetworkEvents.LOGIN.invoker().login(client.gameMode, client.player, handler.getConnection()));
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> ClientNetworkEvents.LOGOUT.invoker().logout(client.gameMode, client.player, handler.getConnection()));

        RegisterAtlasSpriteEvent.event(InventoryMenu.BLOCK_ATLAS).register((atlas, registry) -> {
            for (Fluid fluid : Registry.FLUID) {
                if (!(fluid instanceof PollinatedFluid))
                    continue;
                PollinatedFluid pollinatedFluid = (PollinatedFluid) fluid;
                registry.accept(pollinatedFluid.getStillTextureName());
                registry.accept(pollinatedFluid.getFlowingTextureName());
            }
        });
    }
}
