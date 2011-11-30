package cc.kune.core.server.rack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.inject.Singleton;

@Singleton
public class AdminThread extends Thread {

  private ServerSocket socket;

  public AdminThread() {
    setDaemon(true);
    setName("StopMonitor");
    try {
      socket = new ServerSocket(8079, 1, InetAddress.getByName("127.0.0.1"));
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void run() {
    System.out.println("*** running jetty 'stop' thread");
    Socket accept;
    try {
      accept = socket.accept();
      final BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
      reader.readLine();
      System.out.println("*** stopping jetty embedded server");
      accept.close();
      socket.close();
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }
}