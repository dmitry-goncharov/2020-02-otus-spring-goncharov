package ru.gonch.spring.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.gonch.spring.model.mongo.BookMongo;

public interface BookMongoRepository extends MongoRepository<BookMongo, String>, BookMongoRepositoryCustom {
}
