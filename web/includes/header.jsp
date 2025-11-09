<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String ctx = request.getContextPath();
    HttpSession s = request.getSession(false);
    boolean logged = (s != null && s.getAttribute("usuarioLogado") != null);
%>
<header>
  <nav class="navbar">
    <div class="navbar-brand">
      <a href="<%= ctx %>/produtos" class="logo">CRUD Produtos</a>
    </div>

    <ul class="navbar-links">
      <% if (logged) { %>
        <li><a href="<%= ctx %>/produtos">Início</a></li>
        <li><a href="<%= ctx %>/produtos/novo">Cadastrar</a></li>
        <li><a href="<%= ctx %>/avaliacoes">Avaliações</a></li>
      <% } else { %>
        <li><a href="<%= ctx %>/login">Entrar</a></li>
      <% } %>
    </ul>

    <div class="navbar-actions">
      <% if (logged) { %>
        <a class="btn-logout" href="<%= ctx %>/logout">Sair</a>
      <% } %>
    </div>
  </nav>
</header>

<style>
  header { width:100%; background:#007bff; color:#fff; box-shadow:0 2px 10px rgba(0,0,0,.15); margin-bottom:30px; }
  .navbar { max-width:1200px; margin:0 auto; padding:12px 20px; display:flex; align-items:center; gap:16px; }
  .navbar-brand { flex:1 1 auto; }
  .logo { font-size:1.2rem; font-weight:700; color:#fff; text-decoration:none; letter-spacing:.4px; }
  .navbar-links { list-style:none; display:flex; gap:22px; margin:0; padding:0; }
  .navbar-links a { color:#fff; text-decoration:none; font-weight:500; padding-bottom:3px; transition:all .2s ease; }
  .navbar-links a:hover { border-bottom:2px solid #fff; }
  .navbar-actions { margin-left:auto; display:flex; align-items:center; }
  .btn-logout { background:#ff4d4f; color:#fff; text-decoration:none; padding:8px 14px; border-radius:6px; font-weight:600; transition:filter .2s ease; }
  .btn-logout:hover { filter:brightness(.95); }
  @media (max-width:760px) { .navbar { flex-wrap:wrap; } .navbar-links { width:100%; justify-content:center; margin-top:8px; } .navbar-actions { width:100%; justify-content:center; margin-top:8px; } }
</style>
