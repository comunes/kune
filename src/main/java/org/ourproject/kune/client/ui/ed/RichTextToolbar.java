/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.ourproject.kune.client.ui.ed;

import org.ourproject.kune.client.KuneFactory;
import org.ourproject.kune.client.ui.BorderPanel;
import org.ourproject.kune.client.ui.CustomPushButton;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A sample toolbar for use with {@link RichTextArea}. It provides a simple UI
 * for all rich text formatting, dynamically displayed only for the available
 * functionality.
 */
public class RichTextToolbar extends Composite {

  /**
   * This {@link ImageBundle} is used for all the button icons. Using an image
   * bundle allows all of these images to be packed into a single image, which
   * saves a lot of HTTP requests, drastically improving startup time.
   */
  public interface Images extends ImageBundle {
    
    /**
     * @gwt.resource bold.png
     */
    AbstractImagePrototype bold();

    /**
     * @gwt.resource link.png
     */
    AbstractImagePrototype createLink();

    /**
     * @gwt.resource hfixedline.png
     */
    AbstractImagePrototype hr();

    /**
     * @gwt.resource incrementindent.png
     */
    AbstractImagePrototype indent();

    /**
     * @gwt.resource images.png
     */
    AbstractImagePrototype insertImage();

    /**
     * @gwt.resource italic.png
     */
    AbstractImagePrototype italic();

    /**
     * @gwt.resource centerpara.png
     */
    AbstractImagePrototype justifyCenter();

    /**
     * @gwt.resource alignleft.png
     */
    AbstractImagePrototype justifyLeft();

    /**
     * @gwt.resource alignright.png
     */
    AbstractImagePrototype justifyRight();

    /**
     * @gwt.resource defaultnumbering.png
     */
    AbstractImagePrototype ol();

    /**
     * @gwt.resource decrementindent.png
     */
    AbstractImagePrototype outdent();

    /**
     * @gwt.resource removeFormat.png
     */
    AbstractImagePrototype removeFormat();

    /**
     * @gwt.resource link_break.png
     */
    AbstractImagePrototype removeLink();

    /**
     * @gwt.resource strikeout.png
     */
    AbstractImagePrototype strikeThrough();

    /**
     * @gwt.resource subscript.png
     */
    AbstractImagePrototype subscript();

    /**
     * @gwt.resource superscript.png
     */
    AbstractImagePrototype superscript();

    /**
     * @gwt.resource defaultbullet.png
     */
    AbstractImagePrototype ul();

    /**
     * @gwt.resource underline.png
     */
    AbstractImagePrototype underline();
    
    /**
     * @gwt.resource backcolor.png
     */
    AbstractImagePrototype backcolor();
    
    /**
     * @gwt.resource fontcolor.png
     */
    AbstractImagePrototype fontcolor();
   
    /**
     * @gwt.resource charfontname.png
     */
    AbstractImagePrototype charfontname();
   
    /**
     * @gwt.resource fontheight.png
     */
    AbstractImagePrototype fontheight();
   
  }

  /**
   * Others:
   * fontdialog.png
   * imagebutton.png
   * inserthyperlink.png
   */

  /**
   * This {@link Constants} interface is used to make the toolbar's strings
   * internationalizable.
   */
  public interface Strings extends Constants {

    String backcolor();
    
    String bold();

    String createLink();

    String fontcolor();

    String hr();

    String indent();

    String insertImage();

    String italic();

    String justifyCenter();

    String justifyLeft();

    String justifyRight();

    String ol();

    String outdent();

    String removeFormat();

    String removeLink();

    String fontSize();

    String fontType();
    
    String strikeThrough();

    String subscript();

    String superscript();

    String ul();

    String underline();

    String ExtraSmall();
    
    String VerySmall();
    
    String Small();
    
    String Medium();
    
    String Large();
    
    String VeryLarge();
    
    String ExtraLarge();    
    
    String Save();
    
    String Cancel();

}

