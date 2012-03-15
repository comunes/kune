package cc.kune.core.server.xmpp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ofRoster")
public class RosterItem {

  @Id
  @GeneratedValue
  @Column(name = "rosterID")
  private Long id;
  private String jid;

  private String nick;

  @Column(name = "sub")
  private byte subStatus;

  private String username;

  public Long getId() {
    return id;
  }

  public String getJid() {
    return jid;
  }

  public String getNick() {
    return nick;
  }

  /**
   * <p>
   * 0 - None subscribed
   * <p>
   * <p>
   * 1 - The roster owner has a subscription to the roster item's presence.
   * </p>
   * <p>
   * 2 - The roster item has a subscription to the roster owner's presence.
   * </p>
   * <p>
   * 3 - The roster item and owner have a mutual subscription.
   * </p>
   */
  public byte getSubStatus() {
    return subStatus;
  }

  public String getUsername() {
    return username;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public void setJid(final String jid) {
    this.jid = jid;
  }

  public void setNick(final String nick) {
    this.nick = nick;
  }

  public void setSubStatus(final byte subStatus) {
    this.subStatus = subStatus;
  }

  public void setUsername(final String username) {
    this.username = username;
  }
  // protected RecvType recvStatus;
  //
  // protected Set<String> sharedGroups = new HashSet<String>();
  //
  // protected Set<String> invisibleSharedGroups = new HashSet<String>();
  //
  //
  // protected AskType askStatus;
}
