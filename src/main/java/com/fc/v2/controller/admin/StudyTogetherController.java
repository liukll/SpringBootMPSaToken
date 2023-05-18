package com.fc.v2.controller.admin;

import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.model.auto.TsysUser;
import com.fc.v2.model.custom.Tablepar;
import com.fc.v2.model.auto.StudyTogether;
import com.fc.v2.satoken.SaTokenUtil;
import com.fc.v2.service.StudyTogetherService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.dev33.satoken.annotation.SaCheckPermission;
import net.bytebuddy.implementation.bytecode.constant.DefaultValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
        TsysUser tsysUser = SaTokenUtil.getUser();
        List<StudyTogether> studyTogetherList = studyTogetherService.lambdaQuery()
                .eq(StudyTogether::getCreateUser, tsysUser.getUsername())
                .list();
        PageInfo<StudyTogether> pageInfo = new PageInfo<>(studyTogetherList);
        return pageTable(pageInfo.getList(), pageInfo.getTotal());
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
     * @param mmap
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

    @ApiOperation(value = "学习记录统计", notes = "学习记录统计")
    @SaCheckPermission("gen:studyTogether:statistics")
    @GetMapping("/statisticsPage")
    public String statisticsPage() {
        return "admin/studyTogether/studyStatistics";
    }


}
