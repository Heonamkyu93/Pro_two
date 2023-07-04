package com.example.spring.Pro_two.repository;


import com.example.spring.Pro_two.domain.MemberDto;
import com.example.spring.Pro_two.domain.PicDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MemberRepositoryDb implements MemberRepository {

    private JdbcTemplate jdbcTemplate;
    private DataSourceTransactionManager transactionManager;

    @Autowired
    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Autowired
    public void DbMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public MemberRepositoryDb(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public int joinSave(MemberDto memberDto, ArrayList<MultipartFile> am) {

        // 트랜잭션 정의 생성
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        // 트랜잭션 상태 생성
        TransactionStatus status = transactionManager.getTransaction(def);

        //   SqlParameterSource parameters = new BeanPropertySqlParameterSource(memberDto);  컬럼과 필드수와 이름이 일치하면 dto객체를 바로 인서트 가능
        String sql = "SELECT twmemseq.NEXTVAL FROM DUAL";  //이걸로 시퀀스 불러오고
        Long generatedId = jdbcTemplate.queryForObject(sql, Long.class);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("twmember");
        simpleJdbcInsert.usingColumns("twseq", "twid", "twname", "twpwd", "twage", "twnick", "twpower");     // 여기서 지정한 컬럼들에만 데이터가 인서트된다 이걸 사용하지 않아도 맵이 컬럼명과 일치하면 알아서 들어간다 사용하는 이유는 컬럼순서지정과 명확함

        SimpleJdbcInsert simpleJdbcInsert2 = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert2.withTableName("twinfo");


        //simpleJdbcInsert.usingGeneratedKeyColumns("twseq");         //usingColumns이 있어야 사용가능
        // MapSqlParameterSource parameters = new MapSqlParameterSource();  MapSqlParameterSource는 SqlParameterSource 인터페이스의 구현체로, Spring JDBC에서 많이 사용되는 방법 중 하나입니다.
        // MapSqlParameterSource를 사용하면 Map을 직접 만들 필요 없이 편리하게 값을 추가하고 전달할 수 있습니다.
        // parameters.addValue("컬럼명","데이터");
        memberDto.setTwSeq(Integer.parseInt(String.valueOf(generatedId)));
        Map<String, String> map = new HashMap<>();
        map.put("twseq", String.valueOf(generatedId));
        map.put("twid", memberDto.getTwId());
        map.put("twname", memberDto.getTwName());
        map.put("twpwd", memberDto.getTwPwd());
        map.put("twage", memberDto.getTwAge() + "");
        map.put("twnick", memberDto.getTwNick());
        map.put("twpower", "member");
        java.sql.Date joinDate = new java.sql.Date(System.currentTimeMillis()); // 현재 날짜를 Date 객체로 생성
        String bDate=null;
        // <input type='date'> 는 아무선택을 안하면 빈문자열이 넘어온다
        if (memberDto.getTwBirth().isEmpty()) {
            bDate = "1000-10-10";
        } else{
            bDate = memberDto.getTwBirth();
        } // 문자열을 Date 객체로 변환
        java.sql.Date birthDate = java.sql.Date.valueOf(bDate);
        //simplejdbcinsert가 Timestamp 객체를 사용하기때문에 오라클의 데이터타입이 date면 오류가 난다

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("twseq", memberDto.getTwSeq());
        parameters.addValue("twemail", memberDto.getTwEmail());
        parameters.addValue("twcellphone", memberDto.getTwCellphone());
        parameters.addValue("twjoindate", joinDate);
        parameters.addValue("twgender", memberDto.getTwGender());
        parameters.addValue("twaddress", memberDto.getTwAddress());
        parameters.addValue("twbirth", birthDate);
        parameters.addValue("twuse", "no");
        try {

            int re[] = simpleJdbcInsert.executeBatch(map);
            int re2[] = simpleJdbcInsert2.executeBatch(parameters);

            // 모든 인서트 작업이 성공한 경우에만 커밋
            if (isBatchSuccess(re) && isBatchSuccess(re2)) {
                transactionManager.commit(status);
                return memberDto.getTwSeq();
            } else {
                transactionManager.rollback(status);
                return -1;
            }
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    private boolean isBatchSuccess(int re[]) {
        for (int result : re) {
            if (result <= 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int joinSavePic(PicDto picDto) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("twpic");

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("twseq", picDto.getTwSeq());
        parameters.addValue("twpicser", picDto.getTwPicSer());
        parameters.addValue("twpicori", picDto.getTwPicOri());
        parameters.addValue("twpiccat", picDto.getTwPicCat());
        int re = simpleJdbcInsert.execute(parameters);
        return re;
    }
}
