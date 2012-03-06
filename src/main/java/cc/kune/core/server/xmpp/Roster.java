package cc.kune.core.server.xmpp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ofRoster")
public class Roster {

  @Id
  @GeneratedValue
  @Column(name = "rosterID")
  private Long id;

  private String jid;
  private String nick;
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

  public void setUsername(final String username) {
    this.username = username;
  }

  // protected RecvType recvStatus;
  //
  // protected Set<String> sharedGroups = new HashSet<String>();
  //
  // protected Set<String> invisibleSharedGroups = new HashSet<String>();
  //
  // protected SubType subStatus;
  //
  // protected AskType askStatus;
}
