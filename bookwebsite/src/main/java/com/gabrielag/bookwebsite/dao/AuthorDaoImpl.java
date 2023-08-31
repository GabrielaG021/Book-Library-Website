package com.gabrielag.bookwebsite.dao;

import com.gabrielag.bookwebsite.dto.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Gabriela Gutierrez
 */

@Repository
public class AuthorDaoImpl implements AuthorDao {
    @Autowired
    JdbcTemplate jdbc;

    // SQL QUERIES
    final String GET_ALL_AUTHORS = "SELECT * FROM author";
    final String GET_AUTHOR_BY_ID = "SELECT * FROM author WHERE AuthorId=?";
    final String INSERT_AUTHOR = "INSERT INTO author (authorFirstName, authorLastName, authorDescription) VALUES (?, ?, ?)";
    final String GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
    final String UPDATE_AUTHOR =  "UPDATE author SET authorFirstName=?, authorLastName=?, authorDescription=? WHERE AuthorId=?";
    final String UPDATE_BOOK_AUTHORID = "UPDATE book SET AuthorId = null WHERE AuthorId = ?";
    final String DELETE_AUTHOR = "DELETE FROM author WHERE AuthorId=?";

    @Override
    @Transactional
    public Author addAuthor(Author author) {
        try {
            jdbc.update(INSERT_AUTHOR,
                    author.getFirstName(),
                    author.getLastName(),
                    author.getDescription());

            int newID = jdbc.queryForObject(GET_LAST_INSERT_ID, Integer.class);
            author.setId(newID);

            return author;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Author getAuthorById(int id) {
        try {
            return jdbc.queryForObject(GET_AUTHOR_BY_ID, new AuthorMapper(), id);
        } catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        try {
            return jdbc.query(GET_ALL_AUTHORS, new AuthorMapper());
        } catch (DataAccessException ex) {
            return null;
        }

    }

    @Override
    public void updateAuthor(Author author) {
        try {
            jdbc.update(UPDATE_AUTHOR,
                    author.getFirstName(),
                    author.getLastName(),
                    author.getDescription(),
                    author.getId());
        } catch (DataAccessException ex) {
            System.out.println("Error occurred while updating an author");
        }
    }

    @Override
    @Transactional
    public void deleteAuthorByID(int id) {
        try {
            jdbc.update(UPDATE_BOOK_AUTHORID, id);
            jdbc.update(DELETE_AUTHOR, id);
        } catch (DataAccessException ex) {
            System.out.println("Failed to delete author with ID: " + id);
        }
    }

    // Author Mapper
    public static final class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author();
            author.setId(rs.getInt("AuthorId"));
            author.setFirstName(rs.getString("authorFirstName"));
            author.setLastName(rs.getString("authorLastName"));
            author.setDescription(rs.getString("authorDescription"));

            return author;
        }
    }
}
