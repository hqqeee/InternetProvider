package com.epam.controller.command.subscriber;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.dataaccess.entity.User;
import com.epam.exception.services.UserServiceException;
import com.epam.services.UserService;

public class ReplenishCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			BigDecimal amount = new BigDecimal(req.getParameter("amount"));
			User user = (User) req.getSession().getAttribute("loggedUser");
			String description = "Replenish via web site. Current blanace $" + user.getBalance() +".";
			int userId = user.getId();
			((UserService) req.getServletContext().getAttribute("userService")).changeUserBalance(userId, amount, description);
			user.setBalance(user.getBalance().add(amount));
			req.setAttribute("successMessage", "Funds have been successfully added!");
		}catch (UserServiceException e) {
			e.printStackTrace();
			req.setAttribute("errorMessages", "Unable replenish balance. Please try again later.");
		} 
		catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("errorMessages", "Unable replenish balance. Please check your input.");
		}
		return new ViewAccountCommand().execute(req, resp);
	}

}
