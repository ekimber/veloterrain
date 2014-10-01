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
      <h3>Activities</h3>
      {{#activities}}
        <p><a href="/activities/{{id}}?access-token={{access-token}}">{{name}}</a></p>
      {{/activities}}

    </div>
    <script src="/static/js/lib/jquery-1.9.1.js"></script>
    <script src="/static/js/lib/bootstrap.js"></script>
  </body>
</html>
