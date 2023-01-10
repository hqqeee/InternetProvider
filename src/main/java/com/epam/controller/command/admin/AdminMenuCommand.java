 package com.epam.controller.command.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.dataaccess.entity.User;
import com.epam.exception.services.UserServiceException;
import com.epam.services.UserService;
import com.epam.util.AppContext;
import com.epam.util.SortingOrder;

public class AdminMenuCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
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
		
		UserService userService = AppContext.getInstance().getUserService();
		int numberOfSubscriber;
		try {
			numberOfSubscriber = userService.getSubscribersNumber(currentSearchField);
			int numberOfPages = (int)Math.ceil(numberOfSubscriber * 1.0 / currentRowNumber);
			req.setAttribute("numberOfPages", numberOfPages);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("currentSearchField", currentSearchField);
			req.setAttribute("currentRowNumber", currentRowNumber);
			List<User> subscribers = userService.viewSubscribers(currentSearchField, currentPage, currentRowNumber);
			req.setAttribute("usersToDisplay", subscribers);
		} catch (UserServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return Page.ADMIN_MENU_PAGE;
	}

}
