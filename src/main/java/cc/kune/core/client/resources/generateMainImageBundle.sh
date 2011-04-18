#
# Generates methods AbstractImagePrototype with the content of the directory:
#
#       @Resource("image")
#	AbstractImagePrototype arrowDownWhite();
#
PARAM=$*
cat << EOF

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.resources.client.ImageResource;

/**
 *
 * http://code.google.com/p/google-web-toolkit/wiki/ImageBundleDesign
 *
 */
public interface XXXResources extends ClientBundle {
EOF
#for i in `ls -1 *png *gif 2> /dev/null`
for i in `ls -1 $PARAM 2> /dev/null`
do
   echo -e "\n@Source(\"$i\")\nImageResource" \
  `echo ·$i |cut -d '.' -f 1 | sed 's/-/ /g' | sed 's/_/ /g' | perl -ne '@a=split;foreach (@a) {push @b,ucfirst};print "@b\n"' | sed 's/ //g' | sed 's/·//g'`"();"
done
echo "}"
