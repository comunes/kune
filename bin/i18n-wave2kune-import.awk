#!/usr/bin/gawk
BEGIN {
}
{
    gtype = $2"ł"$3
    if (gtypeprefix)
	gtype = gtypeprefix"ł"gtype

    text = $3
    if ($1 == "en") {
	currentLang = english
    } else {
	currentLang = getLangCode($1)
    }
    result = getKeyInLang(gtype, currentLang)
    if (result > 0) {
	print "'"text "' already in db for lang '" $1 "'"
    } else {
	# print "Dont exists, so insert"
	if (currentLang == english) {
	    insertNewItem($3, gtype, currentLang, NULL)
	    print "Inserting '" text "' in lang '" $1 "'"
	    # FIXME, parent id = self
	    id = getKeyInLang(gtype, currentLang)
	    updateParentId(id);

	} else {
	    parent = getKeyInLang(gtype, english)
	    # find English parent
	    if (parent > 0) {
		# parent found, insert with reference
	    } else {
		# parent dont exit, ignore by now
	    }
	}
    }
}
END {
}

