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

function getLangId(lang) {
    select = "SELECT id FROM globalize_languages g WHERE code='"lang"'"
    return sql(select)
}

function getLangCode(langId) {
    select = "SELECT code FROM globalize_languages g WHERE id="langId
    return sql(select)
}


function getKeyInLang(somekey, somelang) {
    count = sql("SELECT count(*) FROM globalize_translations g WHERE gtype='"somekey"' AND language_id='"somelang"'")    
    if (count > 0) 
	return sql("SELECT id FROM globalize_translations g WHERE gtype='"somekey"' AND language_id='"somelang"'")
    else
	return 0
}

function logInsert(text, langCode) {
    print "Inserting '" text "' in lang '" langCode "'"
}

function escape(text) {
    if (text != "NULL") {
	# Escape quotes etc (from kune TextUtils.java)
	# This replacement sort is important
	gsub("\\&", "\\&amp;", text)
	gsub("\"", "\\&quot;", text)
	gsub("—", "\\&#8212;", text)
	gsub("'", "\\'", text)
	gsub("<", "\\&lt;", text)
	gsub(">", "\\&gt;", text);
    }
    return text
}

function quote(text) {
    # Quote if not null
    if (text != "NULL") {
	return "'"text"'"
    }
    return text
}

function insertNewItem(trkey, text, gtype, lang, parent, keyValue, langCode) {
    trkey = escape(trkey)
    text = escape(text)    

    trkey = quote(trkey)
    text = quote(text)    

    insert = "INSERT INTO globalize_translations "			\
	"(id, facet, item_id, noteForTranslators, pluralization_index, table_name, " \
	"text, tr_key, gtype, language_id, parent_id) "			\
	"VALUES (NULL,'',NULL,'',1,'',"text","trkey", '"gtype"', "lang", "parent")"
    sql(insert);
    logInsert(keyValue, langCode)
}

function updateParentId(id) {
    update = "UPDATE globalize_translations SET parent_id="id" WHERE id="id
    sql(update);
}

BEGIN {
    connect = "mysql -B -p"passwd" -u"username" "db" --skip-column-names -e "
    english=getLangId("en")
    # 1819: English 
    # print english
}

END {
}