package gg.moonflower.pollen.api.resource.modifier.type.forge;

import com.google.gson.*;
import gg.moonflower.pollen.core.mixin.forge.loot.LootPoolAccessor;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Type;

@ApiStatus.Internal
public class LootModifierImpl {

    public static int poolCount;
    public static String poolName;

    public static GsonBuilder createLootTableSerializer() {
        return Deserializers.createFunctionSerializer().registerTypeAdapter(LootPool.class, new LootPoolSerializer()).registerTypeAdapter(LootTable.class, new LootTable.Serializer());
    }

    @ApiStatus.Internal
    public static void initPool(JsonObject json) {
        poolCount = 0;
        poolName = GsonHelper.getAsString(json, "name", null);
    }

    private static class LootPoolSerializer implements JsonDeserializer<LootPool> {

        @Override
        public LootPool deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = GsonHelper.convertToJsonObject(jsonElement, "loot pool");
            LootPoolEntryContainer[] lootPoolEntryContainers = GsonHelper.getAsObject(jsonObject, "entries", jsonDeserializationContext, LootPoolEntryContainer[].class);
            LootItemCondition[] lootItemConditions = GsonHelper.getAsObject(jsonObject, "conditions", new LootItemCondition[0], jsonDeserializationContext, LootItemCondition[].class);
            LootItemFunction[] lootItemFunctions = GsonHelper.getAsObject(jsonObject, "functions", new LootItemFunction[0], jsonDeserializationContext, LootItemFunction[].class);
            RandomIntGenerator randomIntGenerator = RandomIntGenerators.deserialize(jsonObject.get("rolls"), jsonDeserializationContext);
            RandomValueBounds randomValueBounds = GsonHelper.getAsObject(jsonObject, "bonus_rolls", new RandomValueBounds(0.0F, 0.0F), jsonDeserializationContext, RandomValueBounds.class);

            if (poolName == null)
                poolCount++;
            return LootPoolAccessor.init(lootPoolEntryContainers, lootItemConditions, lootItemFunctions, randomIntGenerator, randomValueBounds, poolName != null ? poolName : (poolCount == 1 ? "main" : "pool" + (poolCount - 1)));
        }
    }
}
