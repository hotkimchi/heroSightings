<%-- 
    Document   : organization
    Created on : Jul 13, 2017, 5:13:10 PM
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
                <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/organization/getOrgs">Organizations</a></li>
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
        <h2>Organization Page</h2>
        <div class='row'>
            <div class='col col-lg-8'>
                <table width='100%'>
                    <tr>
                        <th width='15%'>Organization Name</th>
                        <th width='25%'>Description</th>
                        <th width='20%'>Motto</th>
                        <th width='10%'>Heroes</th>
                        <th width='10%'>Contact Info</th>
                        <th width='10%'></th>
                        <th width="10%"></th>
                    </tr>
                    <c:forEach var="org" items="${orgs}" >
                        <tr>
                            <td>
                                <c:out value="${org.organizationName}"/>
                            </td>
                            <td>
                                <c:out value="${org.description}"/>
                            </td>
                            <td>
                                <c:out value="${org.motto}"/>
                            </td>
                            <td>
                                <input class="toggle-table" type="checkbox" data-toggle="toggle" 
                                       data-on="Show Info" data-off="Hide Info" data-size="small" 
                                       value='${org.organizationId}' unchecked/>
                            </td>
                            <td>
                                <input class="toggle-table" type="checkbox" data-toggle="toggle" 
                                       data-on="Show Info" data-off="Hide Info" data-size="small" 
                                       value='${org.organizationId}333' unchecked/>
                            </td>
                            <td>
                                <button class="btn btn-sm btn-success update-org" value="${org.organizationId}">Update</button>
                            </td>
                            <td>
                                <a href="deleteOrg?orgId=${org.organizationId}"><buttun class="btn btn-sm btn-danger">Delete</buttun></a>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="6">
                                <table class='hidden' id='table-${org.organizationId}' width='100%'>
                                    <tr>
                                        <th width='100%'>Heroes In Organization</th>
                                    </tr>
                                    <tr>
                                        <td>
                                            <c:forEach var="hero" items="${org.heroes}">
                                                <c:out value="${hero.heroName}"/><p>    </p>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="6">
                                <table class='hidden' id='table-${org.organizationId}333' width='100%'>
                                    <tr>
                                        <th width='20%'>Email</th>
                                        <th width='20%'>Phone</th>
                                        <th width='40%'>Other Contact</th>
                                        <th width='20%'>Locations</th>
                                    </tr>
                                    <tr>
                                        <td>
                                            <c:out value="${org.email}"/>
                                        </td>
                                        <td>
                                            <c:out value="${org.phone}"/>
                                        </td>
                                        <td>
                                            <c:out value="${org.otherContactInfo}"/>
                                        </td>
                                        <td>
                                            <c:forEach var="local" items="${org.locals}">
                                                <c:out value="${local.localName}"/>  <c:out value="${local.address}"/><br/>
                                            </c:forEach>
                                        </td>
                                    </tr>

                                </table>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div class="col col-lg-4">
                <h2 class="text-center">Is There an Organization Out There we Haven't Heard of?</h2>
                <form method='POST' action='addOrg' class='form-horizontal' role='form'>
                    <div class='form-group'>
                        <label for='add-organization-name' class='control-label'>What is the Organization's Name? *</label>
                        <input id="org-name" type='text' class='form-control' name='orgName' required/>
                    </div>
                    <div class='form-group'>
                        <label for='add-organization-description' class='control-label'>Describe this Organization *</label>
                        <textarea id="org-description" class='form-control text-left' name='orgDescription' required></textarea>
                    </div>
                    <div class='form-group'>
                        <label for='add-organization-motto' class='control-label'>What is their Motto? *</label>
                        <input id="org-motto" type='text' class='form-control' name='orgMotto' required/>
                    </div>
                    <div class='form-group'>
                        <label for='add-organization-email' class='control-label'>Contact Email</label>
                        <input id="org-email" type='email' class='form-control' name='orgEmail'/>
                    </div>
                    <div class='form-group'>
                        <label for='add-organization-phone' class='control-label'>Phone Number</label>
                        <input id="org-phone" type='tel' class='form-control' name='orgPhone'/>
                    </div>
                    <div class='form-group'>
                        <label for='add-organization-otherContact' class='control-label'>Other Way To Contact Them?</label>
                        <textarea id="org-other-contact-info" class='form-control text-left' name='orgOtherContactInfo'></textarea>
                    </div>
                    <div class='form-group'>
                        <label for='add-organization-orgHeroes' class='control-label'>Who is a part of the Organization? *</label>
                        <select id="pre-selected-orgHeroes" class="form-control" name="orgHeroes" multiple="multiple"required>
                            <c:forEach var="hero" items="${heroes}">
                                <option value="<c:out value="${hero.heroId}"/>"><c:out value="${hero.heroName}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class='form-group'>
                        <label for='add-organization-orgLocations' class='control-label'>Where are they Located?</label>
                        <select id="pre-selected-orgLocations" class="form-control" name="orgLocals" multiple="multiple">
                            <c:forEach var="local" items="${locals}">
                                <option value="<c:out value="${local.locationId}"/>"><c:out value="${local.localName}"/> at
                                    <c:out value="${local.address}"/></option>
                                </c:forEach>
                        </select>
                    </div>
                    <div class='form-group'>
                        <input type='submit' class='btn btn-default add-submit' name="addSubmit" value='Add Organization'/>
                        <input type="submit" name="updateSubmit" class='btn btn-default hidden update-submit' value="Update Organization"/>
                        <input type="text" id="hidden-orgId" name="hiddenId" class="form-control hidden"/>
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
    <script src="${pageContext.request.contextPath}/js/jquery.multi-select.js"></script>
    <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
    <script type="text/javascript">
        // run pre selected options
        $('#pre-selected-orgHeroes').multiSelect();
    </script>
    <script type="text/javascript">
        // run pre selected options
        $('#pre-selected-orgLocations').multiSelect();
    </script>
<!--    <script type="text/javascript">
        $('.update-org').on
    </script>-->
</body>
</html>
