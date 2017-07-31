<%-- 
    Document   : sighting
    Created on : Jul 13, 2017, 5:13:47 PM
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
        <link rel="stylesheet" type="text/css" media="screen" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.9.3/css/bootstrap-select.min.css">
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
                <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/sighting/getSights">Sightings</a></li>
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
        <h2>Sighting Page</h2>
        <div class="row">
            <div class="col col-lg-8">
                <table width='100%'>
                    <caption>List of Sightings</caption>
                    <tr>
                        <th width='30%'>Date</th>
                        <th width='30%'>Location</th>
                        <th width='30%'>Heroes Sighted</th>
                        <th width="10%"></th>
                    </tr>
                    <c:forEach var="sight" items="${sights}">
                        <tr>
                            <td>
                                <c:out value="${sight.date}"/>
                            </td>
                            <td>
                                <c:out value="${sight.location.localName}"/> at <c:out value="${sight.location.address}"/>
                            </td>
                            <td>
                                <c:forEach var="hero" items="${sight.heroes}">
                                    <c:out value="${hero.heroName}"/>, 
                                </c:forEach>
                            </td>
                            <td>
                                <form action="deleteSighting" method="GET">
                                    <button type="submit" class="btn btn-sm btn-danger" value="${sight.sightingId}" name="deleteSighting">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div class="col col-lg-4">
                <h2>Did You Spot Any Heroes?</h2>
                <form class="form-horizontal" role="form" method="POST" action="addSighting">
                    <div class="form-group">
                        <label for="add-sighting-heroes" class="control-label">Who Did You See? *</label>
                        <select id="pre-selected-sightHeroes" name="sightHeroes" class="form-control" multiple="multiple" required>
                            <c:forEach var="hero" items="${heroes}">
                                <option value="<c:out value="${hero.heroId}"/>"><c:out value="${hero.heroName}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="add-sighting-location" class="control-label">Where Did You See This? *</label>
                        <select class="selectpicker" name="sightLocation" required>
                            <option value="0" selected="selected">Choose a Location</option>
                            <c:forEach var="local" items="${locals}">
                                <option value="<c:out value="${local.locationId}"/>"><c:out value="${local.localName}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="add-sighting-date" class="control-label">When Was This? *</label>
                        <input type="date" name="sightDate" class="form-control" required/>
                    </div>
                    <div class="form-group">
                        <input type="submit" class="btn btn-default" value="Add Hero Sightings"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/heroSightingSearch.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.multi-select.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.9.3/js/bootstrap-select.min.js"></script>
    <script type="text/javascript">
        // run pre selected options
        $('#pre-selected-sightHeroes').multiSelect();
    </script>
</body>
</html>
