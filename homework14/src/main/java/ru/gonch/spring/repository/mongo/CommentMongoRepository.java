package ru.gonch.spring.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.gonch.spring.model.mongo.CommentMongo;

public interface CommentMongoRepository extends MongoRepository<CommentMongo, String>, CommentMongoRepositoryCustom {
}
