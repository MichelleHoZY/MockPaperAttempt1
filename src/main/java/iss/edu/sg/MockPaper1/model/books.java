package iss.edu.sg.MockPaper1.model;

import java.io.Serializable;
import java.util.Random;

public class books implements Serializable{
    private static final long serialVersionUID = 1L;
    private String title;
    private String author;
    private String bookid;
    private String image;

    public books(){
        this.bookid = generateId(8);
    }

    public books(String bookid, String title, String author, String image){
        this.bookid = bookid;
        this.title = title;
        this.author = author;
        this.image = image;
    }

    public books(String title, String author, String image){
        this.title = title;
        this.author = author;
        this.image = image;
        this.bookid = generateId(8);
    }

    private synchronized String generateId(int numChars){
        Random r = new Random();
        StringBuilder strBuilder = new StringBuilder();
        while(strBuilder.length() < numChars){
            strBuilder.append(Integer.toHexString(r.nextInt()));
        }
        return strBuilder.toString().substring(0, numChars);
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getBookid() {
        return bookid;
    }
    public void setBookid(String bookid) {
        this.bookid = bookid;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    
}
