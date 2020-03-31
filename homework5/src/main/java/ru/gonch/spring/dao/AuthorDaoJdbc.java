package ru.gonch.spring.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.gonch.spring.model.Author;

import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public long insert(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", author.getName());
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("insert into authors (name) values (:name)", params, kh);
        return kh.getKey().longValue();
    }

    @Override
    public List<Author> getAll(int limit, int offset) {
        String sql = "select * from authors limit :limit offset :offset";
        Map<String, Object> params = Map.of(
                "limit", limit,
                "offset", offset
        );
        return jdbc.query(sql, params, getRowMapper());
    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Map.of("id", id);
        return jdbc.queryForObject("select * from authors where author_id=:id", params, getRowMapper());
    }

    @Override
    public void update(Author author) {
        Map<String, Object> params = Map.of(
                "id", author.getId(),
                "name", author.getName()
        );
        jdbc.update("update authors set name=:name where author_id=:id", params);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Map.of("id", id);
        jdbc.update("delete from authors where author_id=:id", params);
    }

    private RowMapper<Author> getRowMapper() {
        return (resultSet, i) -> new Author(
                resultSet.getLong("author_id"),
                resultSet.getString("name")
        );
    }
}
