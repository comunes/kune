package cc.kune.core.server.notifier;

import java.util.Collection;

import org.waveprotocol.wave.model.waveref.WaveRef;

import cc.kune.domain.User;
import cc.kune.wave.server.KuneWaveServerUtils;

/**
 * The Class WaveDestinationProvider. A WaveRef is used to get the participants
 * and to send notifications to them
 */
public class WaveDestinationProvider implements DestinationProvider {

  private final String author;

  /** The list. */
  private Collection<User> list;

  /** The ref. */
  private final WaveRef ref;

  /**
   * Instantiates a new wave destination provider.
   * 
   * @param ref
   *          the ref
   */
  public WaveDestinationProvider(final WaveRef ref, final String author) {
    this.ref = ref;
    this.author = author;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final WaveDestinationProvider other = (WaveDestinationProvider) obj;
    if (ref == null) {
      if (other.ref != null) {
        return false;
      }
    } else if (!ref.equals(other.ref)) {
      return false;
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.wave.server.kspecific.pendnotif.DestinationProvider#getDest()
   */
  @Override
  public Collection<User> getDest() {
    if (list == null) {
      list = KuneWaveServerUtils.getLocalParticipants(ref, author);
    }
    return list;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ref == null) ? 0 : ref.hashCode());
    return result;
  }

}
