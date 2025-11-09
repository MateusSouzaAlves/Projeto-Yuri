<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String ctx = request.getContextPath();
    String erro = (String) request.getAttribute("erro");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="<%= ctx %>/css/style.css">
    <style>
        body { font-family: Arial, sans-serif; background:#f5f5f5; display:flex; justify-content:center; align-items:center; height:100vh; }
        .login-container { background:#fff; border-radius:10px; padding:40px; box-shadow:0 4px 12px rgba(0,0,0,.1); width:350px; }
        h2 { text-align:center; margin-bottom:25px; }
        label { display:block; margin-bottom:5px; font-weight:bold; }
        input[type="email"], input[type="password"] { width:100%; padding:10px; margin-bottom:15px; border:1px solid #ccc; border-radius:5px; }
        button { width:100%; padding:10px; border:none; background:#007bff; color:#fff; font-weight:bold; border-radius:5px; cursor:pointer; }
        button:hover { background:#0056b3; }
        .erro { color:#c00; text-align:center; margin-bottom:10px; }
        .link-visitante { text-align:center; display:block; margin-top:15px; text-decoration:none; color:#333; }
        .link-visitante:hover { text-decoration:underline; }
    </style>
</head>
<body>
<div class="login-container">
    <h2>Login do Sistema</h2>

    <% if (erro != null && !erro.isEmpty()) { %>
        <div class="erro"><%= erro %></div>
    <% } %>

    <form method="post" action="<%= ctx %>/login">
        <label for="email">E-mail:</label>
        <input type="email" id="email" name="email" placeholder="Digite seu e-mail" required>

        <label for="senha">Senha:</label>
        <input type="password" id="senha" name="senha" placeholder="Digite sua senha" required>

        <button type="submit">Entrar</button>
    </form>

    <a href="<%= ctx %>/produtos" class="link-visitante">Entrar como visitante</a>
</div>
</body>
</html>
