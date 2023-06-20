<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>&Closet Search</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
        }
        
        .search-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .product-section {
            margin-bottom: 30px;
        }
        
        .product-section h2 {
            font-size: 20px;
            font-weight: bold;
            margin-bottom: 10px;
        }
        
        .product-list {
            display: flex;
            flex-wrap: wrap;
            justify-content: flex-start;
            align-items: flex-start;
            gap: 20px;
        }
        
        .product-card {
            flex: 1 1 calc(25% - 20px);
            max-width: calc(25% - 20px);
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
        
        .search-result {
            text-align: center;
            margin-bottom: 20px;
        }
        
        .search-result-text {
            font-size: 18px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<!-- 메뉴바 -->
<jsp:include page="../menu.jsp"/>
<hr>
    <div class="search-container">
        <div class="search-result">
            <p class="search-result-text">"${keyword}"의 검색 결과입니다.</p>
        </div>
        
        <div class="product-section">
            <h2>경매 상품</h2>
            <div class="product-list">
                <c:forEach var="prod" items="${productList.AuctionList.pageList}">
                    <div class="product-card">
                        <a href="<c:url value='/auction/detail.do'><c:param name='productId' value='${prod.productId}' /></c:url>">
                            <img src="../../upload/${prod.picture1}" alt="Product Image">
                            <h3>${prod.name}</h3>
                            <p>
                                <c:choose>
                                    <c:when test="${empty prod.price}">
                                        <span>${prod.startPrice}원</span><br>
                                        경매 시작가
                                    </c:when>
                                    <c:otherwise>
                                        <span>${prod.price}원</span><br>
                                        현재 최고가
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
        
        <div class="product-section">
            <h2>공동구매 상품</h2>
            <div class="product-list">
                <c:forEach var="gprod" items="${productList.GroupbuyList.pageList}">
                    <div class="product-card">
                        <a href="<c:url value='/groupbuy/detail.do'><c:param name='productId' value='${gprod.productId}' /></c:url>">
                            <img src="../../upload/${gprod.picture1}" alt="Product Image">
                            <h3>${gprod.name}</h3>
                            <p> 
                                <br>${gprod.price}원<br> 모집인원: ${gprod.peopleSum} / ${gprod.peopleNum}
                            </p>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</body>
</html>