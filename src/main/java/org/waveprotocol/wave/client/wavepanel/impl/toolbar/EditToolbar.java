// @formatter:off
/**
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.waveprotocol.wave.client.wavepanel.impl.toolbar;

import org.waveprotocol.box.webclient.client.HistorySupport;
import org.waveprotocol.wave.client.common.util.WaveRefConstants;
import org.waveprotocol.wave.client.doodad.link.Link;
import org.waveprotocol.wave.client.doodad.link.Link.InvalidLinkException;
import org.waveprotocol.wave.client.editor.Editor;
import org.waveprotocol.wave.client.editor.EditorContext;
import org.waveprotocol.wave.client.editor.EditorContextAdapter;
import org.waveprotocol.wave.client.editor.content.CMutableDocument;
import org.waveprotocol.wave.client.editor.content.ContentElement;
import org.waveprotocol.wave.client.editor.content.ContentNode;
import org.waveprotocol.wave.client.editor.content.misc.StyleAnnotationHandler;
import org.waveprotocol.wave.client.editor.content.paragraph.Paragraph;
import org.waveprotocol.wave.client.editor.content.paragraph.Paragraph.LineStyle;
import org.waveprotocol.wave.client.editor.toolbar.ButtonUpdater;
import org.waveprotocol.wave.client.editor.toolbar.ParagraphApplicationController;
import org.waveprotocol.wave.client.editor.toolbar.ParagraphTraversalController;
import org.waveprotocol.wave.client.editor.toolbar.TextSelectionController;
import org.waveprotocol.wave.client.editor.util.EditorAnnotationUtil;
import org.waveprotocol.wave.client.gadget.GadgetXmlUtil;
import org.waveprotocol.wave.client.wavepanel.impl.toolbar.attachment.AttachmentPopupWidget;
import org.waveprotocol.wave.client.wavepanel.impl.toolbar.gadget.GadgetSelectorWidget;
import org.waveprotocol.wave.client.wavepanel.view.AttachmentPopupView;
import org.waveprotocol.wave.client.wavepanel.view.AttachmentPopupView.Listener;
import org.waveprotocol.wave.client.widget.popup.UniversalPopup;
import org.waveprotocol.wave.client.widget.toolbar.SubmenuToolbarView;
import org.waveprotocol.wave.client.widget.toolbar.ToolbarButtonViewBuilder;
import org.waveprotocol.wave.client.widget.toolbar.ToolbarView;
import org.waveprotocol.wave.client.widget.toolbar.ToplevelToolbarWidget;
import org.waveprotocol.wave.client.widget.toolbar.buttons.ToolbarClickButton;
import org.waveprotocol.wave.client.widget.toolbar.buttons.ToolbarToggleButton;
import org.waveprotocol.wave.media.model.AttachmentIdGenerator;
import org.waveprotocol.wave.media.model.AttachmentIdGeneratorImpl;
import org.waveprotocol.wave.model.document.util.FocusedRange;
import org.waveprotocol.wave.model.document.util.LineContainers;
import org.waveprotocol.wave.model.document.util.Point;
import org.waveprotocol.wave.model.document.util.XmlStringBuilder;
import org.waveprotocol.wave.model.id.IdGenerator;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.WaveRef;
import org.waveprotocol.wave.util.escapers.GwtWaverefEncoder;

import com.google.common.base.Preconditions;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;

/**
 * Attaches actions that can be performed in a Wave's "edit mode" to a toolbar.
 * <p>
 * Also constructs an initial set of such actions.
 *
 * @author kalman@google.com (Benjamin Kalman)
 */
public class EditToolbar {

  /**
   * Container for an alignment.
   */
  private static final class Alignment {
    public final String description;
    public final String iconCss;
    public final LineStyle style;
    public Alignment(final String description, final String iconCss, final LineStyle style) {
      this.description = description;
      this.iconCss = iconCss;
      this.style = style;
    }
  }

  /**
   * Handler for click buttons added with {@link EditToolbar#addClickButton}.
   */
  public interface ClickHandler {
    void onClicked(EditorContext context);
  }

