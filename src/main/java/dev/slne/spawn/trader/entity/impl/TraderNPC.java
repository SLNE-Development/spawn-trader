package dev.slne.spawn.trader.entity.impl;

import dev.slne.spawn.trader.entity.CustomTrader;

import lol.pyr.znpcsplus.api.NpcApi;
import lol.pyr.znpcsplus.api.NpcApiProvider;
import lol.pyr.znpcsplus.api.entity.EntityProperty;
import lol.pyr.znpcsplus.api.npc.NpcEntry;
import lol.pyr.znpcsplus.api.npc.NpcType;
import lol.pyr.znpcsplus.api.skin.SkinDescriptor;
import lol.pyr.znpcsplus.util.LookType;
import lol.pyr.znpcsplus.util.NpcLocation;

import org.bukkit.Location;


public class TraderNPC implements CustomTrader {
  private final NpcApi npcApi = NpcApiProvider.get();
  private final String signature = "G/JtpyiQxNFn2ucGK1sHz7NZlxJhFeLzXNlCFCokL26N4gOg53Cwq2jowVSIUcDwxDxl43+ehMJzPjU5+DCVsoO5xJh5Zsh/98wpkWCE9PcOw/iAWTrcghEjusV5prlqS8QLAH82+ndP746PkNIyVhJlc8l5BACwRFbI3Nd2X2KwqyRJDWSbhPMl/V1Sg/mwGAl+IMzPjdvriN0CJDwqKv9gVugeLSmqFvlNIerwlkgyZqaaKtIYv/zRkaVmbFW0Dv8KxxAlH2zTpicY/F8aof4NjrvwajeuKd6z0jopnS+nNv+b6bqNcAnhbQWlTKz2V3mgzNZT2NjoTNopHa5FzJ+uEzSV9xr3XCrmFIM8PRoDLJEYOSFR2btA6V4PO0Hksdr5sags3lGGHihY+DTYKwNnQsJeL8jKBi12IHtmZ6HJvPkcPElNIzKVaIqgi5w3WEUv6QW3Dfp6U1Q77SrEzBg3d4I3ZSgXfN5L+B6qrE4EKyLCb9jZQD567CxVnOyPEz3k5bo0D/YOd9X75wWKFEnXmVO2UCxFlrueIAJZX+tEjbQM6jnfbkTctURl7qT5tvGhjMowE5arwN0PrZQ8Y1DzLv0FPVS+wkYvpyMURr3pd/pzKcy+60QjUVowzZKWCFtfGkV8Q/KkmYhmkHfSa+njPWMGhJEKkq3ecY8DQ0g==";
  private final String texture = "ewogICJ0aW1lc3RhbXAiIDogMTYxMzA5NDI3OTAzNSwKICAicHJvZmlsZUlkIiA6ICJhOGExZGY5MGE3YjU0NTdjYTEwYjM5ZGQ0N2NmMjM5MCIsCiAgInByb2ZpbGVOYW1lIiA6ICJrZW5ueWtjb21haW4iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2NmOTU4NjQyMmM4NmI5ODk5N2QxMTYyNzc2OTYzNDcyZTQ1YzE4MzE0Y2Q3ODY1ZmY5NzYxOGE5MzdkYmQ2OCIKICAgIH0KICB9Cn0=";

  private final EntityProperty<SkinDescriptor> skinProperty = npcApi.getPropertyRegistry().getByName("skin", SkinDescriptor.class);
  private final EntityProperty<LookType> lookProperty = npcApi.getPropertyRegistry().getByName("look", LookType.class);
  private final SkinDescriptor skin = npcApi.getSkinDescriptorFactory().createStaticDescriptor(texture, signature);

  public static final String STRING_NAME = "<b><gradient:#369d91:#306965>ѕᴘᴀᴡɴ ᴛʀᴀᴅᴇʀ";

  @Override
  public void spawn(Location location, String name) {
    NpcType type = npcApi.getNpcTypeRegistry().getByName("player");

    if (type == null) {
      throw new IllegalStateException("The player NPC type is not registered.");
    }

    NpcEntry entry = npcApi.getNpcRegistry().create(
        name,
        location.getWorld(),
        type,
        new NpcLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch())
    );

    entry.getNpc().getHologram().insertLine(0, STRING_NAME);
    entry.getNpc().setProperty(skinProperty, npcApi.getSkinDescriptorFactory().createStaticDescriptor("Trader"));
    entry.getNpc().setProperty(lookProperty, LookType.PER_PLAYER);
    entry.enableEverything();
  }

  @Override
  public void clear(String name) {
    NpcEntry entry = npcApi.getNpcRegistry().getById(name);

    if (entry != null) {
      npcApi.getNpcRegistry().delete(name);
    }
  }
}
