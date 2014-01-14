
<!DOCTYPE html>





<%@page import="com.crs.scrum.impl.vone.ScrumVOneImpl"%>
<%@page import="com.crs.scrum.impl.BurndownUtil"%>
<html>
<head>

<title>Scrum Burndown</title>
<script class="include" type="text/javascript" src="jquery.min.js"></script>
   
</head>
<body>
<div class="page-header">
  <h1>Example page header <small>Subtext for header TODO</small></h1>
</div>


	<div class="panel-body container">
	<div class="row">
			<div class="col-md-4">TODO</div>
 
			<div class="col-md-4" id="my-pane">

				<script class="codeX" type="text/javascript">
				$(document).ready(function(){

					var val2 = JSON.parse('<%=BurndownUtil.jsonify(new ScrumVOneImpl().getAllSprints())%>');
					
					var anchor = $( "<a/>");
					
					var sdiv = $( "<table >", {"class" : "table table-striped","style":"width:300px;"})
					sdiv.appendTo( "#my-pane");
					
					$( "<a/>", {
							"id": "tabhead",
							"href": "#",
							"class":"btn btn-lg inactive btn-block btn-warning",	
							"role": "label"
							}).text("Chose Sprint to see Burndown").append("<th/>").appendTo( sdiv);

					 $(val2).each(function(index, value){
						 
						 anchor = $( "<a/>", {
								"id": value.name,
								"href": "showburndowns.jsp?sprint=" + JSON.stringify(value),
								"class":"btn btn-lg active btn-block " + (value.status==='CLSD' ? "btn-default" : "btn-success"),	
								"role": "button"
								}).text(value.name).append("<td/>").appendTo( sdiv);
					 });

				   
				});
					
				</script>


				<link  rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
				
				<script src="bootstrap/js/bootstrap.min.js"></script>
				
				<!-- End additional plugins -->

			</div>
			
			 <div class="col-md-4">TODO</div>

		</div>
	</div>

<div class="panel-footer">Panel footer TODO</div>
</body>


</html>
