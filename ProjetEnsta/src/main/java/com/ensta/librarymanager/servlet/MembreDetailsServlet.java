package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.MembreService;
import com.ensta.librarymanager.service.MembreServiceImpl;

@WebServlet("/membre_details")
public class MembreDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	MembreService membreService = MembreServiceImpl.getInstance();
    	EmpruntService loanService = EmpruntServiceImpl.getInstance();

        try {
            req.setAttribute("membre", membreService.getById(Integer.parseInt(req.getParameter("id"))));
        } catch (Exception e) {
            new ServletException("peut pas recuperer le membre", e);
        }

        try {
            req.setAttribute("enCoursMembre", loanService.getListCurrentByMembre(Integer.parseInt(req.getParameter("id"))));
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/View/membre_details.jsp");
        dispatcher.forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	MembreService membreService = MembreServiceImpl.getInstance();
    	EmpruntService loanService = EmpruntServiceImpl.getInstance();

        try {
            if (req.getParameter("prenom") == "" || req.getParameter("prenom") == null || req.getParameter("nom") == "" || req.getParameter("nom") == null){
                throw new ServletException("nom ou prenom vide!");
            } else{
                Membre ajout = membreService.getById(Integer.parseInt(req.getParameter("id")));
                ajout.setPrenom(req.getParameter("prenom"));
                ajout.setNom(req.getParameter("nom"));
                ajout.setEmail(req.getParameter("email"));
                ajout.setTelephone(req.getParameter("telephone"));
                if (req.getParameter("abonnement").equals("BASIC")){
                    ajout.setAbonnement(Abonnement.BASIC);
                } else if (req.getParameter("abonnement").equals("PREMIUM")){
                    ajout.setAbonnement(Abonnement.PREMIUM);
                } else if (req.getParameter("abonnement").equals("VIP")){
                    ajout.setAbonnement(Abonnement.VIP);
                } 
                membreService.update(ajout);
                req.setAttribute("id", ajout.getId());
                req.setAttribute("enCoursMembre", loanService.getListCurrentByMembre(ajout.getId()));
                
                
                resp.sendRedirect(req.getContextPath() + "/membre_details?id=" + ajout.getId());
            }
        } catch (Exception e) {
            new ServletException("erreur!", e);
            req.setAttribute("erreur", "Parametre vide");
        }

    }
}
