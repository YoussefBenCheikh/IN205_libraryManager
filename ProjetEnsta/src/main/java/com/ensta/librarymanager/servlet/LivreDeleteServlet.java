package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.LivreServiceImpl;

@WebServlet("/livre_delete")
public class LivreDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	LivreService livreService = LivreServiceImpl.getInstance();
    	int id = -1;
        if (req.getParameter("id") != null){
            id = Integer.parseInt(req.getParameter("id"));
        }
        try {
            if (id != -1){
                req.setAttribute("livre", livreService.getById(id));
                req.setAttribute("id", id);
            } else{

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/View/livre_delete.jsp");
        dispatcher.forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	LivreService livreService = LivreServiceImpl.getInstance();

        try {
        	livreService.delete(Integer.parseInt(req.getParameter("id")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/livre_list");
    }
}
