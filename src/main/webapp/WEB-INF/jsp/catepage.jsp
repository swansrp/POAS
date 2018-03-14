﻿﻿<!DOCTYPE html>
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
        <link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
        <link href="../bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
        <link href="../assets/styles.css" rel="stylesheet" media="screen">
        <link href="../assets/DT_bootstrap.css" rel="stylesheet" media="screen">
        <!--end data table-->
        
        <!-- radio -->
        <link rel="stylesheet" href="../j-radio-checkbox/css/jquery-labelauty.css">
        <style>
             ul { list-style-type: none;}
             li { display: inline-block;}
             li { margin: 10px 0;}
             input.labelauty + label { font: 12px "Microsoft Yahei";}
           </style>
        <!-- end radio -->
        
    <!-- GOOGLE FONTS-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
    <style type="text/css">

    </style>
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
						<a href="/main"><i class="fa fa-square-o "></i>MainPage</a>
                    </li>
					<li>
						<a href="/add"><i class="fa fa-plus "></i>AddPage</a>
                    </li>
                    <li>
                        <a  href="/test"><i class="fa fa-square-o "></i>Test Page</a>
                    </li>
                    <li>
						<a class="active-menu"  href="/category"><i class="fa fa-plus "></i>Category</a>
                    </li>
                    <li>
                        <a href="/login"><i class="fa fa-sign-in "></i>Login Page</a>
                    </li> 
                    
                </ul>
            </div>

        </nav>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper">
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-head-line">手动分类</h1>
                        <h1 class="page-subhead-line">实现手动分类 </h1>

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
                            </div>
							
                            </div>
                            
                        </div>
            </div>
            
            <!-- form -->
           <div class="row">
           
              
           </div>
            <!--end form -->
            
              <!-- test -->
                     <div class="row-fluid">
                                        
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



           <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-default">
                        
                        <div class="panel-body">
                            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                            <h4 class="modal-title" id="myModalLabel">选择分类并点击保存</h4>
                                           
                                            <input type='button' id='category1' type="text" style="visibility: hidden;" /><!-- style="visibility: hidden;" -->
                                            <input type='button' id='category2' type="text" style="visibility: hidden;"/>
                                            <input type='button' id='category3' type="text" style="visibility: hidden;"/>
                                            <input type='button' id='category4' type="text" style="visibility: hidden;"/>
                                        </div>
                                        <div class="modal-body">
                                        <div class="panel-body">
                                            <table id="add_keyword">
                                           
                                          </table>
                                        </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">确认</button>
                                            <button type="button" class="btn btn-primary" onclick="key_submit()" data-dismiss="modal">保存</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                       
                        
                        
                        
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
        <script src="../datepicker/jquery-ui.min.js"></script>
        
        
        
        <!--data table-->
        <script src="../vendors/jquery-1.9.1.js"></script>
        <script src="../bootstrap/js/bootstrap.min.js"></script>
        <script src="../vendors/datatables/js/jquery.dataTables.min.js"></script>
        <script src="../vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
        <script src="../assets/scripts.js"></script>
        <script src="../assets/DT_bootstrap.js"></script>
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
    	
    	 /* $.ajax({
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
    	});  */
    	$.ajax({
    		type:"post",
    		url:"/config/keyword/getmap",
     		data: {},
     		dataType:'json',
    		success:function(data){
                var json=JSON.stringify(data);
                var result=eval(data);
                  /* alert(json);  */
                var id=0;
                   $.each(data,function(key,value){ 
                	   /*  alert("key:"+key+";value:"+value); */ 
                	    var idname="id"+id;
                	    item="<tr><td><a id='item'  class='btn btn-warning'>"+key+"</a></td><td><ul class='dowebok' id='"+idname+"'></ul></td></tr><br/>";  
                	    $('#add_keyword').append(item); 
                	    /* add keyword */
                	      for(var i =0;i<value.length;i++){
                	    	   /* alert(idname+"i:"+i+"list:"+value[i]); */   
                	    	  item="<li><input type='radio' name='radio' value='"+value[i]+"' data-labelauty='"+value[i]+"'></li>";
                	    	  
                	    	  $("#"+idname).append(item);
                	      }
                	     id++; 
                	   }); 
               	$(':input').labelauty();
        }
    	});
    	
    }/* end show_model_sselect() */
    
    
    function key_submit(){
    	 var modelname=$('#ModelName').val();
    	 var id= $("#category1").val();
    	 var itemId= $("#category2").val(); 
    	 var origin=$("#category3").val();
    	 var sentiment=$('#Sti_'+itemId).val();
    	 var category=$('input:radio:checked').val();
    	 
    	 $("input[id="+itemId+"]").val(category);
    	 alert(modelname+"  "+origin+" "+id+" "+sentiment+" "+category);
    	 var url="/StoreBbs/modelinfo/update/analysis";
	     var json={"modelname":modelname,"origin":origin,"id":id,"sentiment":sentiment,"category":category};
    	 $.ajax({
     		type:"post",
     		async:true,
     		url:url,
     		data:json,
     		dataType: "json",
     		/* success:function(){
    			alert("yes"); 
            },
            error: function () {
                alert("err");
            } */
     	
     	});
    	 
    	
    }
    
    function selt_bt(id,itemId,origin,sent){
    	 $("#category1").val(id); 
     	 $("#category2").val(itemId);
    	 $("#category3").val(origin); 
    	 $("#category4").val(sent);
   	
   }
 
	
	function submit() { 
		 $('#add_table').html("");
		 var modelname=$('#ModelName').val();
	    	var webname=$('#WebName').val();
	    	var start=$('#stime').val();
	     	var end=$('#etime').val();
	    	var url="/StoreBbs/modelinfo";
	    	var json={"modelname":modelname,"start":start,"end":end};
    	 
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
    			var itemId = 0;
    			item="<table cellpadding='0' cellspacing='0' border='0' class='table table-striped table-bordered' id='example'><thead><tr><td>id</td><td>Title</td><td>Comments</td><td>sentiment</td><td>category</td><td>Url</td><td>origin</td></tr></thead><tbody></tbody></table>";
    			$('#add_table').append(item);
    			$.each(json,function(i,result){
    				var id=json[i].id;
    				var title=json[i].title;
    				var comment=json[i].firstcomment;
    				var sentiment=json[i].sentiment;
    				var category=json[i].category;
    				var origin=json[i].origin;
    				var link=json[i].link;
    				
    				//item="<tr><td>"+id+"</td><td>"+title+"</td><td>"+comment+"</td><td>"+sentiment+"</td><td>"+"<input type='button' id='"+itemId+"' class='btn btn-primary btn-lg' data-toggle='modal' data-target='#myModal' value='选择分类' onclick='selt_bt("+id+","+itemId+",\""+origin+"\",\""+sentiment+"\")'/>"+"</td><td><a href=' "+link+" 'target= '_Blank'>"+"link"+"</a></td><td>"+origin+"</td></tr>"; 
    				//$('#example').append(item); 
    				
    				/* test */
    				item="<tr id='tr_"+itemId+"'><td>"+id+"</td><td>"+title+"</td><td>"+comment+"</td></tr>";
    				$('#example').append(item);
    				/* add sentiment */
    				item="<td><select id='Sti_"+itemId+"'class='form-control'><option value='2'>积极</option><option value='1'>中性</option><option value='0'>消极</option><option value='3'>不知道</option></select></td>";
    				$('#tr_'+itemId).append(item);
    				if(sentiment==2){
    					$('#Sti_'+itemId).val(2);
    				}else if(sentiment==0){
    					$('#Sti_'+itemId).val(0);
    				}else if(sentiment==1){
    					$('#Sti_'+itemId).val(1);
    				}else if(sentiment==-1||sentiment==3){
    					$('#Sti_'+itemId).val(3);
    				} 
    				/* add category */
    				 if(category==null){
    					item="<td><input type='button' id='"+itemId+"' class='btn btn-primary btn-lg' data-toggle='modal' data-target='#myModal' value='选择分类' onclick='selt_bt("+id+","+itemId+",\""+origin+"\",\""+sentiment+"\")'/></td>";
    				}else{
    					item="<td><input type='button' id='"+itemId+"' class='btn btn-primary btn-lg' data-toggle='modal' data-target='#myModal' value='"+category+"' onclick='selt_bt("+id+","+itemId+",\""+origin+"\",\""+sentiment+"\")'/></td>";
    				} 
    				$('#tr_'+itemId).append(item);
    				/* add link origin */
    				item="<td><a href=' "+link+" 'target= '_Blank'>"+"link"+"</a></td><td>"+origin+"</td>"; 
    				$('#tr_'+itemId).append(item);
    				/* end test */
    				
    				itemId++;
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
	/* end submit */
	
    
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
 <!-- radio -->
        <script src="../j-radio-checkbox/js/jquery-labelauty.js"></script>
        <div style="text-align:center;clear:both;">
           <script src="/gg_bd_ad_720x90.js" type="text/javascript"></script>
            <script src="/follow.js" type="text/javascript"></script>
        </div>
        <!-- end  radio -->
</body>
</html>
