<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String ctx = request.getContextPath();
    model.Produto produto = (model.Produto) request.getAttribute("produto");
    Integer idProduto = (produto != null) ? produto.getIdProduto() : null;
    String nomeProduto = (produto != null) ? produto.getNomeProduto() : "";
    Integer qtdProduto = (produto != null) ? produto.getQtdProduto() : null;
    Float valorProduto = (produto != null) ? produto.getValorProduto() : null;
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title><%= (produto == null ? "Cadastrar" : "Editar") %> Produto</title>
    <link rel="stylesheet" href="<%= ctx %>/css/style.css">
    <style>
        body { font-family: Arial, sans-serif; background:#f5f5f5; display:flex; flex-direction:column; align-items:center; padding:40px 0; }
        .container { background:#fff; border-radius:10px; padding:30px 40px; box-shadow:0 4px 12px rgba(0,0,0,.1); width:90%; max-width:600px; }
        h2 { text-align:center; margin-bottom:25px; }
        label { display:block; margin-top:12px; font-weight:bold; }
        input[type="text"], input[type="number"] { width:100%; padding:10px; border:1px solid #ccc; border-radius:6px; margin-top:6px; }
        .actions { margin-top:20px; display:flex; gap:10px; justify-content:center; }
        .btn { padding:10px 16px; border:none; border-radius:6px; cursor:pointer; }
        .btn-primary { background:#007bff; color:#fff; }
        .btn-primary:hover { background:#0056b3; }
        .btn-secondary { background:#e0e0e0; }
        .btn-secondary:hover { background:#cfcfcf; }
    </style>
</head>
<body>

    <jsp:include page="../../includes/header.jsp" />

    <div class="container">
        <h2><%= (produto == null ? "Cadastrar" : "Editar") %> Produto</h2>

        <form method="post" action="<%= ctx %>/produtos/salvar">
            <% if (idProduto != null) { %>
                <input type="hidden" name="idProduto" value="<%= idProduto %>">
            <% } %>

            <label for="nomeProduto">Descrição</label>
            <input type="text" id="nomeProduto" name="nomeProduto" value="<%= nomeProduto %>" required>

            <label for="qtdProduto">Quantidade</label>
            <input type="number" id="qtdProduto" name="qtdProduto" value="<%= (qtdProduto == null ? "" : qtdProduto) %>" required>

            <label for="valorProduto">Valor</label>
            <input type="text" id="valorProduto" name="valorProduto" value="<%= (valorProduto == null ? "" : String.format(java.util.Locale.US, "%.2f", valorProduto)) %>" required>

            <div class="actions">
                <button type="submit" class="btn btn-primary">Salvar</button>
                <button type="button" class="btn btn-secondary" onclick="window.location.href='<%= ctx %>/produtos'">Cancelar</button>
            </div>
        </form>
    </div>

    <jsp:include page="../../includes/footer.jsp" />

</body>
</html>
