#/bin/sh

JF_URL=https://jfartifactory.resolve.local:8081
EXPORT_PATH=/root/artifactory-backups
DATE=`date '+%Y%m%d'`
echo ${DATE}

cd $EXPORT_PATH

find . -name '*.tar.gz' -mtime +0 -exec rm {} \;

curl -H "Authorization: Bearer eyJ2ZXIiOiIyIiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYiLCJraWQiOiJyRjRGYWtuVlFET28zRzJLUTAxOWdEMW5ySTlHZzJESGw0al9HYzBMeXVVIn0.eyJzdWIiOiJqZmFjQDAxaDI5eDZhcjkwNGhzMGY1a3Nta3kxYWh6XC91c2Vyc1wvYWRtaW4iLCJzY3AiOiJhcHBsaWVkLXBlcm1pc3Npb25zXC91c2VyIiwiYXVkIjoiKkAqIiwiaXNzIjoiamZmZUAwMWgyOXg2YXI5MDRoczBmNWtzbWt5MWFoeiIsImlhdCI6MTcwMzc1Njc2OCwianRpIjoiMTM4NWRjZWItYTBkYi00ZDFhLThmNjktODI2OTgxZGU5YzZlIn0.D6LtmbHkLYY--iSEpOkHaqdSNNEmzTh1NOs0idRIN9HOBhGby_CRdypGdJm1aAs6fTiITGB4wIJQA5qR1O6oPy4Ysd92MPob7_U5XzGJBbWOw36Ffxi3L_T0IhP8exUS1JF-AyZjWD_MUHgevp_kiqDTMGfI72D8QIm89fKe4Uf5C0QQ-jYNCgQ548XmwK8cqygAJdwkAtKqmUEfBaETOt8DmSBMllywpOnPn3XyRPQ4mV9sX9lK7IkjwJr47vnJyBiFzsDBpHg_H-7uQ2-_XlTan5HCiBq68UlGwSjg1NvzVZPCI6lNy2dLY31kJaKxYcUYe1b6sEZDyjLU7zWIQg" -X POST -H 'accept: application/json, text/plain, */*' ${JF_URL}/artifactory/api/export/system -H 'content-type: application/json' -d '{"exportPath" : "'${EXPORT_PATH}'", "includeMetadata" : true, "createArchive" : false, "bypassFiltering" : false, "verbose" : false, "failOnError" : true, "failIfEmpty" : true, "m2" : false, "incremental" : false, "excludeContent" : true}'

tar -zcvf ${DATE}.tar.gz ${DATE}* --remove-files

echo "Artifactory export data is /root/artifactory-backups/${DATE}.tar.gz" |mailx -v -s "Artifactory export data on ${DATE} completed" given@reslv.io