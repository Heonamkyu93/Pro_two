package com.example.spring.Pro_two.repository;


import com.example.spring.Pro_two.domain.MemberDto;
import com.example.spring.Pro_two.domain.PicDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
     //   SqlParameterSource parameters = new BeanPropertySqlParameterSource(memberDto);  컬럼과 필드수와 이름이 일치하면 dto객체를 바로 인서트 가능
        String sql = "SELECT twmemseq.NEXTVAL FROM DUAL";  //이걸로 시퀀스 불러오고
        Long generatedId = jdbcTemplate.queryForObject(sql, Long.class);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("twmember");
        simpleJdbcInsert.usingColumns("twseq");
        simpleJdbcInsert.usingGeneratedKeyColumns("twseq");         //여기서 시퀀스값 반환해서 나중에 사진 저장할때 사용

        memberDto.setTwSeq(Integer.parseInt(String.valueOf(generatedId)));
        Map<String,String> map = new HashMap<>();
        map.put("twseq",String.valueOf(generatedId));
        map.put("twid",memberDto.getTwId());
        map.put("twname",memberDto.getTwName());
        map.put("twpwd",memberDto.getTwPwd());
        map.put("twage",memberDto.getTwAge()+"");
        map.put("twnick",memberDto.getTwNick());
        int id = simpleJdbcInsert.execute(map) ;
    }

    @Override
    public int joinSavePic(PicDto picDto) {





        return 0;
    }
}
