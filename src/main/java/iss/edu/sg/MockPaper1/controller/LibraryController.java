package iss.edu.sg.MockPaper1.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import iss.edu.sg.MockPaper1.model.books;
import iss.edu.sg.MockPaper1.service.booksRepo;

@Controller
@RequestMapping(path="/library")
public class LibraryController {
    private Logger logger = Logger.getLogger(LibraryController.class.getName());

    @Autowired
    booksRepo service;

    @GetMapping
    public String defaultList(Model model){
        logger.log(Level.INFO, "Show the default list");
        List<books> listOfAllBooks = service.findAll(0);
        model.addAttribute("booklist", listOfAllBooks);
        return "index";
    }

    @PostMapping
    public String bookSubmitted(@ModelAttribute books book, Model model,
    HttpServletResponse httpResponse){
        service.save(book);
        httpResponse.setStatus(HttpStatus.CREATED.value());
        model.addAttribute("book", book);
        return "bookSubmission";
    }

    // @GetMapping
    // public String getLibrary(String title, String author, Model model){
    //     model.addAttribute("title", title);
    //     model.addAttribute("author", author);

    //     if(title != "" && author == ""){

    //         // books book = service.findByTitle(title);
    //         // model.addAttribute("book", book);

    //         return "resultByTitle";
    //     }
    //     if(title == "" && author != ""){
    //         return "resultByAuthor";}
    //     if(title != "" && author != ""){
    //         return "resultByTitle";}

    //     return "error";
    // }
}      
