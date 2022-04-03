package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.LivreServiceImpl;


@WebServlet("/livre_details")

public class LivreDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	LivreService livreService = LivreServiceImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        try {
            req.setAttribute("livre", livreService.getById(Integer.parseInt(req.getParameter("id"))));
            req.setAttribute("listeEnCours", empruntService.getListCurrentByLivre(Integer.parseInt(req.getParameter("id"))));

        } catch (Exception e) {
            new ServletException("peut pas recuperer le livre", e);
        }

        
        

        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/View/livre_details.jsp");
        dispatcher.forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	LivreService livreService = LivreServiceImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        try {
            if (req.getParameter("titre") == "" || req.getParameter("titre") == null){
                throw new ServletException("titre est vide!");
            } else{
                Livre ajout = livreService.getById(Integer.parseInt(req.getParameter("id")));
                ajout.setAuteur(req.getParameter("auteur"));
                ajout.setTitre(req.getParameter("titre"));
                ajout.setIsbn(req.getParameter("isbn"));
                livreService.update(ajout);
                req.setAttribute("id", ajout.getId());
                req.setAttribute("listeEnCours", empruntService.getListCurrentByLivre(ajout.getId()));
        
                resp.sendRedirect(req.getContextPath() + "/livre_details?id=" + ajout.getId());
            }
        } catch (Exception e) {
            new ServletException(e);
            req.setAttribute("errorMessage", "Title is empty!");
        }
    }

}
