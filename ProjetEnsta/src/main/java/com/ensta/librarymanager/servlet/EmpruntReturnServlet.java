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



@WebServlet("/emprunt_return")
public class EmpruntReturnServlet extends HttpServlet  {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        int id = -1;
        if (req.getParameter("id") != null){
            id = Integer.parseInt(req.getParameter("id"));
        }
        try {
        	if (id != -1){
                req.setAttribute("id", id);
                req.setAttribute("empruntEnCours", empruntService.getListCurrent());
            } else{
                req.setAttribute("empruntEnCours", empruntService.getListCurrent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        final RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp");
        dispatcher.forward(req, resp);
        }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    EmpruntService empruntService = EmpruntServiceImpl.getInstance();

    try {
        if (req.getParameter("id") == null){
            throw new ServletException("pas de id!");
        } else{
        	empruntService.returnBook(Integer.parseInt(req.getParameter("id")));
            req.setAttribute("empruntEnCours", empruntService.getListCurrent());
        }
    } catch (Exception e) {
        throw new ServletException("peut pas envoyer!", e);
    }

    resp.sendRedirect(req.getContextPath() + "/emprunt_list");
    }
}
