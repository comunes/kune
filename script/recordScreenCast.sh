#!/bin/bash
WIDTH=806
HEIGHT=707
# TODO: calculate better this to allow other windows sizes
RWIDTH=802
RHEIGHT=649
#SEARCH=Eurosur
#SEARCH=Jane
SEARCH=Firefox
xdotool search --sync --onlyvisible "$SEARCH" windowactivate
xdotool key --clearmodifiers alt+F5
xdotool search --sync --onlyvisible "$SEARCH" windowsize %@ $WIDTH $HEIGHT 
xdotool search --onlyvisible --name "$SEARCH" windowmove %@ 0 0
if [[ $? -eq 0 ]]
then
  recordmydesktop -x 0 -y 83 --width $RWIDTH --height $RHEIGHT --fps 25 --no-sound -o kune-screencast.ogv
fi

#WID=`xdotool search --onlyvisible --name "$1" | head -1`
#xdotool search --onlyvisible --name "Firefox" windowmove %@ 0 0
#recordmydesktop --windowid $WID --fps 25 --no-sound -o foo.ogv
#xdotool search --onlyvisible "gtk-recordMyDesktop" windowmove %@ 1000 1000
#WID=`xdotool search --class "$1" | head -1`
#WID=`xdotool search --onlyvisible --maxdepth 2 --class "$1 "`
#xdotool windowmove $WID 0 0           
#xdotool windowsize $WID 806 707
