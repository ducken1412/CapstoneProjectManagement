$(document).ready(function () {
    $.ajax({
        url: "/chat-content",
        type: "GET",
        success: function (data) {
            $('#drd-chat-content').html(data);
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                window.location.href = "/forum";
            }
        },
    });
    ajaxCall();

    function ajaxCall() {
        $.ajax({
            url: "/number-new-message",
            type: "GET",
            success: function (data) {
                if (data === 'errorLogin') {
                    window.location.href = "/login";
                } else {
                    if (data === '0') {
                        $("#numNewMessage").attr("hidden", true);
                    } else {
                        $('#numNewMessage').removeAttr('hidden');
                        $('#numNewMessage').text(data);
                    }
                    setTimeout(
                        function () {
                            ajaxCall();
                        }, 2000);
                }
            },
            error: function (xhr) {
                if (xhr.status == 302 || xhr.status == 200) {
                    window.location.href = "/forum";
                }
            },
        });
    }
});

$('#drd-chat').click(function (e) {
    $.ajax({
        url: "/chat-content",
        type: "GET",
        success: function (data) {
            $('#drd-chat-content').html(data);
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                window.location.href = "/forum";
            }
        },
    });
});

