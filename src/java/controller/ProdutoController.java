package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.ProdutoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Produto;

@WebServlet(name = "ProdutoController", urlPatterns = {"/produtos","/produtos/novo","/produtos/salvar","/produtos/editar","/produtos/excluir","/produtos/avaliar"})
public class ProdutoController extends HttpServlet {
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        try {
            switch (path) {
                case "/produtos/novo" ->  {
                    RequestDispatcher form = request.getRequestDispatcher("/views/produtos/produto-cadastro.jsp");
                    form.forward(request, response);
                }
                case "/produtos/editar" ->  {
                    editarForm(request, response);
                }
                case "/produtos/excluir" ->  {
                    excluir(request, response);
                }
                case "/produtos/avaliar" ->  {
                    RequestDispatcher avaliacao = request.getRequestDispatcher("/views/produtos/produto-avaliar.jsp");
                    avaliacao.forward(request, response);
                }
                default ->  {
                    listar(request, response);
                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        try {
            if ("/produtos/salvar".equals(path)) {
                salvar(request, response);
            } else {
                doGet(request, response);
            }
        } catch (ServletException | IOException | SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        ArrayList<Produto> lista = produtoDAO.listar();
        request.setAttribute("listaProdutos", lista);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/produtos/produto-listar.jsp");
        dispatcher.forward(request, response);
    }

    private void salvar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String idStr = request.getParameter("idProduto");
        String nome = request.getParameter("nomeProduto");
        String qtdStr = request.getParameter("qtdProduto");
        String valorStr = request.getParameter("valorProduto");

        int qtd = 0;
        if (qtdStr != null && !qtdStr.isEmpty()) {
            qtd = Integer.parseInt(qtdStr);
        }

        float valor = 0f;
        if (valorStr != null && !valorStr.isEmpty()) {
            valorStr = valorStr.replace(",", ".");
            valor = Float.parseFloat(valorStr);
        }

        if (idStr != null && !idStr.isEmpty()) {
     
            int id = Integer.parseInt(idStr);
            Produto p = new Produto(id, nome, qtd, valor);
            produtoDAO.atualizar(p);
        } else {
            Produto p = new Produto(nome, qtd, valor);
            produtoDAO.inserir(p);
        }

        response.sendRedirect(request.getContextPath() + "/produtos");
    }

    private void editarForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/produtos");
            return;
        }
        int id = Integer.parseInt(idParam);
        Produto p = produtoDAO.buscarPorId(id);
        if (p == null) {
            response.sendRedirect(request.getContextPath() + "/produtos");
            return;
        }
        request.setAttribute("produto", p);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/produtos/produto-cadastro.jsp");
        dispatcher.forward(request, response);
    }

    private void excluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);
            produtoDAO.excluir(id);
        }
        response.sendRedirect(request.getContextPath() + "/produtos");
    }
}
