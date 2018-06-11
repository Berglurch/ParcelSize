#!/bin/bash/

sftp tobias@192.168.50.103:/var/www/html/images/parcel-api /home/
sftp tobias@192.168.50.103:/var/www/html/images/parcel-db /home/
sftp tobias@192.168.50.103:/var/www/html/images/parcel-frontend /home/

docker stop $(docker ps -aq)
docker rm $(docker ps -aq)
docker rmi $(docker images -q)

docker network create -d bridge parcelnetwork

docker load -i /home/parcel-api
docker load -i /home/parcel-db
docker load -i /home/parcel-frontend

docker run -d --restart always --network=parcelnetwork --name=parcel-api -p 8443:8443 parcel-api java -jar ./build/libs/ParcelSize.jar
docker run -d --restart always --network=parcelnetwork -p 3306:3306 --name=parcel-db parcel-db
docker run -d --restart always --network=parcelnetwork -p 8080:80 --name=parcel-frontend parcel-frontend
