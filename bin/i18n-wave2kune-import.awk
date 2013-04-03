#!/usr/bin/gawk
BEGIN {
    connect = "mysql -B -p"passwd" -u"username" "db" --skip-column-names -e "
    english=getLangCode("en")
    # 1819: English 
    # print english
}

{
    key = $2"|"$3
    print "key: "$3
    if ($1 == "en") {
	currentLang = english;
    } else {
	currentLang = getLangCode($1)
    }
    result = getKeyInLang(key, currentLang)
    if (result > 0) {
	print "Already in db"
    } else {
	# print "Dont exists, so insert"
	if (currentLang == english) {
	    # just insert
	} else {
	    parent = getKeyInLang(key, english)
	    # find english parent
	    if (parent > 0) {
		# parent found, insert with reference
		# insert = connect "\"INSERT INTO globalize_translations VALUES (NULL,\x27\x27,NULL,1,\x27\x27,\x27"$3"\x27,\x27"$3"\x27,\x27"key"\x27,1819)\""
	    } else {
		# parent dont exit, ignore by now
	    }
	}
    }
}
END {
}

