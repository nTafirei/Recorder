package com.marotech.recording.config;

import org.neo4j.driver.*;
import org.neo4j.driver.types.TypeSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.neo4j.config.AbstractNeo4jConfig;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.concurrent.CompletionStage;

@Configuration
@DependsOn("config")
@EnableNeo4jRepositories(basePackages = "com.marotech.vending.repository")
public class Neo4jConfig extends AbstractNeo4jConfig {

    @Autowired
    private Config config;

    @Override
    @Bean
    public Driver driver() {
        boolean enabled = config.getBooleanProperty("app.neo4j.enabled");
        if (enabled) {
            String user = config.getProperty("app.neo4j.user");
            String password = config.getProperty("app.neo4j.password");
            String dbUrl = config.getProperty("app.neo4j.dburl");
            return GraphDatabase.driver(dbUrl,
                    AuthTokens.basic(user, password));
        }
        LOG.warn("Returning dummy Neo4J driver as it is not enabled in config.properties");
        return new Driver() {
            @Override
            public ExecutableQuery executableQuery(String s) {
                return null;
            }

            @Override
            public BookmarkManager executableQueryBookmarkManager() {
                return null;
            }

            @Override
            public boolean isEncrypted() {
                return false;
            }

            @Override
            public <T extends BaseSession> T session(Class<T> aClass, SessionConfig sessionConfig, AuthToken authToken) {
                return null;
            }

            @Override
            public void close() {

            }

            @Override
            public CompletionStage<Void> closeAsync() {
                return null;
            }

            @Override
            public Metrics metrics() {
                return null;
            }

            @Override
            public boolean isMetricsEnabled() {
                return false;
            }

            @Override
            public TypeSystem defaultTypeSystem() {
                return null;
            }

            @Override
            public void verifyConnectivity() {

            }

            @Override
            public CompletionStage<Void> verifyConnectivityAsync() {
                return null;
            }

            @Override
            public boolean verifyAuthentication(AuthToken authToken) {
                return false;
            }

            @Override
            public boolean supportsSessionAuth() {
                return false;
            }

            @Override
            public boolean supportsMultiDb() {
                return false;
            }

            @Override
            public CompletionStage<Boolean> supportsMultiDbAsync() {
                return null;
            }
        };
    }

    private static final Logger LOG = LoggerFactory.getLogger(Neo4jConfig.class);
}
