<!-- 상품목록조회 -->
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>상품 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript" src="../javascript/CommonScript.js"></script>
</head>

<body bgcolor="#ffffff" text="#000000">

	<div style="width: 98%; margin-left: 10px;">

		<form name="detailForm" action="/listProduct.do?menu=${menu}"
			method="post">

			<table width="100%" height="37" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"
						width="15" height="37" /></td>
					<td background="/images/ct_ttl_img02.gif" width="100%"
						style="padding-left: 10px;">
						
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="93%" class="ct_ttl01">
							<c:set var="title" value="${menu eq 'manage' ? '상품관리' : '상품 목록조회'}" />
							${title}
							</td>
						</tr>
					</table>
							</td>
						<td width="12" height="37">
							<img src="/images/ct_ttl_img03.gif"		width="12" height="37" />
						</td>
				</tr>
			</table>

			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
			    <tr>
			        <td align="right">
			            <c:if test="${not empty search.searchCondition}">
			                <select name="searchCondition" class="ct_input_g" style="width: 80px">
			                    <option value="1" ${search.searchCondition eq '1' ? 'selected' : ''}>상품명</option>
			                    <option value="2" ${search.searchCondition eq '2' ? 'selected' : ''}>상품가격</option>
			                </select>
			                <input type="text" name="searchKeyword" value="${search.searchKeyword}" class="ct_input_g" style="width: 200px; height: 19px">
			            </c:if>
			            <c:if test="${empty search.searchCondition}">
			                <select name="searchCondition" class="ct_input_g" style="width: 80px">
			                    <option value="1">상품명</option>
			                    <option value="2">상품가격</option>
			                </select>
			                <input type="text" name="searchKeyword" class="ct_input_g" style="width: 200px; height: 19px">
			            </c:if>
			        </td>
			        <td align="right" width="70">
			            <table border="0" cellspacing="0" cellpadding="0">
			                <tr>
			                    <td width="17" height="23"><img src="/images/ct_btnbg01.gif" width="17" height="23"></td>
			                    <td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;">
		                       	 <a href="javascript:fncGetList('1');">검색</a>
	                    </td>
			                    <td width="14" height="23"><img src="/images/ct_btnbg03.gif" width="14" height="23"></td>
			                </tr>
			            </table>
			        </td>
			    </tr>
			</table>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
				<tr>
					<td colspan="11">
							전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지
					</td>
				</tr>

				<tr>
					<td class="ct_list_b" width="100">No</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">상품명</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">가격</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">등록일</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">현재상태</td>
				</tr>
				<tr>
					<td colspan="11" bgcolor="808285" height="1"></td>
				</tr>
				
				<%-- <%
					int no = list.size();
					for (int i = 0; i < list.size(); i++) {
						Product prod = (Product)list.get(i);
						System.out.println(prod);
				%> --%>

			<c:set var="i" value="0" /> <!-- i 변수 초기화 -->
			<c:forEach var="product" items="${list}"> <!-- list의 각 요소에 대해 반복 -->
		    <c:set var="i" value="${i+1 }"/> <!-- i 값을 1 증가시킴 -->
			
			<tr class="ct_list_pop">
				<td align="center">${ i }</td>
				<td></td>
				<td align="left">
				<c:choose>
					<c:when test="${menu eq 'manage' }">
						<a href="/updateProductView.do?prodNo=${product.prodNo}&menu=${param.menu}">${product.prodName}</a>
					</c:when>
					<c:otherwise>
						<a href="/getProduct.do?prodNo=${product.prodNo}&menu=${param.menu}">${product.prodName}</a>
					</c:otherwise>
				</c:choose>
				<td></td>
				<td align="left">${product.price}</td>
				<td></td>
				<td align="left">${product.regDate}</td>
				<td></td>
				
				<c:if test="${user.role == 'admin'}">
				    <c:choose>
				        <c:when test="${product.proTranCode == '0'}">
				            <td align="left">판매중</td>
				        </c:when>
				        <c:when test="${product.proTranCode == '1'}">
				            <td align="left">결제완료
				                <a href="/updateTranCodeByProd.do?tranNo=${product.tranNo}&tranCode=2">배송하기</a>
				            </td>
				        </c:when>
				        <c:when test="${product.proTranCode == '2'}">
				            <td align="left">배송중</td>
				        </c:when>
				        <c:when test="${product.proTranCode == '3'}">
				            <td align="left">배송완료</td>
				        </c:when>
				    </c:choose>
				</c:if>
				
				<c:if test="${user.role != 'admin'}">
				    <c:choose>
				        <c:when test="${product.proTranCode == '0'}">
				            <td align="left">판매중</td>
				        </c:when>
				        <c:when test="${product.proTranCode == '1'}">
				            <td align="left">결제완료</td>
				        </c:when>
				        <c:when test="${product.proTranCode == '2'}">
				            <td align="left">배송중</td>
				        </c:when>
				        <c:when test="${product.proTranCode == '3'}">
				            <td align="left">배송완료</td>
				        </c:when>
				        <c:otherwise>
				            <td align="left">재고없음</td>
				        </c:otherwise>
				    </c:choose>
				</c:if>
		
			<tr>
			    <td colspan="11" bgcolor="D6D7D6" height="1"></td>
			</tr>

				
				</td>
			</tr>
				<tr>
				<td colspan="11" bgcolor="D6D7D6" height="1"></td>
				</tr>
		</c:forEach>
	</table>

			
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		<input type="hidden" id="currentPage" name="currentPage" value=""/>
			 <tr>
               <td align="center">
               
				<jsp:include page="../common/pageNavigator.jsp"/>	
               </td> 
            </tr>
			<!--  페이지 Navigator 끝 -->
			
			
		</form>

	</div>
</body>
</html>