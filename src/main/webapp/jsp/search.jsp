<%-- 
    Document   : search
    Created on : Jul 21, 2017, 8:44:17 AM
    Author     : apprentice
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Hero Page</title>
        <!-- Bootstrap core CSS -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/multi-select.css">
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">  
        <link href="${pageContext.request.contextPath}/css/hero-sighting.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <p><h1>HERO <h3>(Hero Education and Relationship Organization)</h3></h1></p>
        <div class="navbar">
            <ul class="nav nav-tabs">
                <li role="presentation"><a href="${pageContext.request.contextPath}/getIndex">Home</a></li>
                <li role="presentation"><a href="${pageContext.request.contextPath}/hero/getHeroes">Heroes</a></li>
                <li role="presentation"><a href="${pageContext.request.contextPath}/power/getPowers">Powers</a></li>
                <li role="presentation"><a href="${pageContext.request.contextPath}/organization/getOrgs">Organizations</a></li>
                <li role="presentation"><a href="${pageContext.request.contextPath}/location/getLocals">Locations</a></li>
                <li role="presentation"><a href="${pageContext.request.contextPath}/sighting/getSights">Sightings</a></li>
            </ul>    
        </div>
        <form method="GET" action="" data-action="${pageContext.request.contextPath}/search/" id="search-form">
            <p style="font-weight: bold">Search: <select name="category" id="search">
                <option value="0">Choose a Category</option>
                <option value="1">Heroes</option>
                <option value="2">Powers</option>
                <option value="3">Organizations</option>
                <option value="4">Locations</option>
                <option value="5">Sightings</option>
            </select>
            By: <select name="byCategory" id ="searchBy">
                <option>Choose a Category</option>
            </select></p>
            <label for="search-for" id="searchFor">Choose a Category</label>
            <input type="text" name="searchInput" id="searchInput" required/>
            <input type="submit" class="btn btn-default" value="Search"/>
        </form>
        <div class="row">
            <div class="col col-lg-8">
                <table width="100%">
                    <tr>
                        <th width="100%">Found</th>
                    </tr>
                    <c:forEach var="hero" items="${heroes}">
                        <tr>
                            <td>
                                <c:out value="${hero.heroName}"/>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:forEach var="power" items="${powers}">
                        <tr>
                            <td>
                                <c:out value="${power.key.powerName}"/> / <c:out value="${power.value}"/>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:forEach var="powerfulPower" items="${powerfulPowers}">
                        <tr>
                            <td>
                                <c:out value="${powerfulPower.powerName}"/>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:forEach var="org" items="${orgs}">
                        <tr>
                            <td>
                                <c:out value="${org.organizationName}"/>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:forEach var="local" items="${locals}">
                        <tr>
                            <td>
                                <c:out value="${local.localName}"/> at <c:out value="${local.address}"/>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:forEach var="sight" items="${sights}">
                        <tr>
                            <td>
                                <c:out value="${sight.date}"/>  at <c:out value="${sight.location.localName}"/> with
                                <c:forEach var="hero" items="${sight.heroes}">
                                    <c:out value="${hero.heroName}"/>,
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>

</body>
<script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/heroSightingSearch.js"></script>
</html>
