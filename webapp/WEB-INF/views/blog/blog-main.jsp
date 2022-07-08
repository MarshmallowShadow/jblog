<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JBlog</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
</head>

<body>
	<div id="wrap">

		<!-- 개인블로그 해더 -->
		<c:import url="/WEB-INF/views/includes/blog-header.jsp"></c:import>
		
		<div id="content" class="clearfix">
			<div id="profilecate_area">
				<div id="profile">
					
					<!-- 기본이미지 -->
					<img id="proImg" src="${pageContext.request.contextPath}/${bMap.LOGOFILE}">
					
					<!-- 사용자업로드 이미지 -->
					<%-- <img id="proImg" src=""> --%>
					
					<div id="nick">${bMap.USERNAME}(${bMap.ID})님</div>
				</div>
				<div id="cate">
					<div class="text-left">
						<strong>카테고리</strong>
					</div>
					<ul id="cateList" class="text-left">
						<c:forEach items="${cList }" var="cVo">
							<li><a class="cateItem" href="${pageContext.request.contextPath }/${bMap.ID}?cateNo=${cVo.cateNo }">${cVo.cateName }</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<!-- profilecate_area -->
			
			<div id="post_area">
				<c:choose>
				<c:when test="${pMap == null }">
					<div id="postBox" class="clearfix">
								<div id="postTitle" class="text-left"><strong>등록된 글이 없습니다.</strong></div>
								<div id="postDate" class="text-left"><strong></strong></div>
								<div id="postNick"></div>
					</div>
				    
					<div id="post" >
					</div>
				</c:when>
				<c:otherwise>
					<div id="postBox" class="clearfix">
							<div id="postTitle" class="text-left"><strong>${pMap.POSTTITLE }</strong></div>
							<div id="postDate" class="text-left"><strong>${pMap.REGDATE }</strong></div>
							<div id="postNick">${pMap.USERNAME }(${pMap.ID })님</div>
					</div>
					<!-- //postBox -->
				
					<div id="post" >
						${pMap.POSTCONTENT }
					</div>
				</c:otherwise>
				</c:choose>
				
				<div id="comment_area">
					<table id="cmtForm">
						<colgroup>
							<col style="width: 100px">
							<col style="">
							<col style="width: 100px">
						</colgroup>
						<c:if test="${authUser != null and pMap != null }">
						<tr id="add_post">
							<td><strong>${authUser.userName }</strong></td>
							<td><input type="text" name="cmtContent" value=""></td>
							<td><button id="btnComment" type="button">저장</button></td>
						</tr>
						</c:if>
					</table>
					<table id="tableCmtView">
						<colgroup>
							<col style="width: 100px">
							<col style="width: 400px">
							<col style="width: 150px">
							<col style="width: 50px">
						</colgroup>
						<tbody id="tbodyCmtView"></tbody>
					</table>
				</div> <!-- comment_area -->
				
				<div id="list">
					<div id="listTitle" class="text-left"><strong>카테고리의 글</strong></div>
					<table>
						<colgroup>
							<col style="">
							<col style="width: 20%;">
						</colgroup>
						<c:forEach items="${pList }" var="pVo">
						<tr>
							<td class="text-left"><a href="${pageContext.request.contextPath }/${bMap.ID}?postNo=${pVo.postNo }&cateNo=${pVo.cateNo }">${pVo.postTitle }</a></td>
							<td class="text-right">${pVo.regDate }</td>
						</tr>
						</c:forEach>
					</table>
				</div>
				<!-- //list -->
			</div>
			<!-- //post_area -->
			
		</div>	
		<!-- //content -->
		<div class=></div>
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp"></c:import>
		
	
	
	</div>
	<!-- //wrap -->
</body>

<script type="text/javascript">
	$(document).ready(function(){
		var postNo = parseInt("${pMap.POSTNO}");
		
		$.ajax({
			url : "${pageContext.request.contextPath}/api/comments/getList",
			type : "post",
			contentType : "application/json",
			data : JSON.stringify(postNo),
			dataType : "json",
			success : function(comList){
				for(var i=0; i < comList.length; i++) {
					render(comList[i], 'bottom');
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});
	
	$("#btnComment").on("click", function(){
		var userNo = '${authUser.userNo }';
		var postNo = parseInt("${pMap.POSTNO}");
		var cmtContent = $("[name=cmtContent]").val();
		
		var comVo = {
			postNo: postNo,
			userNo: userNo,
			cmtContent: cmtContent
		};
		
		//console.log(comVo);
		
		$.ajax({
			url : "${pageContext.request.contextPath}/api/comments/addComment",
			type : "post",
			contentType : "application/json",
			data : JSON.stringify(comVo),
			dataType : "json",
			success : function(comMap){
				render(comMap, 'top');
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});
	
	$("#tableCmtView").on("click", ".com_del", function(){
		var $this = $(this);
		var cmtNo = $this.data("cmtno");
		
		$.ajax({
			url : "${pageContext.request.contextPath}/api/comments/delete",
			type : "post",
			contentType : "application/json",
			data : JSON.stringify(cmtNo),
			dataType : "json",
			success : function(count){
				if(count > 0){
					$("#c" + cmtNo).remove();
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});
	
	function render(cMap, order) {
		var str = '';
		str += '<tr id="c'+ cMap.CMTNO +'">';
		str += '	<td>'+cMap.USERNAME+'</td>';
		str += '	<td class="cmtView">'+cMap.CMTCONTENT+'</td>';
		str += '	<td>'+cMap.REGDATE+'</td>';
		str += '	<td>';
		if('${authUser.id}' == cMap.ID){
			str += '		<span class="com_del" data-cmtno="' + cMap.CMTNO + '" style="cursor: pointer"><strong><font color="red">x</font></strong></span>';
		}
		str += '	</td>';
		str += '</tr>';
		
		if(order == 'bottom'){
			$("#tbodyCmtView").append(str);
		} else {
			$("#tbodyCmtView").prepend(str);
		}
		
	}
</script>

</html>
