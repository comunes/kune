package org.ourproject.kune.platf.client.registry;

public class ContentCapabilitiesRegistry {

    private final CanBeHomepageRegistry canBeHomepage;
    private final ComentableRegistry comentable;
    private final DragableRegistry dragable;
    private final DropableRegistry dropable;
    private final EmailSubscribeAbleRegistry emailSubscribeAble;
    private final LicensableRegistry licensable;
    private final PublishModerableRegistry publishModerable;
    private final RateableRegistry rateable;
    private final TageableRegistry tageable;
    private final RenamableRegistry renamable;
    private final TranslatableRegistry translatable;
    private final VersionableRegistry versionable;
    private final XmppComentableRegistry xmppComentable;
    private final XmppNotifyCapableRegistry xmppNotifyCapable;

    public ContentCapabilitiesRegistry(final CanBeHomepageRegistry canBeHomepage, final ComentableRegistry comentable,
            final DragableRegistry dragable, final DropableRegistry dropable,
            final EmailSubscribeAbleRegistry emailSubscribeAble, final LicensableRegistry licensable,
            final PublishModerableRegistry publishModerable, final RateableRegistry rateable,
            final TageableRegistry tageable, final RenamableRegistry renamable,
            final TranslatableRegistry translatable, final VersionableRegistry versionable,
            final XmppComentableRegistry xmppComentable, final XmppNotifyCapableRegistry xmppNotifyCapable) {
        this.canBeHomepage = canBeHomepage;
        this.comentable = comentable;
        this.dragable = dragable;
        this.dropable = dropable;
        this.emailSubscribeAble = emailSubscribeAble;
        this.licensable = licensable;
        this.publishModerable = publishModerable;
        this.rateable = rateable;
        this.tageable = tageable;
        this.renamable = renamable;
        this.translatable = translatable;
        this.versionable = versionable;
        this.xmppComentable = xmppComentable;
        this.xmppNotifyCapable = xmppNotifyCapable;
    }

    public boolean canBeHomepage(String typeId) {
        return canBeHomepage.contains(typeId);
    }

    public CanBeHomepageRegistry getCanBeHomepage() {
        return canBeHomepage;
    }

    public ComentableRegistry getComentable() {
        return comentable;
    }

    public DragableRegistry getDragable() {
        return dragable;
    }

    public DropableRegistry getDropable() {
        return dropable;
    }

    public EmailSubscribeAbleRegistry getEmailSubscribeAble() {
        return emailSubscribeAble;
    }

    public LicensableRegistry getLicensable() {
        return licensable;
    }

    public PublishModerableRegistry getPublishModerable() {
        return publishModerable;
    }

    public RateableRegistry getRateable() {
        return rateable;
    }

    public RenamableRegistry getRenamableContent() {
        return renamable;
    }

    public TageableRegistry getTageable() {
        return tageable;
    }

    public TranslatableRegistry getTranslatable() {
        return translatable;
    }

    public VersionableRegistry getVersionable() {
        return versionable;
    }

    public XmppComentableRegistry getXmppComentable() {
        return xmppComentable;
    }

    public XmppNotifyCapableRegistry getXmppNotificyCapable() {
        return xmppNotifyCapable;
    }

    public boolean isComentable(String typeId) {
        return comentable.contains(typeId);
    }

    public boolean isDragable(String typeId) {
        return dragable.contains(typeId);
    }

    public boolean isDropable(String typeId) {
        return dropable.contains(typeId);
    }

    public boolean isEmailSubscribeAble(String typeId) {
        return emailSubscribeAble.contains(typeId);
    }

    public boolean isLicensable(String typeId) {
        return licensable.contains(typeId);
    }

    public boolean isPublishModerable(String typeId) {
        return publishModerable.contains(typeId);
    }

    public boolean isRateable(String typeId) {
        return rateable.contains(typeId);
    }

    public boolean isRenamable(String typeId) {
        return renamable.contains(typeId);
    }

    public boolean isTageable(String typeId) {
        return tageable.contains(typeId);
    }

    public boolean isTranslatable(String typeId) {
        return translatable.contains(typeId);
    }

    public boolean isVersionable(String typeId) {
        return versionable.contains(typeId);
    }

    public boolean isXmppComentable(String typeId) {
        return xmppComentable.contains(typeId);
    }

    public boolean isXmppNotifyCapable(String typeId) {
        return xmppNotifyCapable.contains(typeId);
    }

}
