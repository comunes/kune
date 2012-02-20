package cc.kune.common.shared.utils;

public interface SimpleArgCallback<T> {

  /**
   * Notifies this callback
   */
  void onCallback(T arg);
}
