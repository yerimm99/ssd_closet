<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="targetUrl"><c:url value='/bid/confirmBid.do' /></c:set>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>입찰 페이지</title>
  <style>
    /* KREAM 컬러 팔레트 */
    :root {
      --kream-primary-color: #FF3366;
      --kream-secondary-color: #333333;
      --kream-tertiary-color: #F5F5F5;
    }

    body {
      margin: 0;
      background-color: var(--kream-tertiary-color);
    }

    .container {
      max-width: 600px;
      margin: 0 auto;
      padding: 20px;
      background-color: #fff;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    h3 {
      font-size: 24px;
      margin-bottom: 20px;
      color: var(--kream-secondary-color);
      text-align: center;
    }

    .product-info {
      display: flex;
      align-items: center;
      margin-bottom: 20px;
    }

    .product-image {
      width: 200px;
      height: 200px;
      margin-right: 20px;
      background-color: var(--kream-tertiary-color);
      border: 1px solid #ccc;
    }

    .product-details {
      flex: 1;
    }

    .bid-form {
      margin-top: 20px;
      text-align: center;
    }

    .bid-input-container {
      display: flex;
      align-items: center;
      justify-content: center;
      margin-bottom: 10px;
    }

    .bid-input {
      padding: 10px;
      border: 1px solid #ccc;
      width: 150px;
      margin-right: 10px;
      text-align: right;
      font-weight: bold;
      font-size: 18px;
    }

    .bid-input:focus {
      outline: none;
      border-color: var(--kream-primary-color);
      box-shadow: 0 0 0 2px rgba(255, 51, 102, 0.5);
    }

    .submit-button {
      padding: 10px 20px;
      background-color: var(--kream-primary-color);
      color: #fff;
      border: none;
      cursor: pointer;
      border-radius:10px;
    }

    .error-message {
      color: red;
    }
  </style>
</head>
<body>
	<!-- 메뉴바 -->
	<jsp:include page = "../menu.jsp"/>
	<hr>
  <div class="container">
    <h3>입찰하기</h3>
    <div class="product-info">
      <div class="product-image"><img src = "../../upload/${product.picture1}" width="200" height="200"></div>
      <div class="product-details">
        <b>상품명 : ${product.name}</b><br><br>
        색상 : ${product.color}<br>
        사이즈 : ${product.size} <br><br>
      </div>
    </div>
    <hr>
    <div>
      <form:form class="bid-form" modelAttribute="bidForm" method="POST" action="${targetUrl}">
        <div class="bid-input-container">
        <B>구매 희망가</B> &nbsp;&nbsp;&nbsp; <form:input class="bid-input" placeholder="희망가입력" path="bid.bidPrice"/> <B>원</B>
        </div>
        <B><form:errors path="bidPrice" class="error-message" /></B>
        <br><br><input class="submit-button" type="submit" value="입찰하기">
      </form:form>
    </div>
  </div>
</body>
</html>
