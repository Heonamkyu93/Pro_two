package com.example.spring.Pro_two.repository;


import com.example.spring.Pro_two.domain.MemberDto;
import com.example.spring.Pro_two.domain.PicDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
    public int joinSave(MemberDto memberDto, ArrayList<MultipartFile> am) {
     //   SqlParameterSource parameters = new BeanPropertySqlParameterSource(memberDto);  컬럼과 필드수와 이름이 일치하면 dto객체를 바로 인서트 가능
        String sql = "SELECT twmemseq.NEXTVAL FROM DUAL";  //이걸로 시퀀스 불러오고
        Long generatedId = jdbcTemplate.queryForObject(sql, Long.class);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("twmember");
        simpleJdbcInsert.usingColumns("twseq","twid","twname","twpwd","twage","twnick");     // 여기서 지정한 컬럼들에만 데이터가 인서트된다 이걸 사용하지 않아도 맵이 컬럼명과 일치하면 알아서 들어간다 사용하는 이유는 컬럼순서지정과 명확함
  //      simpleJdbcInsert.usingGeneratedKeyColumns("twseq");         //usingColumns이 있어야 사용가능



       // MapSqlParameterSource parameters = new MapSqlParameterSource();  MapSqlParameterSource는 SqlParameterSource 인터페이스의 구현체로, Spring JDBC에서 많이 사용되는 방법 중 하나입니다.
                                                                        // MapSqlParameterSource를 사용하면 Map을 직접 만들 필요 없이 편리하게 값을 추가하고 전달할 수 있습니다.
       // parameters.addValue("컬럼명","데이터");
        memberDto.setTwSeq(Integer.parseInt(String.valueOf(generatedId)));
        Map<String,String> map = new HashMap<>();
        map.put("twseq",String.valueOf(generatedId));
        map.put("twid",memberDto.getTwId());
        map.put("twname",memberDto.getTwName());
        map.put("twpwd",memberDto.getTwPwd());
        map.put("twage",memberDto.getTwAge()+"");
        map.put("twnick",memberDto.getTwNick());
        int id = simpleJdbcInsert.execute(map) ;
        if(id!=0) return Integer.parseInt(String.valueOf(generatedId));
        return -1;
    }

    @Override
    public int joinSavePic(PicDto picDto) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("twpic");

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("twseq",picDto.getTwSeq());
        parameters.addValue("twpicser" ,picDto.getTwPicSer());
        parameters.addValue("twpicori",picDto.getTwPicOri());
        parameters.addValue("twpiccat",picDto.getTwPicCat());
        int re=simpleJdbcInsert.execute(parameters);
        return re;
    }
}
