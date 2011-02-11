#!/bin/bash
APPPUB=src/main/java/org/ourproject/kune/app/public
#WEBAPP=src/main/webapp/gwt/ws

## > $PWD/script/css-compact-and-tidy.sh

#rsync -aC $APPPUB/ws.html $WEBAPP/ws.html
#rsync -aC $APPPUB/js/ $WEBAPP/js
#rsync -aC $APPPUB/css/ $WEBAPP/css
#rsync -aC $APPPUB/images/ $WEBAPP/images

cp src/main/java/cc/kune/chat/public/kune-chat.css target/kune-0.0.7/ws/
cp src/main/java/cc/kune/chat/public/kune-hablar.css target/kune-0.0.7/ws/
cp src/main/java/cc/kune/msgs/public/kune-message.css target/kune-0.0.7/ws/
cp src/main/java/cc/kune/common/public/kune-common.css target/kune-0.0.7/ws/
cp src/main/java/cc/kune/core/public/ws.css target/kune-0.0.7/ws/
cp src/main/java/cc/kune/core/public/ws.html target/kune-0.0.7/ws/
cp src/main/webapp/templates/basic/basic.css  target/kune-0.0.7/templates/basic/basic.css 
cp src/main/webapp/templates/basic/docs.liquid.html  target/kune-0.0.7/templates/basic/
cp src/main/java/cc/kune/common/public/gxt-custom/css/gxt-op-common.css target/kune-0.0.7/ws/gxt-custom/css/gxt-op-common.css
