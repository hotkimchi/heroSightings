<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Index Page</title>
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
                <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/getIndex">Home</a></li>
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
            <input type="text" name="searchInput" id="searchInput"/>
            <input type="submit" class="btn btn-default" value="Search"/>
        </form>
        <h2>Home Page</h2>
        <div class="row">
            <div class="col col-lg-8">
                <table style="width:100%">
                    <caption>Last Ten Heroes Sighted</caption>
                    <tr>
                        <th width="40%">Hero</th>
                        <th width="40%">Location</th>
                        <th width="20%">Date</th>
                    </tr>
                    <c:forEach var="sight" items="${sights}">
                        <tr>
                            <td>
                                <c:forEach var="hero" items="${sight.heroes}">
                                    <c:out value="${hero.heroName}" />
                                </c:forEach>
                            </td>
                            <td>
                                <c:out value="${sight.location.localName}"/>
                            </td>
                            <td>
                                <c:out value="${sight.date}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div class="col col-lg-4 text-center">
<!--                <h2>Did You Spot Any Heroes?</h2>
                <form class="form-horizontal" role="form" method="POST" action="addSighting">
                    <div class="form-group">
                        <h3>Who Did You See?</h3>
                        <select id="pre-selected-homeHeroes" class="form-control" multiple="multiple">
                            <c:forEach var="hero" items="${heroes}">
                                <option value="<c:out value="${hero.heroId}"/>"><c:out value="${hero.heroName}"/></option>
                            </c:forEach>
                        </select>
                        <h3>Where Was This?</h3>
                        <select class="control-control" name="sightLocation">
                            <c:forEach var="local" items="${locals}">
                                <option value="<c:out value="${local.locationId}"/>"><c:out value="${local.localName}"/></option>
                            </c:forEach>
                        </select>
                        <h3>When Was This?</h3>
                        <input type="date" name="sightDate" class="form-control"/>
                    </div>
                </form>-->
            </div>
        </div>
    </div>
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/heroSightingSearch.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.multi-select.js"></script>
    <script type="text/javascript">
        // run pre selected options
        $('#pre-selected-homeHeroes').multiSelect();
    </script>
</body>
</html>

