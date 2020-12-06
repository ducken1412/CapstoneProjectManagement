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