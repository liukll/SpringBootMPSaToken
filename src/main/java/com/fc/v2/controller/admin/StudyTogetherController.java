package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.mapper.auto.StudyTogetherMapper;
import com.fc.v2.mapper.custom.TsysUserDao;
import com.fc.v2.model.auto.TsysUser;
import com.fc.v2.model.auto.StudyTogether;
import com.fc.v2.model.custom.Tablepar;
import com.fc.v2.satoken.SaTokenUtil;
import com.fc.v2.service.StudyTogetherService;
import com.fc.v2.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 学习记录表Controller
 *
 * @author fuce
 * @ClassName: StudyTogetherController
 * @date 2023-05-17 15:31:03
 */
@Api(value = "学习记录表")
@Controller
@RequestMapping("/StudyTogetherController")
public class StudyTogetherController extends BaseController {

    private String prefix = "admin/studyTogether";

    @Autowired
    private StudyTogetherService studyTogetherService;
    @Resource
    private StudyTogetherMapper studyTogetherMapper;

    /**
     * 学习记录表页面展示
     *
     * @param model
     * @return String
     * @author fuce
     */
    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/view")
    @SaCheckPermission("gen:studyTogether:view")
    public String view(ModelMap model) {
        return prefix + "/list";
    }


    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/list")
    @SaCheckPermission("gen:studyTogether:list")
    @ResponseBody
    public ResultTable list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit, StudyTogether studyTogether) {
        PageHelper.startPage(page, limit);
//        TsysUser tsysUser = SaTokenUtil.getUser();
        List<StudyTogether> studyTogetherList = studyTogetherMapper.getStudyList();
        PageInfo<StudyTogether> pageInfo = new PageInfo<>(studyTogetherList);
        List<StudyTogether> list = pageInfo.getList().stream().peek(it -> it.setIdStr(it.getId().toString())).collect(Collectors.toList());
        return pageTable(list, pageInfo.getTotal());
    }

    /**
     * 新增跳转
     */
    @ApiOperation(value = "新增跳转", notes = "新增跳转")
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        return prefix + "/add";
    }

    /**
     * 新增保存
     *
     * @param
     * @return
     */
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/add")
    @SaCheckPermission("gen:studyTogether:add")
    @ResponseBody
    public AjaxResult add(StudyTogether studyTogether, HttpServletRequest request) {
        TsysUser tsysUser = SaTokenUtil.getUser();
        studyTogether.setUserId(Long.valueOf(tsysUser.getId()));
        studyTogether.setUserName(tsysUser.getUsername());
        studyTogether.setCreateIp(request.getRemoteAddr());
        studyTogether.setCreateTime(new Date());
        studyTogether.setCreateUser(tsysUser.getUsername());
        Long startTime = studyTogether.getStudyStartTime().getTime();
        Long endTime = studyTogether.getStudyEndTime().getTime();
        float time = new BigDecimal((endTime - startTime) / 1000 / 60)
                .setScale(1, RoundingMode.HALF_UP).floatValue();
        studyTogether.setTodayStudyCount(time);
        boolean rtn = studyTogetherService.save(studyTogether);
        if (rtn) {
            return success();
        } else {
            return error();
        }
    }

    /**
     * 学习记录表删除
     *
     * @param ids
     * @return
     */
    //@Log(title = "学习记录表删除", action = "111")
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping("/remove")
    @SaCheckPermission("gen:studyTogether:remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        boolean rtn = studyTogetherService.removeBatchByIds(Arrays.asList(ids.split(",")));
        if (rtn) {
            return success();
        } else {
            return error();
        }
    }


    /**
     * 修改跳转
     *
     * @param id
     * @param map
     * @return
     */
    @ApiOperation(value = "修改跳转", notes = "修改跳转")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap map) {
        map.put("StudyTogether", studyTogetherService.getById(id));

        return prefix + "/edit";
    }

    /**
     * 修改保存
     */
    //@Log(title = "学习记录表修改", action = "111")
    @ApiOperation(value = "修改保存", notes = "修改保存")
    @SaCheckPermission("gen:studyTogether:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StudyTogether studyTogether) {
        boolean rtn = studyTogetherService.updateById(studyTogether);
        if (rtn) {
            return success();
        } else {
            return error();
        }
    }

    @ApiOperation(value = "学习记录统计页面", notes = "学习记录统计页面")
    @SaCheckPermission("gen:studyTogether:statistics")
    @GetMapping("/statisticsPage")
    public String statisticsPage() {
        return "admin/studyTogether/studyStatistics";
    }

    @ApiOperation(value = "学习记录统计数据", notes = "学习记录统计数据")
    @SaCheckPermission("gen:studyTogether:statistics")
    @GetMapping("/statisticsData")
    @ResponseBody
    public ResultTable statisticsData(@RequestParam(value = "rangDate") String rangeData, @RequestParam(value = "userId") String userId) {
        String startDate = "";
        String endDate = "";
        if (StringUtils.isNotEmpty(rangeData)) {
            String[] split = rangeData.split(" ~ ");
            startDate = split[0];
            endDate = split[1];
        }
        List<LinkedHashMap<String, Object>> timeCountStatistics = studyTogetherMapper.getTimeCountStatistics(startDate, endDate);
        List<LinkedHashMap<String, Object>> timeCountTrend = studyTogetherMapper.getTimeCountTrend(startDate, endDate, userId);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("xList1", timeCountStatistics.stream().map(it -> it.get("username")).toArray());
        returnMap.put("yList1", timeCountStatistics.stream().map(it -> it.get("timecount")).toArray());
        returnMap.put("xList2", timeCountTrend.stream().map(it -> it.get("date")).toArray());
        returnMap.put("yList2", timeCountTrend.stream().map(it -> it.get("timecount")).toArray());
        return dataTable(returnMap);
    }

    @ApiOperation(value = "获取用户", notes = "获取用户")
    @GetMapping("/userList")
    @SaCheckPermission("system:user:list")
    @ResponseBody
    public ResultTable userList() {
        List<Map<String, Object>> userList = studyTogetherMapper.getUserList();
        return dataTable(userList);
    }
}
