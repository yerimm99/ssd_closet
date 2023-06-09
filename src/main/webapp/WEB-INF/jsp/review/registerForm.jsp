<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>리뷰 등록 페이지</title>
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
        
       .product-info-container {
		  display: flex;
		  flex-direction: column;
		  align-items: center;
		  text-align: center;
		}
		
		.product-image {
		  margin-bottom: 20px; /* Adjust the spacing as desired */
		}
		        
        .resized-image {
            max-width: 200px;
            max-height: 200px;
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
            border-radius: 10px;
        }
        
        .error-message {
            color: red;
        }
        
        .content-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            text-align: center;
        }
    </style>
</head>
<body>
    <!-- 메뉴바 -->
    <jsp:include page="../menu.jsp" />
    <hr>
    <div class="container">
        <h3>판매자 리뷰 작성하기</h3>
        <div class="product-info-container"><br><br>
            <div class="product-image">
                <img src="../../upload/${product.picture1}" class="resized-image">
            </div>
            <div class="product-details">
                <b>상품명:</b> ${product.name}<br>
                <b>색상:</b> ${product.color}<br>
                <b>사이즈:</b> ${product.size}<br>
                <br><br>
            </div>
        </div>
        <hr>
        <div class="content-container">
            <div>
                <form method="POST" action="/review/registerForm.do?productId=${product.productId}">
                    <div class="bid-input-container">
                        <b>별점:</b>
                        <input type="text" class="bid-input" placeholder="0~5점" name="rating" required><br>
                        <b>한줄평:</b>
                        <input type="text" class="bid-input" placeholder="한줄평" name="content" required><br>
                    </div><br><br>
                    <input class="submit-button" type="submit" value="저장하기">
                </form>
            </div>
        </div>
    </div>
</body>
</html>
