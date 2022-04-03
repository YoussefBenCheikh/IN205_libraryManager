package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.EmpruntServiceImpl;


@WebServlet("/emprunt_list")
public class EmpruntListServer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

    	EmpruntService empruntService = EmpruntServiceImpl.getInstance();
	try {
		if (request.getParameter("show") != null && request.getParameter("show").contains("all")){
            request.setAttribute("empruntList", empruntService.getList());
            request.setAttribute("show", "all");
        }else {
            request.setAttribute("empruntList", empruntService.getListCurrent());
            request.setAttribute("show", "current");
        }
	} catch (Exception e) {
        e.printStackTrace();
    }
	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp");
    dispatcher.forward(request, response);

    }
}
