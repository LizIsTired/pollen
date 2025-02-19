package gg.moonflower.pollen.api.resource.condition.forge;

import gg.moonflower.pollen.api.resource.condition.PollinatedResourceConditionProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.crafting.conditions.*;
import org.jetbrains.annotations.ApiStatus;

import java.util.Arrays;

@ApiStatus.Internal
public class PollinatedResourceConditionImpl {

    private static final PollinatedResourceConditionProvider FALSE = wrap(FalseCondition.INSTANCE);
    private static final PollinatedResourceConditionProvider TRUE = wrap(TrueCondition.INSTANCE);

    public static PollinatedResourceConditionProvider and(PollinatedResourceConditionProvider... values) {
        return wrap(new AndCondition(Arrays.stream(values).map(ConditionWrapper::new).toArray(ICondition[]::new)));
    }

    public static PollinatedResourceConditionProvider FALSE() {
        return FALSE;
    }

    public static PollinatedResourceConditionProvider TRUE() {
        return TRUE;
    }

    public static PollinatedResourceConditionProvider not(PollinatedResourceConditionProvider value) {
        return wrap(new NotCondition(new ConditionWrapper(value)));
    }

    public static PollinatedResourceConditionProvider or(PollinatedResourceConditionProvider... values) {
        return wrap(new OrCondition(Arrays.stream(values).map(ConditionWrapper::new).toArray(ICondition[]::new)));
    }

    public static PollinatedResourceConditionProvider itemExists(ResourceLocation name) {
        return wrap(new ItemExistsCondition(name));
    }

    public static PollinatedResourceConditionProvider blockExists(ResourceLocation name) {
        return wrap(new BlockExistsCondition(name));
    }

    public static PollinatedResourceConditionProvider fluidExists(ResourceLocation name) {
        return wrap(new FluidExistsCondition(name));
    }

    public static PollinatedResourceConditionProvider itemTagPopulated(Tag.Named<Item> tag) {
        return wrap(new ItemTagPopulatedCondition(tag.getName()));
    }

    public static PollinatedResourceConditionProvider blockTagPopulated(Tag.Named<Block> tag) {
        return wrap(new BlockTagPopulatedCondition(tag.getName()));
    }

    public static PollinatedResourceConditionProvider fluidTagPopulated(Tag.Named<Fluid> tag) {
        return wrap(new FluidTagPopulatedCondition(tag.getName()));
    }

    public static PollinatedResourceConditionProvider allModsLoaded(String... modIds) {
        return modIds.length == 1 ? wrap(new ModLoadedCondition(modIds[0])) : wrap(new AndCondition(Arrays.stream(modIds).map(ModLoadedCondition::new).toArray(ICondition[]::new)));
    }

    public static PollinatedResourceConditionProvider anyModsLoaded(String... modIds) {
        return modIds.length == 1 ? wrap(new ModLoadedCondition(modIds[0])) : wrap(new OrCondition(Arrays.stream(modIds).map(ModLoadedCondition::new).toArray(ICondition[]::new)));
    }

    private static PollinatedResourceConditionProvider wrap(ICondition condition) {
        return new ForgeResourceConditionProvider(condition);
    }

    private static class ConditionWrapper implements ICondition {

        private final PollinatedResourceConditionProvider provider;

        private ConditionWrapper(PollinatedResourceConditionProvider provider) {
            this.provider = provider;
        }

        @Override
        public ResourceLocation getID() {
            return this.provider.getName();
        }

        @Override
        public boolean test() {
            return false;
        }
    }
}
