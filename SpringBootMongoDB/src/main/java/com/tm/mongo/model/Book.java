package com.tm.mongo.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
//@Getter
//@Setter
//@ToString
//@AllArgsConstructor

@Document(collection = "book")
public class Book {
	
	@Id
	private int id;
	private String bookName;
	private String authorName;
	

}
