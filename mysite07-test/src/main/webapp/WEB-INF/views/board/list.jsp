<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	pageContext.setAttribute("newLine", "\n");

%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form"
					action="${pageContext.request.contextPath }/board" method="post">
					<input type="radio" name="chk_info" id=conORtit " value="conORtit"
						checked="true"> 내용/제목 <input type="radio" name="chk_info"
						id="name" value="name"> 글쓴이 <input type="text" id="kwd"
						name="kwd" value=""> <input type="submit" value="찾기">
					<input type="hidden" name="a" value="search">
				</form>

				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>삭제</th>
					</tr>
					<c:set var='listcount' value='${totalcount }' />
					<c:forEach var="vo" varStatus="status" items="${list }">
						<tr>

							<td>${listcount-(status.index+(pageNo-1)*5) }</td>
							<td style="text-align:left; padding-left:${20*vo.depth }px">
								<a
								href="${pageContext.request.contextPath }/board/view/${vo.no}/${vo.name}">
									<c:if test="${(vo.depth)!='0' }">
										<img
											src='${pageContext.request.contextPath }/assets/images/reply.png'>
									</c:if>${vo.title }</a>
							</td>
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td><c:if test="${authUser.name==vo.name }">
									
									<a href="${pageContext.request.contextPath }/board/delete/${vo.no}/${pageNo}"
										class="del"><img src='${pageContext.request.contextPath }/assets/images/recycle.png'> </a>
								</c:if></td>
						</tr>
					</c:forEach>

				</table>
				
				<c:choose>
					<c:when test="${pageNo<=2 }">
						<c:set var="start" value="${1 }" />
						<c:set var="end" value="${start+4 }" />
					</c:when>
					<c:when test="${pageNo>2 }">
						<c:set var="start" value="${pageNo-2 }" />
						<c:set var="end" value="${start+4 }" />
					</c:when>
				</c:choose>

				<c:set var="pageN" value="${start }" />

				<c:choose>
					<c:when test="${pageNo==maxPage }">
						<c:set var="end" value="${maxPage-2 }" />
						<c:set var="start" value="${pageNo-4 }" />


					</c:when>
					<c:when test="${pageNo==(maxPage-1) }">
						<c:set var="start" value="${1 }" />
						<c:set var="end" value="${maxPage }" />

						<c:if test="${(maxPage-1)>2}">
							<c:set var="end" value="${maxPage }" />
							<c:set var="start" value="${pageNo-2 }" />
						</c:if>

					</c:when>
					<c:when test="${pageNo!=maxPage }">
						<c:set var="end" value="${start+4 }" />
					</c:when>
				</c:choose>



				<div class="pager">
					<ul>
						
						<li><c:choose>
						<c:when test = "${pageNo !=1 }">
							<a href="${pageContext.request.contextPath }/board/paging/${pageNo-1}">◀</a></li>
						</c:when>
						<c:when test = "${pageNo ==1 }">
							
						</c:when>
						</c:choose>
						<c:forEach begin="${start }" end="${end }" varStatus="status">

							<li><a
								href="${pageContext.request.contextPath }/board/paging/${pageN }"><c:if
										test="${pageN == pageNo }">
										<span style="color: red">
									</c:if>${pageN }</a></li>
							<c:set var="pageN" value="${pageN+1 }" />
						</c:forEach>


						<li><c:choose>
								<c:when test="${maxPage!=pageNo }">
									<a
										href="${pageContext.request.contextPath }/board/paging/${pageNo+1}">▶</a>
								</c:when>
								<c:when test="${maxPage==pageNo }">
								
							</c:when>
							</c:choose></li>
					</ul>
				</div>




				<c:choose>
					<c:when test='${empty authUser.name }'>
					</c:when>
					<c:otherwise>
						<div class="bottom">
							<a href="${pageContext.request.contextPath }/board/write"
								id="new-book">글쓰기</a>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>