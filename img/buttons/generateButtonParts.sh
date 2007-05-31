#!/bin/bash
BASEIMAGE=buttons-base.png
OUTPUTDIR=.

# Help buttons
convert $BASEIMAGE -crop 20x20+0+74 $OUTPUTDIR/button-help-light.png
convert $BASEIMAGE -crop 20x20+30+74 $OUTPUTDIR/button-help-blue.png
convert $BASEIMAGE -crop 20x20+60+74 $OUTPUTDIR/button-help-dark.png

# Rest of buttons
X=0
Y=0
for a in light blue dark xlight
do
    for j in 20 17 15
    do
	for i in l c r
	do
	    if [[ $i == l ]]
	    then
	# right corner
		convert $BASEIMAGE -crop 4x${j}+${X}+${Y} $OUTPUTDIR/button$j$i$a.png
		X=$(($X+4))
	    elif [[ $i == r ]] 
	    then
	# right corner
		convert $BASEIMAGE -crop 4x${j}+${X}+${Y} $OUTPUTDIR/button$j$i$a.png
		X=$(($X-56))
	    else
	# center
		convert $BASEIMAGE -crop 1x${j}+${X}+${Y} $OUTPUTDIR/button$j$i$a.png
		X=$(($X+52))
	    fi
	done	
	Y=$(($Y+24))
    done
    X=$(($X+60+10))
    Y=$(($Y-72))
done
    
    
