public class KafkaStreamsExample {
    private static final Logger log = LogManager.getLogger(KafkaStreamsExample.class);

    public static void main(String[] args) {
        KafkaStreamsConfig config = KafkaStreamsConfig.fromEnv();

        log.info(KafkaStreamsConfig.class.getName() + ": {}",  config.toString());

        Properties props = KafkaStreamsConfig.createProperties(config);

        StreamsBuilder builder = new StreamsBuilder();

        builder.stream(config.getSourceTopic(), Consumed.with(Serdes.String(), Serdes.String()))
                .mapValues(value -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append(value);
                    return sb.reverse().toString();
                })
                .to(config.getTargetTopic(), Produced.with(Serdes.String(), Serdes.String()));

        KafkaStreams streams;

        if (System.getenv("JAEGER_SERVICE_NAME") != null)   {
            Tracer tracer = Configuration.fromEnv().getTracer();
            GlobalTracer.registerIfAbsent(tracer);

            KafkaClientSupplier supplier = new TracingKafkaClientSupplier(tracer);
            streams = new KafkaStreams(builder.build(), props, supplier);
        } else {
            streams = new KafkaStreams(builder.build(), props);
        }

        streams.start();
    }