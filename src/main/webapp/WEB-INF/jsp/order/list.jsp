<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>주문 리스트</title>
     <style type="text/css">
        body {
            font-family: Arial, sans-serif;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .product-list {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            grid-gap: 20px;
        }
        
        .product-card {
            background-color: #fff;
            border-radius: 4px;
            padding: 20px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        
        .product-card a {
            color: #333;
            text-decoration: none;
        }
        
        .product-card img {
            max-width: 100%;
            height: auto;
            margin-bottom: 10px;
        }
        
        .product-card h3 {
            font-size: 18px;
            margin-bottom: 10px;
        }
        
        .product-card p {
            font-size: 14px;
            color: #888;
            margin-bottom: 10px;
        }
        
        .product-card span {
            font-weight: bold;
            font-size: 16px;
            color: #FF4E50;
        }
        .page-button {
		  border: none;
		  background: none;
		  color: inherit;
		  cursor: pointer;
		  padding: 0;
		}

        .page-buttons {
            text-align: center;
            margin-top: 20px;
        }
        
        .page-buttons button {
            background-color: #fff;
            border: 1px solid #ddd;
            padding: 5px 10px;
            margin: 0 5px;
            cursor: pointer;
            font-family: Arial, sans-serif;
            font-size: 14px;
            color: #333;
            text-decoration: none;
        }
        
        .page-buttons button:hover {
            background-color: #f5f5f5;
        }
        
        .page-buttons .current {
            color: #333;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="order-list">
            <c:choose>
                <c:when test="${orderList.getSource() == null}">
                    <div class="sell">
                        공동구매 상품이 하나도 없습니다.
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="order" items="${orderList.pageList}">
                        <div class="order-card">
                            <a href="<c:url value='/order/detail.do'>
                                <c:param name='orderId' value='${order.orderId}' />
                            </c:url>">
<%--                                 <img src="../../upload/${order.picture1}" alt="Product Image"> --%>
                                <h3>주문번호 : ${order.orderId}</h3>
                                <p> 
                                <br>상품번호 : ${order.productId}
                                <br>${order.price}원
                                <br>주문날짜: ${order.orderDate}
                                <br>배송지 : ${order.shipAddress}
                                </p>
                            </a>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
	
        <div class="page-buttons">
            <!-- 이전 페이지 버튼 -->
            <c:if test="${orderList.page > 0}">
                <form action="/myPage/myOrderList2.do?pageName=previous" method="get" style="display: inline;">
                    <input type="hidden" name="pageName" value="previous">
                    <button type="submit">
                        &lt;
                    </button>
                </form>
            </c:if>

            <c:forEach var="pageNum" begin="1" end="${orderList.pageCount}">
			    <c:choose>
			        <c:when test="${pageNum == orderList.page + 1}">
			            <span class="current">${pageNum}</span>
			        </c:when>
			        <c:otherwise>
			              ${pageNum}
			        </c:otherwise>
			    </c:choose>
			</c:forEach>
			

            <!-- 다음 페이지 버튼 -->
            <c:if test="${orderList.page + 1 < orderList.pageCount}">
                <form action="/myPage/myOrderList2.do?pageName=next" method="get" style="display: inline;">
                    <input type="hidden" name="pageName" value="next">
                    <button type="submit">
                        &gt;
                    </button>
                </form>
            </c:if>
        </div>
</body>
</html>