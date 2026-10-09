package se.labb1.ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.labb1.bo.DatabasFel;
import se.labb1.bo.OrderHanterare;
import se.labb1.bo.ProduktHanterare;
import se.labb1.bo.ProduktInfo;
import se.labb1.bo.VarukorgInfo;

import java.io.IOException;

@WebServlet("/varukorg")
public class VarukorgServlet extends HttpServlet {

    private ProduktHanterare produktHanterare = new ProduktHanterare();
    private OrderHanterare orderHanterare = new OrderHanterare();

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

        VarukorgInfo varukorgInfo = hamtaVarukorgInfo(session);
        request.setAttribute("varukorgInfo", varukorgInfo);

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

        ProduktInfo produktInfo;
        try {
            produktInfo = produktHanterare.hamtaProdukt(produktId);
        } catch (DatabasFel fel) {
            visaFelPaProduktsidan(request, response, fel.getMessage());
            return;
        }

        if (produktInfo == null) {
            visaFelPaProduktsidan(request, response, "Produkten finns inte.");
            return;
        }

        VarukorgInfo varukorgInfo = hamtaVarukorgInfo(session);
        VarukorgInfo nyVarukorgInfo = orderHanterare.laggTillIVarukorg(varukorgInfo, produktInfo, antal);
        if (nyVarukorgInfo == null) {
            visaFelPaProduktsidan(request, response, "Det finns bara " + produktInfo.getLagerAntal()
                    + " st av " + produktInfo.getNamn() + " i lager. Du kan inte ha fler än så i varukorgen.");
            return;
        }

        session.setAttribute("varukorgInfo", nyVarukorgInfo);

        response.sendRedirect(request.getContextPath() + "/varukorg");
    }

    private VarukorgInfo hamtaVarukorgInfo(HttpSession session) {
        VarukorgInfo varukorgInfo = (VarukorgInfo) session.getAttribute("varukorgInfo");
        if (varukorgInfo == null) {
            varukorgInfo = orderHanterare.skapaTomVarukorg();
            session.setAttribute("varukorgInfo", varukorgInfo);
        }
        return varukorgInfo;
    }

    private void visaFelPaProduktsidan(HttpServletRequest request, HttpServletResponse response,
                                       String felmeddelande) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("felmeddelande", felmeddelande);
        response.sendRedirect(request.getContextPath() + "/produkter");
    }
}