  /**
   * Container for a font family.
   */
  private static final class FontFamily {
    public final String description;
    public final String style;
    public FontFamily(final String description, final String style) {
      this.description = description;
      this.style = style;
    }
  }

  private static <E> E[] asArray(final E... elements) {
    return elements;
  }
  /**
   * Attaches editor behaviour to a toolbar, adding all the edit buttons.
   */
  public static EditToolbar create(final ParticipantId user, final IdGenerator idGenerator) {
    final ToplevelToolbarWidget toolbarUi = new ToplevelToolbarWidget();
    final EditorToolbarResources.Css css = EditorToolbarResources.Loader.res.css();
    return new EditToolbar(css, toolbarUi, user, idGenerator);
  }
  private final AttachmentIdGenerator attachmentIdGenerator;
  private final EditorToolbarResources.Css css;

  private final EditorContextAdapter editor = new EditorContextAdapter(null);
  private final ToplevelToolbarWidget toolbarUi;

  private final ButtonUpdater updater = new ButtonUpdater(editor);

  private final ParticipantId user;

  private EditToolbar(final EditorToolbarResources.Css css, final ToplevelToolbarWidget toolbarUi,
      final ParticipantId user, final IdGenerator idGenerator) {
    this.css = css;
    this.toolbarUi = toolbarUi;
    this.user = user;
    attachmentIdGenerator = new AttachmentIdGeneratorImpl(idGenerator);
  }

  /**
   * Adds a button to this toolbar.
   */
  public void addClickButton(final String icon, final ClickHandler handler) {
    final ToolbarClickButton.Listener uiHandler = new ToolbarClickButton.Listener() {
      @Override
      public void onClicked() {
        handler.onClicked(editor);
      }
    };
    new ToolbarButtonViewBuilder().setIcon(icon).applyTo(toolbarUi.addClickButton(), uiHandler);
  }

  private void createAlignButtons(final ToolbarView toolbar) {
    final SubmenuToolbarView submenu = toolbar.addSubmenu();
    new ToolbarButtonViewBuilder()
        .setIcon(css.alignDrop())
        .applyTo(submenu, null);
    submenu.setShowDropdownArrow(false); // Icon already has dropdown arrow.
    final ToolbarView group = submenu.addGroup();
    for (final Alignment alignment : asArray(
        new Alignment("Left", css.alignLeft(), Paragraph.Alignment.LEFT),
        new Alignment("Centre", css.alignCentre(), Paragraph.Alignment.CENTER),
        new Alignment("Right", css.alignRight(), Paragraph.Alignment.RIGHT))) {
      final ToolbarToggleButton b = group.addToggleButton();
      new ToolbarButtonViewBuilder()
          .setText(alignment.description)
          .setIcon(alignment.iconCss)
          .applyTo(b, createParagraphApplicationController(b, alignment.style));
    }
  }

  private void createBoldButton(final ToolbarView toolbar) {
    final ToolbarToggleButton b = toolbar.addToggleButton();
    new ToolbarButtonViewBuilder()
        .setIcon(css.bold())
        .applyTo(b, createTextSelectionController(b, "fontWeight", "bold"));
  }

  private void createClearFormattingButton(final ToolbarView toolbar) {
    new ToolbarButtonViewBuilder()
        .setIcon(css.clearFormatting())
        .applyTo(toolbar.addClickButton(), new ToolbarClickButton.Listener() {
          @Override public void onClicked() {
            EditorAnnotationUtil.clearAnnotationsOverSelection(editor, asArray(
                StyleAnnotationHandler.key("backgroundColor"),
                StyleAnnotationHandler.key("color"),
                StyleAnnotationHandler.key("fontFamily"),
                StyleAnnotationHandler.key("fontSize"),
                StyleAnnotationHandler.key("fontStyle"),
                StyleAnnotationHandler.key("fontWeight"),
                StyleAnnotationHandler.key("textDecoration")
                // NOTE: add more as required.
            ));
            createClearHeadingsListener().onClicked();
          }
        });
  }

  private ToolbarClickButton.Listener createClearHeadingsListener() {
    return new ParagraphTraversalController(editor, new ContentElement.Action() {
        @Override public void execute(final ContentElement e) {
          e.getMutableDoc().setElementAttribute(e, Paragraph.SUBTYPE_ATTR, null);
        }
      });
  }

