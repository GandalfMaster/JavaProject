package BookManager;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String author;
    private int price;


    public Book(String title, String author, int price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Book [title=" + title + ", author=" + author + ", price=" + price + "]";
    }

    /*public static class BookBuilder{
        private String title;
        private String author;
        private int price;

        private BookBuilder(){};

        public BookBuilder title(String title){
            System.out.println("1");
            this.title = title;
            return this;
        }

        public BookBuilder author(String author){
            System.out.println("1");
            this.author = author;
            return this;
        }

        public BookBuilder price(int price){
            System.out.println("1");
            this.price = price;
            return this;
        }

        public Book build(){
            return new Book(title, author, price);
        }
    }*/

}
