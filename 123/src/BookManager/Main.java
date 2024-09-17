package BookManager;

import java.io.*;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.List;
public class Main {

    private static List<Book> LIST;

    public static void main(String[] args){
        System.out.println("Enter the BSS......");
        load();
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("========== BSS ============");
            System.out.println("1.input book info");
            System.out.println("2.read book info");
            System.out.println("3.delete book info");
            System.out.println("4.change book info");
            System.out.println("5.quit");
            switch(scanner.nextInt()){
                case 1:
                    insert(scanner);
                    break;
                case 2:
                    list();
                    break;
                case 3:
                    delete(scanner);
                    break;
                case 4:
                    modify(scanner);
                    break;
                case 5:
                    System.out.println("storing......");
                    save();
                    System.out.println("goodbye!");
                    System.exit(0);
                    return;
                default:

            }

        }

    }
    private static void save(){
        try(ObjectOutputStream stream = new  ObjectOutputStream(new FileOutputStream("data"))){
            stream.writeObject(LIST);
            stream.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
    private static void load(){
        File file = new File("data");
        if(file.exists()){
            try(ObjectInputStream stream = new  ObjectInputStream(new FileInputStream("data"))){
                LIST = (List<Book>) stream.readObject();
            }catch(IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        else{
            LIST = new LinkedList<>();
        }

    }

    private static void insert(Scanner scanner){
        scanner.nextLine();
        System.out.println("1");
        String title  = scanner.nextLine();
        System.out.println("2");
        String author  = scanner.nextLine();
        System.out.println("3");
        int price = scanner.nextInt();
        scanner.nextLine();
        Book newBook = new Book(title, author, price);
        LIST.add(newBook);
        System.out.println(LIST);
    }

    private static void modify(Scanner scanner){
        scanner.nextLine();
        System.out.println("input index!");
        int index  = scanner.nextInt();
        scanner.nextLine();
        while(index > LIST.size() - 1 || index < 0){
            System.out.println("input wrong");
            index  = scanner.nextInt();
            scanner.nextLine();
        }
        Book book  = LIST.get(index);
        System.out.println("1");
        book.setTitle(scanner.nextLine());
        System.out.println("2");
        book.setAuthor(scanner.nextLine());
        System.out.println("3");
        book.setPrice(scanner.nextInt());
        scanner.nextLine();
        System.out.println("OK");
    }

    private static void  list(){
        for(int i = 0; i < LIST.size(); i++){
            System.out.println(i + "." + LIST.get(i));
        }
    }

    private static void delete(Scanner scanner){
        scanner.nextLine();
        int index = scanner.nextInt();
        scanner.nextLine();
        while(index > LIST.size() - 1 || index < 0){
            System.out.println("index wrong!");
            index = scanner.nextInt();;
            scanner.nextLine();
        }
        LIST.remove(index);
        System.out.println("delete success");

    }

}