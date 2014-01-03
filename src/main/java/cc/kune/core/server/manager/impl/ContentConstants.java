/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.core.server.manager.impl;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentConstants.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public final class ContentConstants {

  /** The Constant INITIAL_CONTENT. */
  public static final String INITIAL_CONTENT = "<span style=\"font-weight: bold;\">Welcome to [%s]</span><br/>"
      + "    <br/>"
      + "    This site is powered by <a target=\"_blank\""
      + "href=\"http://kune.ourproject.org/\">Kune</a>.<br/>"
      + "    <br/>"
      + "    Kune is currently under development.<br/>"
      + "    <br/>"
      + "    To test it, <a href=\"#signin\">sign in registering"
      + "        an user</a>, but take into account that:<br/>"
      + "      <ul>"
      + "        <li>This site is not optimized yet, then the initial load and"
      + "          other operations maybe are slow.</li>"
      + "        <li>Don't use passwords that you are using in other sites (this"
      + "          site isn't secure yet storing passwords).</li>"
      + "        <li>We need your feedback. Please help us to improve this"
      + "          software reporting <a target=\"_blank\""
      + "href=\"http://kune.ourproject.org/issues/\">bugs"
      + "            and/or suggestions</a>. Also you <a target=\"_blank\""
      + "            href=\"&amp;\">help us to"
      + "            translate it</a> into other languages.</li>"
      + "      </ul>"
      + "      Thank you,<br/>"
      + "      --<br/>"
      + "      <span style=\"font-style: italic;\">The Kune development team</span>";

  /** The Constant WELCOME_WAVE_CONTENT. */
  public static final String WELCOME_WAVE_CONTENT = "<span style=\"font-weight: bold;\">Welcome to [%s]. Some tips to getting started</span><br/>"
      + "    <br/>"
      + "    Check out some tips to get you started in this site.<br/>"
      + "    <br/>"
      + "    <span style=\"font-weight: bold;\">The Basics</span><br/>"
      + "    <br/>"
      + "    This site have several spaces:<br/>"
      + "    <br/>"
      + "    <ul>"
      + "      <li><span style=\"text-decoration: underline;\">the Home Space</span>:"
      + "        your welcome page, with news and contents related to your"
      + "        groups, and where you can see your friends and others activity.</li>"
      + "      <li><span style=\"text-decoration: underline;\">the Inbox</span>:"
      + "        the same way that your email Inbox shows a list of emails (old"
      + "        and new), this space shows a list of all your documents and"
      + "        contents in which you participate, highlighting the old ones"
      + "        with new contents. From here you can communicate with others,"
      + "        but also generate contents together that you can publish later.</li>"
      + "      <li><span style=\"text-decoration: underline;\">the Group &amp;"
      + "          Personal Space</span>: the contents you built can be part of"
      + "        your personal or specific group web space. You can have one"
      + "        personal space (like your blog, or your academic CV, etc), and"
      + "        belong to multiple group spaces (like initiatives you"
      + "        participate in your neighbourhood, or some scholar or"
      + "        proffesional initiatives, etc).</li>"
      + "      <li><span style=\"text-decoration: underline;\">the Public Space</span>:"
      + "        any content from the previous space, when finished, can be"
      + "        published in the web so anyone can see it. In this space you can"
      + "        see a preview of how the Personal or Group Space looks like on"
      + "        the web, outside the [%s] site.</li>"
      + "    </ul>"
      + "    <br/>"
      + "    In sum, your contents will be<br/>"
      + "    <ul>"
      + "      <li>somehow <span style=\"text-decoration: underline;\">private</span>"
      + "        (chat with your friends, private contents created with them,"
      + "        shared photos, draft documents, etc)</li>"
      + "      <li><span style=\"text-decoration: underline;\">public</span> (works"
      + "        you want to share with everyone, finished documents, public"
      + "        photos/videos) </li>"
      + "    </ul>"
      + "    <br/>"
      + "    Here we are talking about \"contents\", but in fact those will be"
      + "    called \"waves\", a new way of communicating with others. Let us"
      + "    introduce it...<br/>"
      + "    <br/>"
      + "    <span style=\"font-weight: bold;\">Here comes the Wave</span><br/>"
      + "    <br/>"
      + "    A \"wave\" is a special document (like in a word processor text) where"
      + "    you can include comments and conversations (like in a forum). Any"
      + "    wave can be edited by you like any document, but it will be saved"
      + "    online instead of in your computer. Besides, anyone you \"share\" the"
      + "    wave with (your friends Layla and Xavier, for example) can also edit"
      + "    the same document, improving and extending what you wrote. You can"
      + "    turn off your computer, come back the next day, and see how others"
      + "    changed the wave while you were away.<br/>"
      + "    <br/>"
      + "    Thus, all your contents will be different \"waves\", regardless if it"
      + "    is a photo gallery, a discussion, a blog post, a meeting"
      + "    announcement, or a CV.<br/>"
      + "    <br/>"
      + "    <span style=\"font-weight: bold;\">Starting a new wave</span><br/>"
      + "    <br/>"
      + "    Anyone can create a new wave (like a blank document). For starting a"
      + "    wave, just click on the <span style=\"font-style: italic;\">New Wave</span>"
      + "    button in your Inbox. You can share this wave with any of your"
      + "    friends. On the other hand, the waves that your friends created and"
      + "    shared with you, will directly appear in your inbox. <br/>"
      + "    <br/>"
      + "    <span style=\"font-weight: bold;\">Replying to a wave</span><br/>"
      + "    <br/>"
      + "    Layla and Xavier can also make comments to the text, in any"
      + "    paragraph or sentence they want to comment. Any of you can reply to"
      + "    every comment, establishing discussions about certain parts of the"
      + "    document. <br/>"
      + "    <ul>"
      + "      <li>For making a comment on the bottom of the wave:</li>"
      + "      <ul>"
      + "        <li><span style=\"font-style: italic;\">with your mouse</span>:"
      + "          click on the \"Click here to reply\" in the bottom, or</li>"
      + "        <li><span style=\"font-style: italic;\">with the keyboard</span>:"
      + "          press the \"Enter\" key.</li>"
      + "      </ul>"
      + "      <li> For making a comment on a specific part of the text:</li>"
      + "      <ul>"
      + "        <li><span style=\"font-style: italic;\">with your mouse</span>:"
      + "          Double click on the point you want to insert the comment and"
      + "          click \"Reply\", or</li>"
      + "        <li><span style=\"font-style: italic;\">with the keyboard</span>:"
      + "          Select that text (or comment) and press the \"Enter\" key</li>"
      + "      </ul>"
      + "    </ul>"
      + "    <br/>"
      + "    <span style=\"font-weight: bold;\">Editing waves</span><br/>"
      + "    <br/>"
      + "    All participants on a wave can edit any part of the wave, at the"
      + "    same time. You don't need to wait until Layla finishes a section to"
      + "    begin editing, you can all do it at once. <br/>"
      + "    <ul>"
      + "      <li>For editing a wave:</li>"
      + "      <ul>"
      + "        <li><span style=\"font-style: italic;\">with your mouse</span>:"
      + "          Double click on the wave (or comment) and click \"Edit\", or</li>"
      + "        <li><span style=\"font-style: italic;\">with your keyboard</span>:"
      + "          Select where you want to edit, and press at once the keys"
      + "          \"Ctrl\" and \"E\"</li>"
      + "      </ul>"
      + "      <li>To finish the edition of the wave or a comment:</li>"
      + "      <ul>"
      + "        <li><span style=\"font-style: italic;\">with your mouse</span>:"
      + "          Click on the \"Done\" button at the bottom of your wave, or</li>"
      + "        <li><span style=\"font-style: italic;\">with your keyboard</span>:"
      + "          press at once the keys \"Shift\" and \"Enter\"</li>"
      + "      </ul>"
      + "    </ul>"
      + "    <br/>"
      + "    <span style=\"font-weight: bold;\">Reading through waves</span><br/>"
      + "    <br/>"
      + "    When you open a wave that others edited, there will be comment you"
      + "    already read before, and new comments they wrote when you were away."
      + "    The unread comments are highlighted so you can easily know what's"
      + "    new. <br/>"
      + "    <ul>"
      + "      <li>For surfing quickly through the next unread comments, you can</li>"
      + "      <ul>"
      + "        <li><span style=\"font-style: italic;\">with your keyboard:</span>"
      + "          press the \"Spacebar\" key.</li>"
      + "      </ul>"
      + "    </ul>"
      + "    <br/>"
      + "    <span style=\"font-weight: bold;\">Rich contents and add-ons</span><br/>"
      + "    <br/>"
      + "    Any wave can have not only text, but also images or videos... and"
      + "    much more. It's easy to insert add-ons in your waves, that allow you"
      + "    to include other \"rich content\" (polls, maps...), extra"
      + "    functionalities (translations, word-counters, spelling...) or"
      + "    integration with other sites (newspapers, twitter...)<br/>"
      + "    <br/>"
      + "    <a target=\"_blank\""
      + "href=\"http://wave-samples-gallery.appspot.com\">Visit"
      + "      the Google Wave sample extensions</a><br/>"
      + "    <br/>"
      + "    <span style=\"font-weight: bold;\">Finding one specific wave</span><br/>"
      + "    <br/>"
      + "    In the Inbox you have your list of waves, the ones you created or"
      + "    the ones others shared with you. You can use the search box to"
      + "    search through all your waves, using specific keywords.<br/>";

  /** The Constant WELCOME_WAVE_CONTENT_TITLE. */
  public static final String WELCOME_WAVE_CONTENT_TITLE = "Welcome to [%s]";

  /**
   * Instantiates a new content constants.
   */
  private ContentConstants() {
  }
}
