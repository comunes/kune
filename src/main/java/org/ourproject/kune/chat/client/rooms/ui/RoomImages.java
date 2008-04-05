package org.ourproject.kune.chat.client.rooms.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface RoomImages extends ImageBundle {

    public static class App {
	private static RoomImages ourInstance = null;

	public static synchronized RoomImages getInstance() {
	    if (ourInstance == null) {
		ourInstance = (RoomImages) GWT.create(RoomImages.class);
	    }
	    return ourInstance;
	}
    }

    /**
     * @gwt.resource alien.png
     */
    AbstractImagePrototype alien();

    /**
     * @gwt.resource andy.png
     */
    AbstractImagePrototype andy();

    /**
     * @gwt.resource angel.png
     */
    AbstractImagePrototype angel();

    /**
     * @gwt.resource angry.png
     */
    AbstractImagePrototype angry();

    /**
     * @gwt.resource bandit.png
     */
    AbstractImagePrototype bandit();

    /**
     * @gwt.resource blushing.png
     */
    AbstractImagePrototype blushing();

    /**
     * @gwt.resource bullet_black.png
     */
    AbstractImagePrototype bulletBlack();

    /**
     * @gwt.resource bullet_star.png
     */
    AbstractImagePrototype bulletStar();

    /**
     * @gwt.resource cool.png
     */
    AbstractImagePrototype cool();

    /**
     * @gwt.resource crying.png
     */
    AbstractImagePrototype crying();

    /**
     * @gwt.resource devil.png
     */
    AbstractImagePrototype devil();

    /**
     * @gwt.resource grin.png
     */
    AbstractImagePrototype grin();

    /**
     * @gwt.resource happy.png
     */
    AbstractImagePrototype happy();

    /**
     * @gwt.resource heart.png
     */
    AbstractImagePrototype heart();

    /**
     * @gwt.resource joyful.png
     */
    AbstractImagePrototype joyful();

    /**
     * @gwt.resource kissing.png
     */
    AbstractImagePrototype kissing();

    /**
     * @gwt.resource lol.png
     */
    AbstractImagePrototype lol();

    /**
     * @gwt.resource love.png
     */
    AbstractImagePrototype love();

    /**
     * @gwt.resource ninja.png
     */
    AbstractImagePrototype ninja();

    /**
     * @gwt.resource pinched.png
     */
    AbstractImagePrototype pinched();

    /**
     * @gwt.resource policeman.png
     */
    AbstractImagePrototype policeman();

    /**
     * @gwt.resource pouty.png
     */
    AbstractImagePrototype pouty();

    /**
     * @gwt.resource sad.png
     */
    AbstractImagePrototype sad();

    /**
     * @gwt.resource sick.png
     */
    AbstractImagePrototype sick();

    /**
     * @gwt.resource sideways.png
     */
    AbstractImagePrototype sideways();

    /**
     * @gwt.resource sleeping.png
     */
    AbstractImagePrototype sleeping();

    /**
     * @gwt.resource smile.png
     */
    AbstractImagePrototype smile();

    /**
     * @gwt.resource surprised.png
     */
    AbstractImagePrototype surprised();

    /**
     * @gwt.resource tongue.png
     */
    AbstractImagePrototype tongue();

    /**
     * @gwt.resource uncertain.png
     */
    AbstractImagePrototype uncertain();

    /**
     * @gwt.resource unsure.png
     */
    AbstractImagePrototype unsure();

    /**
     * @gwt.resource w00t.png
     */
    AbstractImagePrototype w00t();

    /**
     * @gwt.resource whistling.png
     */
    AbstractImagePrototype whistling();

    /**
     * @gwt.resource wink.png
     */
    AbstractImagePrototype wink();

    /**
     * @gwt.resource wizard.png
     */
    AbstractImagePrototype wizard();

    /**
     * @gwt.resource wondering.png
     */
    AbstractImagePrototype wondering();

}
