package com.ensta.librarymanager.servlet;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.LivreServiceImpl;
import com.ensta.librarymanager.service.MembreService;
import com.ensta.librarymanager.service.MembreServiceImpl;
import javax.servlet.annotation.WebServlet;


@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
	
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	MembreService membreService = MembreServiceImpl.getInstance();
	LivreService livreService = LivreServiceImpl.getInstance();
	EmpruntService empruntService = EmpruntServiceImpl.getInstance();
	
	
	
	List<Emprunt> empruntsCourants = new ArrayList<Emprunt>();
	int nombreMembres = -1;
	int nombreLivres = -1;
	int nombreEmprunts = -1;
	
	try {
		nombreMembres = membreService.count();
		nombreLivres = livreService.count();
		nombreEmprunts = empruntService.count();
		empruntsCourants = empruntService.getListCurrent();
		
	} catch (ServiceException e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
	}
	
	request.setAttribute("nombreMembres", nombreMembres);
	request.setAttribute("nombreLivres",nombreLivres);
	request.setAttribute("nombreEmprunts",nombreEmprunts);
	request.setAttribute("empruntsCourants",empruntsCourants);
	
	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
	dispatcher.forward(request, response);
}
	
}
