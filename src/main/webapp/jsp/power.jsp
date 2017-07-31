<%-- 
    Document   : power
    Created on : Jul 13, 2017, 5:12:49 PM
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
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/hero-sighting.css" rel="stylesheet">
        <script language="javascript">
            function updatePower(powerId) {
                var heroName = 
            }
        </script>
    </head>
    <body>
        <div class="container">
            <p><h1>HERO <h3>(Hero Education and Relationship Organization)</h3></h1></p>
        <div class="navbar">
            <ul class="nav nav-tabs">
                <li role="presentation"><a href="${pageContext.request.contextPath}/getIndex">Home</a></li>
                <li role="presentation"><a href="${pageContext.request.contextPath}/hero/getHeroes">Heroes</a></li>
                <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/power/getPowers">Powers</a></li>
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
            <input type="submit" class="btn btn-default" value="Search" required/>
        </form>
        <h2>Power Page</h2>
        <div class="row">
            <div class="col col-lg-8">
                <table width="100%">
                    <caption>List of Powers</caption>
                    <tr>
                        <th width="20%">Power Name</th>
                        <th width="45%">Power's Description</th>
                        <th width='15%'>Heroes with Power</th>
                        <th width='10%'></th>
                        <th width="10%"></th>
                    </tr>
                    <c:forEach var="power" items="${powers}">
                        <tr>
                            <td id="power-name-${power.superPowerId}">
                                <c:out value="${power.powerName}"/>
                            </td>
                            <td id="power-description-${power.superPowerId}">
                                <c:out value="${power.description}" />
                            </td>
                            <td id="power-heroes-${power.superPowerId}">
                                <c:forEach var="hero" items="${power.heroes}">
                                    <c:out value="${hero.heroName}"/><br/>
                                </c:forEach>
                            </td>
                            <td>
                                <button class="btn btn-sm btn-success update-power" value="${power.superPowerId}">Update</button>
                            </td>
                            <td>
                                <a href="deletePower?powerId=${power.superPowerId}"><button class="btn btn-sm btn-danger">Delete</button></a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div class='col col-lg-4'>
                <h2 class='text-center'>Is There an Unknown Power Out There?</h2>
                <form method='POST' action='addPower' id="powerForm" class='form-horizontal' role='form'>
                    <div class='form-group'>
                        <label for='add-power-name' class='control-label'>What is the Power's Name? *</label>
                        <input type='text' id="power-name" class='form-control col-md-2' name='powerName' required/>
                    </div>
                    <div class='form-group'>
                        <label for='add-power-description' class='control-label'>Describe What the Power Does *</label>
                        <textarea id="power-description" class='form-control text-left' name='powerDescription' required></textarea>
                    </div>
                    <div class='form-group'>
                        <input type='submit' name="addSubmit" class='btn btn-default add-submit' value='Add Power'/>
                        <input type="submit" name="updateSubmit" class='btn btn-default hidden update-submit' value="Update Power"/>
                        <input type="text" id="hidden-powerId" name="hiddenId" class="form-control hidden"/>
                        <button type="button" class="btn btn-default hidden back-to-add">Back To Add</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/heroSightingSearch.js"></script>
</body>
</html>
