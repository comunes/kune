#
# Generates methods AbstractImagePrototype with the content of the directory:
#
#	/**
#	 * @gwt.resource org/ourproject/kune/public/images/arrow-down-white.gif
#	 */
#	AbstractImagePrototype arrowDownWhite();
#

for i in `ls -1 *png *gif`
do
  echo -e "/**\n * @gwt.resource org/ourproject/kune/platf/public/images/$i\n */\nAbstractImagePrototype" \
  `echo ·$i |cut -d '.' -f 1 | sed 's/-/ /g' | sed 's/_/ /g' | perl -ne '@a=split;foreach (@a) {push @b,ucfirst};print "@b\n"' | sed 's/ //g' | sed 's/·//g'`"();\n"
done
