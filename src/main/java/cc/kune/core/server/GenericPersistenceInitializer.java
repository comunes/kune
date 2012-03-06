package cc.kune.core.server;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

public class GenericPersistenceInitializer {
  @Inject
  GenericPersistenceInitializer(final PersistService service) {
    service.start();
    // At this point JPA is started and ready.
  }
}