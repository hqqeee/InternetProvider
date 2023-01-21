import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.admin.DailyWithdrawCommand;
import com.epam.dataaccess.dao.DAOFactory;
import com.epam.dataaccess.dao.RoleDAO;
import com.epam.dataaccess.dao.ServiceDAO;
import com.epam.dataaccess.dao.TariffDAO;
import com.epam.dataaccess.dao.TransactionDAO;
import com.epam.dataaccess.dao.UserDAO;
import com.epam.dataaccess.dao.mariadb.impl.DAOFactoryMariaDB;
import com.epam.dataaccess.dao.mariadb.impl.MariaDBConstants;
import com.epam.dataaccess.entity.Role;
import com.epam.dataaccess.entity.Service;
import com.epam.dataaccess.entity.Tariff;
import com.epam.dataaccess.entity.Transaction;
import com.epam.dataaccess.entity.User;
import com.epam.exception.dao.DAOException;
import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.TariffServiceException;
import com.epam.exception.services.UserServiceException;
import com.epam.util.AppContext;
import com.epam.util.EmailUtil;
import com.epam.util.SortingOrder;

@WebServlet("/test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
    public static void main(String[] args) {
		
    }
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		EmailUtil.INSTANCE.addRegistrationEmailToQueue("yinigac598@usharer.com", "asd", "asd");
		EmailUtil.INSTANCE.addSendNewPasswordEmailToQueue("yinigac598@usharer.com", "asd", "asd");
		EmailUtil.INSTANCE.addRegistrationEmailToQueue("yinigac598@usharer.com", "123", "131");
		EmailUtil.INSTANCE.sendMails();
		System.out.println(Thread.currentThread().getName());
//		AppContext appContext = AppContext.getInstance();
//		new DailyWithdrawCommand().run();
//		System.out.println("User DAO test");
//		DAOFactory factory = new DAOFactoryMariaDB();
//		UserDAO dao = factory.getUserDAO();
//		try {
////			System.out.println(MariaDBConstants.GET_USER_BY_LOGIN_AND_PASSWORD);
////			User user = dao.getUser("admin","2e2b24f8ee40bb847fe85bb23336a39ef5948e6b49d897419ced68766b16967a");
////			System.out.println(user);
////			//dao.delete(user);
////			System.out.println(dao.getSalt("admin"));	
////			System.out.println(MariaDBConstants.UPDATE_USER);
////			System.out.println(MariaDBConstants.GET_USERS_TARIFFS_BY_ID);
////			System.out.println(factory.getTariffDAO().getUsersTariffs(2));
//			
////			System.out.println("TransactionDAO test");
////			TransactionDAO transactionDAO = factory.getTransactionDAO();
////			System.out.println(transactionDAO.get(26));
////			System.out.println(transactionDAO.getAll());
////			transactionDAO.insert(new Transaction(2,1,new Timestamp(System.currentTimeMillis()),BigDecimal.TEN,"desc"
////					));
////			transactionDAO.delete(new Transaction(27,1,new Timestamp(System.currentTimeMillis()),BigDecimal.TEN,"desc"));
////			System.out.println(MariaDBConstants.UPDATE_TRANSACTION);
////			transactionDAO.update(new Transaction(36,1,new Timestamp(System.currentTimeMillis()),BigDecimal.TEN,"UPDATED"));
//		
////			System.out.println("Tariff DAO test");
////			TariffDAO tariffDAO = factory.getTariffDAO();
////			System.out.println("All" + tariffDAO.getAll());
////			Tariff tariff = new Tariff(9,"NEW ASDASD", "DESC", BigDecimal.ONE,3);
////			tariffDAO.insert(tariff);
////			tariffDAO.delete(tariff);
////			tariff.setId(22);
////			tariffDAO.update(tariff);
////			System.out.println("All" + tariffDAO.getAll());
////			System.out.println(tariffDAO.getAll(1));
////			System.out.println(tariffDAO.getAll(2,1,4,SortingOrder.DESC,"name"));
////			System.out.println(tariffDAO.getTariffCount());
////			System.out.println(tariffDAO.getTariffCount(3));
//			
////			System.out.println("Service DAO test");
////			ServiceDAO serviceDAO = factory.getServiceDAO();
////			Service service = new Service(5, "SUPER PUPER", "ASD");
////			serviceDAO.insert(service);
////			System.out.println(serviceDAO.getAll());
////			System.out.println(serviceDAO.get(1));
////			service.setDescription("UPDATED");
////			serviceDAO.update(service);
////			serviceDAO.delete(new Service(5, "asd","asd"));
//			
//			
//			System.out.println("ROLE DAO TEST");
//			RoleDAO roleDAO = factory.getRoleDAO();
//			Role role = new Role(3,"sud","asd");
//			roleDAO.delete(role);
////			roleDAO.insert(role);
////
////			System.out.println(roleDAO.getAll());
////			System.out.println(roleDAO.get(1));
////			role.setName("SUD");
////			roleDAO.update(role);
////			System.out.println(roleDAO.getAll());
////			
//		
//		} catch (DAOException e) {
//			e.printStackTrace();
//		}
//		DAOFactory factoryDAO = (DAOFactory) getServletContext().getAttribute("daoFactory");
//		try {
//			System.out.println(factoryDAO.getUserDAO().get(1));
//		} catch (DAOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Map <String,Command> commonActions = ((Map<String,Command>) getServletContext().getAttribute("commonActions"));
//		commonActions.get("login").execute(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
