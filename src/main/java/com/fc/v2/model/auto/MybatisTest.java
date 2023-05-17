package com.fc.v2.model.auto;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 自动生成测试
 * </p>
 *
 * @author v2
 * @since 2023-04-07
 */
@TableName("t_mybatis_test")
public class MybatisTest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 标题
     */
    private String titlex;

    /**
     * 是与否
     */
    private Integer ifabc;

    /**
     * 创建时间
     */
    private LocalDateTime reatetime;

    /**
     * 修改时间
     */
    private LocalDateTime asdtime;

    /**
     * 副标题
     */
    private String fsv;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitlex() {
        return titlex;
    }

    public void setTitlex(String titlex) {
        this.titlex = titlex;
    }

    public Integer getIfabc() {
        return ifabc;
    }

    public void setIfabc(Integer ifabc) {
        this.ifabc = ifabc;
    }

    public LocalDateTime getReatetime() {
        return reatetime;
    }

    public void setReatetime(LocalDateTime reatetime) {
        this.reatetime = reatetime;
    }

    public LocalDateTime getAsdtime() {
        return asdtime;
    }

    public void setAsdtime(LocalDateTime asdtime) {
        this.asdtime = asdtime;
    }

    public String getFsv() {
        return fsv;
    }

    public void setFsv(String fsv) {
        this.fsv = fsv;
    }

    @Override
    public String toString() {
        return "MybatisTest{" +
        "id=" + id +
        ", titlex=" + titlex +
        ", ifabc=" + ifabc +
        ", reatetime=" + reatetime +
        ", asdtime=" + asdtime +
        ", fsv=" + fsv +
        "}";
    }
}