  private void createFontFamilyButton(final ToolbarView toolbar) {
    final SubmenuToolbarView submenu = toolbar.addSubmenu();
    new ToolbarButtonViewBuilder()
        .setIcon(css.fontFamily())
        .applyTo(submenu, null);
    submenu.setShowDropdownArrow(false); // Icon already has dropdown arrow.
    createFontFamilyGroup(submenu.addGroup(), new FontFamily("Default", null));
    createFontFamilyGroup(submenu.addGroup(),
        new FontFamily("Sans Serif", "sans-serif"),
        new FontFamily("Serif", "serif"),
        new FontFamily("Wide", "arial black,sans-serif"),
        new FontFamily("Narrow", "arial narrow,sans-serif"),
        new FontFamily("Fixed Width", "monospace"));
    createFontFamilyGroup(submenu.addGroup(),
        new FontFamily("Arial", "arial,helvetica,sans-serif"),
        new FontFamily("Comic Sans MS", "comic sans ms,sans-serif"),
        new FontFamily("Courier New", "courier new,monospace"),
        new FontFamily("Garamond", "garamond,serif"),
        new FontFamily("Georgia", "georgia,serif"),
        new FontFamily("Tahoma", "tahoma,sans-serif"),
        new FontFamily("Times New Roman", "times new roman,serif"),
        new FontFamily("Trebuchet MS", "trebuchet ms,sans-serif"),
        new FontFamily("Verdana", "verdana,sans-serif"));
  }

  private Element createFontFamilyElement(final FontFamily family) {
    final Element e = Document.get().createSpanElement();
    e.getStyle().setProperty("fontFamily", family.style);
    e.setInnerText(family.description);
    return e;
  }

  private void createFontFamilyGroup(final ToolbarView toolbar, final FontFamily... families) {
    for (final FontFamily family : families) {
      final ToolbarToggleButton b = toolbar.addToggleButton();
      b.setVisualElement(createFontFamilyElement(family));
      b.setListener(createTextSelectionController(b, "fontFamily", family.style));
    }
  }

  private void createFontSizeButton(final ToolbarView toolbar) {
    final SubmenuToolbarView submenu = toolbar.addSubmenu();
    new ToolbarButtonViewBuilder()
        .setIcon(css.fontSize())
        .applyTo(submenu, null);
    submenu.setShowDropdownArrow(false); // Icon already has dropdown arrow.
    // TODO(kalman): default text size option.
    final ToolbarView group = submenu.addGroup();
    for (final int size : asArray(8, 9, 10, 11, 12, 14, 16, 18, 21, 24, 28, 32, 36, 42, 48, 56, 64, 72)) {
      final ToolbarToggleButton b = group.addToggleButton();
      final double baseSize = 12.0;
      b.setVisualElement(createFontSizeElement(baseSize, size));
      b.setListener(createTextSelectionController(b, "fontSize", (size / baseSize) + "em"));
    }
  }

  private Element createFontSizeElement(final double baseSize, final double size) {
    final Element e = Document.get().createSpanElement();
    e.getStyle().setFontSize(size / baseSize, Unit.EM);
    e.setInnerText(((int) size) + "");
    return e;
  }

  private void createHeadingButton(final ToolbarView toolbar) {
    final SubmenuToolbarView submenu = toolbar.addSubmenu();
    new ToolbarButtonViewBuilder()
        .setIcon(css.heading())
        .applyTo(submenu, null);
    submenu.setShowDropdownArrow(false); // Icon already has dropdown arrow.
    final ToolbarClickButton defaultButton = submenu.addClickButton();
    new ToolbarButtonViewBuilder()
        .setText("Default")
        .applyTo(defaultButton, createClearHeadingsListener());
    final ToolbarView group = submenu.addGroup();
    for (final int level : asArray(1, 2, 3, 4)) {
      final ToolbarToggleButton b = group.addToggleButton();
      b.setVisualElement(createHeadingElement(level));
      b.setListener(createParagraphApplicationController(b, Paragraph.regularStyle("h" + level)));
    }
  }

