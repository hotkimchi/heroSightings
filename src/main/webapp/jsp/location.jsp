<%-- 
    Document   : location
    Created on : Jul 13, 2017, 5:13:29 PM
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
        <link rel="stylesheet" type="text/css" media="screen" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.9.3/css/bootstrap-select.min.css">
        <link href="${pageContext.request.contextPath}/css/hero-sighting.css" rel="stylesheet">
        <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
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
                <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/location/getLocals">Locations</a></li>
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
        <h2>Location Page</h2>
        <div class='row'> 
            <div class='col col-lg-8'>
                <table width='100%'>
                    <caption>List of Locations</caption>
                    <tr>
                        <th width='25%'>Name of Location</th>
                        <th width='15%'>Location Info</th>
                        <th width='25%'>Organization Name</th>
                        <th width='15%'>Number of Sightings</th>
                        <th width='10%'></th>
                        <th width='10%'></th>
                    </tr>
                    <c:forEach var="local" items="${locals}">
                        <tr>
                            <td>
                                <c:out value="${local.localName}"/>
                            </td>
                            <td>
                                <input class="toggle-table" type="checkbox" data-toggle="toggle" 
                                       data-on="Show Info" data-off="Hide Info" data-size="small"
                                       value="${local.locationId}" unchecked/>
                            </td>
                            <td>
                                <c:out value="${local.org.organizationName}"/>
                            </td>
                            <td>
                                <c:out value="${numOfSightings}"/>
                            </td>
                            <td>
                                <button class="btn btn-sm btn-success update-local" value="${local.locationId}">Update</button>
                            </td>
                            <td>
                                <a href="deleteLocal?localId=${local.locationId}"><buttun class="btn btn-sm btn-danger">Delete</buttun></a>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="5">
                                <table class="hidden" id="table-${local.locationId}" width="100%">
                                    <tr>
                                        <th width='25%'>Address</th>
                                        <th width='25%'>Description</th>
                                        <th width='25%'>Latitude</th>
                                        <th width='25%'>Longitude</th>
                                    </tr>
                                    <tr>
                                        <td>
                                            <c:out value="${local.address}"/>
                                        </td>
                                        <td>
                                            <c:out value="${local.description}"/>
                                        </td>
                                        <td>
                                            <c:out value="${local.latitude}"/>
                                        </td>
                                        <td>
                                            <c:out value="${local.longitude}"/>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div class='col col-lg-4'>
                <h2 class='text-center'>Any Other Locations We Should Know About?</h2>
                <form method='POST' action='addLocal' class='form-horizontal' role='form'>
                    <div class='form-group'>
                        <label for='add-location-name' class='control-label'>What is the Name of the Location? *</label>
                        <input id="local-name" type='text' class='form-control' name='localName' required/>
                    </div>
                    <div class='form-group'>
                        <label for='add-location-address' class='control-label'>What is the Address? *</label>
                        <input id="local-address" type='text' class='form-control' name='localAddress' required/>
                    </div>
                    <div class='form-group'>
                        <label for='add-location-description' class='control-label'>Describe this Location *</label>
                        <textarea id="local-description" class='form-control text-left' name='localDescription' required></textarea>
                    </div>
                    <div class='form-group'>
                        <label for='add-location-latitude' class='control-label'>What is the Location's Latitude? *</label>
                        <input id="local-lat" type='number' step="0.00000000001"class='form-control' name='localLatitude' required/>
                    </div>
                    <div class='form-group'>
                        <label for='add-location-longitude' class='control-label'>What is the Location's Longitude? *</label>
                        <input id="local-lon" type='number' step="0.00000000001" class='form-control' name='localLongitude'  required/>
                    </div>
                    <div class='form-group'>
                        <label for='add-location-org' class='control-label'>Is This Location Part of Any Hero Organization?</label>
                        <select id="local-org" class="selectpicker" name='localOrg'>
                            <option value="0" selected="selected">Choose an Organization</option>
                            <c:forEach var="org" items="${orgs}">
                                <option value='<c:out value="${org.organizationId}"/>'><c:out value="${org.organizationName}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class='form-group'>
                        <input type='submit' name="addSubmit" class='btn btn-default add-submit' value="Add Location"/>
                        <input type='submit' name="updateSubmit" class='btn btn-default hidden update-submit' value="Update Location"/>
                        <input type='text' id="hidden-localId" name="hiddenId" class='form-control hidden'/>
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
    <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.9.3/js/bootstrap-select.min.js"></script>
    <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
</body>
</html>
