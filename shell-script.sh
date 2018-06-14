#!/bin/bash/

cd /home
wget 192.168.50.103/images/parcel-api
wget 192.168.50.103/images/parcel-db
wget 192.168.50.103/images/parcel-frontend
wget 192.168.50.103/jar/ParcelSize.jar
cd ..

docker network create -d bridge parcelnetwork

docker load -i /home/parcel-api
docker load -i /home/parcel-db
docker load -i /home/parcel-frontend

docker run -d --restart always --network=parcelnetwork --name=parcel-api -p 8443:8443 parcel-api java -jar ./build/libs/ParcelSize.jar
docker run -d --restart always --network=parcelnetwork -p 3306:3306 --name=parcel-db parcel-db
docker run -d --restart always --network=parcelnetwork -p 8080:80 --name=parcel-frontend parcel-frontend
