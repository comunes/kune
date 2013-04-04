#!/usr/bin/gawk
function sqlImpl(operation, noact) {
    cmd = connect "\""operation"\""
    if (noact > 0) 
	print cmd
    else {
	cmd |& getline res
	close(cmd)
    }
    return res    
}

function sql(operation) {
    return sqlImpl(operation, 0)
}

function getLangCode(lang) {
    select = "SELECT id FROM globalize_languages g WHERE code=\x27"lang"\x27"
    return sql(select)
}


function getKeyInLang(somekey, somelang) {
    select = "SELECT count(*) FROM globalize_translations g WHERE gtype=\x27"somekey"\x27 AND language_id=\x27"somelang"\x27"
    return sql(select)
}

function insertNewItem(text, gtype, lang, parent) {
    insert = "INSERT INTO globalize_translations "			\
	"(id, facet, item_id, noteForTranslators, pluralization_index, table_name, " \
	"text, tr_key, gtype, language_id, parent_id) "			\
	"VALUES (NULL,'',NULL,'',1,'','"text"','', '"gtype"', "lang", "parent")"
    sqlImpl(insert);
}

BEGIN {
    connect = "mysql -B -p"passwd" -u"username" "db" --skip-column-names -e "
    english=getLangCode("en")
    # 1819: English 
    # print english
}

END {
}