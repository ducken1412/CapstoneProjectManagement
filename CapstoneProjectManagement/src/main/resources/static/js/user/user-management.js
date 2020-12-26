$(document).ready(function () {
    getSearch();
    $('#notification-container').css('display', 'none');
});


function getSearch() {
    const params = new URL(location.href).searchParams;
    var site = $('#dr-site').val();
    var semester = $('#dr-semester').val();
    var type = $('#dr-type').val().toString();
    $.LoadingOverlay("show", {
        size: 50,
        maxSize: 50,
    });
    $.ajax({
        url: "/student-management?site=" + site + "&semester=" + semester + "&type=" + type,
        type: "GET",

        success: function (data) {
            $('#student-container').html(data);
            $('#student-container').LoadingOverlay("hide");
            $.LoadingOverlay("hide");
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                // window.location.href = "/forum";
            }
        },

    });
}

$(document).on("click", "#btn-searchStudentManagement", function () {
    getSearch();
});

$(document).on("click", "#btn-exportExcel", function () {

    var site = $('#dr-site').val();
    var semester = $('#dr-semester').val();
    var type = $('#dr-type').val().toString();
    $.ajax({
        url: "/exportExcelUser?site=" + site + "&semester=" + semester + "&type=" + type,
        type: "GET",
        success: function (data) {
            debugger;
            $('#linkDowloadExel')[0].click();
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                window.location.href = "/ad/capstoneproject";
            }
        },
    });
});


$(document).on("change", "#activeUser", function () {
    const postId = $(this).attr("postId");

    const status = $(this).val();

    $.ajax({
        url: "/update-StatusUser?id=" + postId + "&status=" + status,
        type: "GET",
        success: function (data) {
            if(data === "true"){
                $.showNotification({
                    body: "Active User Successfully",
                    type: "success",
                    duration: 2000,
                    shadow: "0 2px 6px rgba(0,0,0,0.2)",
                    zIndex: 100,
                    margin: "1rem"
                })
            }else {
                $.showNotification({
                    body: "InActive User Successfully",
                    type: "danger",
                    duration: 2000,
                    shadow: "0 2px 6px rgba(0,0,0,0.2)",
                    zIndex: 100,
                    margin: "1rem"
                })
            }
            getSearch();
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                window.location.href = "/ad/capstoneproject";
            }
        },
    });
})



$(document).on("change", "#deActiveUser", function () {
    const postId = $(this).attr("postId");

    const status = $(this).val();

    $.ajax({
        url: "/update-StatusUser?id=" + postId + "&status=" + status,
        type: "GET",
        success: function (data) {
            if(data === "true"){
                $.showNotification({
                    body: "Active User Successfully",
                    type: "success",
                    duration: 2000,
                    shadow: "0 2px 6px rgba(0,0,0,0.2)",
                    zIndex: 100,
                    margin: "1rem"
                })
            }else {
                $.showNotification({
                    body: "InActive User Successfully",
                    type: "danger",
                    duration: 2000,
                    shadow: "0 2px 6px rgba(0,0,0,0.2)",
                    zIndex: 100,
                    margin: "1rem"
                })
            }
            getSearch();
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                window.location.href = "/ad/capstoneproject";
            }
        },
    });
})