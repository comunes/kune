package cc.kune.core.server.auth.openfire;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.jivesoftware.openfire.auth.AuthProvider;
import org.jivesoftware.openfire.auth.ConnectionException;
import org.jivesoftware.openfire.auth.InternalUnauthenticatedException;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.waveprotocol.wave.model.wave.ParticipantId;

public class KuneAuthProvider implements AuthProvider {

  protected class ClassLoadingObjectInputStream extends ObjectInputStream {

    /**
     * Code copied from HashSessionManager
     * 
     * @throws IOException
     */
    public ClassLoadingObjectInputStream() throws IOException {
      super();
    }

    public ClassLoadingObjectInputStream(final java.io.InputStream in) throws IOException {
      super(in);
    }

    @Override
    public Class<?> resolveClass(final java.io.ObjectStreamClass cl) throws IOException,
        ClassNotFoundException {
      try {
        return Class.forName(cl.getName(), false, Thread.currentThread().getContextClassLoader());
      } catch (final ClassNotFoundException e) {
        return super.resolveClass(cl);
      }
    }
  }

  private static final String USER_NOT_LOGGED = "User not logged";

  @SuppressWarnings("unused")
  @Override
  public void authenticate(final String username, final String token) throws UnauthorizedException,
      ConnectionException, InternalUnauthenticatedException {
    try {
      final File session = new File("/var/lib/kune/_sessions/" + token);
      boolean logged = false;
      if (session.exists()) {
        final FileInputStream is = new FileInputStream(session);
        final DataInputStream in = new DataInputStream(is);

        final String clusterId = in.readUTF();
        in.readUTF(); // nodeId
        final long created = in.readLong();
        final long accessed = in.readLong();
        final int requests = in.readInt();

        final int size = in.readInt();
        if (size > 0) {
          final ClassLoadingObjectInputStream ois = new ClassLoadingObjectInputStream(in);
          for (int i = 0; i < size; i++) {
            final String key = ois.readUTF();
            final Object value = ois.readObject();
            if (value instanceof ParticipantId) {
              if (((ParticipantId) value).getAddress().startsWith(username + "@")) {
                logged = true;
                break;
              }
            }
          }
          ois.close();
        }
        in.close();
        is.close();
        if (!logged) {
          throw new UnauthorizedException("Incorrect username");
        }
      } else {
        throw new UnauthorizedException(USER_NOT_LOGGED);
      }
    } catch (final FileNotFoundException e) {
      throw new UnauthorizedException(USER_NOT_LOGGED, e);
    } catch (final IOException e) {
      throw new UnauthorizedException(USER_NOT_LOGGED, e);
    } catch (final ClassNotFoundException e) {
      throw new UnauthorizedException(USER_NOT_LOGGED, e);
    }
  }

  @Override
  public void authenticate(final String username, final String token, final String digest)
      throws UnauthorizedException, ConnectionException, InternalUnauthenticatedException {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getPassword(final String arg0) throws UserNotFoundException,
      UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isDigestSupported() {
    return false;
  }

  @Override
  public boolean isPlainSupported() {
    return true;
  }

  @Override
  public void setPassword(final String arg0, final String arg1) throws UserNotFoundException,
      UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean supportsPasswordRetrieval() {
    return false;
  }

}
