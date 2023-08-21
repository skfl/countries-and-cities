package com.skfl.city.configuration;

import lombok.Getter;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.utility.Base58;

import java.time.Duration;

public class MinioContainer extends GenericContainer<MinioContainer> {

    private static final int DEFAULT_PORT = 9000;
    private static final String DEFAULT_IMAGE = "minio/minio";
    private static final String DEFAULT_TAG = "edge";

    private static final String MINIO_ACCESS_KEY = "MINIO_ACCESS_KEY";
    private static final String MINIO_SECRET_KEY = "MINIO_SECRET_KEY";

    private static final String MINIO_ACCESS_KEY_DEFAULT_VALUE = "MINIO_ACCESS_KEY_VALUE";
    private static final String MINIO_SECRET_KEY_DEFAULT_VALUE = "MINIO_SECRET_KEY_VALUE";

    private static final String DEFAULT_STORAGE_DIRECTORY = "/data";
    private static final String HEALTH_ENDPOINT = "/minio/health/ready";
    private static final int CONTAINER_ID_LENGTH = 6;

    @Getter
    private final String username;
    @Getter
    private final String password;

    MinioContainer(final String image, String username, String password){
        super(image == null ? DEFAULT_IMAGE + ":" + DEFAULT_TAG : image);
        this.username = username == null ? MINIO_ACCESS_KEY_DEFAULT_VALUE : username;
        this.password = password == null ? MINIO_SECRET_KEY_DEFAULT_VALUE: password;
        withNetworkAliases("minio-" + Base58.randomString(CONTAINER_ID_LENGTH));
        addExposedPort(DEFAULT_PORT);
        withEnv(MINIO_ACCESS_KEY, this.username);
        withEnv(MINIO_SECRET_KEY, this.password);
        withCommand("server", DEFAULT_STORAGE_DIRECTORY);
        withMinimumRunningDuration(Duration.ofSeconds(2));
        setWaitStrategy(new HttpWaitStrategy().forPort(DEFAULT_PORT)
                .forPath(HEALTH_ENDPOINT)
                .withStartupTimeout(Duration.ofMinutes(2)));
    }

    MinioContainer(final String image) {
        this(image, null, null);
    }

    public MinioContainer() {
        this(null);
    }

    public String getHostAddress() {
        return "http://" + getHost() + ":" + getMappedPort(DEFAULT_PORT);
    }
}
