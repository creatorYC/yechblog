<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no">
<title>同类问题</title>
<link type="text/css" href="css/bootstrap.min.css" rel="stylesheet">
<link type="text/css" href="css/myStyle.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>

</head>
<body>
	 <nav role="navigation" class="navbar navbar-default">
		  <ul class="nav navbar-nav">
		  	  <li><a href="#">话题</a></li>
		  	  <li><a href="QuestionAction_pagination">发现</a></li>
		  	  <li><a href="BlogAction_pagination">博客</a></li>
		  	  <li><a href="QuestionAction_toAskQuestionPage">提问题</a></li>
		  </ul>
	 </nav>
	<section class="content-wrap">
		<div class="container">
			<div class="row">
				<main class="col-md-9 main-content"> 
					<!-- 迭代问题列表 -->
					 <div class="course-notes course-in">
						<ul class="post">
						<s:iterator var="aq" value="similarQuestionList">
							 <li style="list-style-type:none;">
						        <div class="notes-img">
		                           <a class=" js-user-card" href="#">
		    						  <img class="avatar-sm" src="image/personImg.jpg" width="50" height="50">
		   						   </a>
		                        </div>
		                        <div class="notes-content">
		                            <h4>
		                          		<a href="QuestionAction_readDetail?qid=<s:property value='#aq.id'/>"><s:property value="#aq.title"/></a>
		                            </h4>
		                            <div class="metas">
		                            	<span><i class="tag-cloud"><a href="QuestionAction_similarQuestionsPagination?categoryName=<s:property value='#aq.category'/>"><s:property value="#aq.category"/></a></i></span>
		                                <span class="name">
		                                	<a href="UserAction_toOtherHomePage?userId=#aq.user.id"><s:property value="#aq.user.username"/></a>
		                                </span>
		                                <span class="badge badge-important pull-right">0</span>
		                                <span class="time">1 人关注 • 0 个回复 • 117 次浏览</span>
		                                <span class="time"><s:property value="#aq.createTime"/></span>
		                            </div>
		                        </div>
				              </li>	
						</s:iterator>
						</ul>
					</div>
				<nav class="pagination" role="navigation"> 
				<span class="label label-warning">第&nbsp;<s:property value="currentPageIndex"/>&nbsp;页 &frasl; 共&nbsp; <s:property value="pageCount"/>&nbsp;页</span>
				 <ul class="pager">
				 	<s:if test="currentPageIndex != 1">
    					<li><a href="QuestionAction_pagination?pageIndex=1"><span><font color="#e67e22">首页</font></span></a></li>
    				</s:if>
    				<s:if test="currentPageIndex > 1">
    					<li><a href="QuestionAction_pagination?pageIndex=<s:property value='currentPageIndex-1' />"><span><font color="#e67e22">上一页</font></span></a></li>
   					</s:if>
   					<s:if test="currentPageIndex < pageCount">
   						<li><a href="QuestionAction_pagination?pageIndex=<s:property value='currentPageIndex+1' />"><span><font color="#e67e22">下一页</font></span></a></li>
   					</s:if>
   					<s:if test="currentPageIndex != pageCount">
   						<li><a href="QuestionAction_pagination?pageIndex=<s:property value='pageCount' />"><span><font color="#e67e22">尾页</font></span></a></li>
  					</s:if>
  				</ul>
			</nav> 
				</main>
				<aside class="col-md-3 sidebar">
			
			<div class="widget">
				<h4 class="title">
					<span class="content">YechBlog</span>
				</h4>
					<span class="mylabel">
						<s:if test="#session['user'] != null">
							<span class="welcome">欢迎:</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<span class="name">
								<a href="BlogAction_toPersonalPage"><s:property value="#session['user'].username" /></a>
								<a href="UserAction_toMessageCenter">
									<span class="badge badge-important">
										<s:property value="#session['user'].messages.size()"/>
									</span>
								</a>
							</span>&nbsp;&nbsp;
						</s:if>
						<s:else>
							<div class="content download">
								<a href="LoginAction_toLoginPage" class="btn btn-default btn-block" onclick="">
									<span class="">去登录</span>
								</a>
							</div>
						</s:else>
					</span>
			</div>
			</aside>
			<aside class="col-md-3 sidebar">
				<div class="widget">
					<form id="searchForm" role="search">
						<div class="input-group">
							<input name="friendName" id="friendName" type="text" class="form-control" placeholder="搜好友...">
							<span class="input-group-addon">
							   <a id="searchit" href=""> <img alt="" src="image/search.png"></a>
							</span>
						</div>
					</form>
					<div id="search-result" style="display: none;"> 
						
					</div>
				</div>
			</aside>
			</div>
		</div>
	</section>
	
</body>
</html>