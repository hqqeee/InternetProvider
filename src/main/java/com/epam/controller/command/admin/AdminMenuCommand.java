 package com.epam.controller.command.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.dataaccess.entity.User;
import com.epam.services.UserService;
import com.epam.util.SortingOrder;

public class AdminMenuCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {

		System.out.println(req.getParameter("page"));
		System.out.println(req.getParameter("rowNumber"));
		int currentPage;
		String reqParam = req.getParameter("page");
		if(reqParam != null && !reqParam.trim().isEmpty()) {
			currentPage = Integer.parseInt(req.getParameter("page"));
		} else {
			currentPage = 1;
		}
		String currentSearchField = req.getParameter("searchField");
		if(currentSearchField == null){
			currentSearchField = "";
		}
		int currentRowNumber;
		reqParam = req.getParameter("rowNumber");
		if(reqParam != null && !reqParam.trim().isEmpty()) {
			currentRowNumber = Integer.parseInt(reqParam);
		} else {
			currentRowNumber = 5;
		}
		

		int numberOfSubscriber = ((UserService) req.getServletContext().getAttribute("userService")).getSubscribersNumber(currentSearchField);
		int numberOfPages = (int)Math.ceil(numberOfSubscriber * 1.0 / currentRowNumber);
		req.setAttribute("numberOfPages", numberOfPages);
		req.setAttribute("currentPage", currentPage);
		req.setAttribute("currentSearchField", currentSearchField);
		req.setAttribute("currentRowNumber", currentRowNumber);
		List<User> subscribers = ((UserService) req.getServletContext().getAttribute("userService")).viewSubscribers(currentSearchField, currentPage, currentRowNumber);
		req.setAttribute("usersToDisplay", subscribers);
		
		return Page.ADMIN_MENU_PAGE;
	}

}
