package service;

import dao.BookDao;
import entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    //添加的方法
    public void addBook(Book book){
        bookDao.add(book);
    }

    public void updateBook(Book book){
        bookDao.update(book);
    }

    public void deleteBook(String id){
        bookDao.delete(id);
    }

    public void selectCount(){
        System.out.println(bookDao.selectCount());
    }

    public Book queryForSingleBook(String id){
        return bookDao.queryForSingleBook(id);
    }

    public List<Book> queryForBookList(){
        return bookDao.queryForBookList();
    }

    public int[] batchAppend(List<Object[]> bookList){
        return bookDao.batchAppend(bookList);
    }

    public int[] batchModify(List<Object[]> bookList){
        return bookDao.batchModify(bookList);
    }

    public int[] batchDelete(List<Object[]> bookList){
        return bookDao.batchDelete(bookList);
    }

}
