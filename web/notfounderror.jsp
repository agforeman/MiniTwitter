<%-- 
    Document   : notfounderror
    Created on : Oct 16, 2018, 7:32:46 PM
    Author     : Alex
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styles/header.css" />
        <link rel="stylesheet" type="text/css" href="styles/footer.css" />
        <link rel="stylesheet" type="text/css" href="styles/main.css" />
        <title>Resource Not Found Page</title>
    </head>
    <body>
        <c:import url="/header.jsp" />
            <h1>404 Error - Requested Page could not be found!</h1>
        <c:import url="/footer.jsp" />
    </body>
</html>
