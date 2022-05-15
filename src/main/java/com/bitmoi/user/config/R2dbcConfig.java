package com.bitmoi.user.config;

import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Slf4j
@Configuration
@EnableR2dbcRepositories
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    @Override
    public ConnectionFactory connectionFactory() {
        return MySqlConnectionFactory.from(
                MySqlConnectionConfiguration.builder().build()
        );
    }

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
//        resourceDatabasePopulator.addScript(new ClassPathResource("schema.sql"));
        initializer.setDatabasePopulator(resourceDatabasePopulator);
        return initializer;
    }

//    @Override
//    public ConnectionFactory connectionFactory() {
//        return new H2ConnectionFactory(H2ConnectionConfiguration.builder().inMemory("user").build());
//    }
    //
    //// private static final Logger log =
    // LoggerFactory.getLogger(OnlineEduPlatformUserApplication.class);
    // private UserRepository userRepository;
    //
    // @Override
    // public ConnectionFactory connectionFactory() {
    //// return null;
    // return new H2ConnectionFactory(
    // H2ConnectionConfiguration.builder()
    // .inMemory("user")
    // .property(H2ConnectionOption.DB_CLOSE_DELAY, "-1") // DB연결이 닫혀도 유지되도록 설정
    // .username("sa")
    // .build());
    // }
    //
    //// @Bean
    //// public H2ConnectionFactory connectionFactory() {
    //// return new H2ConnectionFactory(
    //// H2ConnectionConfiguration.builder()
    //// .url("mem:testdb;DB_CLOSE_DELAY=-1;")
    //// .username("sa")
    //// .inMemory("user")
    //// .build()
    //// );
    //// }
    //
    // @Bean
    // public ConnectionFactoryInitializer initializer(ConnectionFactory
    // connectionFactory) {
    //
    // ConnectionFactoryInitializer initializer = new
    // ConnectionFactoryInitializer();
    // initializer.setConnectionFactory(connectionFactory);
    // initializer.setDatabasePopulator(new ResourceDatabasePopulator(new
    // ClassPathResource("schema.sql")));
    //
    // return initializer;
    // }
    //
    // @Bean
    // public CommandLineRunner setAdmin(UserRepository userRepository) {
    // return args -> {
    // log.info("------------------------------");
    //
    // // save a admin
    // userRepository.save(new User(1, UserType.ADVISOR.getValue(), "관리자", "@",
    // "1234", "addr", "010-0000-0000"));
    //
    // log.info("Initializer Test");
    //
    // // check data
    // userRepository.findAll().doOnNext(user -> {
    // log.info(user.toString());
    // }).blockLast(Duration.ofSeconds(10));
    //
    // log.info("");
    // };
    // }
}
