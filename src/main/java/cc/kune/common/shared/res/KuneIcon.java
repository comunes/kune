package cc.kune.common.shared.res;

/**
 * The Class KuneIcon is used with the kune SVG (see the layer names in
 * kune-artwork/iconic/iconsfonts.svg) to show kune icons from the kune icons
 * generated ttf.
 */
public class KuneIcon {

  /** The character. */
  private final Character character;

  /**
   * Instantiates a new kune icon.
   * 
   * @param character
   *          the character that maps the icon in the ttf
   */
  public KuneIcon(final Character character) {
    this.character = character;
  }

  /**
   * Gets the character.
   * 
   * @return the character
   */
  public Character getCharacter() {
    return character;
  };

}
