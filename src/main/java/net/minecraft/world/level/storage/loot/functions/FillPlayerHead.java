package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.authlib.GameProfile;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class FillPlayerHead extends LootItemConditionalFunction
{
    final LootContext.EntityTarget entityTarget;

    public FillPlayerHead(LootItemCondition[] pConditions, LootContext.EntityTarget pEntityTarget)
    {
        super(pConditions);
        this.entityTarget = pEntityTarget;
    }

    public LootItemFunctionType getType()
    {
        return LootItemFunctions.FILL_PLAYER_HEAD;
    }

    public Set < LootContextParam<? >> getReferencedContextParams()
    {
        return ImmutableSet.of(this.entityTarget.getParam());
    }

    public ItemStack run(ItemStack pStack, LootContext pContext)
    {
        if (pStack.is(Items.PLAYER_HEAD))
        {
            Entity entity = pContext.getParamOrNull(this.entityTarget.getParam());

            if (entity instanceof Player)
            {
                GameProfile gameprofile = ((Player)entity).getGameProfile();
                pStack.getOrCreateTag().put("SkullOwner", NbtUtils.writeGameProfile(new CompoundTag(), gameprofile));
            }
        }

        return pStack;
    }

    public static LootItemConditionalFunction.Builder<?> fillPlayerHead(LootContext.EntityTarget pEntityTarget)
    {
        return simpleBuilder((p_165211_) ->
        {
            return new FillPlayerHead(p_165211_, pEntityTarget);
        });
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<FillPlayerHead>
    {
        public void serialize(JsonObject pJson, FillPlayerHead pValue, JsonSerializationContext pSerializationContext)
        {
            super.serialize(pJson, pValue, pSerializationContext);
            pJson.add("entity", pSerializationContext.serialize(pValue.entityTarget));
        }

        public FillPlayerHead b(JsonObject p_80615_, JsonDeserializationContext p_80616_, LootItemCondition[] p_80617_)
        {
            LootContext.EntityTarget lootcontext$entitytarget = GsonHelper.getAsObject(p_80615_, "entity", p_80616_, LootContext.EntityTarget.class);
            return new FillPlayerHead(p_80617_, lootcontext$entitytarget);
        }
    }
}
