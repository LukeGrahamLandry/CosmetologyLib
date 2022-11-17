package ca.lukegrahamlandry.cosmetology.forge;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import net.minecraftforge.fml.common.Mod;

@Mod(CosmetologyApi.MOD_ID)
public class CosmetologyForge {
    public CosmetologyForge() {

    }

    /* TODO
     * config that has
     * - a list of urls to be added as RemoteAvailableCosmetics that are shown in the ModSupporterCosmeticSelectGui (auto add to ModSupportCosmeticsRegistry)
     * - a list of urls to be added as remote resource packs
     *
     * on the server we make a ModSupportCosmetics that mods can register to
     * - allow mods to include a json manifest of a DataStoreAvailable as modid:cosmetology/manifest.json
     * - read a RemoteAvailableCosmetics from mods.toml
     * - sync to a normal ClientPacketDataStore
     *
     * have a way for a cosmetic to just let them add their own render layer
     *
     * ideas for my mods
     * - mimic pet that follows you around
     * - torcherino block hat
     * - travel anchor skull
     */
}
