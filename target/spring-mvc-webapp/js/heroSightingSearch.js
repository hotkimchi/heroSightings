/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    $("#getSelectedPowers").hide();
    $("#getSightingDate").hide();
    var searchCategory = [
        "chooseCategory",
        "heroes",
        "powers",
        "organizations",
        "locations",
        "sightings"
    ]
    var searchOptions = [
        ["Choose a Category"],
        ["Choose a Category", "power", "organization", "location", "date"],
        ["Choose a Category", "hero", "power level"],
        ["Choose a Category", "hero", "location"],
        ["Choose a Category", "hero", "date", "organization"],
        ["Choose a Category", "hero", "location", "date"]];
    var labelOptions = [
        ["Choose a Category"],
        ["Choose a Category", "Power", "Organization", "Location", "Date"],
        ["Choose a Category", "Hero", "Power Level"],
        ["Choose a Category", "Hero", "Location"],
        ["Choose a Category", "Hero", "Date", "Organization"],
        ["Choose a Category", "Hero", "Location", "Date"]];
    var placeholderOptions = [
        ["Choose a Category"],
        ["Choose a Category", "Name of Power", "Name of Organization", "Name of Location", "DD / MM / YYYY"],
        ["Choose a Category", "Name of Hero", "Whole Number"],
        ["Choose a Category", "Name of Hero", "Name of Location"],
        ["Choose a Category", "Name of Hero", "MM / DD / YYYY", "Organization Name"],
        ["Choose a Category", "Name of Hero", "Name of Location", "DD / MM / YYYY"]];
    var currentCategory;
    $("#search").on('change',function () {
        $("#searchBy").children().remove();
        var idx = $(this).find(':selected').val();
        currentCategory = searchCategory[idx];
        var searchForm = $("#search-form");
        searchForm.attr("action", searchForm.data('action') + currentCategory);
        for (var i = 0; i < searchOptions[idx].length; i++) {
            $("#searchBy").append($('<option>', {
                value: i,
                text: searchOptions[idx][i]
            }));
        }
    });
    $("#searchBy").on('change',function () {
        var forIdx = $("#search").find(':selected').val();
        var byIdx = $(this).find(':selected').val();
        $("#searchFor").text(labelOptions[forIdx][byIdx]);
        if (forIdx == 1 && byIdx == 4 || forIdx == 4 && byIdx == 2
                || forIdx == 5 && byIdx == 3){
            $("#searchInput").prop("type", "date");
        } else if (forIdx == 2 && byIdx == 2 ) {
            $("#searchInput").prop("type", "number");
        } else {
            $("#searchInput").prop("type", "text");
        }
        $("#searchInput").attr("Placeholder", placeholderOptions[forIdx][byIdx]);
    });
    $("#pre-selected-powers").on('change', function () {
        var powers = $(this).val();
        var selectedOptions = $("#pre-selected-powers option:selected");
        if (powers.length !== 0) {
            $("#getSelectedPowers").show();
        } else {
            $("#getSelectedPowers").hide();
        }
        $("#getSelectedPowers input").remove();
        $("#getSelectedPowers .power-label").remove();
        $("#getSelectedPowers br").remove();
        for (var i = 0; i < powers.length; i++) {
            var powerLabel = '<br/><label for="add-hero-powerLevel" class="control-label power-label">' + selectedOptions[i].text + '</label><br/>';
            var powerName = '<input type="number" class="form-control" name="powerId-' + powers[i] + '"/>';
            $("#getSelectedPowers").append(powerLabel).append(powerName);
        }
    });
    $("#pre-selected-locations").on('change', function () {
        var locals = $(this).val();
        var selectedOptions = $("#pre-selected-locations option:selected");
        if (locals.length !== 0) {
            $("#getSightingDate").show();
        } else {
            $("#getSightingDate").hide();
        }
        $("#getSightingDate .when-label").remove();
        $("#getSightingDate input").remove();
        $("#getSightingDate br").remove();
        for (var i = 0; i < locals.length; i++) {
            var localLabel = '<br/><label for="add-sighting-date" class="control-label when-label">' + selectedOptions[i].text + '</label>';
            var localName = '<input type="date" class="form-control" name="localId-' + locals[i] + '"/>';
            $("#getSightingDate").append(localLabel).append(localName);
        }
    });
    $(".toggle-table").change( function () {
        var classId = $(this).val();
        $('#table-' + classId).toggleClass('hidden');
    });
    $(".update-power").on('click', function () {
        var powerId = $(this).val();
        $.ajax({
            type: 'GET',
            url: 'power/' + powerId,
            dataType: 'json',
            success: function (power) {
                $("#power-name").val(power.powerName);
                $("#power-description").html(power.description);
                $("#hidden-powerId").val(power.superPowerId);
                $(".add-submit").addClass('hidden');
                $(".update-submit").removeClass('hidden');
                $(".back-to-add").removeClass('hidden');
            }
        });
    });
    $(".back-to-add").on('click', function () {
        $(".add-submit").removeClass('hidden');
        $(".update-submit").addClass('hidden');
        $(".back-to-add").addClass('hidden');
    });
    $(".update-local").on('click', function () {
        var localId = $(this).val();
        $.ajax({
            type: 'GET',
            url: 'location/' + localId,
            dataType: 'json',
            success: function (local) {
                $('#local-name').val(local.localName);
                $('#local-address').val(local.address);
                $('#local-description').html(local.description);
                $('#local-lat').val(local.latitude);
                $('#local-lon').val(local.longitude);
                $('#hidden-localId').val(local.locationId);
                if (local.org.organizationId !== null) {
                    $('#local-org').val(local.org.organizationId);
                }
                $('.add-submit').addClass('hidden');
                $('.update-submit').removeClass('hidden');
                $('.back-to-add').removeClass('hidden');
            }
        });
    });
    $(".update-org").on('click', function () {
        var orgId = $(this).val();
        $.ajax({
            type: 'GET',
            url: 'org/' + orgId,
            dataType: 'json',
            success: function (org) {
                $('#pre-selected-orgLocations option:selected').removeAttr("selected");
                $('#pre-selected-orgHeroes option:selected').removeAttr("selected");
                $('#org-name').val(org.organizationName);
                $('#org-description').html(org.description);
                $('#org-motto').val(org.motto);
                $('#org-email').val(org.email);
                $('#org-phone').val(org.phone);
                $('#org-other-contact-info').html(org.otherContactInfo);
                var orgLocals = org.locals;
                var orgHeroes = org.heroes;
                $.each(orgLocals, function(i,e) {
                    var option = $('#pre-selected-orgLocations option[value=' + e.locationId + ']');
                    option.attr("selected", "selected");
                    
                });
                $('#pre-selected-orgLocations').multiSelect("refresh");
                $.each(orgHeroes, function(i,e) {
                    var option = $('#pre-selected-orgHeroes option[value=' + e.heroId + ']');
                    option.attr("selected", "selected");
                });
                $('#pre-selected-orgHeroes').multiSelect("refresh");
                $('#hidden-orgId').val(org.organizationId);
                $('.add-submit').addClass('hidden');
                $('.update-submit').removeClass('hidden');
                $('.back-to-add').removeClass('hidden');
            }
        });
    });
    $(".update-hero").on('click', function() {
        var heroId = $(this).val();
        $.ajax({
            type: 'GET',
            url: 'hero/' + heroId,
            dataType: 'json',
            success: function (hero) {
                $('#hero-name').val(hero.heroName);
                $('#hero-description').html(hero.description);
                if (hero.goodHero === true){
                    $('#hero-good').prop("checked", "checked");
                } else {
                    $('#hero-bad').prop("checked", "checked");
                }
                var heroOrgs = hero.orgs;
                $.each(heroOrgs, function(i,e) {
                    var option = $('#pre-selected-orgs option[value=' + e.organizationId + ']');
                    option.attr("Selected", "selected");
                });
                $('#pre-selected-orgs').multiSelect("refresh");
                var heroPowers = hero.powers;
                $.each(heroPowers, function(key, value) {
                    var option = $('#pre-selected-powers option[value=' + value.superPowerId + ']');
                    option.attr("selected", "selected");
                });
                $('#pre-selected-powers').multiSelect("refresh");
                var heroSights = hero.sightings;
                $.each(heroSights, function(i,e) {
                    var option = $('#pre-selected-locations option[value=' +e.location.locationId + ']');
                    option.attr("selected", "selected");
                });
                $('#pre-selected-locations').multiSelect("refresh");
                $('#hidden-heroId').val(hero.heroId);
                $('.add-submit').addClass('hidden');
                $('.update-submit').removeClass('hidden');
                $('.back-to-add').removeClass('hidden');
            }
        });
    });
    
});



