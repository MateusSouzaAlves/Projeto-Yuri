<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String ctx = request.getContextPath();
    java.util.List lista = (java.util.List) request.getAttribute("listaAvaliacoes");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Avaliações</title>
    <link rel="stylesheet" href="<%= ctx %>/css/style.css">
    <style>
        body { font-family: Arial, sans-serif; background:#f5f5f5; display:flex; flex-direction:column; align-items:center; padding:40px 0; }
        .container { background:#fff; border-radius:10px; padding:30px 40px; box-shadow:0 4px 12px rgba(0,0,0,0.1); width:90%; max-width:900px; }
        h2 { text-align:center; margin-bottom:25px; }
        table { width:100%; border-collapse:collapse; }
        th, td { padding:10px; border-bottom:1px solid #ddd; text-align:center; }
        th { background:#007bff; color:#fff; }
        tr:hover { background:#f1f1f1; }
        img { border-radius:6px; }
        .btn-voltar { display:inline-block; background:#007bff; color:#fff; padding:10px 18px; border-radius:5px; text-decoration:none; margin-top:20px; }
        .btn-voltar:hover { background:#0056b3; }
        .mensagem { text-align:center; color:#999; font-style:italic; }
    </style>
</head>
<body>

    <jsp:include page="../../includes/header.jsp"/>

    <div class="container">
        <h2>Avaliações</h2>

        <%
            if (lista == null || lista.isEmpty()) {
        %>
            <p class="mensagem">Nenhuma avaliação encontrada.</p>
        <%
            } else {
        %>
            <table>
                <thead>
                    <tr>
                        <th>Usuário</th>
                        <th>Nota</th>
                        <th>Comentário</th>
                        <th>Imagem</th>
                        <th>Produto</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    for (Object obj : lista) {
                        model.Avaliacao av = (model.Avaliacao) obj;
                        String imagem = av.getImagem();
                        String nomeProduto = null;
                        try { // caso o DAO preencha (listarTodas)
                            java.lang.reflect.Method m = av.getClass().getMethod("getNomeProduto");
                            Object v = m.invoke(av);
                            nomeProduto = (v != null ? v.toString() : null);
                        } catch (Exception ignore) {}
                %>
                    <tr>
                        <td><%= av.getNomeUsuario() %></td>
                        <td><%= av.getNota() %></td>
                        <td><%= av.getComentario() %></td>
                        <td>
                            <%
                                if (imagem != null && !imagem.isEmpty()) {
                            %>
                                <img src="<%= ctx %>/uploads/<%= imagem %>" width="80" height="80">
                            <%
                                } else {
                            %>
                                <span style="color:#aaa;">Sem imagem</span>
                            <%
                                }
                            %>
                        </td>
                        <td><%= (nomeProduto != null && !nomeProduto.isEmpty()) ? nomeProduto : ("#"+av.getIdProduto()) %></td>
                    </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        <%
            }
        %>

        <a href="<%= ctx %>/produtos" class="btn-voltar">Voltar aos Produtos</a>
    </div>

    <jsp:include page="../../includes/footer.jsp"/>

</body>
</html>
