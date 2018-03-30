#!/bin/sh

#git checkout master
git pull origin master
git add .
git commit -am "update"
git push
echo Press Enter...
