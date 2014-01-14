<!DOCTYPE html>

<%@page import="com.crs.vonebd.apiclient.example.GettingStartedDriver"%>
<html>
<head>

<title>Scrum Subsystem Burndown</title>
<script class="include" type="text/javascript" src="../jquery.min.js"></script>

</head>
<body>
	<div>
		<h3>Subsystem wise burndown charts.</h3>
	</div>

	<div class="colmaskX leftmenuX">
		<div class="colleftX">
			<div class="col1X" id="example-contentX">


				<script class="codeX" type="text/javascript">
				$(document).ready(function(){
					

					var val2 = JSON.parse('<%=GettingStartedDriver.getStories()%>');
					
					function plotChart(val1) {
					
						var div1 =  $( "<div/>", {
								"id": val1.name,
								"style":"height:400px; width:700px;",			
								}).appendTo( "body");
						
						$.jqplot(val1.name, [val1.work, val1.days, val1.idealWork], 
							{ 
							  title: val1.name + ' Subsystem Burndown', 
							  // Set default options on all series, turn on smoothing.
							  seriesDefaults: {
								  rendererOptions: {
									  smooth: true
								  }
							  },
							  // Series options are specified as an array of objects, one object
							  // for each series.
							  series:[ 
								  {
									// Change our line width and use a diamond shaped marker.
									lineWidth:2, 
									markerOptions: { size:0}
								  }, 
								  {
									// Don't show a line, just show markers.
									// Make the markers 7 pixels with an 'x' style
									showLine:false, 
									markerOptions: { size: 0, style:"none" }
								  },
								  { 
									// Use (open) circlular markers.
									markerOptions: { size:0 }
								  }
							  ]
							}
						  );
				  };
				  

					 $(val2).each(function(index, value){
						plotChart(value);
					 });

				   
				});
					
				</script>


				<!-- Don't touch this! -->
				<link class="include" rel="stylesheet" type="text/css"
					href="../jquery.jqplot.min.css" />
				<script class="include" type="text/javascript"
					src="../jquery.jqplot.min.js"></script>

				<!-- End Don't touch this! -->

				<!-- Additional plugins go here -->

				<script class="include" type="text/javascript"
					src="../plugins/jqplot.canvasTextRenderer.min.js"></script>
				<script class="include" type="text/javascript"
					src="../plugins/jqplot.canvasAxisLabelRenderer.min.js"></script>

				<!-- End additional plugins -->

			</div>

		</div>
	</div>


</body>


</html>
