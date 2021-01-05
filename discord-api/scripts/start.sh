#!/bin/bash
app_name="discord-api"
app_file="discord-api.jar"
profile="production"

# application start.
echo "$app_name is start."
nohup java -Dserver.port=5000 -Dspring.profiles.active=${profile} -jar ${app_file} > /dev/null &
