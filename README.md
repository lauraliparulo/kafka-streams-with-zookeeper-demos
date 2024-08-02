# Kafka Streams With Zookeeper Demo Applications

Inspired by the Oreilly's sandboxed...

A template for a brand new Kafka Streams application. Includes:

- Docker Compose configurations for running a local Kafka cluster
- A customizable bootstrap script for pre-creating and pre-populating the Kafka topics needed by your application
- Bundled build tooling (via Gradle Wrapper) so you don't need to worry about installing build tools

# Usage
```sh
# clone the repo
git clone https://github.com/mitch-seymour/kafka-streams-template.git

cd kafka-streams-template/

# reinit git
rm -rf .git && git init

# start the local Kafka cluster
docker-compose up -d

# run your Kafka Streams application
cd app/
./gradlew run --info

# you should see the test data get printed to your screen
```

## Interact with the Kafka cluster

Run "docker-compose up -d" to get kafka + zookeeper running!

Then run the following to access the Kafka container:
> docker-compose exec kafka bash

Once accessed the kafka container, to create a Kafka Topic run:
> $ kafka-topics --bootstrap-server localhost:9092 --create --topic tweets --partitions 4

To list kafka topics, use the kafka-topics script:
> kafka-topics  --bootstrap-server localhost:9092 --list

To produce data to a Kafka topic, use :
> kafka-console-producer  --bootstrap-server localhost:9092 --topic tweets --property 'key.separator=|'  --property 'parse.key=true'

Then type: "1|{"id": 1, "name": "Elyse"} 2|{"id": 2, "name": "Mitch"}"

Instead of adding the messages manually in the prompt, you can as well use a file. For example "inputs.txt":

``
3|{"id": 3, "name": "Isabelle"}
``
``
4|{"id": 4, "name": "Chloe"}
``

Then run:
kafka-console-producer --bootstrap-server localhost:9092 --topic tweets --property 'parse.key=true' --property 'key.separator=|' < /data/inputs.txt 

To verify that the kafka broker got the messages from the producer, you can use the kafka-console-consumer:
> kafka-console-consumer --bootstrap-server localhost:9092 --topic tweets --from-beginning --property print.key=true

Notice that the order in which the messages arrive might be different, according to the kafka brokers processing.


# Customizing
- The script for pre-creating and pre-populating topics can be found in [/scripts/bootstrap-topics.sh](/scripts/bootstrap-topics.sh)
- The root directory for the Kafka Streams app can be found in the [/app](/app) directory
- Dependencies can be updated and added to [/app/build.gradle](/app/build.gradle)
- Your Kafka Streams topology can be updated in [/app/src/main/java/com/example/MyTopology.java](/app/src/main/java/com/example/MyTopology.java)

# Gradle commands
- Build the project with `./gradlew build --info`
- Run the application with `./gradlew run --info`
- View the dependencies using `./gradlew dependencies`
