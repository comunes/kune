#.H1  A MySql Client 
#.H2 Download
#.P 
#Download from
#.URL http://lawker.googlecode.com/svn/fridge/lib/awk/mysql.awk LAWKER.
#.H2 Code
#.H3 Set Up
#.PRE
BEGIN {
    if (!mysql["path"]) {
        mysql["path"] = "/usr/bin/mysql"
    }
    if (mysql["user"]) mysql["user"] = "-u" mysql["user"]
    if (mysql["pass"]) mysql["pass"] = "-p" mysql["pass"]

    if (!mysql["tempfile_command"]) {
        mysql["tempfile_command"] = "mktemp /tmp/__mysql.awk.XXXXXX"
    }
    mysql["resource_id"] = 1
    __mysql_dequote["r"]  = "\r"
    __mysql_dequote["n"]  = "\n"
    __mysql_dequote["t"]  = "\t"
    __mysql_dequote["\\"] = "\\"
}
#./PRE
#.H3 Main Functions
#.PRE
function mysql_db (db)      { mysql["database"] = db    }
function mysql_path (path)  { mysql["path"]     = path  }

function mysql_tempfile_command (command) {
    mysql["tempfile_command"] = command
}
function mysql_login (username, password, host, args) {
    mysql["user"] = "-u" username
    mysql["pass"] = "-p" password
        if (host) mysql["host"] = "-h" host
        if (args) mysql["args"] = args
}
function mysql_query (query    ,input,key,i,call,resource) {
    resource = mysql["resource_id"]++
    mysql["tempfile_command"] | getline mysql[resource]
    close(mysql["tempfile_command"])
    call = sprintf("%s %s %s %s %s %s > %s",
            mysql["path"], mysql["user"], mysql["pass"], mysql["host"],
                        mysql["args"], mysql["database"],
            mysql[resource])
    print query | call
    close(call)
    if (getline input < mysql[resource]) {
        for (i = split(input, key, "\t"); i > 0; i--)
            mysql[resource, i] = key[i]
    }
    return resource
}
function mysql_fetch_assoc (resource,row  ,input,i,fields) {
    fields = 0
    if (getline input < mysql[resource]) {
        fields = mysql_split(row, input)
        for (i = 1; i <= fields; i++)
            row[mysql[resource, i]] = row[i]
    }
    return fields
}
function mysql_split (row, input,   r,i) {
     r = split(input, row, "\t")
     for (i = 0; i <= r; i++) {
         row[i] = mysql_dequote(row[i])
     }
     return r
}
function mysql_fetch_row (resource,row  ,input,r,i) {
    if (getline input < mysql[resource]) {
        return mysql_split(row, input)
    }
    return 0
}
function mysql_index (resource, id) {
    return mysql[resource, id]
}
function mysql_finish (resource, i) {
    close(mysql[resource])
    system(sprintf("rm %s", mysql[resource]))
    delete mysql[resource]
    i = 1
    while (mysql[resource,i])
        delete mysql[resource, i++]
}
function mysql_cleanup (  i) {
    for (i = 1; i < mysql["resource_id"]; i++)
        if (mysql[i]) {
            close(mysql[i])
            system(sprintf("rm %s", mysql[i]))
            delete mysql[resource]
            i = 1
            while (mysql[resource,i])
                delete mysql[resource, i++]
        }
}
#./PRE
#.H3 Support Utils
#.P
# Scan a string for mysql escaped tokens and replace them with the appropriate
# character.  This is a fairly slow operation for large strings but it's
# necessary.
#.PRE
function mysql_dequote (string, result,i,l,c) {
    result = ""
    l = length(string)
    for (i = 1; i <= l; i++) {
        c = substr(string, i, 1)
        if (c == "\\") {
            # This simply shouldn't happen...
            ## if ((i + 1) == l) continue;
            c = substr(string, ++i, 1)
            result = result __mysql_dequote[c]
        }
        else {
            result = result c
        }
    }
    return result
}
function mysql_quote (string,   result) {
    gsub(/\\/, "\\\\", string)
    gsub(/'/, "\\'", string)
    return "'" string "'"
}
#./PRE
#.H2 Copyright
#.P
#"THE BEER-WARE LICENSE" (Revision 43) borrowed from FreeBSD's jail.c:
#<tag@cpan.org> wrote this file.  As long as you retain this notice you
#can do whatever you want with this stuff. If we meet some day, and you think
# this stuff is worth it, you can buy me a beer in return.   
#.H2 Author
#.P Scott S. McCoy

