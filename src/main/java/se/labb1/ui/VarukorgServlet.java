package se.labb1.ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.labb1.bo.DatabasFel;
import se.labb1.bo.Produkt;
import se.labb1.bo.ProduktHanterare;
import se.labb1.bo.Varukorg;

import java.io.IOException;

@WebServlet("/varukorg")
public class VarukorgServlet extends HttpServlet {

    private ProduktHanterare produktHanterare = new ProduktHanterare();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("anvandare") == null) {
            response.sendRedirect(request.getContextPath() + "/loggain");
            return;
        }

        String felmeddelande = (String) session.getAttribute("felmeddelande");
        if (felmeddelande != null) {
            request.setAttribute("felmeddelande", felmeddelande);
            session.removeAttribute("felmeddelande");
        }

        hamtaVarukorg(session);

        request.getRequestDispatcher("/WEB-INF/jsp/varukorg.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("anvandare") == null) {
            response.sendRedirect(request.getContextPath() + "/loggain");
            return;
        }

        int produktId;
        try {
            produktId = Integer.parseInt(request.getParameter("produktId"));
        } catch (NumberFormatException fel) {
            visaFelPaProduktsidan(request, response, "Produkten finns inte.");
            return;
        }

        int antal;
        try {
            antal = Integer.parseInt(request.getParameter("antal"));
        } catch (NumberFormatException fel) {
            visaFelPaProduktsidan(request, response, "Antal måste vara ett heltal.");
            return;
        }

        if (antal < 1) {
            visaFelPaProduktsidan(request, response, "Antal måste vara minst 1.");
            return;
        }

        Produkt produkt;
        try {
            produkt = produktHanterare.hamtaProdukt(produktId);
        } catch (DatabasFel fel) {
            visaFelPaProduktsidan(request, response, fel.getMessage());
            return;
        }

        if (produkt == null) {
            visaFelPaProduktsidan(request, response, "Produkten finns inte.");
            return;
        }

        Varukorg varukorg = hamtaVarukorg(session);
        boolean lagdesTill = varukorg.laggTill(produkt, antal);
        if (!lagdesTill) {
            visaFelPaProduktsidan(request, response, "Det finns bara " + produkt.getLagerAntal()
                    + " st av " + produkt.getNamn() + " i lager. Du kan inte ha fler än så i varukorgen.");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/varukorg");
    }

    private Varukorg hamtaVarukorg(HttpSession session) {
        Varukorg varukorg = (Varukorg) session.getAttribute("varukorg");
        if (varukorg == null) {
            varukorg = new Varukorg();
            session.setAttribute("varukorg", varukorg);
        }
        return varukorg;
    }

    private void visaFelPaProduktsidan(HttpServletRequest request, HttpServletResponse response,
                                       String felmeddelande) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("felmeddelande", felmeddelande);
        response.sendRedirect(request.getContextPath() + "/produkter");
    }
}
