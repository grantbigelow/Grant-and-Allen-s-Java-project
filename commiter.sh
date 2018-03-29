#!/bin/sh

#git checkout master
git pull origin master
git add .
git commit -am "website update"
git push
echo Press Enter...
