package cc.kune.core.server.properties;

import java.util.ArrayList;
import java.util.List;

import cc.kune.core.client.errors.DefaultException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ReservedWordsRegistry extends ArrayList<String> {

  private static final long serialVersionUID = 7455756500618858360L;

  public static List<String> fromList(final KuneProperties kuneProperties) {
    return kuneProperties.getList(KuneProperties.RESERVED_WORDS);
  }

  @Inject
  public ReservedWordsRegistry(final KuneProperties kuneProperties) {
    super(fromList(kuneProperties));
  }

  public void check(final String... names) {
    for (final String name : names) {
      if (this.contains(name) || this.contains(name.toLowerCase())) {
        throw new DefaultException("This name is a reserved word and cannot be used");
      }
    }
  }
}
