<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String ctx = request.getContextPath();
    java.util.List lista = (java.util.List) request.getAttribute("listaProdutos");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Lista de Produtos</title>
    <link rel="stylesheet" href="<%= ctx %>/css/style.css">
    <style>
        body { font-family: Arial, sans-serif; background:#f5f5f5; display:flex; flex-direction:column; align-items:center; padding:40px 0; }
        .container { background:#fff; border-radius:10px; padding:30px 40px; box-shadow:0 4px 12px rgba(0,0,0,0.1); width:90%; max-width:900px; }
        h2 { text-align:center; margin-bottom:25px; }
        table { width:100%; border-collapse:collapse; }
        th, td { padding:10px; border-bottom:1px solid #ddd; text-align:center; }
        th { background:#007bff; color:#fff; }
        tr:hover { background:#f1f1f1; }
        a { text-decoration:none; color:#007bff; margin:0 5px; }
        a:hover { text-decoration:underline; }
        .btn-novo { display:inline-block; background:#007bff; color:#fff; padding:10px 18px; border-radius:5px; text-decoration:none; margin-bottom:20px; }
        .btn-novo:hover { background:#0056b3; }
        .mensagem { text-align:center; color:#999; font-style:italic; }
    </style>
</head>
<body>

    <jsp:include page="../../includes/header.jsp" />

    <div class="container">
        <h2>Lista de Produtos</h2>

        <a href="<%= ctx %>/produtos/novo" class="btn-novo">Cadastrar Produto</a>

        <%
            if (lista == null || lista.isEmpty()) {
        %>
            <p class="mensagem">Nenhum produto cadastrado.</p>
        <%
            } else {
        %>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Quantidade</th>
                        <th>Valor</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    for (Object obj : lista) {
                        model.Produto p = (model.Produto) obj;
                %>
                    <tr>
                        <td><%= p.getIdProduto() %></td>
                        <td><%= p.getNomeProduto() %></td>
                        <td><%= p.getQtdProduto() %></td>
                        <td>R$ <%= String.format("%.2f", p.getValorProduto()) %></td>
                        <td>
                            <a href="<%= ctx %>/produtos/editar?id=<%= p.getIdProduto() %>">Editar</a> |
                            <a href="<%= ctx %>/produtos/excluir?id=<%= p.getIdProduto() %>"
                               onclick="return confirm('Tem certeza que deseja excluir este produto?');">Excluir</a> |
                            <a href="<%= ctx %>/avaliacoes/listar?idProduto=<%= p.getIdProduto() %>">Avaliações</a>
                        </td>
                    </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        <%
            }
        %>
    </div>

    <jsp:include page="../../includes/footer.jsp" />

</body>
</html>
