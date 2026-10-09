package se.labb1.ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.labb1.bo.AnvandarHanterare;
import se.labb1.bo.AnvandareInfo;
import se.labb1.bo.DatabasFel;

import java.io.IOException;

@WebServlet("/loggain")
public class LoggaInServlet extends HttpServlet {

    private AnvandarHanterare anvandarHanterare = new AnvandarHanterare();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        visaInloggningssidan(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String anvandarnamn = request.getParameter("anvandarnamn");
        String losenord = request.getParameter("losenord");

        if (anvandarnamn == null || losenord == null || anvandarnamn.isEmpty() || losenord.isEmpty()) {
            request.setAttribute("felmeddelande", "Fyll i både användarnamn och lösenord.");
            visaInloggningssidan(request, response);
            return;
        }

        AnvandareInfo anvandare;
        try {
            anvandare = anvandarHanterare.loggaIn(anvandarnamn, losenord);
        } catch (DatabasFel fel) {
            request.setAttribute("felmeddelande", fel.getMessage());
            visaInloggningssidan(request, response);
            return;
        }

        if (anvandare == null) {
            request.setAttribute("felmeddelande", "Fel användarnamn eller lösenord.");
            visaInloggningssidan(request, response);
            return;
        }

        request.getSession().invalidate();
        HttpSession session = request.getSession();
        session.setAttribute("anvandare", anvandare);

        response.sendRedirect(request.getContextPath() + "/produkter");
    }

    private void visaInloggningssidan(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }
}
