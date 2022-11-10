package ca.lukegrahamlandry.cosmetology.command;

import ca.lukegrahamlandry.cosmetology.data.api.CosmeticInfo;
import ca.lukegrahamlandry.cosmetology.data.api.DataStore;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CosmeticSlotArgumentType extends ResourceLocationArgument {
    private Supplier<DataStore> backingDataStore;

    public CosmeticSlotArgumentType(Supplier<DataStore> backingDataStore){
        this.backingDataStore = backingDataStore;
    }
    private static final DynamicCommandExceptionType INVALID = new DynamicCommandExceptionType((p_106991_) -> {
        return new TranslationTextComponent("error.cosmetology.invalid_event", p_106991_);
    });

    public static ResourceLocation get(CommandContext<CommandSource> p_107002_, String p_107003_, DataStore backingDataStore) throws CommandSyntaxException {
        ResourceLocation resourcelocation = getId(p_107002_, p_107003_);
        if (!options(backingDataStore).contains(resourcelocation)) {
            throw INVALID.create(resourcelocation);
        } else {
            return resourcelocation;
        }
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        StringReader stringreader = new StringReader(builder.getInput());
        stringreader.setCursor(builder.getStart());
        String s = stringreader.getRemaining();
        stringreader.setCursor(stringreader.getTotalLength());

        stringreader.skipWhitespace();

        for (ResourceLocation check : options(this.backingDataStore.get())){
            if (check.toString().startsWith(s) || check.getPath().startsWith(s)) builder.suggest(check.toString());
        }

        return builder.buildFuture();
    }

    private static Set<ResourceLocation> options(DataStore backingDataStore){
        return backingDataStore.getSlots();
    }

    public Collection<String> getExamples() {
        return new ArrayList<>();
    }
}