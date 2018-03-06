<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

    <!-- GOOGLE FONTS-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
    
     <!-- PAGE LEVEL STYLES -->
    <link href="assets/css/bootstrap-fileupload.min.css" rel="stylesheet" />
    
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
                        <a  href="index.html"><i class="fa fa-dashboard "></i>Dashboard</a>
                    </li>
					<li>
						<a  href="/main"><i class="fa fa-square-o "></i>MainPage</a>
                    </li>
					<li>
						<a  href="/add"><i class="fa fa-plus "></i>AddPage</a>
                    </li>
                    <li>
                        <a  class="active-menu" ><i class="fa fa-square-o "></i>Test Page</a>
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
                                            <label>Input Text</label>
                                            <input id="text"  class="form-control" type="text" placeholder="输入...">
                                     
                                        </div>
                             <div class="form-group">
                                            <label>Input url</label>
                                            <input id="url"  class="form-control" type="text" placeholder="输入...">
                                     
                                        </div>
							
							 <button id="search" type="submit" class="btn btn-danger">确定 </button>
                            </div>
                        </div>
            </div>
           <!--  right -->
            <div class="col-md-6 col-sm-6 col-xs-12">
                <div class="panel panel-danger">
                        <div class="panel-heading">
                           SINGUP FORM
                        </div>
                        <div  class="panel-body" >
                           <div class="panel-heading">
                            File Uploads
                           </div>
                           
                            <div class="form-group">
                                       <label class="control-label col-lg-4">File Uploads</label>
                                       <div class="">
                                           <div class="fileupload fileupload-new" data-provides="fileupload">
                                               <span class="btn btn-file btn-default">
                                                   <span class="fileupload-new">Select file</span>
                                                   <span class="fileupload-exists">Change</span>
                                                   <input id="upfile" type="file">
                                               </span>
                                               <span class="fileupload-preview"></span>
                                               <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none">×</a>
                                           </div>
                                       </div>
                                       <button id="upload" onclick="upLoadFile()" type="submit" class="btn btn-danger">提交</button>
                            </div>
                           
                           
                        </div>
                </div>
            </div>
            <!--  end right -->
			 <div class="row">
                    <div class="col-md-12">
                        <div id="returnData" class="alert alert-info" >
                           
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
    
        <!-- PAGE LEVEL SCRIPTS -->
    <script src="assets/js/bootstrap-fileupload.js"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="../assets/js/jquery.metisMenu.js"></script>
    <!-- CUSTOM SCRIPTS -->
    <script src="../assets/js/custom.js"></script>

    
    <script type="text/javascript">
    function upLoadFile() { 
    	var formData = new FormData();
        var name = $("#upfile").val();
        formData.append("file",$("#upfile")[0].files[0]);
        formData.append("name",name);
        $.ajax({
            url : '/nlp/upload',
            type : 'POST',
            async : false,
            data : formData,
            // 告诉jQuery不要去处理发送的数据
            processData : false,
            // 告诉jQuery不要去设置Content-Type请求头
            contentType : false,
            beforeSend:function(){
                console.log("正在进行，请稍候");
            },
            success : function(responseStr) {
                if(responseStr=="01"){
                    alert("导入成功");
                }else{
                    alert("导入失败");
                }
            }
        });
    }

   
    
    
    $(function(){
 
        $("#search").click(function(){
        	     $( "#returnData" ).html("");
    		    var text=$('#text').val();
            	var url=$('#url').val();
            	var json={"text":text};
            	$.ajax({
            		type:"post",
            		async:true,
            		url:url,
            		data:json,
            		dataType: "json",
            		success:function(msg){
            			/* alert(JSON.stringify(msg));  */
            			$( "#returnData" ).html($("#returnData").html() + "<br>" + JSON.stringify(msg) + "<br/>");
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
