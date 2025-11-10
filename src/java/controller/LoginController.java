package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import dao.UsuarioDAO;
import model.Usuario;
import java.io.IOException;

public class LoginController extends HttpServlet {

@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
    String path = req.getServletPath();

    if (path.equals("/logout")) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate(); 
        }
        resp.sendRedirect("login"); 
    } else {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/login/login.jsp");
        dispatcher.forward(req, resp);
    }
}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        UsuarioDAO dao = new UsuarioDAO();
        Usuario user = dao.autenticar(email, senha);

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("usuarioLogado", user);
            resp.sendRedirect("produtos");
        } else {
            req.setAttribute("erro", "Email ou senha inválidos");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/views/login/login.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
