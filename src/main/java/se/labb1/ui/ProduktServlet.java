package se.labb1.ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.labb1.bo.Anvandare;
import se.labb1.bo.DatabasFel;
import se.labb1.bo.Produkt;
import se.labb1.bo.ProduktHanterare;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/produkter")
public class ProduktServlet extends HttpServlet {

    private ProduktHanterare produktHanterare = new ProduktHanterare();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {HttpSession session = request.getSession();

        if (session.getAttribute("anvandare") == null) {
            response.sendRedirect(request.getContextPath() + "/loggain");
            return;
        }

        String felmeddelande = (String) session.getAttribute("felmeddelande");
        if (felmeddelande != null) {
            request.setAttribute("felmeddelande", felmeddelande);
            session.removeAttribute("felmeddelande");
        }

        String meddelande = (String) session.getAttribute("meddelande");
        if (meddelande != null) {
            request.setAttribute("meddelande", meddelande);
            session.removeAttribute("meddelande");
        }

        try {
            ArrayList<Produkt> produkter = produktHanterare.hamtaAllaProdukter();
            request.setAttribute("produkter", produkter);
        } catch (DatabasFel fel) {
            request.setAttribute("felmeddelande", fel.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/jsp/produkter.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        Anvandare anvandare = (Anvandare) session.getAttribute("anvandare");
        if (anvandare == null) {
            response.sendRedirect(request.getContextPath() + "/loggain");
            return;
        }

        int produktId;
        try {
            produktId = Integer.parseInt(request.getParameter("produktId"));
        } catch (NumberFormatException fel) {
            produktId = 0;
        }

        int antal;
        try {
            antal = Integer.parseInt(request.getParameter("antal"));
        } catch (NumberFormatException fel) {
            antal = 0;
        }

        String felmeddelande;
        try {
            felmeddelande = produktHanterare.fyllPaLager(anvandare, produktId, antal);
        } catch (DatabasFel fel) {
            felmeddelande = fel.getMessage();
        }

        if (felmeddelande != null) {
            session.setAttribute("felmeddelande", felmeddelande);
        } else {
            session.setAttribute("meddelande", "Lagret är påfyllt.");
        }

        response.sendRedirect(request.getContextPath() + "/produkter");
    }
}
