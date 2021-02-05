package dao;

import entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDao {

    public void add(Book book);

    public void update(Book book);

    public void delete(String id);

    public int selectCount();

    public Book queryForSingleBook(String id);

    public List<Book> queryForBookList();

    public int[] batchAppend(List<Object[]> bookList);

    public int[] batchModify(List<Object[]> objects);

    public int[] batchDelete(List<Object[]> objects);
}
