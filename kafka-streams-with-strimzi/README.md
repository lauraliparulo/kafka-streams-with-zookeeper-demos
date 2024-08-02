{\rtf1}

Inspired by the Oreilly's sandboxes by Jowanza Joseph

Check the kafka cluster pods are running:
> kubectl wait kafka/my-cluster --for=condition=Ready --timeout=300s -n kafka

Following pods should be running:

$ kubectl get pods
NAME                                         READY   STATUS    RESTARTS   AGE
my-cluster-entity-operator-7d85575b6-lzdbs   2/2     Running   0          2m48s
my-cluster-kafka-0                           1/1     Running   0          3m13s
my-cluster-zookeeper-0                       1/1     Running   0          3m47s
my-cluster-zookeeper-1                       1/1     Running   0          3m47s
my-cluster-zookeeper-2                       1/1     Running   0          3m47s
strimzi-cluster-operator-5ff96549f8-29ksn    1/1     Running   0          5m3s


Add the topics with the Strimzi Kubernetes operators:
> kubectl create -f entry-topic.yml
> kubectl create -f reverse-topic.yml

Check two elements of kind KafkaTopic are created:

$ kubectl get Kafkatopic
NAME            CLUSTER      PARTITIONS   REPLICATION FACTOR   READY
my-topic        my-cluster   1            1                    True
reverse-topic   my-cluster   1            1                    True


Run kafka producer :
> kubectl -n kafka run kafka-producer -ti --image=strimzi/kafka:0.19.0-kafka-2.5.0 --rm=true --restart=Never -- bin/kafka-console-producer.sh --broker-list my-cluster-kafka-bootstrap:9092 --topic my-topic

Add messages. 

The check the consumer will get the reverted messages:
kubectl -n kafka run kafka-producer -ti --image=strimzi/kafka:0.19.0-kafka-2.5.0 --rm=true --restart=Never -- bin/kafka-console-producer.sh --broker-list my-cluster-kafka-bootstrap:9092 --topic my-topic

