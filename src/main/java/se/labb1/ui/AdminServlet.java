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
import se.labb1.bo.Roll;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private AnvandarHanterare anvandarHanterare = new AnvandarHanterare();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!arAdmin(request, response)) {
            return;
        }

        HttpSession session = request.getSession();
        String felmeddelande = (String) session.getAttribute("felmeddelande");
        if (felmeddelande != null) {
            request.setAttribute("felmeddelande", felmeddelande);
            session.removeAttribute("felmeddelande");
        }

        try {
            ArrayList<AnvandareInfo> allaAnvandare = anvandarHanterare.hamtaAllaAnvandare();
            request.setAttribute("allaAnvandare", allaAnvandare);
        } catch (DatabasFel fel) {
            request.setAttribute("felmeddelande", fel.getMessage());
        }

        request.setAttribute("roller", Roll.values());

        request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!arAdmin(request, response)) {
            return;
        }

        String atgard = request.getParameter("atgard");
        String felmeddelande;
        if ("laggTill".equals(atgard)) {
            felmeddelande = laggTillAnvandare(request);
        } else if ("andraRoll".equals(atgard)) {
            felmeddelande = andraRoll(request);
        } else if ("taBort".equals(atgard)) {
            felmeddelande = taBortAnvandare(request);
        } else {
            felmeddelande = "Okänd åtgärd.";
        }

        if (felmeddelande != null) {
            request.getSession().setAttribute("felmeddelande", felmeddelande);
        }

        response.sendRedirect(request.getContextPath() + "/admin");
    }

    private boolean arAdmin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        AnvandareInfo anvandare = (AnvandareInfo) session.getAttribute("anvandare");

        if (anvandare == null) {
            response.sendRedirect(request.getContextPath() + "/loggain");
            return false;
        }

        if (!anvandare.isAdmin()) {
            session.setAttribute("felmeddelande", "Du har inte behörighet att öppna adminsidan.");
            response.sendRedirect(request.getContextPath() + "/produkter");
            return false;
        }

        return true;
    }

    private String laggTillAnvandare(HttpServletRequest request) {
        String anvandarnamn = request.getParameter("anvandarnamn");
        String losenord = request.getParameter("losenord");
        Roll roll = lasRoll(request.getParameter("roll"));

        if (anvandarnamn == null || losenord == null || anvandarnamn.isEmpty() || losenord.isEmpty()) {
            return "Fyll i både användarnamn och lösenord.";
        }
        if (roll == null) {
            return "Välj en giltig roll.";
        }

        try {
            anvandarHanterare.laggTillAnvandare(anvandarnamn, losenord, roll);
        } catch (DatabasFel fel) {
            return fel.getMessage();
        }
        return null;
    }

    private String andraRoll(HttpServletRequest request) {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException fel) {
            return "Användaren finns inte.";
        }

        Roll roll = lasRoll(request.getParameter("roll"));
        if (roll == null) {
            return "Välj en giltig roll.";
        }

        AnvandareInfo inloggad = (AnvandareInfo) request.getSession().getAttribute("anvandare");
        if (id == inloggad.getId()) {
            return "Du kan inte ändra din egen roll.";
        }

        try {
            anvandarHanterare.andraRoll(id, roll);
        } catch (DatabasFel fel) {
            return fel.getMessage();
        }
        return null;
    }

    private String taBortAnvandare(HttpServletRequest request) {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException fel) {
            return "Användaren finns inte.";
        }

        AnvandareInfo inloggad = (AnvandareInfo) request.getSession().getAttribute("anvandare");
        if (id == inloggad.getId()) {
            return "Du kan inte ta bort dig själv.";
        }

        try {
            anvandarHanterare.taBortAnvandare(id);
        } catch (DatabasFel fel) {
            return fel.getMessage();
        }
        return null;
    }

    private Roll lasRoll(String text) {
        Roll[] roller = Roll.values();
        for (int i = 0; i < roller.length; i = i + 1) {
            if (roller[i].name().equals(text)) {
                return roller[i];
            }
        }
        return null;
    }
}