  /**
   * We use an inner EventListener class to avoid exposing event methods on the
   * RichTextToolbar itself.
   */
  private class EventListener implements ClickListener, ChangeListener,
      KeyboardListener {

    public void onChange(Widget sender) {
    	fireEdit();
    }

    public void onClick(Widget sender) {
      if (sender == bold) {
        basic.toggleBold();
      } else if (sender == italic) {
        basic.toggleItalic();
      } else if (sender == underline) {
        basic.toggleUnderline();
      } else if (sender == subscript) {
        basic.toggleSubscript();
      } else if (sender == superscript) {
        basic.toggleSuperscript();
      } else if (sender == strikethrough) {
        extended.toggleStrikethrough();
      } else if (sender == indent) {
        extended.rightIndent();
      } else if (sender == outdent) {
        extended.leftIndent();
      } else if (sender == justifyLeft) {
        basic.setJustification(RichTextArea.Justification.LEFT);
      } else if (sender == justifyCenter) {
        basic.setJustification(RichTextArea.Justification.CENTER);
      } else if (sender == justifyRight) {
        basic.setJustification(RichTextArea.Justification.RIGHT);
      } else if (sender == insertImage) {
        String url = Window.prompt("Enter an image URL:", "http://");
        if (url != null) {
          extended.insertImage(url);
        }
      } else if (sender == createLink) {
        String url = Window.prompt("Enter a link URL:", "http://");
        if (url != null) {
          extended.createLink(url);
        }
      } else if (sender == backColor) {
    	  factory.getWebSafePalette().show(backColor.getAbsoluteLeft(), backColor.getAbsoluteTop() + 20);
    	  factory.getWebSafePalette().addPopupListener(new PopupListener() {
              public void onPopupClosed(PopupPanel sender, boolean autoClosed) {
                  if (factory.getWebSafePalette().isColorSelected()) {
                      basic.setBackColor(factory.getWebSafePalette().getColorSelected());
                  }
                  sender.removePopupListener(this);
              }
          });
      } else if (sender == fontColor) {
    	  factory.getWebSafePalette().show(fontColor.getAbsoluteLeft(), fontColor.getAbsoluteTop() + 20);
    	  factory.getWebSafePalette().addPopupListener(new PopupListener() {
              public void onPopupClosed(PopupPanel sender, boolean autoClosed) {
                  if (factory.getWebSafePalette().isColorSelected()) {
                      basic.setForeColor(factory.getWebSafePalette().getColorSelected());
                  }
                  sender.removePopupListener(this);
              }
          });
      } else if (sender == removeLink) {
        extended.removeLink();
      } else if (sender == hr) {
        extended.insertHorizontalRule();
      } else if (sender == ol) {
        extended.insertOrderedList();
      } else if (sender == ul) {
        extended.insertUnorderedList();
      } else if (sender == removeFormat) {
        extended.removeFormat();
      } else if (sender == richText) {
        // We use the RichTextArea's onKeyUp event to update the toolbar status.
        // This will catch any cases where the user moves the cursur using the
        // keyboard, or uses one of the browser's built-in keyboard shortcuts.
        updateStatus();
      }
      if (sender != richText) {
    	  // some button pressed is equiv to edit
          fireEdit();
      }
    }

    public void onKeyDown(Widget sender, char keyCode, int modifiers) {
    }

    public void onKeyPress(Widget sender, char keyCode, int modifiers) {
    }

    public void onKeyUp(Widget sender, char keyCode, int modifiers) {
      if (sender == richText) {
        // We use the RichTextArea's onKeyUp event to update the toolbar status.
        // This will catch any cases where the user moves the cursur using the
        // keyboard, or uses one of the browser's built-in keyboard shortcuts.
        updateStatus();
        fireEdit();
      }
    }
  }

  private static final RichTextArea.FontSize[] fontSizesConstants = new RichTextArea.FontSize[] {
      RichTextArea.FontSize.XX_SMALL, RichTextArea.FontSize.X_SMALL,
      RichTextArea.FontSize.SMALL, RichTextArea.FontSize.MEDIUM,
      RichTextArea.FontSize.LARGE, RichTextArea.FontSize.X_LARGE,
      RichTextArea.FontSize.XX_LARGE};

