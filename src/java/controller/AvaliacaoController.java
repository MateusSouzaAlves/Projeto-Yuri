package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.AvaliacaoDAO;
import model.Avaliacao;

import java.io.IOException;
import java.util.List;

public class AvaliacaoController extends HttpServlet {

    private final AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final String path = request.getServletPath();

        try {
            switch (path) {
                case "/avaliacoes", "/avaliacoes/listar" -> {
                    String idProdutoParam = request.getParameter("idProduto");
                    if (idProdutoParam != null && !idProdutoParam.isBlank()) {
                        int idProduto = Integer.parseInt(idProdutoParam);
                        List<Avaliacao> lista = avaliacaoDAO.listarPorProduto(idProduto);
                        request.setAttribute("listaAvaliacoes", lista);
                        request.setAttribute("idProduto", idProduto);
                    } else {
                        List<Avaliacao> lista = avaliacaoDAO.listarTodas();
                        request.setAttribute("listaAvaliacoes", lista);
                    }
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/views/avaliacoes/avaliacao-listar.jsp");
                    dispatcher.forward(request, response);
                }
                case "/avaliacoes/novo" ->  {
                    String idProdutoParam = request.getParameter("idProduto");
                    if (idProdutoParam != null && !idProdutoParam.isBlank()) {
                        request.setAttribute("idProduto", Integer.valueOf(idProdutoParam));
                    }
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/views/avaliacoes/avaliacao-form.jsp");
                    dispatcher.forward(request, response);
                }
                case "/avaliacoes/editar" ->  {
                    String idAvaliacaoParam = request.getParameter("idAvaliacao");
                    if (idAvaliacaoParam == null || idAvaliacaoParam.isBlank()) {
                        response.sendRedirect(request.getContextPath() + "/avaliacoes");
                        return;
                    }
                    int idAvaliacao = Integer.parseInt(idAvaliacaoParam);
                    Avaliacao avaliacao = avaliacaoDAO.buscarPorId(idAvaliacao);
                    if (avaliacao == null) {
                        response.sendRedirect(request.getContextPath() + "/avaliacoes");
                        return;
                    }
                    request.setAttribute("avaliacao", avaliacao);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/views/avaliacoes/avaliacao-form.jsp");
                    dispatcher.forward(request, response);
                }
                default -> {
                    response.sendRedirect(request.getContextPath() + "/avaliacoes");
                }
            }
                    } catch (ServletException | IOException | NumberFormatException e) {
            throw new ServletException("Erro ao processar GET em " + path, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        final String path = request.getServletPath();

        try {
            switch (path) {
                case "/avaliacoes/salvar" ->  {
                    Avaliacao avaliacao = new Avaliacao();
                    avaliacao.setIdProduto(Integer.parseInt(request.getParameter("idProduto")));
                    avaliacao.setNomeUsuario(request.getParameter("nomeUsuario"));
                    avaliacao.setComentario(request.getParameter("comentario"));
                    avaliacao.setNota(Integer.parseInt(request.getParameter("nota")));

                    avaliacaoDAO.inserir(avaliacao);

                    response.sendRedirect(request.getContextPath() + "/avaliacoes/listar?idProduto=" + avaliacao.getIdProduto());
                }

                case "/avaliacoes/atualizar" ->  {
                    Avaliacao avaliacao = new Avaliacao();
                    avaliacao.setIdAvaliacao(Integer.parseInt(request.getParameter("idAvaliacao")));
                    avaliacao.setIdProduto(Integer.parseInt(request.getParameter("idProduto")));
                    avaliacao.setNomeUsuario(request.getParameter("nomeUsuario"));
                    avaliacao.setComentario(request.getParameter("comentario"));
                    avaliacao.setNota(Integer.parseInt(request.getParameter("nota")));

                    avaliacaoDAO.atualizar(avaliacao);

                    response.sendRedirect(request.getContextPath() + "/avaliacoes/listar?idProduto=" + avaliacao.getIdProduto());
                }

                case "/avaliacoes/excluir" ->  {
                    int idAvaliacao = Integer.parseInt(request.getParameter("idAvaliacao"));
                    String idProdutoParam = request.getParameter("idProduto");

                    avaliacaoDAO.excluir(idAvaliacao);

                    if (idProdutoParam != null && !idProdutoParam.isBlank()) {
                        response.sendRedirect(request.getContextPath() + "/avaliacoes/listar?idProduto=" + idProdutoParam);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/avaliacoes");
                    }
                }

                default -> {
                    response.sendRedirect(request.getContextPath() + "/avaliacoes");
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new ServletException("Erro ao processar POST em " + path, e);
        }
    }
}
