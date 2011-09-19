#!/bin/bash

# This script should generate from Kune strings db translations to GWT I18n .java resources

usage() {
    echo "Use: $0 -l langcode"
    echo "$0 -l en"
}

while getopts “hl:” OPTION
do
    case $OPTION in
	h)
            usage
            exit 1
            ;;
	l)
	    L=$OPTARG
	    ;;
	?)
            usage
            exit
            ;;
    esac
done	

if [[ -z $L ]]
then
    usage
    exit 1
fi