  private Images images = (Images) GWT.create(Images.class);
  private Strings strings = (Strings) GWT.create(Strings.class);
  private EventListener listener = new EventListener();

  private RichTextArea richText;
  private RichTextArea.BasicFormatter basic;
  private RichTextArea.ExtendedFormatter extended;

  private VerticalPanel outer = new VerticalPanel();
  private HorizontalPanel topPanel = new HorizontalPanel();
  private ToggleButton bold;
  private ToggleButton italic;
  private ToggleButton underline;
  private ToggleButton subscript;
  private ToggleButton superscript;
  private ToggleButton strikethrough;
  private PushButton indent;
  private PushButton outdent;
  private PushButton justifyLeft;
  private PushButton justifyCenter;
  private PushButton justifyRight;
  private PushButton hr;
  private PushButton ol;
  private PushButton ul;
  private PushButton insertImage;
  private PushButton createLink;
  private PushButton removeLink;
  private PushButton removeFormat;
  private PushButton backColor;
  private PushButton fontColor;
  private CustomPushButton save;
  private CustomPushButton cancel;
  
  private HTML expandCell = null;
  private KuneFactory factory;
  private CustomRichTextAreaController controller; 
  
  /**
   * Creates a new toolbar that drives the given rich text area.
   * 
   * @param richText the rich text area to be controlled
   */
  public RichTextToolbar(RichTextArea richText, CustomRichTextAreaController controller) {
    this.richText = richText;
    this.basic = richText.getBasicFormatter();
    this.extended = richText.getExtendedFormatter();
    this.factory = KuneFactory.get();
    this.controller = controller;

    outer.add(topPanel);
    outer.setWidth("100%");
    topPanel.setWidth("100%");
    outer.setCellWidth(topPanel, "100%");
    
    expandCell = new HTML("&nbsp;");
	    
    initWidget(outer);
    setStyleName("gwt-RichTextToolbar");

    if (basic != null) {
      topPanel.add(bold = createToggleButton(images.bold(), strings.bold()));
      topPanel.add(italic = createToggleButton(images.italic(), strings.italic()));
      topPanel.add(underline = createToggleButton(images.underline(),
          strings.underline()));
    }
    
    if (extended != null) {
      topPanel.add(strikethrough = createToggleButton(images.strikeThrough(),
          strings.strikeThrough()));
    }
    
    if (basic != null) {
      topPanel.add(subscript = createToggleButton(images.subscript(),
          strings.subscript()));
      topPanel.add(superscript = createToggleButton(images.superscript(),
          strings.superscript()));
      topPanel.add(justifyLeft = createPushButton(images.justifyLeft(),
          strings.justifyLeft()));
      topPanel.add(justifyCenter = createPushButton(images.justifyCenter(),
          strings.justifyCenter()));
      topPanel.add(justifyRight = createPushButton(images.justifyRight(),
          strings.justifyRight()));
    }

    if (extended != null) {
      topPanel.add(indent = createPushButton(images.indent(), strings.indent()));
      topPanel.add(outdent = createPushButton(images.outdent(), strings.outdent()));
      topPanel.add(hr = createPushButton(images.hr(), strings.hr()));
      topPanel.add(ol = createPushButton(images.ol(), strings.ol()));
      topPanel.add(ul = createPushButton(images.ul(), strings.ul()));
      topPanel.add(insertImage = createPushButton(images.insertImage(),
          strings.insertImage()));
      topPanel.add(createLink = createPushButton(images.createLink(),
          strings.createLink()));
      topPanel.add(removeLink = createPushButton(images.removeLink(),
          strings.removeLink()));
      topPanel.add(removeFormat = createPushButton(images.removeFormat(),
          strings.removeFormat()));
    }

    if (basic != null) {
      topPanel.add(backColor = createPushButton(images.backcolor(),
          strings.backcolor()));
      topPanel.add(fontColor = createPushButton(images.fontcolor(),
          strings.fontcolor()));
      topPanel.add(createFontsMenu());
      topPanel.add(createFontSizesMenu());

      // We only use these listeners for updating status, so don't hook them up
      // unless at least basic editing is supported.
      richText.addKeyboardListener(listener);
      richText.addClickListener(listener);
    }
    topPanel.add(expandCell);
    topPanel.setCellWidth(expandCell, "100%");
    expandCell.setWidth("100%");
    
    save = new CustomPushButton(strings.Save(), CustomPushButton.SMALL, new ClickListener() {
        public void onClick(Widget sender) {
        	if (sender == save & save.isEnabled()) {
        		fireSave();
        	}
        }
    });
    cancel = new CustomPushButton(strings.Cancel(), CustomPushButton.SMALL, new ClickListener() {
        public void onClick(Widget sender) {
        	if (sender == cancel & cancel.isEnabled()) {
        		fireCancel();
        	}
        }
    });
    topPanel.add(save); //, ClickListener listener))
    topPanel.add(new BorderPanel(cancel, 0, 0, 0, CustomPushButton.HORSPACESMALL)); //, ClickListener listener))
  }
  

