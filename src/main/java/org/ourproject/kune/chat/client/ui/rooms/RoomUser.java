package org.ourproject.kune.chat.client.ui.rooms;

public class RoomUser {
    public static final UserType MODERADOR = new UserType();
    public static final UserType PARTICIPANT = new UserType();
    public static final UserType VISITOR = new UserType();
    public static final UserType NONE = new UserType();

    public static class UserType {
	private UserType() {
	}
    }

    private String color;
    private String alias;
    private UserType type;

    public RoomUser(final String alias, final String color, final UserType userType) {
	this.alias = alias;
	this.color = color;
	this.type = userType;
    }

    public String getColor() {
	return color;
    }

    public String getAlias() {
	return alias;
    }

    public UserType getUserType() {
	return type;
    }

    public void setColor(final String color) {
	this.color = color;
    }

    public void setAlias(final String alias) {
	this.alias = alias;
    }

    public void setUserType(final UserType userType) {
	this.type = userType;
    }
}
