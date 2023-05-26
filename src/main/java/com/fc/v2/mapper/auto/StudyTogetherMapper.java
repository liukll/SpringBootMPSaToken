package com.fc.v2.mapper.auto;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fc.v2.model.auto.StudyTogether;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 学习记录表 StudyTogetherMapper
 *
 * @author fuce_自动生成
 * @email ${email}
 * @date 2023-05-17 15:31:03
 */
public interface StudyTogetherMapper extends BaseMapper<StudyTogether> {

    @Select({" <script> " +
            " select user_name username,sum(today_study_count) timecount " +
            " from study_together where 1=1 " +
            " <if test='startDate != null and startDate != \"\"'> and study_start_time &gt;=#{startDate} </if>" +
            " <if test='endDate != null and endDate != \"\"'> and study_end_time &lt;=#{endDate} </if>" +
            " group by user_name " +
            " </script> "})
    List<LinkedHashMap<String, Object>> getTimeCountStatistics(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Select({" <script> " +
            " select DATE_FORMAT(study_start_time, '%Y-%m-%d') date,sum(today_study_count) timecount " +
            "from study_together where 1=1  " +
            " <if test='startDate != null and startDate != \"\"'> and study_start_time &gt;=#{startDate} </if>" +
            " <if test='endDate != null and endDate != \"\"'> and study_end_time &lt;=#{endDate} </if>" +
            " <if test='userId != null and userId != \"\"'> and user_id=#{userId} </if>" +
            "group by DATE_FORMAT(study_start_time, '%Y-%m-%d') " +
            " </script> "})
    List<LinkedHashMap<String, Object>> getTimeCountTrend(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userId") String userId);

    @Select(" select id,username from t_sys_user where username<>'admin' ")
    List<Map<String, Object>> getUserList();

    @Select(" select * from study_together ")
    List<StudyTogether> getStudyList();
}