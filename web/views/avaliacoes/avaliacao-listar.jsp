<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <title>Lista de Avaliações</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f8f9fa;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 900px;
            margin: 40px auto;
            background: white;
            padding: 25px 30px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            text-align: center;
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }
        th {
            background: #007bff;
            color: white;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        a {
            text-decoration: none;
            color: #007bff;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<jsp:include page="/includes/header.jsp"/>

<div class="container">
    <h2>Avaliações</h2>

    <c:if test="${empty listaAvaliacoes}">
        <p style="text-align:center; color:#777;">Nenhuma avaliação encontrada.</p>
    </c:if>

    <c:if test="${not empty listaAvaliacoes}">
        <table>
            <thead>
                <tr>
                    <th>Usuário</th>
                    <th>Nota</th>
                    <th>Comentário</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="av" items="${listaAvaliacoes}">
                    <tr>
                        <td>${av.nomeUsuario}</td>
                        <td>${av.nota}</td>
                        <td>${av.comentario}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <br>
    <div style="text-align:center;">
        <a href="<%= request.getContextPath() %>/produtos">Voltar aos produtos</a>
    </div>
</div>

<jsp:include page="/includes/footer.jsp"/>
</body>
</html>
