package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.MembreService;
import com.ensta.librarymanager.service.MembreServiceImpl;

@WebServlet("/membre_add")
public class MembreAddServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	MembreService membreService = MembreServiceImpl.getInstance();
        try {
            req.setAttribute("listeMembreDispo", membreService.getList());
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/View/membre_add.jsp");
        dispatcher.forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	MembreService membreService = MembreServiceImpl.getInstance();
    	EmpruntService empruntService = EmpruntServiceImpl.getInstance();
    	try {
            if (req.getParameter("nom") == null || req.getParameter("prenom") == null || req.getParameter("nom") == "" || req.getParameter("prenom") == "" ){
                throw new ServletException("nom ou prenom est vide!");
            } else {
                int id = membreService.create(req.getParameter("nom"), req.getParameter("prenom"), req.getParameter("adresse"), req.getParameter("email"), req.getParameter("telephone"), Abonnement.BASIC);
                req.setAttribute("id", id);
                req.setAttribute("listeEmprunt", empruntService.getListCurrentByMembre(id));
                resp.sendRedirect(req.getContextPath() + "/membre_details?id=" + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
