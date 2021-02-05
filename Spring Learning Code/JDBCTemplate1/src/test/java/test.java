import entity.Book;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.BookService;

import java.util.ArrayList;
import java.util.List;

public class test {

    @Test
    public void testAdd(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");
        BookService bookService=context.getBean("bookService",BookService.class);
        Book book=new Book("Book2","2","Sold out");
        bookService.addBook(book);
    }

    @Test
    public void testUpdate(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");
        BookService bookService=context.getBean("bookService",BookService.class);
        Book book=new Book("Book1","1","1000 Reserve");
        bookService.updateBook(book);
    }

    @Test
    public void testDelete(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");
        BookService bookService=context.getBean("bookService",BookService.class);
        bookService.deleteBook("1");
    }

    @Test
    public void testSelectCount(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");
        BookService bookService=context.getBean("bookService",BookService.class);
        bookService.selectCount();
    }

    @Test
    public void testQueryForSingleBook(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");
        BookService bookService=context.getBean("bookService",BookService.class);
        System.out.println(bookService.queryForSingleBook("2"));
    }

    @Test
    public void testQuesyForBookList(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");
        BookService bookService=context.getBean("bookService",BookService.class);
        List<Book> result=bookService.queryForBookList();
        for(Book book:result){
            System.out.println(book);
        }
    }

    @Test
    public void testBatchAppend(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");
        BookService bookService=context.getBean("bookService",BookService.class);
        List<Object[]> objects=new ArrayList<>();
        objects.add(new Object[]{"3","java","aa"});
        objects.add(new Object[]{"4","java","aa2"});
        objects.add(new Object[]{"5","java","aa33"});
        int[] result=bookService.batchAppend(objects);
        for(int i:result){
            System.out.println(i);
        }
    }

    @Test
    public void testBatchModify(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");
        BookService bookService=context.getBean("bookService",BookService.class);
        List<Object[]> objects=new ArrayList<>();
        objects.add(new Object[]{"Python","aa","3"});
        objects.add(new Object[]{"Ruby","aaa","4"});
        objects.add(new Object[]{"Rust","aaa","5"});
        int[] result=bookService.batchModify(objects);
        for(int i:result){
            System.out.println(i);
        }
    }

    @Test
    public void testBatchDelete(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");
        BookService bookService=context.getBean("bookService",BookService.class);
        List<Object[]> objects=new ArrayList<>();
        objects.add(new Object[]{"3"});
        objects.add(new Object[]{"4"});
        objects.add(new Object[]{"5"});
        int[] result=bookService.batchDelete(objects);
        for(int i:result){
            System.out.println(i);
        }
    }

}
