<!DOCTYPE html>
<html>
  <!-- http://mustache.github.com/mustache.5.html -->
  <head>
    <!-- partial is just like copy and paste the template here -->
    {{>partials/header}}
  </head>
  <body>
    {{>partials/navbar}}

    <div class="container">
      <a href="/activities?access-token={{access-token}}">Activities</a>
    </div>
    <script src="/static/js/lib/jquery-1.9.1.js"></script>
    <script src="/static/js/lib/bootstrap.js"></script>
  </body>
</html>
