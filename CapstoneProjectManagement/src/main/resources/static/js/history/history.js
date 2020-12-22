$(document).ready(function () {
    $('#notification-container').css('display', 'none');
});


$(document).on("change", "#dr-role", function (e) {
    $('#history-container').LoadingOverlay("show", {
        size: 50,
        maxSize: 50,
    });
    const role = this.value;
    $.ajax({
        url: "/history-records",
        type: "POST",
        data: {
            'role' : role
        },
        success: function (data) {
            $('#history-container').html(data);
            $('#history-container').LoadingOverlay("hide");
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                // window.location.href = "/forum";
            }
        },
    });
})

$(document).on("change", "#dr-cp", function (e) {
    $('#history-container').LoadingOverlay("show", {
        size: 50,
        maxSize: 50,
    });
    const type = this.value;
    $.ajax({
        url: "/history-records",
        type: "POST",
        data: {
            'type' : type
        },
        success: function (data) {
            $('#history-container').html(data);
            $('#history-container').LoadingOverlay("hide");
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                // window.location.href = "/forum";
            }
        },
    });
})