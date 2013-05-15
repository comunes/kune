#!/usr/bin/gawk
# This receibe something like
# lang|file-used-in-gtype|trkey|text
function redirect(translation, file) {
    printf translation > file
}
BEGIN {
}
{
    split($4,gtype,"ł")
    langCode = getLangCode($5)
    propertiesFile = dest""gtype[2]"_"getLangCode($5)".properties"
    if (langCode == "en") {
	translation = gtype[3]" = "unescape($3)"\n"
	redirect(translation, propertiesFile)
    } else {
	if ($2 != "NULL") {
	    translation = gtype[3]" = "unescape($2)"\n"
	    redirect(translation, propertiesFile)
	}
    }    
}
END {
}

