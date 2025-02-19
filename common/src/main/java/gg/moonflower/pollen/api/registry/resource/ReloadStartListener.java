package gg.moonflower.pollen.api.registry.resource;

import net.minecraft.server.packs.resources.ResourceManager;

import java.util.concurrent.Executor;

/**
 * Prepares a reload listener for a reload instance reloading.
 * @author Ocelot
 * @since 1.0.0
 */
public interface ReloadStartListener {

    /**
     * Called just before the listener reloads.
     * @param resourceManager
     * @param backgroundExecutor
     * @param gameExecutor
     */
    void onReloadStart(ResourceManager resourceManager, Executor backgroundExecutor, Executor gameExecutor);
}