  private Element createHeadingElement(final int level) {
    final Element e = Document.get().createElement("h" + level);
    e.getStyle().setMarginTop(2, Unit.PX);
    e.getStyle().setMarginBottom(2, Unit.PX);
    e.setInnerText("Heading " + level);
    return e;
  }

  private void createIndentButton(final ToolbarView toolbar) {
    final ToolbarClickButton b = toolbar.addClickButton();
    new ToolbarButtonViewBuilder()
        .setIcon(css.indent())
        .applyTo(b, new ParagraphTraversalController(editor, Paragraph.INDENTER));
  }

  private void createInsertAttachmentButton(final ToolbarView toolbar, final ParticipantId user) {
    // Find the current wave id.
    final String encodedToken = History.getToken();
    WaveRef waveRef = null;
    if (encodedToken != null && !encodedToken.isEmpty()) {
      waveRef = HistorySupport.waveRefFromHistoryToken(encodedToken);
    }
    // Kune workaround while we do a HistorySupport patch
    if (waveRef == null) {
      return;
    }
    Preconditions.checkState(waveRef != null);
    final String waveRefToken = URL.encode(GwtWaverefEncoder.encodeToUriQueryString(waveRef));

    new ToolbarButtonViewBuilder().setIcon(css.insertAttachment()).setTooltip("Insert attachment")
        .applyTo(toolbar.addClickButton(), new ToolbarClickButton.Listener() {
          @Override
          public void onClicked() {
            int tmpCursor = -1;
            final FocusedRange focusedRange = editor.getSelectionHelper().getSelectionRange();
            if (focusedRange != null) {
              tmpCursor = focusedRange.getFocus();
            }
            final int cursorLoc = tmpCursor;
            final AttachmentPopupView attachmentView = new AttachmentPopupWidget();
            attachmentView.init(new Listener() {

              @Override
              public void onDone(final String encodedWaveRef, final String attachmentId, final String fullFileName) {
                // Insert a file name linking to the attachment URL.
                final int lastSlashPos = fullFileName.lastIndexOf("/");
                final int lastBackSlashPos = fullFileName.lastIndexOf("\\");
                String fileName = fullFileName;
                if (lastSlashPos != -1) {
                  fileName = fullFileName.substring(lastSlashPos + 1, fullFileName.length());
                } else if (lastBackSlashPos != -1) {
                  fileName = fullFileName.substring(lastBackSlashPos + 1, fullFileName.length());
                }
                final XmlStringBuilder xml = XmlStringBuilder.createFromXmlString(fileName);
                int to = -1;
                final int docSize = editor.getDocument().size();
                if (cursorLoc != -1) {
                  // Insert the attachment at the cursor location.
                  final CMutableDocument doc = editor.getDocument();
                  final Point<ContentNode> point = doc.locate(cursorLoc);
                  doc.insertXml(point, xml);
                } else {
                  LineContainers.appendLine(editor.getDocument(), xml);
                }
                // Calculate the link length for the attachment.
                to = cursorLoc + editor.getDocument().size() - docSize;
                final String linkValue =
                    GWT.getHostPageBaseURL() + "attachment/" + attachmentId + "?fileName="
                        + fileName + "&waveRef=" + encodedWaveRef;
                EditorAnnotationUtil.setAnnotationOverRange(editor.getDocument(),
                    editor.getCaretAnnotations(), Link.KEY, linkValue, cursorLoc, to);
                // Store the attachment information as annotations to allow
                // robots detect and process them.
                EditorAnnotationUtil.setAnnotationOverRange(editor.getDocument(),
                    editor.getCaretAnnotations(), "attachment/id", attachmentId, cursorLoc, to);
                EditorAnnotationUtil.setAnnotationOverRange(editor.getDocument(),
                    editor.getCaretAnnotations(), "attachment/fileName", fileName, cursorLoc, to);
              }

              @Override
              public void onHide() {
              }

              @Override
              public void onShow() {
              }
            });

            attachmentView.setAttachmentId(attachmentIdGenerator.newAttachmentId());
            attachmentView.setWaveRef(waveRefToken);
            attachmentView.show();
          }
        });
}

