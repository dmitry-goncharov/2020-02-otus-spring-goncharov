package ru.gonch.spring.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.gonch.spring.model.Genre;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public long insert(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("insert into genres (name) values (:name)", params, kh);
        return kh.getKey().longValue();
    }

    @Override
    public List<Genre> getAll(int limit, int offset) {
        String sql = "select genre_id, name from genres limit :limit offset :offset";
        Map<String, Object> params = Map.of(
                "limit", limit,
                "offset", offset
        );
        return jdbc.query(sql, params, getRowMapper());
    }

    @Override
    public Optional<Genre> getById(long id) {
        try {
            Map<String, Object> params = Map.of("id", id);
            return Optional.ofNullable(jdbc.queryForObject("select genre_id, name from genres where genre_id=:id", params, getRowMapper()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public int update(Genre genre) {
        Map<String, Object> params = Map.of(
                "id", genre.getId(),
                "name", genre.getName()
        );
        return jdbc.update("update genres set name=:name where genre_id=:id", params);
    }

    @Override
    public int deleteById(long id) {
        Map<String, Object> params = Map.of("id", id);
        return jdbc.update("delete from genres where genre_id=:id", params);
    }

    private RowMapper<Genre> getRowMapper() {
        return (resultSet, i) -> new Genre(
                resultSet.getLong("genre_id"),
                resultSet.getString("name")
        );
    }
}
