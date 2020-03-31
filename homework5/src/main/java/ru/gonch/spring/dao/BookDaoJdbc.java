package ru.gonch.spring.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.gonch.spring.model.Author;
import ru.gonch.spring.model.Book;
import ru.gonch.spring.model.Genre;

import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public long insert(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", book.getName());
        params.addValue("genreId", book.getGenre().getId());
        params.addValue("authorId", book.getAuthor().getId());
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("insert into books (name,genre_id,author_id) values (:name,:genreId,:authorId)", params, kh);
        return kh.getKey().longValue();
    }

    @Override
    public List<Book> getAll(int limit, int offset) {
        String sql = getAllBooksSql() +
                " limit :limit offset :offset";
        Map<String, Object> params = Map.of(
                "limit", limit,
                "offset", offset
        );
        return jdbc.query(sql, params, getRowMapper());
    }

    @Override
    public List<Book> getBooksByGenreId(long genreId, int limit, int offset) {
        String sql = getAllBooksSql() +
                " where b.genre_id=:genreId" +
                " limit :limit offset :offset";
        Map<String, Object> params = Map.of(
                "genreId", genreId,
                "limit", limit,
                "offset", offset
        );
        return jdbc.query(sql, params, getRowMapper());
    }

    @Override
    public List<Book> getBooksByAuthorId(long authorId, int limit, int offset) {
        String sql = getAllBooksSql() +
                " where b.author_id=:authorId" +
                " limit :limit offset :offset";
        Map<String, Object> params = Map.of(
                "authorId", authorId,
                "limit", limit,
                "offset", offset
        );
        return jdbc.query(sql, params, getRowMapper());
    }

    @Override
    public Book getById(long id) {
        String sql = getAllBooksSql() +
                " where b.book_id=:id";
        Map<String, Object> params = Map.of("id", id);
        return jdbc.queryForObject(sql, params, getRowMapper());
    }

    @Override
    public void update(Book book) {
        Map<String, Object> params = Map.of(
                "id", book.getId(),
                "name", book.getName(),
                "genreId", book.getGenre().getId(),
                "authorId", book.getAuthor().getId()
        );
        jdbc.update("update books set name=:name,genre_id=:genreId,author_id=:authorId where book_id=:id", params);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Map.of("id", id);
        jdbc.update("delete from books where book_id=:id", params);
    }

    private String getAllBooksSql() {
        return "select b.book_id, b.name as book_name, b.genre_id, b.author_id," +
                " g.genre_id, g.name as genre_name, a.author_id, a.name as author_name" +
                " from books b" +
                " left join genres g on b.genre_id = g.genre_id" +
                " left join authors a on b.author_id = a.author_id";
    }

    private RowMapper<Book> getRowMapper() {
        return (resultSet, i) -> new Book(
                resultSet.getLong("book_id"),
                resultSet.getString("name"),
                new Genre(
                        resultSet.getLong("genre_id"),
                        resultSet.getString("genre_name")
                ),
                new Author(
                        resultSet.getLong("author_id"),
                        resultSet.getString("author_name")
                )
        );
    }
}
