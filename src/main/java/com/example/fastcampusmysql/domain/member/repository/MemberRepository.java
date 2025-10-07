package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepository {
    static final String TABLE = "Member";
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Member> ROW_MAPPER = (ResultSet rs, int rowNum) -> Member.builder()
            .id(rs.getLong("id"))
            .nickname(rs.getString("nickname"))
            .email(rs.getString("email"))
            .birthday(rs.getObject("birthday", LocalDate.class))
            .createdAt(rs.getObject("createdAt", LocalDateTime.class))
            .build();


    public Optional<Member> findById(Long id) {
        String query = String.format("SELECT * FROM %s WHERE id = ?", TABLE);
        List<Member> members = jdbcTemplate.query(query, ROW_MAPPER, id);
        Member nullableMember = DataAccessUtils.singleResult(members);
        return Optional.ofNullable(nullableMember);
    }

    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Member.builder()
                .id(id)
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .createdAt(member.getCreatedAt())
                .build();
    }

}
