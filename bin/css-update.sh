#!/bin/bash
APPPUB=src/main/java/org/ourproject/kune/app/public
#WEBAPP=src/main/webapp/gwt/ws

## > $PWD/bin/css-compact-and-tidy.sh

#rsync -aC $APPPUB/ws.html $WEBAPP/ws.html
#rsync -aC $APPPUB/js/ $WEBAPP/js
#rsync -aC $APPPUB/css/ $WEBAPP/css
#rsync -aC $APPPUB/images/ $WEBAPP/images

. bin/kune-init-functions

for i in ws wse 
do
  cp src/main/java/cc/kune/chat/public/kune-chat.css target/kune-$KUNE_VERSION/$i/
  cp src/main/java/cc/kune/chat/public/kune-hablar.css target/kune-$KUNE_VERSION/$i/
  cp src/main/java/cc/kune/common/public/kune-message.css target/kune-$KUNE_VERSION/$i/
  cp src/main/java/cc/kune/common/public/kune-common.css target/kune-$KUNE_VERSION/$i/
  cp src/main/java/cc/kune/common/public/kune-custom-common.css target/kune-$KUNE_VERSION/$i/
  cp src/main/java/cc/kune/core/public/ws.css target/kune-$KUNE_VERSION/$i/
  cp src/main/java/cc/kune/core/public/ws-rtl.css target/kune-$KUNE_VERSION/$i/
  cp src/main/java/cc/kune/gxtbinds/public/gxt-custom/css/gxt-op-common.css target/kune-$KUNE_VERSION/$i/gxt-custom/css/gxt-op-common.css
  #cp src/main/java/cc/kune/core/public/ws.html target/kune-$KUNE_VERSION/
  #cp src/main/webapp/templates/basic/basic.css  target/kune-$KUNE_VERSION/templates/basic/basic.css 
  #cp src/main/webapp/templates/basic/docs.liquid.html  target/kune-$KUNE_VERSION/templates/basic/
done

