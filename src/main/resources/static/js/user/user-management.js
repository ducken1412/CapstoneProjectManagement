$(document).ready(function () {
    getSearch();
    $('#notification-container').css('display', 'none');
});


function getSearch() {
    const params = new URL(location.href).searchParams;
    var site = $('#dr-site').val();
    var semester = $('#dr-semester').val();
    var type = $('#dr-type').val().toString();
    debugger;
    // $.LoadingOverlay("show", {
    //     size: 50,
    //     maxSize: 50,
    // });
    $.ajax({
        url: "/student-management?site=" + site + "&semester=" + semester + "&type=" + type,
        type: "GET",

        success: function (data) {
            $('#student-container').html(data);
            $('#student-container').LoadingOverlay("hide");
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