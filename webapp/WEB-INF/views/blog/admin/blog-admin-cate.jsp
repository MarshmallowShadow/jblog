<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JBlog</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>

</head>

<body>
	<div id="wrap">
		
		<!-- 개인블로그 해더 -->
		<c:import url="/WEB-INF/views/includes/blog-header.jsp"></c:import>

		<div id="content">
			<ul id="admin-menu" class="clearfix">
				<li class="tabbtn"><a href="${pageContext.request.contextPath}/${authUser.id }/admin/basic">기본설정</a></li>
				<li class="tabbtn selected"><a href="${pageContext.request.contextPath}/${authUser.id }/admin/category">카테고리</a></li>
				<li class="tabbtn"><a href="${pageContext.request.contextPath}/${authUser.id }/admin/writeForm">글작성</a></li>
			</ul>
			<!-- //admin-menu -->
			
			<div id="admin-content">
			
				<table id="admin-cate-list">
					<colgroup>
						<col style="width: 50px;">
						<col style="width: 200px;">
						<col style="width: 100px;">
						<col>
						<col style="width: 50px;">
					</colgroup>
		      		<thead>
			      		<tr>
			      			<th>번호</th>
			      			<th>카테고리명</th>
			      			<th>포스트 수</th>
			      			<th>설명</th>
			      			<th>삭제</th>      			
			      		</tr>
		      		</thead>
		      		<tbody id="cateList">
						<!-- 리스트 영역 -->
					</tbody>
				</table>
      	
		      	<table id="admin-cate-add" >
		      		<colgroup>
						<col style="width: 100px;">
						<col style="">
					</colgroup>
		      		<tr>
		      			<td class="t">카테고리명</td>
		      			<td><input type="text" name="cateName" value=""></td>
		      		</tr>
		      		<tr>
		      			<td class="t">설명</td>
		      			<td><input type="text" name="description" value=""></td>
		      		</tr>
		      	</table> 
			
				<div id="btnArea">
		      		<button id="btnAddCate" class="btn_l" type="submit" >카테고리추가</button>
		      	</div>
			
			</div>
			<!-- //admin-content -->
		</div>	
		<!-- //content -->
		
		
		<!-- 개인블로그 푸터 -->
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp"></c:import>
	
	
	</div>
	<!-- //wrap -->
</body>

<script type="text/javascript">
	var id = '${authUser.id}';
	
	//로드시 리스트 얻어오기
	$(document).ready(function(){
		$.ajax({
			url : "${pageContext.request.contextPath}/api/category/getList",
			type: "post",
			contentType : "application/json",
			data : JSON.stringify(id),
			dataType : "json",
			success : function(gList){
				//console.log(gList);
				
				for(var i=0; i<gList.length; i++){
					render(gList[i], 'down');
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});
	
	//카테고리 추가
	$("#btnAddCate").on("click", function(){
		var cateName = $("[name=cateName]").val();
		var description = $("[name=description]").val();
		
		if(cateName == ""){
			alert("카테고리명을 입력해 주세요.");
			return;
		}
		
		var gVo = {
				cateName: cateName,
				id: id,
				description: description
		};
		
		$.ajax({
			url : "${pageContext.request.contextPath}/api/category/addCategory",
			type: "post",
			contentType : "application/json",
			data : JSON.stringify(gVo),
			dataType : "json",
			success : function(gMap){
				console.log(gMap);
				render(gMap, 'up');
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});
	
	//카테고리 삭제
	$("#cateList").on("click", ".btnCateDel", function(){
		var $this = $(this);
		var pCount = parseInt($this.data("pcount"));
		//console.log(pCount);
		if(pCount > 0){
			alert("삭제할 수 없습니다.");
			return;
		}
		
		var cateNo = parseInt($this.data("cateno"));
		//console.log(cateNo);
		
		$.ajax({
			url : "${pageContext.request.contextPath}/api/category/deleteCategory",
			type: "post",
			contentType : "application/json",
			data : JSON.stringify(cateNo),
			dataType : "json",
			success : function(count){
				if(count > 0) {
					$("#t" + cateNo).remove();
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});
	
	
	//테이블 데이터 추가
	function render(pMap, order) {
		var pCount = pMap.PCOUNT;
		if(pCount == null){
			pCount = 0;
		}
		
		var str;
		str += '<tr id="t' + pMap.CATENO + '">';
		str += '	<td>' + pMap.CATENO + '</td>';
		str += '	<td>' + pMap.CATENAME + '</td>';
		str += '	<td>' + pCount + '</td>';
		str += '	<td>' + pMap.DESCRIPTION + '</td>';
		str += '	<td class="text-center">';
		str += '		<img class="btnCateDel" src="${pageContext.request.contextPath}/assets/images/delete.jpg"';
		str += '				data-pcount="' + pCount + '" data-cateno="' + pMap.CATENO + '">';
		str += '	</td>';
		str += '</tr>';
		
		if(order == 'down') {
			$("#cateList").append(str);
		} else {
			$("#cateList").prepend(str);
		}
	}
</script>


</html>