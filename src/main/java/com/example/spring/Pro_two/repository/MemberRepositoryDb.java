package com.example.spring.Pro_two.repository;


import com.example.spring.Pro_two.domain.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.util.ArrayList;
@Repository
public class MemberRepositoryDb implements MemberRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void DbMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public MemberRepositoryDb(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void joinSave(MemberDto memberDto, ArrayList<MultipartFile> am) {

    }
}
