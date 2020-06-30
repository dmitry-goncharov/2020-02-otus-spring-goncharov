package ru.gonch.spring.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.gonch.spring.model.mongo.GenreMongo;

public interface GenreMongoRepository extends MongoRepository<GenreMongo, String> {
}
