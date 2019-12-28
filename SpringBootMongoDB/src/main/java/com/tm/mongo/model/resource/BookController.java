package com.tm.mongo.model.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tm.mongo.model.Book;
import com.tm.mongo.repository.BookRepository;

@RestController
public class BookController {

	@Autowired
	private BookRepository bookRepository;
	
	@PostMapping("/addBook")
	public String saveBook(@RequestBody Book book) {
		bookRepository.save(book);
		return "Added Book with id :"+book.getId();
	}
	
	@GetMapping("/findAllBooks")
	public List<Book> getBooks(){
		return bookRepository.findAll();
	}
	
	@GetMapping("/findAllBooks/{id}")
	public Optional<Book> getBook(@PathVariable int id){
		return bookRepository.findById(id);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteBook(@PathVariable int id) {
		bookRepository.deleteById(id);
		return "Book deleted with id :"+id;
	}
	
	@PutMapping("/updateBook")
	public String updateBook(@RequestBody Book book){
		bookRepository.save(book);
		return "Book updated :"+book.getId();
	}
	
}
