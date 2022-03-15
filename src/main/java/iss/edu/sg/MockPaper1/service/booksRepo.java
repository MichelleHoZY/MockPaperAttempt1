package iss.edu.sg.MockPaper1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import iss.edu.sg.MockPaper1.model.books;

@Service
public class booksRepo implements RedisRepo{
    private static final String BOOKS_ID = "bookid6";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(final books book){
        redisTemplate.opsForList().leftPush(BOOKS_ID, book.getBookid());
        redisTemplate.opsForHash().put(BOOKS_ID + "_Map", book.getBookid(), book);
    }

    @Override
    public books findByAuthor(final String author){
        books resultByAuthor = (books)redisTemplate.opsForHash().get(BOOKS_ID + "_Map", author);
        return resultByAuthor;
    }

    @Override
    public books findByTitle(final String title) {
        books resultByTitle = (books)redisTemplate.opsForHash().get(BOOKS_ID + "_Map", title);
        return resultByTitle;
    }

    @Override
    public List<books> findAll(int startIndex){
        List<Object> fromBooksList = redisTemplate.opsForList()
            .range(BOOKS_ID, startIndex, startIndex+15);
        List<books> bks = 
            (List<books>)redisTemplate.opsForHash()
                .multiGet(BOOKS_ID + "_Map", fromBooksList)
                .stream()
                .filter(books.class::isInstance)
                .map(books.class::cast)
                .toList();

            return bks;
    }
    
}
