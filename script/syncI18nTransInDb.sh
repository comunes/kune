#!/bin/bash
mysql -B --skip-column-names --password="db4kune" -u kune kune_dev -e \
"SELECT id, tr_key, language_id FROM globalize_translations" \
| sed 's/	/ł/g' \
| awk -F "ł" '
BEGIN {
  print "BEGIN;"
}
{
  trkey=$2
  id=$1
  lang=$3
  #gsub(/\(/, "\\(", trkey)
  #gsub(/\)/, "\\)", trkey)
  gsub(/\[/, "\\[", trkey)
  gsub(/\]/, "\\]", trkey)
  gsub(/&/, "\\\\&", trkey)
  gsub(/;/, "\\;", trkey)
  #print trkey
  #exit
  cmd="bin/findMsg.sh \""trkey"\""
  #print cmd
  #exit
  mat=match($2, "(.*NT weekday.*|.*NT month.*|.*NT abbreviated weekday.*|.*NT abbreviated month.*|.*default.*|.*green.*|.*purple.*|.*grey.*|.*red.*|.*sahara.*|.*blue.*|.*redblack.*|.*camp.*|.*pink.*|.*highcontrast.*|.*whiteblack.*|.*blackwhite.*|.*transparent.*)")
  if (mat == 0) {
    result = system(cmd)
    #print result
    if (result != "0") {     
      print "DELETE FROM globalize_translations WHERE tr_key=\""$2"\" AND language_id="lang";"
    }
  }
}
END {
  print "ROLLBACK;"
}'

# NOT USED NOW, BUT MAYBE FOR TESTS:

#| grep "accept this member" \

#|tail -200 | \

#grep globalize_translations src/main/resources/db/update-00* | grep INSERT \
#| sed "s/['\"],['\"]/SEPAR/g" \
#| sed "s/['\"],/SEPAR/g" \
#| sed "s/SEPAR/ł/g" \
#| awk -F "ł" '{print $4"ł"$6}' \
#| sed "s/^['\"]//g" | sed "s/['\"],/\|/g" | sed 's/);//g' \
#| sed "s/\([^\]\)'/\1\\\'/g" \

