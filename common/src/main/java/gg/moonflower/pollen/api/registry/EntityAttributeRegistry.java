package gg.moonflower.pollen.api.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import gg.moonflower.pollen.api.platform.Platform;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.Supplier;

public class EntityAttributeRegistry {

    @ExpectPlatform
    public static <T extends LivingEntity> void register(Supplier<EntityType<T>> entity, Supplier<AttributeSupplier.Builder> attributeBuilder) {
        Platform.error();
    }
}
