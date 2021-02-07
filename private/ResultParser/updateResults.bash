#!/bin/bash
#grab files from server
echo Grab files from server
mkdir rawData
mkdir processedData
rsync -av rlai.cs.ualberta.ca:/var/www/apps/rl-comp_results/event_\{6..11\}_* ./rawData/

echo Add gz extension to files
cd rawData
for i in event_*;do cp $i ../processedData/$i.gz;done;

echo Unzip files
cd ../processedData
gunzip -f *.gz
