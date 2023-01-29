package com.rfpiodemo.assessment.mongodb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//import converter.ZonedDateTimeReadConverter;
//import converter.ZonedDateTimeWriteConverter;

@Configuration
@EnableMongoRepositories(basePackages = "com.baeldung.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {
	
	@Value("spring.data.mongodb.uri")
	private String springDataMongodbUri = null;
	
	@Value("spring.data.mongodb.database")
	private String springDataMongoDbDatabase = null;
	
//    private final List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();

    @Override
    protected String getDatabaseName() {
        return "test";
    }

//    @Bean
//    public MongoTemplate mongoTemplate() {
//    	MongoTemplate mongoTemplate = new MongoTemplate(MongoClients.create(),springDataMongoDbDatabase);
//    	MappingMongoConverter mongoMapping = (MappingMongoConverter) mongoTemplate.getConverter();
//        mongoMapping.setCustomConversions(customConversions()); // tell mongodb to use the custom converters
//        mongoMapping.afterPropertiesSet();
//        return mongoTemplate;
//    }
//
//    
//    @Override
//    public MongoCustomConversions customConversions() {
//        return new MongoCustomConversions(Arrays.asList(new DcocumentToUserConverter()));
//    }



    @Bean
    MongoTransactionManager transactionManager(SimpleMongoClientDatabaseFactory dbFactory) {
            return new MongoTransactionManager(dbFactory);
    }


}
