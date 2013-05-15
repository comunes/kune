#!/bin/bash

HOST=kune@kune.beta.iepala.es

rsync --partial bin/convertI18nMsgToMethods* $HOST:/home/kune/kune-current/bin/
rsync --partial bin/i18n-* $HOST:/home/kune/kune-current/bin/
ssh $HOST "cd /home/kune/kune-current/; bin/i18n-all.ssh"
scp $HOST:/home/kune/kune-current/KuneCon* src/main/java/cc/kune/core/client/i18n/
