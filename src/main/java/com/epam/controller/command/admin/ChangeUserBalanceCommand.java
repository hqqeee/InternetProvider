package com.epam.controller.command.admin;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.UserService;

public class ChangeUserBalanceCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println(req.getParameter("amount"));
		System.out.println(req.getParameter("description"));
		System.out.println(req.getParameter("balanceChangeType"));
		System.out.println(req.getParameter("userId"));
		try {
			BigDecimal difference = new BigDecimal(req.getParameter("amount"));
			if(req.getParameter("balanceChangeType").equals("withdraw")) {
				difference = difference.negate();
			}
			String description = req.getParameter("description");
			int userId =Integer.parseInt(req.getParameter("userId"));
			((UserService) req.getServletContext().getAttribute("userService")).changeUserBalance(userId, difference, description);
			req.setAttribute("successMessage", "User's balance uccessfully changed!");
		}catch (NegativeUserBalanceException e) {
			e.printStackTrace();
			req.setAttribute("errorMessages", "User balance cannot be negative.");
		} 
		catch (UserServiceException e) {
			e.printStackTrace();
			req.setAttribute("errorMessages", "Unable change user balance. Please try again later.");
		} 
		catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("errorMessages", "Unable change user balance. Please check your input.");
		}
		return new AdminMenuCommand().execute(req, resp);
	}

}
