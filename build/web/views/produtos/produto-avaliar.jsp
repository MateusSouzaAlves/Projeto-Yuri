<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Avaliar Produto</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<jsp:include page="../../includes/header.jsp"/>

<h2>Avaliar Produto</h2>

<form action="avaliar" method="post" enctype="multipart/form-data">
    <input type="hidden" name="idProduto" value="${param.idProduto}">

    <label>Nota (1 a 5):</label><br>
    <select name="nota" required>
        <option value="1">1 - Péssimo</option>
        <option value="2">2 - Ruim</option>
        <option value="3">3 - Regular</option>
        <option value="4">4 - Bom</option>
        <option value="5">5 - Excelente</option>
    </select><br><br>

    <label>Comentário:</label><br>
    <textarea name="comentario" rows="4" cols="40"></textarea><br><br>

    <label>Imagem (opcional):</label><br>
    <input type="file" name="imagem"><br><br>

    <button type="submit">Enviar Avaliação</button>
</form>

<br>
<a href="produto-listar.jsp">Voltar à lista</a>

<jsp:include page="../../includes/footer.jsp"/>
</body>
</html>
