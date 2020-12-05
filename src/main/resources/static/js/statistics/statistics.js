
$(document).ready(function () {
    getListPostInit();

});

function getListPostInit() {

    debugger;
    const params = new URL(location.href).searchParams;
    const size = params.get("size");
    const page = params.get("page");
    const  semesters = $('#SearchSemesters').val();
    const  sites = $('#SearchSites').val();
    const  nameSearch = $('#nameSearch').val();
    const  userSearch = $('#userSearch').val();
    $.LoadingOverlay("show", {
        size: 50,
        maxSize: 50,
    });
    $.ajax({
        url: "/db/listCap?size=" + size + "&page=" + page+ "&sites=" + sites+ "&semesters=" + semesters+ "&nameSearch=" + nameSearch +"&userSearch=" + userSearch,
        type: "GET",
        success: function (data) {
            $("#Capstone-container").html(data);
            $.LoadingOverlay("hide");
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                window.location.href = "/db/listCap";
            }
        },
    });
}


function getListStatisticsSearch() {
    const params = new URL(location.href).searchParams;
    const size = null;
    const page = null;
    const  semesters = $('#SearchSemesters').val();
    const  sites = $('#SearchSites').val();
    const  nameSearch = $('#nameSearch').val();
    const  userSearch = $('#userSearch').val();
    $.LoadingOverlay("show", {
        size: 50,
        maxSize: 50,
    });
    $.ajax({
        url: "/db/listCap?size=" + size + "&page=" + page+ "&sites=" + sites+ "&semesters=" + semesters+ "&nameSearch=" + nameSearch +"&userSearch=" + userSearch,
        type: "GET",
        success: function (data) {
            $("#Capstone-container").html(data);
            $.LoadingOverlay("hide");

        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                window.location.href = "/db/listCap";
            }
        },
    });
}