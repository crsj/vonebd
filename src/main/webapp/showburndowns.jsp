<!DOCTYPE html>

<%@page import="com.crs.scrum.impl.BurndownUtil"%>
<%@page import="com.crs.vone.vo.Sprint"%>
<%@page import="com.crs.vonebd.apiclient.example.GettingStartedDriver"%>
<html>
<head>

<title>Scrum Burndown</title>
<script class="include" type="text/javascript" src="jquery.min.js"></script>

</head>
<body>

	<div>
		<h3>Story/Subsystem(s) wise burndowns.(&#946;)</h3>
		(Just for fun!)(Build: Verion 1.2)
	</div>

	<%
		Sprint sprint = (Sprint) BurndownUtil.jsonToJava(request
				.getParameter("sprint").replaceAll(" 000", "+000"),
				Sprint.class);
	%>

	<div class="colmaskX leftmenuX">
		<div class="colleftX">
			<div class="col1X" id="example-contentX">

				<script class="codeX" type="text/javascript">
				$(document).ready(function(){
					
					var val2 = JSON.parse('<%=GettingStartedDriver.getStories(sprint)%>');

					function plotChart(val1) {
	
						if (val1.name === null || val1.name === "")
							return;
						
						var div1 =  $( "<div/>", {
								"id": val1.name,
								"style":"height:500px; width:800px;",			
								}).appendTo( "body");
						
						$.jqplot(val1.name, [val1.idealWork, val1.work], 
								{ 
								  title: val1.name + ' Subsystem(s) Burndown', 
								  axes:{
								        xaxis:{
								          renderer:$.jqplot.DateAxisRenderer,
								          tickOptions:{formatString:'%d-%b'},
								          tickInterval:'2 day'
								        }
								      },
								  animate: true,
						            // Will animate plot on calls to plot1.replot({resetAxes:true})
						            animateReplot: true,
								  
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
										markerOptions: { size:0},
										
									  },
									  { 
										// Use (open) circlular markers.
										lineWidth:2, 
										markerOptions: { size:0},
										rendererOptions: {
					                        // speed up the animation a little bit.
					                        // This is a number of milliseconds.
					                        // Default for a line series is 2500.
					                        animation: {
					                            speed: 4000
					                        }
					                    }
									  }
								  ]
								}
							  );
				  };
				  

										$(val2).each(function(index, value) {
											plotChart(value);
										});

									});
				</script>


				<!-- Don't touch this! -->
				<link class="include" rel="stylesheet" type="text/css"
					href="jquery.jqplot.min.css" />
				<script class="include" type="text/javascript"
					src="jquery.jqplot.min.js"></script>

				<!-- End Don't touch this! -->

				<!-- Additional plugins go here -->

				<script class="include" type="text/javascript"
					src="plugins/jqplot.canvasTextRenderer.min.js"></script>
				<script class="include" type="text/javascript"
					src="plugins/jqplot.canvasAxisLabelRenderer.min.js"></script>
				<script type="text/javascript"
					src="plugins/jqplot.dateAxisRenderer.min.js"></script>

				<!-- End additional plugins -->

			</div>

		</div>
	</div>


</body>


</html>
