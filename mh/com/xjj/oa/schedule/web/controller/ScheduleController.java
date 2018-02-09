package com.xjj.oa.schedule.web.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/schedule")
public class ScheduleController {

	@RequestMapping(value = "/parseScheduleXml", method = { RequestMethod.GET, RequestMethod.POST })
	public String parseScheduleXml(HttpServletRequest request, HttpServletResponse response) {
		
		String startY = request.getParameter("startY");
		String startM = request.getParameter("startM");
		String change = request.getParameter("changeMonth");
		int y, m, c;// 实际日期
		if (startY == null || startM == null || change == null) {
			Calendar startCal = Calendar.getInstance();
			y = startCal.get(Calendar.YEAR);
			m = startCal.get(Calendar.MONTH);
			c = 0;
		} else {
			y = Integer.parseInt(startY);
			m = Integer.parseInt(startM);
			c = Integer.parseInt(change);
		}
		Calendar cal = Calendar.getInstance();
		cal.set(y, m + c, 1);
		int yy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH);
		request.getSession().setAttribute("startY", yy);
		request.getSession().setAttribute("startM", mm);
		return "schedule/index";
	}
}
