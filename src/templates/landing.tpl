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
        <a href="https://www.strava.com/oauth/authorize?client_id=2792&redirect_uri=http://localhost:8080/login&response_type=code" class="connect_strava"></a>
        <!-- <img src="img/ConnectWithStrava.png" alt="Connect with Strava"/> -->
      <p>Your user agent is : {{ user-agent }}</p>

      <h3>Example list</h3>
      <ul>
        {{#list}}
          <li>{{ . }}</li>
        {{/list}}
      </ul>

    </div>
    <script src="/static/js/lib/jquery-1.9.1.js"></script>
    <script src="/static/js/lib/bootstrap.js"></script>
  </body>
</html>
