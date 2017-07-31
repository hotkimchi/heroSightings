<%-- 
    Document   : hero
    Created on : Jul 13, 2017, 4:35:09 PM
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
                <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/hero/getHeroes">Heroes</a></li>
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
        <h2>Hero Page</h2>
        <div class="row">
            <div class="col col-lg-8">

                <table style="width:100%">
                    <caption>List of Heroes</caption>
                    <tr>
                        <th width="70%" class="text-left">Hero</th>
                        <th width="10%"></th>
                        <th width="10%"></th>
                        <th width="10%"></th>
                    </tr>
                    <c:forEach var="hero" items="${heroes}">
                        <tr id="hero-info-${hero.heroId}">
                            <td>
                                <c:out value="${hero.heroName}"/> 
                                <table class="hidden" id="table-${hero.heroId}" width="100%">
                                    <tr>
                                        <th width="30%">Description</th>
                                        <th width="20%">Good or Evil</th>
                                        <th width="20%">Powers / Level</th>
                                        <th width="20%">Organizations</th>
                                        <th width="10%">Times Sighted</th>
                                    </tr>
                                    <tr>
                                        <td>
                                            <c:out value="${hero.description}"/>
                                        </td>
                                        <td>
                                            <c:out value="${hero.goodOrEvil}"/>
                                        </td>
                                        <td>
                                            <c:forEach var="power" items="${hero.powers}">
                                                <c:out value="${power.key.powerName}"/><br/> <c:out value="${power.value}"/><br/>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:forEach var="org" items="${hero.orgs}">
                                                <c:out value="${org.organizationName}"/>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:out value="${hero.sightings.size()}" />
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td>
                                <input class="toggle-table" type="checkbox" data-toggle="toggle" 
                                       data-on="Show Info" data-off="Hide Info" data-size="small" 
                                       value="${hero.heroId}" unchecked/>

                            </td>
                            <td>
                                <button class="btn btn-sm btn-success update-hero" value="${hero.heroId}">Update</button
                            </td>
                            <td>
                                <a href="deleteHero?heroId=${hero.heroId}"><buttun class="btn btn-sm btn-danger">Delete</buttun></a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div class="col col-lg-4">
                <h2 class='text-center'>Any Heroes Not on the List?</h2>
                <hr/>
                <form class="form-horizontal" role="form" method="POST" action="addHero">
                    <div class="form-group">
                        <label for="add-hero-name" class="control-label">Hero's Name *</label>
                        <input id="hero-name" type="text" class="form-control" name="heroName" required/>
                    </div>
                    <div class="form-group">
                        <label for="add-hero-description" class="control-label">Hero's Description *</label>
                        <textarea id="hero-description" class='form-control text-left' name='heroDescription' required></textarea>
                    </div>
                    <div class='form-group'>
                        <label for='add-hero-good' class='control-label'>Is the Hero Good or Evil? *</label><br/>
                        <input id="hero-good" type='radio' name='isGood' value='good' checked="unchecked" required>   Good</input>
                        <input id="hero-bad" type="radio" name='isGood' value='evil' checked="unchecked" required>   Evil</input>
                    </div>
                    <div class='form-group'>
                        <label for='add-hero-org' class='control-label'>Is the Hero Part of Any Organizations?</label>
                        <select id="pre-selected-orgs" class="form-control" name="organizations" multiple="multiple">
                            <c:forEach var="org" items="${orgs}">
                                <option value="<c:out value="${org.organizationId}"/>"><c:out value="${org.organizationName}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class='form-group'>
                        <label for='add-hero-power' class='control-label'>What Powers does the Hero Have?</label>
                        <select id="pre-selected-powers" class="form-control" name="superpowers" multiple="multiple">
                            <c:forEach var="power" items="${powers}">
                                <option id='addPowerLevel' value="<c:out value="${power.superPowerId}"/>"><c:out value="${power.powerName}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id="getSelectedPowers" class='form-group'>
                        <label for='add-hero-powerLevel' class='control-label'>What is the Power Level?</label>
                    </div>
                    <div class='form-group'>
                        <label for='add-hero-sightings' class='control-label'>Where Have You Seen This Hero?</label>
                        <select id="pre-selected-locations" class="form-control" name="sighitngLocations" multiple="multiple">
                            <c:forEach var="local" items="${locals}">
                                <option id='addLocation' value="<c:out value="${local.locationId}"/>"><c:out value="${local.localName}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id="getSightingDate" class='form-group'>
                        <label for='add-sighting-date' class='control-label'>When Was This?</label>
                    </div>
                    <div class='form-group'>
                        <input type='submit' name="addSubmit" class='btn btn-default add-submit' value='Add Hero'/>
                        <input type="submit" name="updateSubmit" class='btn btn-default hidden update-submit' value="Update Hero"/>
                        <input type="text" id="hidden-heroId" name="hiddenId" class="form-control hidden"/>
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
        $('#pre-selected-orgs').multiSelect();
    </script>
    <script type="text/javascript">
        // run pre selected options
        $('#pre-selected-powers').multiSelect();
    </script>
    <script type="text/javascript">
        // run pre selected options
        $('#pre-selected-locations').multiSelect();
    </script>
</body>
</html>
