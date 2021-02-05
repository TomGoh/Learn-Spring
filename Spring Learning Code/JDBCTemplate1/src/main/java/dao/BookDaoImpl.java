package dao;

import entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao{

    //注入JDBCTemplate
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add(Book book) {
        String sql="insert into t_book values(?,?,?)";
        int result=jdbcTemplate.update(sql,book.getUserId(),book.getUserName(),book.getUstatus());
        System.out.println(result);
    }

    @Override
    public void update(Book book) {
        String sql="update t_book set username=?, ustatus=? where user_id=?";
        int result=jdbcTemplate.update(sql,book.getUserName(),book.getUstatus(),book.getUserId());
        System.out.println(result);
    }

    @Override
    public void delete(String id) {
        String sql="delete from t_book where user_Id=?";
        int resukt=jdbcTemplate.update(sql,id);
        System.out.println(resukt);
    }

    @Override
    public int selectCount() {
        String sql= "select count(*) from t_book";
        int result= jdbcTemplate.queryForObject(sql,Integer.class);
        return result;
    }

    @Override
    public Book queryForSingleBook(String id) {
        String sql="select * from t_book where user_id=?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Book>(Book.class),new String[]{id});
    }

    @Override
    public List<Book> queryForBookList() {
        String sql="select * from t_book";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Book>(Book.class));
    }

    @Override
    public int[] batchAppend(List<Object[]> bookList) {
        String sql="insert into t_book values(?,?,?)";
        return jdbcTemplate.batchUpdate(sql,bookList);
    }

    @Override
    public int[] batchModify(List<Object[]> objects) {
        String sql="update t_book set username=?, ustatus=? where user_id=?";
        return jdbcTemplate.batchUpdate(sql,objects);
    }

    @Override
    public int[] batchDelete(List<Object[]> objects) {
        String sql="delete from t_book where user_Id=?";
        return jdbcTemplate.batchUpdate(sql,objects);
    }
}
