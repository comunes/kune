#!/usr/bin/gawk
# This receibe something like
# lang|file-used-in-gtype|trkey|text
function redirect(translation, file) {
    if (noact > 0)
	printf translation
    else 
	printf translation > file
}

function addApacheCopyright(file) {
    if (verbose > 0)
	printf "Adding Apache license"
    printf "#\n" > file
    printf "#\n" > file
    printf "# Licensed to the Apache Software Foundation (ASF) under one\n" > file
    printf "# or more contributor license agreements.  See the NOTICE file\n" > file
    printf "# distributed with this work for additional information\n" > file
    printf "# regarding copyright ownership.  The ASF licenses this file\n" > file
    printf "# to you under the Apache License, Version 2.0 (the\n" > file
    printf "# \"License\"); you may not use this file except in compliance\n" > file
    printf "# with the License.  You may obtain a copy of the License at\n" > file
    printf "#\n" > file
    printf "#   http://www.apache.org/licenses/LICENSE-2.0\n" > file
    printf "#\n" > file
    printf "# Unless required by applicable law or agreed to in writing,\n" > file
    printf "# software distributed under the License is distributed on an\n" > file
    printf "# \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY\n" > file
    printf "# KIND, either express or implied.  See the License for the\n" > file
    printf "# specific language governing permissions and limitations\n" > file
    printf "# under the License.\n" > file
    printf "#\n" > file
    printf "#\n" > file
    printf "\n" > file
}

BEGIN {
}
{
    split($4,gtype,"ł")
    langCode = getLangCode($5)
    propertiesFile = dest""gtype[2]"_"getLangCode($5)".properties"
    if (gtypeprefix == "wave" && !(propertiesFile in processed)) {
	addApacheCopyright(propertiesFile);	
    }
    processed[propertiesFile] = "yes";
    previousFile = propertiesFile;
    if (verbose > 0)
	printf "Processing " propertiesFile
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

