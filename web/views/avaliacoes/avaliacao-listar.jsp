<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <title>Avaliações</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <style>
        body { font-family: Arial, sans-serif; background: #f8f9fa; margin: 0; padding: 0; }
        .container { max-width: 1000px; margin: 40px auto; background: #fff; padding: 24px 28px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,.1); }
        h2 { margin: 0 0 12px 0; }
        .actions { margin: 12px 0 20px; }
        .btn { display: inline-block; padding: 8px 14px; border-radius: 6px; text-decoration: none; border: 1px solid #007bff; }
        .btn-primary { background: #007bff; color: #fff; }
        .btn-outline { background: #fff; color: #007bff; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { text-align: left; padding: 10px; border-bottom: 1px solid #eaeaea; }
        th { background: #007bff; color: #fff; }
        .right { text-align: right; }
        .muted { color: #777; }
        form.inline { display: inline; margin: 0; padding: 0; }
    </style>
</head>
<body>

<jsp:include page="/includes/header.jsp"/>

<div class="container">
    <c:choose>
        <c:when test="${not empty idProduto}">
            <h2>Avaliações do Produto #${idProduto}</h2>
        </c:when>
        <c:otherwise>
            <h2>Todas as Avaliações</h2>
        </c:otherwise>
    </c:choose>

    <div class="actions">
        <c:choose>
            <c:when test="${not empty idProduto}">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/avaliacoes/novo?idProduto=${idProduto}">Nova Avaliação</a>
                &nbsp;
                <a class="btn btn-outline" href="${pageContext.request.contextPath}/produtos">Voltar aos Produtos</a>
            </c:when>
            <c:otherwise>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/avaliacoes/novo">Nova Avaliação</a>
                &nbsp;
                <a class="btn btn-outline" href="${pageContext.request.contextPath}/produtos">Voltar aos Produtos</a>
            </c:otherwise>
        </c:choose>
    </div>

    <c:if test="${empty listaAvaliacoes}">
        <p class="muted">Nenhuma avaliação encontrada.</p>
    </c:if>

    <c:if test="${not empty listaAvaliacoes}">
        <table>
            <thead>
            <tr>
                <th>#</th>
                <th>Produto</th>
                <th>Usuário</th>
                <th>Nota</th>
                <th>Comentário</th>
                <th class="right">Ações</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="av" items="${listaAvaliacoes}">
                <tr>
                    <td>${av.idAvaliacao}</td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty av.nomeProduto}">
                                ${av.nomeProduto} (ID: ${av.idProduto})
                            </c:when>
                            <c:otherwise>
                                Produto ID: ${av.idProduto}
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${av.nomeUsuario}</td>
                    <td>${av.nota}</td>
                    <td>${av.comentario}</td>
                    <td class="right">
                        <a class="btn btn-outline" href="${pageContext.request.contextPath}/avaliacoes/editar?idAvaliacao=${av.idAvaliacao}">Editar</a>
                        &nbsp;
                        <form class="inline" method="post" action="${pageContext.request.contextPath}/avaliacoes/excluir"
                              onsubmit="return confirm('Confirma excluir a avaliação #${av.idAvaliacao}?');">
                            <input type="hidden" name="idAvaliacao" value="${av.idAvaliacao}"/>
                            <input type="hidden" name="idProduto" value="${not empty idProduto ? idProduto : av.idProduto}"/>
                            <button type="submit" class="btn btn-outline">Excluir</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

<jsp:include page="/includes/footer.jsp"/>

</body>
</html>
