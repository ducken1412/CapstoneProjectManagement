$(document).on("submit", "#comment-form", function (e) {
    e.preventDefault();
    $("#loading-send").removeAttr('hidden');
    let dataForm = $("#comment-form").serialize();
    let postId = $('#postId').val();
    $.ajax({
        url: "/report-comment",
        type: "POST",
        data: dataForm,
        success: function (data) {
            $('#report-container').html(data);
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                window.location.href = "/forum";
            }
        },
    });
})

