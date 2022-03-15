package iss.edu.sg.MockPaper1.config;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    private Logger logger = Logger.getLogger(RedisConfig.class.getName());

    @Value("${spring.redis.host}") // @Value is injected from application property
    private String redisHost;

    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    // @Bean an object for spring boot to manage, annotated here to initialize the bean you want to use later on
    // it initializes dependencies for us by @Autowired
    // you tell spring that you @Autowired by the other annotations @Scope, @Controller, @RestController etc
    // @Scope tells spring how long a bean lives

    @Bean 
    @Scope("singleton")
    public RedisTemplate<String, Object> redisTemplate(){
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        
        logger.log(Level.INFO, "redisHost > " + redisHost + '\n' + "redisPort > " + redisPort);

        config.setHostName(redisHost);
        config.setPort(redisPort.get());
        config.setPassword(redisPassword);
        // config.setDatabase(redisDatabase);

        final JedisClientConfiguration jedisClient = JedisClientConfiguration
                .builder()
                .build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();

        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();

        template.setConnectionFactory(jedisFac);

        // converts the values/keys into strings for redis serializer
        // template.setValueSerializer(new StringRedisSerializer());
        // template.setHashValueSerializer(new StringRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());
        template.setValueSerializer(serializer);

        return template;
    }
}

