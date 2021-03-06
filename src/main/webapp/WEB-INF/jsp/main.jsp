﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
        <link href="../datepicker/jquery-ui.min.css" rel="stylesheet" />
        
        
        <!--data table-->
		<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
        <link href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
        <link href="assets/styles.css" rel="stylesheet" media="screen">
        <link href="assets/DT_bootstrap.css" rel="stylesheet" media="screen">
        <!--end data table-->
       
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
                <a class="navbar-brand" href="index.html"> Mobile Phone</a>
            </div>

            <div class="header-right">


                <a href="#" class="btn btn-primary" title="New Task"><b> </b><i class="fa fa-bars fa-2x"></i></a>
                <a href="#" class="btn btn-danger" title="Logout"><i class="fa fa-exclamation-circle fa-2x"></i></a>


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
                        <a  href="#"><i class="fa fa-dashboard "></i>Dashboard</a>
                    </li>
					<li>
						<a class="active-menu" href="/main"><i class="fa fa-square-o "></i>MainPage</a>
                    </li>
					<li>
						<a href="/add"><i class="fa fa-plus "></i>AddPage</a>
                    </li>
                    <li>
                        <a  href="/test"><i class="fa fa-square-o "></i>Test Page</a>
                    </li>
                    <li>
						<a  href="/category"><i class="fa fa-plus "></i>Category</a>
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
               
			<div class="col-md-12 col-sm-6 col-xs-12">
               <div class="panel panel-danger">
                        <div class="panel-heading">
                           SINGUP FORM
                        </div>
                        <div class="panel-body">
                             <div class="form-group col-md-2">
                                            <label>Select Modle</label>
                                            <select id="ModelName" class="form-control">

                                                
                                            </select>
                                        </div>
							<div class="form-group col-md-2">
                                            <label>Select Website</label>
                                            <select id="WebName" class="form-control">
                                            <option>全部</option>
                                            </select>
                                        </div>
                            <div class="form-group col-md-2">
                                            <label>Input start time</label>
                                            <input id="stime"  class="form-control" type="text" placeholder="" >
                                     
                            </div>
                            <div class="form-group col-md-2">
                                            <label>Input end time</label>
                                            <input id="etime"  class="form-control" type="text" placeholder="" >
                                     
                            </div>
                            <div class="form-group col-md-6">
						   	         <button id="submit" type="submit" class="btn btn-danger" onclick="submit()">确定 </button>
							         <button id="download" type="submit" class="btn btn-danger" onclick="download()">下载 </button>
							         <!-- <a href="../workbook1.xls" class="btn btn-success ">点击下载</a> -->
                                     
                            </div>
							
                            </div>
                            
                        </div>
            </div>
			<!--  <div class="row">
                    <div class="col-md-12">
                        <div class="alert alert-info">
                           
                        </div>
                        <div class="panel panel-default">
                        <div class="panel-heading">
                            COMMENTS
                        </div>
                        
                        test
                        
                        test end
                        <div class="panel-body">
                            <div class="table-responsive">
                               
                               
                            </div>
                            
                    </div>
                    </div>
                </div> -->

               <!-- test -->
                     <div class="row-fluid">
                        <div >
                                            <select id="atti" class="form-control">
                                            <option>消极</option>
                                            <option>积极</option>
                                            </select>
                                        </div>
                                        
                         <!-- <div class="block">  -->
                            <div class="navbar navbar-inner block-header">
                                <div class="muted pull-left">COMMENTS </div>
                            </div>
                            <div class="block-content collapse in">
                                <div class="span12" id="add_table">
                                    
  								
                                </div>
                            </div>
                       <!--  </div>  -->
                        
                    </div> 
                   
                  <!--  end test -->




            </div>
            <!-- /. PAGE INNER  -->
        </div>
        <!-- /. PAGE WRAPPER  -->
    </div>
    <!-- /. WRAPPER  -->
    <div id="footer-sec">
            <a href="http://www.miitbeian.gov.cn/"> 津ICP备18002110号</a>
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
        <script src="../datepicker/jquery-ui.min.js"></script>
        
        
        
        
        <!--data table-->
        <script src="vendors/jquery-1.9.1.js"></script>
        <script src="bootstrap/js/bootstrap.min.js"></script>
        <script src="vendors/datatables/js/jquery.dataTables.min.js"></script>
        <script src="vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
        <script src="assets/scripts.js"></script>
        <script src="assets/DT_bootstrap.js"></script>
        <!--end  data table-->
  
    <script type="text/javascript">
    function show_model_sselect() { 
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
 
    
    
    /* function submit() { 
    	$('#cTable').html("");
	    var modelname=$('#ModelName').val();
    	var webname=$('#WebName').val();
    	var start=$('#stime').val();
    	var end=$('#etime').val();
    	var url;
    	var json={"modelname":modelname,"start":start,"end":end};
    	if(webname=="京东") {
            url="/JD/modelinfo";
        } else if(webname=="淘宝") {
             url="/TB/modelinfo";
        } else if(webname=="天猫") {
             url="/TM/modelinfo";
        } else if(webname=="亚马逊") {
            url="/AMZ/modelinfo";
        } else if(webname=="百度贴吧") {
            url="/BD/modelinfo";
        } else if(webname=="盖乐世社区") {
            url="/GC/modelinfo";
        } else if(webname=="机锋") {
            url="/JF/modelinfo";
        } else if(webname=="国美") {
            url="/GM/modelinfo";
        } else if(webname=="苏宁易购") {
            url="/SN/modelinfo";
        } else if(webname=="全部") {
            url="/StoreBbs/modelinfo";
        }
    	 
    	$.ajax({
    		type:"post",
    		async:true,
    		url:url,
    		data:json,
    		dataType: "json",
    		success:function(msg){
    			var json = eval(JSON.stringify(msg.data));
    			var item;
    			var num=msg.code;
    			$.each(json,function(i,result){
    				var id=json[i].id;
    				var comment=json[i].firstcomment;
    				var sentiment=json[i].sentiment;
    				var category=json[i].category;
    				var link;
    				if (url="/BD/modelinfo"){link=json[i].link;}
    				item="<tr><td>"+id+"</td><td>"+comment+"</td><td>"+sentiment+"</td><td>"+category+"</td><td><a href=' "+link+" 'target= '_Blank'> "+link+"</a></td></tr>"; 
    				$('#cTable').append(item);  
    				}); 
    			 /*  alert(JSON.stringify(msg.data));  */
    			/*$("#Pagination").pagination(id, {
    	    	    num_edge_entries: 2,
    	    	    num_display_entries: 4,
    	    	    callback: pageselectCallback,
    	    	    items_per_page:1
    	    	});
            },
            error: function (msg) {
                alert("err");
            }
    	
    	});
	} */  /* end submit */  
	
	function submit() { 
		 $('#add_table').html("");
 	    
		 var modelname=$('#ModelName').val();
     	var webname=$('#WebName').val();
     	var start=$('#stime').val();
     	var end=$('#etime').val();
     	var url;
     	var json={"modelname":modelname,"start":start,"end":end};
     	if(webname=="京东") {
             url="/JD/modelinfo";
         } else if(webname=="淘宝") {
              url="/TB/modelinfo";
         } else if(webname=="天猫") {
              url="/TM/modelinfo";
         } else if(webname=="亚马逊") {
             url="/AMZ/modelinfo";
         } else if(webname=="百度贴吧") {
             url="/BD/modelinfo";
         } else if(webname=="盖乐世社区") {
             url="/GC/modelinfo";
         } else if(webname=="机锋") {
             url="/JF/modelinfo";
         } else if(webname=="国美") {
             url="/GM/modelinfo";
         } else if(webname=="苏宁易购") {
             url="/SN/modelinfo";
         } else if(webname=="全部") {
             url="/StoreBbs/modelinfo";
         }
     	 
     	$.ajax({
     		type:"post",
     		async:true,
     		url:url,
     		data:json,
     		dataType: "json",
     		success:function(msg){
     			var json = eval(JSON.stringify(msg.data));
     			var item;
     			var num=msg.code;
     			
     			item="<table cellpadding='0' cellspacing='0' border='0' class='table table-striped table-bordered' id='example'><thead><tr><td>#</td><td>Comments</td><td>sentiment</td><td>category</td><td>Link</td></tr></thead><tbody>";
     			$('#add_table').append(item);
     			item="</tbody></table>";
     			$('#add_table').append(item);
     			$.each(json,function(i,result){
     				var id=json[i].id;
     				var comment=json[i].firstcomment;
     				var sentiment=json[i].sentiment;
     				var category=json[i].category;
     				var link;
     				if (url="/BD/modelinfo"){link=json[i].link;}
     				item="<tr><td>"+id+"</td><td>"+comment+"</td><td>"+sentiment+"</td><td>"+category+"</td><td><a href=' "+link+" 'target= '_Blank'> "+link+"</a></td></tr>"; 
     				$('#example').append(item);  
     				}); 
     			
     			 $('#example').dataTable( {
     				"sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
     				"sPaginationType": "bootstrap",
     				"oLanguage": {
     					"sLengthMenu": "_MENU_ records per page"
     				}
     			
     			} ); 
     			
     			
     			
     			/*  alert(JSON.stringify(msg.data)); */
     			
             },
             error: function (msg) {
                 alert("err");
             }
     	
     	});

} 
	
	function download() { 
		$('#cTable').html("");
	    var modelname=$('#ModelName').val();
    	var webname=$('#WebName').val();
    	var start=$('#stime').val();
    	var end=$('#etime').val();
    	var url;
    	var json={"modelname":modelname,"start":start,"end":end};
    	if(webname=="京东") {
            url="/JD/modelinfo/download";
        } else if(webname=="淘宝") {
             url="/TB/modelinfo/download";
        } else if(webname=="天猫") {
             url="/TM/modelinfo/download";
        } else if(webname=="亚马逊") {
            url="/AMZ/modelinfo/download";
        } else if(webname=="百度贴吧") {
            url="/BD/modelinfo/download";
        } else if(webname=="盖乐世社区") {
            url="/GC/modelinfo/download";
        } else if(webname=="机锋") {
            url="/JF/modelinfo/download";
        } else if(webname=="国美") {
            url="/GM/modelinfo/download";
        } else if(webname=="苏宁易购") {
            url="/SN/modelinfo/download";
        } else if(webname=="全部") {
            url="/StoreBbs/modelinfo/download";
        }
    	
     	window.location.href = url 
     			+ "?modelname=" + modelname
    			+ "&start=" + start
    			+ "&end=" + end;		
	}  /* end download */ 
	
	
	 $('#atti').change(function(){
		/* var attitufr=$('#atti').val();
		var url;
		if(atti="积极"){
			url="/sentiment";
		}else{
			url="/category";
		}
		$.ajax({
    		type:"post",
    		async:true,
    		url:url,
    		data:json,
    		dataType: "json",
    		success:function(data){
                //data是你从后端获取的数据
    			var result = data;
    			alert(JSON.stringify(data));
    			}
    			
        } */ 
        
        
		$('#add_table').html("");
    	var atti=$('#atti').children('option:selected').val();
  	    
		var modelname=$('#ModelName').val();
     	var webname=$('#WebName').val();
     	var start=$('#stime').val();
     	var end=$('#etime').val();
     	var url;
     	var json={"modelname":modelname,"start":start,"end":end};
     	if(webname=="京东") {
             url="/JD/modelinfo";
         } else if(webname=="淘宝") {
              url="/TB/modelinfo";
         } else if(webname=="天猫") {
              url="/TM/modelinfo";
         } else if(webname=="亚马逊") {
             url="/AMZ/modelinfo";
         } else if(webname=="百度贴吧") {
             url="/BD/modelinfo";
         } else if(webname=="盖乐世社区") {
             url="/GC/modelinfo";
         } else if(webname=="机锋") {
             url="/JF/modelinfo";
         } else if(webname=="国美") {
             url="/GM/modelinfo";
         } else if(webname=="苏宁易购") {
             url="/SN/modelinfo";
         } else if(webname=="全部") {
             url="/StoreBbs/modelinfo";
         }
     	 
     	$.ajax({
     		type:"post",
     		async:true,
     		url:url,
     		data:json,
     		dataType: "json",
     		success:function(msg){
     			var json = eval(JSON.stringify(msg.data));
     			var item;
     			var num=msg.code;
     			
     			item="<table cellpadding='0' cellspacing='0' border='0' class='table table-striped table-bordered' id='example'><thead><tr><td>#</td><td>Comments</td><td>sentiment</td><td>category</td><td>Link</td></tr></thead><tbody>";
     			$('#add_table').append(item);
     			item="</tbody></table>";
     			$('#add_table').append(item);
     			$.each(json,function(i,result){
     				var id=json[i].id;
     				var comment=json[i].firstcomment;
     				var sentiment=json[i].sentiment;
     				var category=json[i].category;
     				var link;
     				 if (url="/BD/modelinfo"){link=json[i].link;}
     				if(atti=="积极"&&sentiment==2){
     					item="<tr><td>"+id+"</td><td>"+comment+"</td><td>"+sentiment+"</td><td>"+category+"</td><td><a href=' "+link+" 'target= '_Blank'> "+link+"</a></td></tr>"; 
         				$('#example').append(item);  
     				}
     				if(atti=="消极"&&sentiment==0){
     					item="<tr><td>"+id+"</td><td>"+comment+"</td><td>"+sentiment+"</td><td>"+category+"</td><td><a href=' "+link+" 'target= '_Blank'> "+link+"</a></td></tr>"; 
         				$('#example').append(item);  
     				} 
     				}); 
     			
     			 $('#example').dataTable( {
     				"sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
     				"sPaginationType": "bootstrap",
     				"oLanguage": {
     					"sLengthMenu": "_MENU_ records per page"
     				}
     			} ); 
     			
     			
     			
     			/*  alert(JSON.stringify(msg.data)); */
     			
             },
             error: function (msg) {
                 alert("err");
             }
    			
        });
        
        
        /* $('#atti').change(function(){
    		
        	
        	}) */
        
        
        
	 })  /* end getAtti */ 
	
	

    
    $(function(){
    	show_model_sselect();
     	
    	$( "#stime" ).datepicker({
    	      defaultDate: "+1w",
    	       changeMonth: true,
    	      numberOfMonths: 3,
    	      dateFormat: 'yy-mm-dd 00:00:00',
    	      onClose: function( selectedDate ) {
    	        $( "#etime" ).datepicker( "option", "minDate", selectedDate );
    	      }
    	    });
    	$( "#etime" ).datepicker({
    	      defaultDate: "+1w",
    	      changeMonth: true,
    	      numberOfMonths: 3,
    	      dateFormat: 'yy-mm-dd 00:00:00',
    	      onClose: function( selectedDate ) {
    	        $( "#stime" ).datepicker( "option", "maxDate", selectedDate );
    	      }
    	    });
    		
    	
    
        
     
        
    });
    </script>

</body>
</html>
