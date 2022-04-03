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

@WebServlet("/livre_add")
public class LivreAddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	LivreService livreService = LivreServiceImpl.getInstance();
    	try {
            req.setAttribute("availableBookList", livreService.getListDispo());
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/View/livre_add.jsp");
        dispatcher.forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	LivreService livreService = LivreServiceImpl.getInstance();
    	try {
            if (req.getParameter("titre") == null){
                throw new ServletException("titre est vide");
            }else{
                int id = livreService.create(req.getParameter("titre"), req.getParameter("auteur"), req.getParameter("isbn"));
                req.setAttribute("id", id);

                resp.sendRedirect(req.getContextPath() + "/livre_details?id=" + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