  private void createInsertGadgetButton(final ToolbarView toolbar, final ParticipantId user) {
    new ToolbarButtonViewBuilder()
        .setIcon(css.insertGadget())
        .applyTo(toolbar.addClickButton(), new ToolbarClickButton.Listener() {
          @Override public void onClicked() {
            final GadgetSelectorWidget selector = new GadgetSelectorWidget();
            selector.addFeaturedOptions();
            final UniversalPopup popup = selector.showInPopup();
            selector.setListener(new GadgetSelectorWidget.Listener() {
              @Override public void onSelect(final String url) {
                insertGadget(url);
                popup.hide();
              }
            });
          }
        });
  }

  private void createInsertLinkButton(final ToolbarView toolbar) {
    // TODO (Yuri Z.) use createTextSelectionController when the full
    // link doodad is incorporated
    new ToolbarButtonViewBuilder()
        .setIcon(css.insertLink())
        .applyTo(toolbar.addClickButton(), new ToolbarClickButton.Listener() {
              @Override  public void onClicked() {
                final FocusedRange range = editor.getSelectionHelper().getSelectionRange();
                if (range == null || range.isCollapsed()) {
                  Window.alert("Select some text to create a link.");
                  return;
                }
                final String rawLinkValue =
                    Window.prompt("Enter link: URL or Wave ID.", WaveRefConstants.WAVE_URI_PREFIX);
                // user hit "ESC" or "cancel"
                if (rawLinkValue == null) {
                  return;
                }
                try {
                  final String linkAnnotationValue = Link.normalizeLink(rawLinkValue);
                  EditorAnnotationUtil.setAnnotationOverSelection(editor, Link.KEY,
                      linkAnnotationValue);
                } catch (final InvalidLinkException e) {
                  Window.alert(e.getLocalizedMessage());
                }
              }
            });
  }

  private void createItalicButton(final ToolbarView toolbar) {
    final ToolbarToggleButton b = toolbar.addToggleButton();
    new ToolbarButtonViewBuilder()
        .setIcon(css.italic())
        .applyTo(b, createTextSelectionController(b, "fontStyle", "italic"));
  }

  private void createOrderedListButton(final ToolbarView toolbar) {
    final ToolbarToggleButton b = toolbar.addToggleButton();
    new ToolbarButtonViewBuilder()
        .setIcon(css.orderedlist())
        .applyTo(b, createParagraphApplicationController(
            b, Paragraph.listStyle(Paragraph.LIST_STYLE_DECIMAL)));
  }

  private void createOutdentButton(final ToolbarView toolbar) {
    final ToolbarClickButton b = toolbar.addClickButton();
    new ToolbarButtonViewBuilder()
        .setIcon(css.outdent())
        .applyTo(b, new ParagraphTraversalController(editor, Paragraph.OUTDENTER));
  }

  private ToolbarToggleButton.Listener createParagraphApplicationController(final ToolbarToggleButton b,
      final LineStyle style) {
    return updater.add(new ParagraphApplicationController(b, editor, style));
  }

  private void createRemoveLinkButton(final ToolbarView toolbar) {
    new ToolbarButtonViewBuilder()
        .setIcon(css.removeLink())
        .applyTo(toolbar.addClickButton(), new ToolbarClickButton.Listener() {
          @Override public void onClicked() {
            if (editor.getSelectionHelper().getSelectionRange() != null) {
              EditorAnnotationUtil.clearAnnotationsOverSelection(editor, Link.LINK_KEYS);
            }
          }
        });
  }

  private void createStrikethroughButton(final ToolbarView toolbar) {
    final ToolbarToggleButton b = toolbar.addToggleButton();
    new ToolbarButtonViewBuilder()
        .setIcon(css.strikethrough())
        .applyTo(b, createTextSelectionController(b, "textDecoration", "line-through"));
  }

  private void createSubscriptButton(final ToolbarView toolbar) {
    final ToolbarToggleButton b = toolbar.addToggleButton();
    new ToolbarButtonViewBuilder()
        .setIcon(css.subscript())
        .applyTo(b, createTextSelectionController(b, "verticalAlign", "sub"));
  }

