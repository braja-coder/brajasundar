package com.iiht.evaluation.coronakit.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iiht.evaluation.coronakit.dao.KitDao;
import com.iiht.evaluation.coronakit.dao.ProductMasterDao;
import com.iiht.evaluation.coronakit.model.CovidKit;
import com.iiht.evaluation.coronakit.model.KitDetail;
import com.iiht.evaluation.coronakit.model.ProductMaster;
import com.iiht.evaluation.coronakit.model.UserDetails;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private KitDao kitDAO;
	private ProductMasterDao productMasterDao;

	public void setKitDAO(KitDao kitDAO) {
		this.kitDAO = kitDAO;
	}

	public void setProductMasterDao(ProductMasterDao productMasterDao) {
		this.productMasterDao = productMasterDao;
	}

	public void init(ServletConfig config) {
		String jdbcURL = config.getServletContext().getInitParameter("jdbcUrl");
		String jdbcUsername = config.getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = config. getServletContext().getInitParameter("jdbcPassword");
		
		this.kitDAO = new KitDao(jdbcURL, jdbcUsername, jdbcPassword);
		//this.productMasterDao = new ProductMasterDao(jdbcURL, jdbcUsername, jdbcPassword);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		
		String viewName = "";
		try {
			switch (action) {
			case "loginuser":
				viewName = userLogin(request, response);
				break;
			case "newuser":
				viewName = showNewUserForm(request, response);
				break;
			case "insertuser":
				insertNewUser(request, response);
				viewName = showAllProducts(request, response);
				break;
			case "showproducts":
				viewName = showAllProducts(request, response);
				break;	
			case "addnewitem":
				viewName = addNewItemToKit(request, response);
				break;
			case "deleteitem":
				viewName = deleteItemFromKit(request, response);
				break;
			case "showkit":
				viewName = showKitDetails(request, response);
				break;
			case "placeorder":
				viewName = showPlaceOrderForm(request, response);
				break;
			case "saveorder":
				viewName = saveOrderForDelivery(request, response);
				break;	
			case "ordersummary":
				viewName = showOrderSummary(request, response);
				break;	
			default : viewName = "notfound.jsp"; break;	
			}
		} catch (Exception ex) {
			
			throw new ServletException(ex.getMessage());
		}
			RequestDispatcher dispatch = 
					request.getRequestDispatcher(viewName);
			dispatch.forward(request, response);
	
	}
	private String userLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("userid");
		String password = request.getParameter("password");

		if (username.equalsIgnoreCase("user") && password.equalsIgnoreCase("user")) {

			return "user?action=list";
		} else {
			request.setAttribute("msg", "Invalid Crediantials");
			request.setAttribute("username", username);
			return "index.jsp";
		}
	}
	private String showOrderSummary(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return "";
	}

	private String saveOrderForDelivery(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		HttpSession session = request.getSession();

		String Address = request.getParameter("pAddress");
		UserDetails usrdt = (UserDetails) session.getAttribute("userdetails");
		List<KitDetail> kitDetails = (List<KitDetail>) request.getSession().getAttribute("selectedKitDetails");

		int totalamount = 0;

		for (KitDetail kit : kitDetails) {

			totalamount += kit.getAmount();
		}
		int coronkitID = kitDAO.addCovidkit(usrdt.getUserName(), usrdt.getEmail(), usrdt.getPhone(), totalamount,
				Address, java.time.LocalDate.now().toString(), true);

		for (KitDetail kit : kitDetails) {

			kitDAO.addKitdetails(coronkitID, kit.getProductId(), kit.getQuantity(), kit.getAmount());
		}

		request.setAttribute("ckit", new CovidKit(0, usrdt.getUserName(), usrdt.getEmail(), usrdt.getPhone(),
				totalamount, Address, java.time.LocalDate.now().toString(), true));
		request.setAttribute("KitDetails", new ArrayList<>(kitDetails));
		request.getSession().invalidate();
		return "ordersummary.jsp";
	}

	private String showPlaceOrderForm(HttpServletRequest request, HttpServletResponse response) {
		String view="placeorder.jsp";
		return view;
	}

	private String showKitDetails(HttpServletRequest request, HttpServletResponse response) {
		for (int i = 0; i < Integer.parseInt(request.getParameter("psize")); i++) {

			String id = request.getParameter("pid" + i);
			String quantityStr = request.getParameter("quantity" + i);
			if (quantityStr != null && !quantityStr.trim().equals("")) {

				int quantity = Integer.valueOf(quantityStr.trim());
				if (0 == quantity) {
					continue;
				}

				String costStr = request.getParameter("cost" + i);
				int cost = Integer.parseInt(costStr);
				int productId = Integer.parseInt(id);

				String pname = request.getParameter("pname" + i);
				int name=Integer.parseInt(pname);

				KitDetail kitDetail = new KitDetail(productId, 0, productId, quantity, cost * quantity, pname);
				List<KitDetail> values = (List<KitDetail>) request.getAttribute("KitDetails");
				if (null == values) {
					values = new ArrayList<>();
				}
				values.add(kitDetail);
				request.setAttribute("KitDetails", values);
				request.getSession().setAttribute("selectedKitDetails", values);
			}

		}
		return "showkit.jsp";
	}

	private String deleteItemFromKit(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return "";
	}

	private String addNewItemToKit(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return "";
	}

	private String showAllProducts(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		List<ProductMaster> products = this.productMasterDao.getAllproductRecords();
		request.setAttribute("products", products);

		try {
			List<KitDetail> kitDetails = (List<KitDetail>) request.getSession().getAttribute("selectedKitDetails");
			if (null != kitDetails) {
				for (ProductMaster prod : products) {
					for (KitDetail kit : kitDetails) {
						if (kit.getId() == prod.getId()) {
							prod.setQuantity(kit.getQuantity());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "showproductstoadd.jsp";
	}

	private void insertNewUser(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		String name = request.getParameter("pname");
		String email = request.getParameter("pemail");
		String phone = request.getParameter("pphone");
		this.kitDAO.addNewVisitor(name, email, phone);
		UserDetails usrdetails = new UserDetails(name, email, phone);
		request.getSession().setAttribute("userdetails", usrdetails);
	}

	private String showNewUserForm(HttpServletRequest request, HttpServletResponse response) {
		String view = "newUser.jsp";
		return "view";
	}
}