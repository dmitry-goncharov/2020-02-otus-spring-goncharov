package ru.gonch.spring.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.gonch.spring.model.mongo.AuthorMongo;

public interface AuthorMongoRepository extends MongoRepository<AuthorMongo, String> {
}