  private void fireEdit() {
      controller.onEdit();
  }

  private void fireSave() {
      controller.onSave();
  }
  
  private void fireCancel() {
      controller.onCancel();
  }
  
  private MenuBar createFontsMenu() {
	  MenuBar menu = new MenuBar();
	  MenuBar submenu = new MenuBar(true);
      String fontName[] = {"Times New Roman", "Arial", "Courier New", "Georgia", "Trebuchet", "Verdana"};
	
      // strings.fontType()
	  menu.addItem(images.charfontname().getHTML(), true, submenu);
	  for (int i = 0; i < fontName.length; i++) {
		  final String f = fontName[i];
          submenu.addItem("<span style=\"font-family: " + f + "\">" + f + "</span>", true, new Command() {
              public void execute() {
                  basic.setFontName(f);
                  fireEdit();
              }
          });
      }
	  menu.setStyleName("RichTextToolbar-menu");
	  submenu.setStyleName("RichTextToolbar-submenu");
      return menu;
  }
  
  private MenuBar createFontSizesMenu() {
	  MenuBar menu = new MenuBar();
	  MenuBar submenu = new MenuBar(true);
      String fontSizes[] = {strings.ExtraSmall(), strings.VerySmall(), strings.Small(),
    		  strings.Medium(), strings.Large(), strings.VeryLarge(), strings.ExtraLarge()};

      // strings.fontSize()
	  menu.addItem(images.fontheight().getHTML(), true, submenu);
	  for (int i = 0; i < fontSizes.length; i++) {
		  final String f = fontSizes[i];
		  final int fontSize = i;
          submenu.addItem("<font size=\"" + (i + 1) + "\">" + f + "</font>", true, new Command() {
              public void execute() {
                  basic.setFontSize(fontSizesConstants[fontSize]);
                  fireEdit();
              }
          });
      }
	  menu.setStyleName("RichTextToolbar-menu");
	  submenu.setStyleName("RichTextToolbar-submenu");
      return menu;
  }

  private PushButton createPushButton(AbstractImagePrototype img, String tip) {
    PushButton pb = new PushButton(img.createImage());
    pb.addClickListener(listener);
    pb.setTitle(tip);
    return pb;
  }

  private ToggleButton createToggleButton(AbstractImagePrototype img, String tip) {
    ToggleButton tb = new ToggleButton(img.createImage());
    tb.addClickListener(listener);
    tb.setTitle(tip);
    return tb;
  }
  
  public void setEnabledSaveButton(boolean enabled) {
      save.setEnabled(enabled);
  }
  
  public void setEnabledCancelButton(boolean enabled) {
      cancel.setEnabled(enabled);
  }
  
  public void setTextSaveButton(String text) {
      save.setText(text);
  }
  
  /**
   * Updates the status of all the stateful buttons.
   */
  private void updateStatus() {
    if (basic != null) {
      bold.setDown(basic.isBold());
      italic.setDown(basic.isItalic());
      underline.setDown(basic.isUnderlined());
      subscript.setDown(basic.isSubscript());
      superscript.setDown(basic.isSuperscript());
    }

    if (extended != null) {
      strikethrough.setDown(extended.isStrikethrough());
    }
  }
}

