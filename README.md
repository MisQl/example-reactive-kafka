useful commands:
```
docker run -p 2181:2181 -p 9092:9092 -v /mnt/c/dev/storage/:/storage/ -it -d openjdk 
tar -xzf kafka_2.13-2.7.0.tgz
nano config/server.properties 
 listeners=PLAINTEXT://0.0.0.0:9092
 advertised.listeners=PLAINTEXT://localhost:9092
 
bin/zookeeper-server-start.sh config/zookeeper.properties &
bin/kafka-server-start.sh config/server.properties &

bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092
bin/kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:9092
bin/kafka-console-consumer.sh --topic quickstart-events --from-beginning --bootstrap-server localhost:9092
bin/kafka-topics.sh --list --zookeeper localhost:2181
```



