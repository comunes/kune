#!/usr/bin/gawk
BEGIN {
}
{
    gtype = $2"ł"$3
    if (gtypeprefix)
	gtype = gtypeprefix"ł"gtype
    trkey = $3
    text = $4
    if ($1 == "en") {
	currentLang = english
    } else {
	currentLang = getLangCode($1)
    }
    result = getKeyInLang(gtype, currentLang)
    if (result > 0) {
	# print "'"trkey "' already in db for lang '" $1 "'"
    } else {
	# print "Dont exists, so insert"
	if (currentLang == english) {
	    insertNewItem(text, gtype, currentLang, "NULL")
	    print "Inserting '" trkey "' in lang '" $1 "'"
	    # FIXME, parent id = self
	    id = getKeyInLang(gtype, currentLang)
	    updateParentId(id);
	} else {
	    # Other langs different than English
	    parent = getKeyInLang(gtype, english)
	    # find English parent
	    if (parent > 0) {
		# parent found, insert with reference
		insertNewItem(text, gtype, currentLang, parent)
	    } else {
		print "'"trkey "' is not added for language English so no processing it for lang '" $1 "'"
	    }
	}
    }
}
END {
}

