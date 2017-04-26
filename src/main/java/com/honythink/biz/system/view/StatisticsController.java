package com.honythink.biz.system.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.honythink.Constants;
import com.honythink.biz.base.controller.BaseController;
import com.honythink.biz.system.dto.BaseDto;
import com.honythink.biz.system.dto.EntryDto;
import com.honythink.biz.system.dto.KPIDto;
import com.honythink.biz.system.dto.Series;
import com.honythink.biz.system.service.MailService;
import com.honythink.biz.system.service.ResumeService;
import com.honythink.db.entity.Customer;
import com.honythink.db.entity.Dic;
import com.honythink.db.entity.Entry;
import com.honythink.db.entity.Interview;
import com.honythink.db.entity.SysUser;
import com.honythink.db.mapper.CustomerMapper;
import com.honythink.db.mapper.DicMapper;
import com.honythink.db.mapper.EntryMapper;
import com.honythink.db.mapper.InterviewMapper;
import com.honythink.db.mapper.StatisticsMapper;
import com.honythink.db.mapper.SysUserMapper;

/**
 * 
 * @author
 * @version : 1.00
 * @Copyright http://www.onehome.cn/
 * @Create Time : 2017年2月28日 上午12:58:47
 * @Description : interview
 * @History：Editor version Time Operation Description*
 *
 */
@Controller
@RequestMapping(value = "/statistics")
public class StatisticsController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger("StatisticsController");

    @Autowired
    private EntryMapper entryMapper;

    @Autowired
    private InterviewMapper interviewMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private DicMapper dicMapper;

    @Autowired
    private StatisticsMapper statisticsMapper;

    @RequestMapping(value = "/statistics_recommend", method = RequestMethod.GET)
    public ModelAndView statistics_index() {
        ModelAndView mav = new ModelAndView("statistics_recommend");
        // 2
        // presentKPI
        // entryKPI
        // salesKPI
        return mav;
    }

    @RequestMapping(value = "/recommendKPI", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject recommendKPI(String start, String end) throws ParseException {
        // 1
        // 推荐统计
        // recommendKPI
        // 获取数据
        Map<String, String[]> map = new HashMap<String, String[]>();
        List<String> xAxisData = new ArrayList<String>();
        List<String> legendData = new ArrayList<String>();
        List<JSONObject> seriesList = new ArrayList<JSONObject>();
        List<SysUser> hrs = sysUserMapper.getUsersByRole(Constants.ROLE_HR);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(start);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar;
        int days = 30;
        if (!end.equals("")) {
            Date endDate = sdf.parse(end);
            endCalendar = Calendar.getInstance();
            endCalendar.setTime(endDate);
            days = (int) ((endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis()) / (1000 * 3600 * 24));
        } else {
            endCalendar = Calendar.getInstance();
        }

        for (int day = days; day >= 0; day--) {
            Calendar date = Calendar.getInstance();
            date.set(Calendar.DATE, endCalendar.get(Calendar.DATE) - day);
            // x-axsis
            xAxisData.add(sdf.format(date.getTime()));
        }
        for (SysUser hr : hrs) {
            List<String> list = new ArrayList<String>();
            for (int day = days; day >= 0; day--) {
                Calendar date = Calendar.getInstance();
                date.set(Calendar.DATE, endCalendar.get(Calendar.DATE) - day);
                // y-axsis
                Map<String, String> params = new HashMap<String, String>();
                params.put("username_hr", hr.getUsername());
                params.put("recommend_time", sdf.format(date.getTime()));
                KPIDto KPIdto = statisticsMapper.selectRecommendKPI(params);
                if (null == KPIdto) {
                    list.add("0");
                } else {
                    list.add(KPIdto.getCount());
                }
            }
            Map<String, String> params = new HashMap<String, String>();
            params.put("username_hr", hr.getUsername());
            params.put("start",start);
            params.put("end",end);
            int count = statisticsMapper.selectRecommendTotal(params);
            //legend  总数
            legendData.add(hr.getRealname()+"("+count+")");
//            legendData.add(hr.getRealname());
            Series series = new Series(hr.getRealname()+"("+count+")", Series.TYPE_LINE, list);
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("name", series.toName());
            jsonObject2.put("type", "bar");
            jsonObject2.put("data", series.data);
            seriesList.add(jsonObject2);
        }

        // x-axis

        // xAxisData和seriesList转为json

        JSONObject recommendKPI = new JSONObject();
        recommendKPI.put("xAxisData", xAxisData);
        recommendKPI.put("legendData", legendData);
        recommendKPI.put("seriesList", seriesList);
        return recommendKPI;
    }
}
