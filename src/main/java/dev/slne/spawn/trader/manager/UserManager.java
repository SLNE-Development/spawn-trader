package dev.slne.spawn.trader.manager;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import dev.slne.spawn.trader.user.User;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Duration;
import java.util.UUID;
@Accessors(fluent = true)
public class UserManager {
    @Getter
    private static final UserManager instance = new UserManager();
    private final LoadingCache<UUID, User> users = Caffeine.newBuilder().expireAfterAccess(Duration.ofMinutes(10)).refreshAfterWrite(Duration.ofMinutes(10)).build(this::createUser);

    private User createUser(UUID uuid){
        return new User(uuid);
    }

    public User getUser(UUID uuid){
        return this.users.get(uuid);
    }
}