  private void createSuperscriptButton(final ToolbarView toolbar) {
    final ToolbarToggleButton b = toolbar.addToggleButton();
    new ToolbarButtonViewBuilder()
        .setIcon(css.superscript())
        .applyTo(b, createTextSelectionController(b, "verticalAlign", "super"));
  }

  private ToolbarToggleButton.Listener createTextSelectionController(final ToolbarToggleButton b,
      final String styleName, final String value) {
    return updater.add(new TextSelectionController(b, editor,
        StyleAnnotationHandler.key(styleName), value));
  }

  private void createUnderlineButton(final ToolbarView toolbar) {
    final ToolbarToggleButton b = toolbar.addToggleButton();
    new ToolbarButtonViewBuilder()
        .setIcon(css.underline())
        .applyTo(b, createTextSelectionController(b, "textDecoration", "underline"));
  }

  private void createUnorderedListButton(final ToolbarView toolbar) {
    final ToolbarToggleButton b = toolbar.addToggleButton();
    new ToolbarButtonViewBuilder()
        .setIcon(css.unorderedlist())
        .applyTo(b, createParagraphApplicationController(b, Paragraph.listStyle(null)));
  }

  /**
   * Stops listening to editor changes.
   *
   * @throws IllegalStateException if this toolbar is not currently enabled
   * @throws IllegalArgumentException if this toolbar is currently enabled for a
   *         different editor
   */
  public void disable(final Editor editor) {
    this.editor.checkEditor(editor);
    // The above won't throw if we're not currently enabled, but it makes sure
    // 'editor' is the same as the current editor, if any. So if 'editor' is
    // null, it means we aren't enabled (the wrapped editor is null too).
    Preconditions.checkState(editor != null);
    editor.removeUpdateListener(updater);
    this.editor.switchEditor(null);
  }

  /**
   * Starts listening to editor changes.
   *
   * @throws IllegalStateException if this toolbar is already enabled
   * @throws IllegalArgumentException if the editor is <code>null</code>
   */
  public void enable(final Editor editor) {
    this.editor.checkEditor(null);
    Preconditions.checkArgument(editor != null);
    this.editor.switchEditor(editor);
    editor.addUpdateListener(updater);
    updater.updateButtonStates();
  }

  /**
   * @return the {@link ToplevelToolbarWidget} backing this toolbar.
   */
  public ToplevelToolbarWidget getWidget() {
    return toolbarUi;
  }

  /** Constructs the initial set of actions in the toolbar. */
  public void init() {
    ToolbarView group = toolbarUi.addGroup();
    createBoldButton(group);
    createItalicButton(group);
    createUnderlineButton(group);
    createStrikethroughButton(group);

    group = toolbarUi.addGroup();
    createSuperscriptButton(group);
    createSubscriptButton(group);

    group = toolbarUi.addGroup();
    createFontSizeButton(group);
    createFontFamilyButton(group);
    createHeadingButton(group);

    group = toolbarUi.addGroup();
    createIndentButton(group);
    createOutdentButton(group);

    group = toolbarUi.addGroup();
    createUnorderedListButton(group);
    createOrderedListButton(group);

    group = toolbarUi.addGroup();
    createAlignButtons(group);
    createClearFormattingButton(group);

    group = toolbarUi.addGroup();
    createInsertLinkButton(group);
    createRemoveLinkButton(group);

    group = toolbarUi.addGroup();
    createInsertGadgetButton(group, user);

    group = toolbarUi.addGroup();
    createInsertAttachmentButton(group, user);
  }

  private void insertGadget(final String url) {
    int from = -1;
    final FocusedRange focusedRange = editor.getSelectionHelper().getSelectionRange();
    if (focusedRange != null) {
      from = focusedRange.getFocus();
    }
    if (url != null && !url.isEmpty()) {
      final XmlStringBuilder xml = GadgetXmlUtil.constructXml(url, "", user.getAddress());
      final CMutableDocument document = editor.getDocument();
      if (document == null) {
        return;
      }
      if (from != -1) {
        final Point<ContentNode> point = document.locate(from);
        document.insertXml(point, xml);
      } else {
        LineContainers.appendLine(document, xml);
      }
    }
  }
}
