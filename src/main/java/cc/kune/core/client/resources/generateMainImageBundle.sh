#
# Generates methods AbstractImagePrototype with the content of the directory:
#
#       @Resource("image")
#	AbstractImagePrototype arrowDownWhite();
#

cat << EOF

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

/**
 *
 * http://code.google.com/p/google-web-toolkit/wiki/ImageBundleDesign
 *
 */
public interface XXXResources extends ClientBundle {
EOF
for i in `ls -1 *png *gif 2> /dev/null`
do
   echo -e "\n@Source(\"$i\")\nImageResource" \
  `echo ·$i |cut -d '.' -f 1 | sed 's/-/ /g' | sed 's/_/ /g' | perl -ne '@a=split;foreach (@a) {push @b,ucfirst};print "@b\n"' | sed 's/ //g' | sed 's/·//g'`"();"
done
echo "}"
