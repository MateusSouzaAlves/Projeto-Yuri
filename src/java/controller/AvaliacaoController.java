package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import dao.AvaliacaoDAO;
import model.Avaliacao;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AvaliacaoController", urlPatterns = {"/avaliacoes","/avaliacoes/listar","/avaliacoes/salvar"})
public class AvaliacaoController extends HttpServlet {

    private final AvaliacaoDAO dao = new AvaliacaoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();

        try {
            switch (path) {
                case "/avaliacoes/listar" ->  {
                    String idParam = req.getParameter("idProduto");
                    if (idParam == null || idParam.isEmpty()) {
                        resp.sendRedirect(req.getContextPath() + "/avaliacoes");
                        return;
                    }

                    int idProduto = Integer.parseInt(idParam);
                    List<Avaliacao> lista = dao.listarPorProduto(idProduto);
                    req.setAttribute("listaAvaliacoes", lista);
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/views/avaliacoes/avaliacao-listar.jsp");
                    dispatcher.forward(req, resp);
                }

                case "/avaliacoes" ->  {
                    List<Avaliacao> lista = dao.listarTodas();
                    req.setAttribute("listaAvaliacoes", lista);
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/views/avaliacoes/avaliacao-listar.jsp");
                    dispatcher.forward(req, resp);
                }

                default ->  {
                    resp.sendRedirect(req.getContextPath() + "/produtos");
                }
            }
        } catch (ServletException | IOException | NumberFormatException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            Avaliacao a = new Avaliacao();
            a.setIdProduto(Integer.parseInt(req.getParameter("idProduto")));
            a.setNomeUsuario(req.getParameter("nomeUsuario"));
            a.setComentario(req.getParameter("comentario"));
            a.setNota(Integer.parseInt(req.getParameter("nota")));

            dao.inserir(a);
            resp.sendRedirect(req.getContextPath() + "/avaliacoes/listar?idProduto=" + a.getIdProduto());
        } catch (IOException | NumberFormatException e) {
            throw new ServletException(e);
        }
    }
}
