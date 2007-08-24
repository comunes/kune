package org.ourproject.kune.chat.client.ui.rooms;

public class RoomUser {
    public static final int MODERADOR = 1;
    public static final int PARTICIPANT = 2;
    public static final int VISITOR = 3;
    public static final int NONE = 0;

    private String color;
    private String alias;
    private int userType;

    public RoomUser(String alias, String color, int userType) {
	this.alias = alias;
	this.color = color;
	this.userType = userType;
    }

    public String getColor() {
        return color;
    }

    public String getAlias() {
        return alias;
    }

    public int getUserType() {
        return userType;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
