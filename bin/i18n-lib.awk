#!/usr/bin/gawk
function sql(operation, noact) {
    cmd = connect "\""operation"\""
    if (noact > 0) 
	print cmd
    else {
	cmd |& getline res
	close(cmd)
    }
    return res    
}

function getLangCode(lang) {
    select = "SELECT id FROM globalize_languages g WHERE code='"lang"'"
    return sql(select)
}


function getKeyInLang(somekey, somelang) {
    count = sql("SELECT count(*) FROM globalize_translations g WHERE gtype='"somekey"' AND language_id='"somelang"'")    
    if (count > 0) 
    return sql("SELECT id FROM globalize_translations g WHERE gtype='"somekey"' AND language_id='"somelang"'")
    else
	return 0
}

function insertNewItem(text, gtype, lang, parent) {
    insert = "INSERT INTO globalize_translations "			\
	"(id, facet, item_id, noteForTranslators, pluralization_index, table_name, " \
	"text, tr_key, gtype, language_id, parent_id) "			\
	"VALUES (NULL,'',NULL,'',1,'','"text"','', '"gtype"', "lang", "parent")"
    sql(insert);
}

function updateParentId(id) {
    update = "UPDATE globalize_translations SET parent_id="id" WHERE id="id
    sql(update);
}

BEGIN {
    connect = "mysql -B -p"passwd" -u"username" "db" --skip-column-names -e "
    english=getLangCode("en")
    # 1819: English 
    # print english
}

END {
}