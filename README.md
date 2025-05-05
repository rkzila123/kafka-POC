# kafka-POC

.\bin\windows\kafka-storage.bat random-uuid

.\bin\windows\kafka-storage.bat format -t 1C8WRdjCTHuMXjejYz6UUw -c .\config\kraft\server.properties

# Start Kafka server
.\bin\windows\kafka-server-start.bat .\config\kraft\server.properties

# Open Consumer console
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic KAFKA-ORDER-TOPICS --from-beginning

#Create Topic
.\bin\windows\kafka-topics.bat --create --topic orders --bootstrap-server localhost:9092 --partitions 2 --replication-factor 1

#Produce Message
.\bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic orders --property parse.key=true --property key.separator=:

#Consume Message
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic orders --group og
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic KAFKA-ORDER-TOPICS --from-beginning

#List all Topic in Kafka server
.\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 list

#List all partitions in particular Topic
.\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --topic orders --describe

#List details of partcular Group
.\bin\windows\kafka-consumer-groups.bat --bootstrap-server localhost:9092 --group og --describe
