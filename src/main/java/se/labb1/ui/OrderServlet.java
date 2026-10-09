package se.labb1.ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.labb1.bo.AnvandareInfo;
import se.labb1.bo.DatabasFel;
import se.labb1.bo.OrderHanterare;
import se.labb1.bo.VarukorgInfo;

import java.io.IOException;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    private OrderHanterare orderHanterare = new OrderHanterare();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("anvandare") == null) {
            response.sendRedirect(request.getContextPath() + "/loggain");
            return;
        }

        if (session.getAttribute("senasteOrderId") == null) {
            response.sendRedirect(request.getContextPath() + "/produkter");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/jsp/bekraftelse.jsp").forward(request, response);
        session.removeAttribute("senasteOrderId");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        AnvandareInfo anvandare = (AnvandareInfo) session.getAttribute("anvandare");
        if (anvandare == null) {
            response.sendRedirect(request.getContextPath() + "/loggain");
            return;
        }

        VarukorgInfo varukorgInfo = (VarukorgInfo) session.getAttribute("varukorgInfo");
        if (varukorgInfo == null || varukorgInfo.isTom()) {
            visaFelPaVarukorgssidan(request, response, "Varukorgen är tom.");
            return;
        }

        int orderId;
        try {
            orderId = orderHanterare.skickaOrder(anvandare, varukorgInfo);
        } catch (DatabasFel fel) {
            visaFelPaVarukorgssidan(request, response, fel.getMessage());
            return;
        }

        if (orderId == -1) {
            visaFelPaVarukorgssidan(request, response, "Logga in som kund och beställ tack!");
            return;
        }

        if (orderId == 0) {
            visaFelPaVarukorgssidan(request, response, "Ordern kunde inte skickas eftersom någon vara "
                    + "inte finns i tillräckligt antal i lager. Ingenting har dragits från lagret.");
            return;
        }

        VarukorgInfo tomVarukorg = orderHanterare.skapaTomVarukorg();
        session.setAttribute("varukorgInfo", tomVarukorg);
        session.setAttribute("senasteOrderId", orderId);

        response.sendRedirect(request.getContextPath() + "/order");
    }

    private void visaFelPaVarukorgssidan(HttpServletRequest request, HttpServletResponse response,
                                         String felmeddelande) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("felmeddelande", felmeddelande);
        response.sendRedirect(request.getContextPath() + "/varukorg");
    }
}
