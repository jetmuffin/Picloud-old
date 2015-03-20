<!DOCTYPE html>
<html>
<head>
  <title></title>
  <link rel="stylesheet" type="text/css" href="${RESOURCES}/css/hdview.css">
</head>
<body>
  <div class="bg-wrapper">
    <div id="hd_container"  data-url="${PLUGIN}/seadragon/"  data-pic="${hdImage.name}" ></div>
	<div id="url_base" data-url="${IP}${ROOT}"></div>
  </div>
    <script type="text/javascript" src="${RESOURCES}/js/jquery-1.11.1.min.js"></script>
  <script type="text/javascript" src="${PLUGIN}/seadragon/seadragon-min.js"></script>

  <script type="text/javascript">
    $(document).ready(function(){
      var height = $(window).height();
      $('body').height(height);
      //高清展示
      var viewer = new Seadragon.Viewer("hd_container");
      var uid = $('#hd_container').attr('data-uid');
      var pic = $('#hd_container').attr('data-pic');
      var url_base = $('#url_base').attr('data-url');
      var url_hd = url_base + "/hd/read?fileName=" +pic + ".dzi";
      console.log(url_hd);

       viewer.openDzi(url_hd); 
    });

  </script>
</body>
</html>
