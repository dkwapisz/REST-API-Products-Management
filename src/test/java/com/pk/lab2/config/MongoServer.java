package com.pk.lab2.config;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

public class MongoServer {

    private MongodExecutable mongodExecutable;

    public void start() throws Exception {
        String ip = "localhost";
        int port = 31017;

        ImmutableMongodConfig mongodConfig = ImmutableMongodConfig.builder()
                .version(Version.Main.V6_0)
                .net(new Net(ip, port, Network.localhostIsIPv6()))
                .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
    }

    public void stop() {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }
}
