#!/bin/bash
WIDTH=806
HEIGHT=707
# TODO: calculate better this to allow other windows sizes
RWIDTH=802
RHEIGHT=649
#SEARCH=Eurosur
#SEARCH=Jane

# Not needed, now firefox webdriver can be resized
#SEARCH=Firefox
#xdotool search --sync --onlyvisible "$SEARCH" windowactivate
#xdotool key --clearmodifiers alt+F5
#xdotool search --sync --onlyvisible "$SEARCH" windowsize %@ $WIDTH $HEIGHT 
#xdotool search --onlyvisible --name "$SEARCH" windowmove %@ 0 0

if [[ $? -eq 0 ]]
then
  recordmydesktop -x 0 -y 83 --width $RWIDTH --height $RHEIGHT --fps 25 --no-sound -o docs/screencasts/kune-screencast.ogv
fi
