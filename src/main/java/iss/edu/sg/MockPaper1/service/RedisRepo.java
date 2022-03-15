package iss.edu.sg.MockPaper1.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import iss.edu.sg.MockPaper1.model.books;

@Repository
public interface RedisRepo {
    public books findByTitle(final String title);
    public books findByAuthor(final String author);
    public void save(final books book);
    public List<books> findAll(int startIndex);
}
