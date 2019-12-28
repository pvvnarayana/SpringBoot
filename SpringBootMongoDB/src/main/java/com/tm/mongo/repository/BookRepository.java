package com.tm.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tm.mongo.model.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, Integer>{

}
