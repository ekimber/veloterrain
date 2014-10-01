<!DOCTYPE html>
<html>
  <!-- http://mustache.github.com/mustache.5.html -->
  <head>
    <!-- partial is just like copy and paste the template here -->
     <link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.css" />

    {{>partials/header}}
  </head>
  <body>
    <script src="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.js"></script>
    {{>partials/navbar}}

    <div class="container">
      <h3>{{name}}</h3>
      <div id="map"></div>
    </div>

    <script>
      var map = L.map('map').setView({{map-centre}}, {{zoom-level}});
      L.tileLayer('http://{s}.tiles.mapbox.com/v3/edkimber.jka1j3p1/{z}/{x}/{y}.png', {
    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
    maxZoom: 18
}).addTo(map);
      var latlngs = {{activity-path}};
      var polyline = L.polyline(latlngs, {color: 'red'}).addTo(map);
    </script>
    <script src="/static/js/lib/jquery-1.9.1.js"></script>
    <script src="/static/js/lib/bootstrap.js"></script>
  </body>
</html>
