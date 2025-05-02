# kafka-POC

.\bin\windows\kafka-storage.bat random-uuid

.\bin\windows\kafka-storage.bat format -t 1C8WRdjCTHuMXjejYz6UUw -c .\config\kraft\server.properties

# Start Kafka server
.\bin\windows\kafka-server-start.bat .\config\kraft\server.properties

# Open Consumer console
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic KAFKA-ORDER-TOPICS --from-beginning
