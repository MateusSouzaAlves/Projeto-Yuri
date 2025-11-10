<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <title>
        <c:choose>
            <c:when test="${not empty avaliacao}">Editar Avaliação #${avaliacao.idAvaliacao}</c:when>
            <c:otherwise>Nova Avaliação</c:otherwise>
        </c:choose>
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <style>
        body { font-family: Arial, sans-serif; background: #f8f9fa; margin: 0; padding: 0; }
        .container { max-width: 720px; margin: 40px auto; background: #fff; padding: 24px 28px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,.1); }
        h2 { margin-top: 0; }
        .form-group { margin-bottom: 16px; }
        label { display: block; font-weight: bold; margin-bottom: 6px; }
        input[type="text"], input[type="number"], textarea, select {
            width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 6px; box-sizing: border-box;
        }
        textarea { min-height: 120px; resize: vertical; }
        .actions { margin-top: 18px; display: flex; gap: 10px; flex-wrap: wrap; }
        .btn { appearance: none; border: 1px solid #007bff; padding: 10px 16px; border-radius: 6px; text-decoration: none; cursor: pointer; }
        .btn-primary { background: #007bff; color: #fff; }
        .btn-outline { background: #fff; color: #007bff; }
        .muted { color: #777; font-size: 0.9em; }
    </style>
</head>
<body>

<jsp:include page="/includes/header.jsp"/>

<div class="container">
    <h2>
        <c:choose>
            <c:when test="${not empty avaliacao}">Editar Avaliação #${avaliacao.idAvaliacao}</c:when>
            <c:otherwise>Nova Avaliação</c:otherwise>
        </c:choose>
    </h2>

    <c:set var="ctx" value="${pageContext.request.contextPath}"/>

    <!-- Se 'avaliacao' existir no request, estamos editando; caso contrário, criando -->
    <c:choose>
        <c:when test="${not empty avaliacao}">
            <form method="post" action="${ctx}/avaliacoes/atualizar" accept-charset="UTF-8">
                <input type="hidden" name="idAvaliacao" value="${avaliacao.idAvaliacao}"/>

                <div class="form-group">
                    <label for="campoIdProduto">ID do Produto</label>
                    <input id="campoIdProduto" type="number" name="idProduto" value="${avaliacao.idProduto}" required/>
                    <div class="muted">Mantenha o mesmo ID do produto ao qual a avaliação pertence.</div>
                </div>

                <div class="form-group">
                    <label for="campoNomeUsuario">Nome do Usuário</label>
                    <input id="campoNomeUsuario" type="text" name="nomeUsuario" value="${avaliacao.nomeUsuario}" maxlength="100" required/>
                </div>

                <div class="form-group">
                    <label for="campoNota">Nota</label>
                    <select id="campoNota" name="nota" required>
                        <c:forEach var="n" begin="1" end="5">
                            <option value="${n}" <c:if test="${avaliacao.nota == n}">selected</c:if>>${n}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="campoComentario">Comentário</label>
                    <textarea id="campoComentario" name="comentario" maxlength="1000" required>${avaliacao.comentario}</textarea>
                </div>

                <div class="actions">
                    <button type="submit" class="btn btn-primary">Salvar alterações</button>
                    <a class="btn btn-outline" href="${ctx}/avaliacoes/listar?idProduto=${avaliacao.idProduto}">Cancelar</a>
                </div>
            </form>
        </c:when>

        <c:otherwise>
            <form method="post" action="${ctx}/avaliacoes/salvar" accept-charset="UTF-8">
                <div class="form-group">
                    <label for="campoIdProdutoNovo">ID do Produto</label>
                    <input id="campoIdProdutoNovo" type="number" name="idProduto"
                           value="<c:out value='${idProduto}' default=''/>"
                           required/>
                    <div class="muted">Se você veio da lista por produto, o ID já aparece preenchido.</div>
                </div>

                <div class="form-group">
                    <label for="campoNomeUsuarioNovo">Nome do Usuário</label>
                    <input id="campoNomeUsuarioNovo" type="text" name="nomeUsuario" maxlength="100" required/>
                </div>

                <div class="form-group">
                    <label for="campoNotaNovo">Nota</label>
                    <select id="campoNotaNovo" name="nota" required>
                        <c:forEach var="n" begin="1" end="5">
                            <option value="${n}">${n}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="campoComentarioNovo">Comentário</label>
                    <textarea id="campoComentarioNovo" name="comentario" maxlength="1000" required></textarea>
                </div>

                <div class="actions">
                    <button type="submit" class="btn btn-primary">Salvar</button>
                    <c:choose>
                        <c:when test="${not empty idProduto}">
                            <a class="btn btn-outline" href="${ctx}/avaliacoes/listar?idProduto=${idProduto}">Cancelar</a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-outline" href="${ctx}/avaliacoes">Cancelar</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </form>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/includes/footer.jsp"/>

</body>
</html>
