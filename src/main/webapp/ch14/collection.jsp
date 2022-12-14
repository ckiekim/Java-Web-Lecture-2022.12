<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>EL 표현언어</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body style="height: 3000px">>
    <div class="container-fluid p-3 bg-primary text-white text-center">
        <h1>JSP EL(Expression Language, 표현 언어)</h1>
        <p>3. 컬렉션</p> 
    </div>
    
    <div class="container mt-5">
        <div class="row">
            <div class="col-2"></div>
            <div class="col-8">
                <table class="table">
                    <tr><th>표현식</th><th>결과</th></tr>
                    <tr><td>\${fruits[0]}</td><td>${fruits[0]}</td></tr>
                    <tr><td>\${fruits[1]}</td><td>${fruits[1]}</td></tr>
                    <tr><td></td><td></td></tr>
                    <tr><td>\${list[0]}</td><td>${list[0]}</td></tr>
                    <tr><td>\${list[1]}</td><td>${list[1]}</td></tr>
                    <tr><td></td><td></td></tr>
                    <tr><td>\${map.key}</td><td>${map.key}</td></tr>
                    <tr><td>\${map.value}</td><td>${map.value}</td></tr>
                </table>
            </div>
            <div class="col-2"></div>
        </div>
    </div>
</body>
</html>