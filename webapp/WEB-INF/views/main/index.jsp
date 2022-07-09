<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JBlog</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">

</head>
<body>
	<div id="center-content">
		
		<!--메인 해더 자리 -->
		<c:import url="/WEB-INF/views/includes/main-header.jsp"></c:import>
		
		<form id="search-form" action="${pageContext.request.contextPath}/search" method="get">
			<fieldset>
				<input type="text" name="keyword" >
				<button id="btnSearch" type="submit" >검색</button>
			</fieldset>
			
			<fieldset>
				<label for="rdo-title">블로그 제목</label> 
				<input id="rdo-title" type="radio" name="kwdOpt" value="optTitle" > 
				
				<label for="rdo-userName">블로거 이름</label> 
				<input id="rdo-userName" type="radio" name="kwdOpt" value="optName" > 
			</fieldset>
		</form>
		
		<div id="resultList">
		<c:choose>
			<c:when test="${bList != null }">
			<c:forEach items="${ bList}" var="bMap">
			<table>
				<colgroup>
					<col style="width: 10%">
					<col style="">
					<col style="width: 20%">
					<col style="width: 20%">
				</colgroup>
				<tr>
					<td><img src="${pageContext.request.contextPath}/${bMap.LOGOFILE }"></td>
					<td id="title_view"><a href="${pageContext.request.contextPath }/${bMap.ID}">${bMap.BLOGTITLE }</a></td>
					<td>${bMap.USERNAME }(${bMap.ID })</td>
					<td>${bMap.JOINDATE }</td>
				</tr>
			</table>
			</c:forEach>
			</c:when>
			<c:when test="${param.keyword != null }"><h3>검색결과가 없습니다.</h3></c:when>
			<c:otherwise></c:otherwise>
		</c:choose>
		</div> <!-- resultList -->
		<!-- 페이징 -->
		<c:if test="${bList != null }">
			<div id="paging">
				<ol>
				<c:if test="${pageMap.prev }">
					<li><a href="${pageContext.request.contextPath }/search?pageNo=${pageMap.startPage-1 }&keyword=${param.keyword }&kwdOpt=${param.kwdOpt }">◀</a></li>
				</c:if>
				<c:forEach begin="${pageMap.startPage }" end="${pageMap.endPage }" step="1" var="pageNum">
					<c:choose>
						<c:when test="${pageNum == pageMap.currPage }">
							<li><strong><a href="${pageContext.request.contextPath }/search?pageNo=${pageNum }&keyword=${param.keyword }&kwdOpt=${param.kwdOpt }">${pageNum }</a></strong></li>
						</c:when>
						<c:otherwise>
							<li><a href="${pageContext.request.contextPath }/search?pageNo=${pageNum }&keyword=${param.keyword }&kwdOpt=${param.kwdOpt }">${pageNum }</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${pageMap.next }">
					<li><a href="${pageContext.request.contextPath }/search?pageNo=${pageNum }&keyword=${param.keyword }&kwdOpt=${param.kwdOpt }">▶</a></li>
				</c:if>
				</ol>
			</div> <!-- paging -->
		</c:if>
		
		<!-- 메인 푸터  자리-->
		<c:import url="/WEB-INF/views/includes/main-footer.jsp"></c:import>
	
	</div>
	<!-- //center-content -->
</body>
</html>