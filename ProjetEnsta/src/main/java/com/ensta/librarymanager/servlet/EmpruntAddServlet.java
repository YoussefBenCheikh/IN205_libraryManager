package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.LivreServiceImpl;
import com.ensta.librarymanager.service.MembreService;
import com.ensta.librarymanager.service.MembreServiceImpl;

@WebServlet("/emprunt_add")
public class EmpruntAddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	MembreService membreService = MembreServiceImpl.getInstance();
    	LivreService livreService = LivreServiceImpl.getInstance();
    	try {
    		request.setAttribute("listeMembreDispo", membreService.getListMembreEmpruntPossible());
    		request.setAttribute("listeLivresDispo", livreService.getListDispo());
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp");
        dispatcher.forward(request, response);

    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	EmpruntService empruntService = EmpruntServiceImpl.getInstance();
    	MembreService membreService = MembreServiceImpl.getInstance();
    	LivreService livreService = LivreServiceImpl.getInstance();
    	try {
            if (request.getParameter("idMembre") == null || request.getParameter("idLivre") == null){
                throw new ServletException("Données manquantes");
            } else{
            	empruntService.create(Integer.parseInt(request.getParameter("idMembre")), Integer.parseInt(request.getParameter("idLivre")), LocalDate.now());
				request.setAttribute("emprunt_list", empruntService.getListCurrent());
            }
            request.setAttribute("listeMembreDispo", membreService.getListMembreEmpruntPossible());
            request.setAttribute("listeLivresDispo", livreService.getListDispo());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	response.sendRedirect(request.getContextPath() + "/emprunt_list");
    }

}
