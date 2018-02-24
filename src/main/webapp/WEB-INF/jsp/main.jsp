<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Responsive Bootstrap Advance Admin Template</title>

    <!-- BOOTSTRAP STYLES-->
    <link href="../assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FONTAWESOME STYLES-->
    <link href="../assets/css/font-awesome.css" rel="stylesheet" />
    <!--CUSTOM BASIC STYLES-->
    <link href="../assets/css/basic.css" rel="stylesheet" />
    <!--CUSTOM MAIN STYLES-->
    <link href="../assets/css/custom.css" rel="stylesheet" />
        <link href="../datepicker/jquery-ui.css" rel="stylesheet" />
    <!-- GOOGLE FONTS-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>
<body>
    <div id="wrapper">
        <nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.html">Sumsung Mobile Phone</a>
            </div>

            <div class="header-right">

              <a href="message-task.html" class="btn btn-info" title="New Message"><b>30 </b><i class="fa fa-envelope-o fa-2x"></i></a>
                <a href="message-task.html" class="btn btn-primary" title="New Task"><b>40 </b><i class="fa fa-bars fa-2x"></i></a>
                <a href="login.html" class="btn btn-danger" title="Logout"><i class="fa fa-exclamation-circle fa-2x"></i></a>


            </div>
        </nav>
        <!-- /. NAV TOP  -->
      <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
                    <li>
                        <div class="user-img-div">
                           

                            <div class="inner-text">
                                Manager
                            <br />
                                <small>Demo </small>
                            </div>
                        </div>

                    </li>


                    <li>
                        <a  href="index.html"><i class="fa fa-dashboard "></i>Dashboard</a>
                    </li>
					<li>
						<a class="active-menu" href="mainpage.html"><i class="fa fa-square-o "></i>MainPage</a>
                    </li>
					<li>
						<a href="/add"><i class="fa fa-plus "></i>AddPage</a>
                    </li>
                    <li>
                        <a  href="/test"><i class="fa fa-square-o "></i>Test Page</a>
                    </li>
                    <li>
                        <a href="login.html"><i class="fa fa-sign-in "></i>Login Page</a>
                    </li> 
                </ul>
            </div>

        </nav>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper">
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-head-line">MAIN PAGE</h1>
                        <h1 class="page-subhead-line">This is dummy text , you can replace it with your original text. </h1>

                    </div>
                </div>
                <!-- /. ROW  -->
               
			<div class="col-md-6 col-sm-6 col-xs-12">
               <div class="panel panel-danger">
                        <div class="panel-heading">
                           SINGUP FORM
                        </div>
                        <div class="panel-body">
                             <div class="form-group">
                                            <label>Select Modle</label>
                                            <select id="ModelName" class="form-control">

                                                
                                            </select>
                                        </div>
							<div class="form-group">
                                            <label>Select Website</label>
                                            <select id="WebName" class="form-control">

                                                
                                            </select>
                                        </div>
                            <div class="form-group">
                                            <label>Input start time</label>
                                            <input id="stime"  class="form-control" type="text" placeholder="">
                                     
                            </div>
                            <div class="form-group">
                                            <label>Input end time</label>
                                            <input id="etime"  class="form-control" type="text" placeholder="">
                                     
                            </div>
							 <button type="submit" class="btn btn-danger">确定 </button>
                            </div>
                        </div>
            </div>
			 <div class="row">
                    <div class="col-md-12">
                        <div class="alert alert-info">
                           
                        </div>
                    </div>
                </div>

            </div>
            <!-- /. PAGE INNER  -->
        </div>
        <!-- /. PAGE WRAPPER  -->
    </div>
    <!-- /. WRAPPER  -->
    <div id="footer-sec">
        &copy; 2018 <a>Samsung</a>
    </div>
    <!-- /. FOOTER  -->
    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="../assets/js/jquery-1.10.2.js"></script>
    <!-- BOOTSTRAP SCRIPTS -->
    <script src="../assets/js/bootstrap.js"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="../assets/js/jquery.metisMenu.js"></script>
    <!-- CUSTOM SCRIPTS -->
    <script src="../assets/js/custom.js"></script>
        <script src="../datepicker/jquery-ui.js"></script>
    <script type="text/javascript">
    function show_model_sselect() { 
    	jQuery.support.cors = true;
    	$.ajax({
    		type:"post",
    		url:"/config/model/name",
     		data: {},
    		success:function(data){
                //data是你从后端获取的数据
    			var result = data;
    			console.log(result);
    			for (var i = 0; i < result.length; i++) {
    				/* console.log(result[i]); */
    		         $('#ModelName').append("<option value='"+result[i]+"'>"+result[i]+"</option>");
    			}
    			
        }
    	});
    	
    	$.ajax({
    		type:"post",
    		url:"/config/source/name",
     		data: {},
    		success:function(data){
    			var result = data;
    			console.log(result);
    			for (var i = 0; i < result.length; i++) {
    		         $('#WebName').append("<option value='"+result[i]+"'>"+result[i]+"</option>");
    			}
    			
        }
    	});
    	
    }/* end show_model_sselect() */
    
    
    
    $(function(){
    	show_model_sselect();
    	$( "#stime" ).datepicker({
    	      defaultDate: "+1w",
    	      changeMonth: true,
    	      numberOfMonths: 3,
    	      onClose: function( selectedDate ) {
    	        $( "#etime" ).datepicker( "option", "minDate", selectedDate );
    	      }
    	    });
    	$( "#etime" ).datepicker({
    	      defaultDate: "+1w",
    	      changeMonth: true,
    	      numberOfMonths: 3,
    	      onClose: function( selectedDate ) {
    	        $( "#stime" ).datepicker( "option", "maxDate", selectedDate );
    	      }
    	    });
        $("#").click(function(){
    		    var weburl=$('#weburl').val();
            	var webname=$('#WebName').val();
            	var modelname=$('#ModelName').val();
            	var json={"weburl":weburl,"webname":webname,"modelname":modelname};
            	$.ajax({
            		type:"post",
            		async:true,
            		url:"/config/url/add",
            		data:json,
            		dataType: "json",
            		success:function(msg){
            			alert("数据提交成功"); 
                    },
                    error: function (msg) {
                        alert("err");
                    }
            	
            	});
        });  /* end search */ 
    });
    </script>

</body>
</html>
