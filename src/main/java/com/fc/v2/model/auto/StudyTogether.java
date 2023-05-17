package com.fc.v2.model.auto;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.date.DateUtil;
import java.util.Date;

@TableName("study_together")
public class StudyTogether implements Serializable {
    private static final long serialVersionUID = 1L;

	
	@ApiModelProperty(value = "主键id，雪花算法")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Integer id;
	
	@ApiModelProperty(value = "用户id")
	private Integer userId;
	
	@ApiModelProperty(value = "用户名")
	private String userName;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@ApiModelProperty(value = "学习开始时间")
	private Date studyStartTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@ApiModelProperty(value = "学习结束时间")
	private Date studyEndTime;
	
	@ApiModelProperty(value = "学习内容")
	private String detail;
	
	@ApiModelProperty(value = "当日学习时长，自动计算")
	private Integer todayStudyCount;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@ApiModelProperty(value = "录入时间")
	private Date createTime;
	
	@ApiModelProperty(value = "创建人")
	private String createUser;
	
	@ApiModelProperty(value = "创建数据ip地址")
	private String createIp;
	
	@JsonProperty("id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id =  id;
	}
	@JsonProperty("userId")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId =  userId;
	}
	@JsonProperty("userName")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName =  userName;
	}
	@JsonProperty("studyStartTime")
	public Date getStudyStartTime() {
		return studyStartTime;
	}

	public void setStudyStartTime(Date studyStartTime) {
		this.studyStartTime =  studyStartTime;
	}
	@JsonProperty("studyEndTime")
	public Date getStudyEndTime() {
		return studyEndTime;
	}

	public void setStudyEndTime(Date studyEndTime) {
		this.studyEndTime =  studyEndTime;
	}
	@JsonProperty("detail")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail =  detail;
	}
	@JsonProperty("todayStudyCount")
	public Integer getTodayStudyCount() {
		return todayStudyCount;
	}

	public void setTodayStudyCount(Integer todayStudyCount) {
		this.todayStudyCount =  todayStudyCount;
	}
	@JsonProperty("createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime =  createTime;
	}
	@JsonProperty("createUser")
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser =  createUser;
	}
	@JsonProperty("createIp")
	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp =  createIp;
	}


	public StudyTogether(Integer id,Integer userId,String userName,Date studyStartTime,Date studyEndTime,String detail,Integer todayStudyCount,Date createTime,String createUser,String createIp) {
		
		this.id = id;
		
		this.userId = userId;
		
		this.userName = userName;
		
		this.studyStartTime = studyStartTime;
		
		this.studyEndTime = studyEndTime;
		
		this.detail = detail;
		
		this.todayStudyCount = todayStudyCount;
		
		this.createTime = createTime;
		
		this.createUser = createUser;
		
		this.createIp = createIp;
		
	}

	public StudyTogether() {
	    super();
	}

	public String dateToStringConvert(Date date) {
		if(date!=null) {
			return DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	

}