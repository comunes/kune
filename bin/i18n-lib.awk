#!/usr/bin/gawk
function sql(operation) {
    cmd = connect "\""operation"\""
    cmd |& getline res
    close(cmd)
    # print cmd
    return res
}

function getLangCode(lang) {
    select = "SELECT id FROM globalize_languages g WHERE code=\x27"lang"\x27"
    # print select
    return sql(select)
}

function getKeyInLang(somekey, somelang) {
    select = "SELECT count(*) FROM globalize_translations g WHERE gtype=\x27"somekey"\x27 AND language_id=\x27"somelang"\x27"
    return sql(select)
}