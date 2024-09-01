package dev.slne.spawn.trader.manager;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import dev.slne.spawn.trader.user.User;
import java.time.Duration;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * The type User manager.
 */
@Accessors(fluent = true)
public class UserManager {

  @Getter
  private static final UserManager instance = new UserManager();
  private final LoadingCache<UUID, User> users = Caffeine.newBuilder()
      .expireAfterAccess(Duration.ofMinutes(10))
      .build(this::createUser);

  /**
   * Create user user.
   *
   * @param uuid the uuid
   * @return the user
   */
  private User createUser(UUID uuid) {
    return new User(uuid);
  }

  /**
   * Gets user.
   *
   * @param uuid the uuid
   * @return the user
   */
  public User getUser(UUID uuid) {
    return this.users.get(uuid);
  }
}
