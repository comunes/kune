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
public interface Images extends ImageBundle {

    public static class App {
        private static Images ourInstance = null;

        public static synchronized Images getInstance() {
            if (ourInstance == null) {
                ourInstance = (Images) GWT.create(Images.class);
            }
            return ourInstance;
        }
    }

EOF
for i in `ls -1 *png *gif 2> /dev/null`
do
   echo -e "\n@Resource(\"org/ourproject/kune/platf/public/images/$i\")\nAbstractImagePrototype" \
  `echo ·$i |cut -d '.' -f 1 | sed 's/-/ /g' | sed 's/_/ /g' | perl -ne '@a=split;foreach (@a) {push @b,ucfirst};print "@b\n"' | sed 's/ //g' | sed 's/·//g'`"();"
done
echo "}"
