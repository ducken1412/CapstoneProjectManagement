$(document).ready(function () {
    getListPostInit();
});

function getListPostInit() {
    $.LoadingOverlay("show", {
        size: 50,
        maxSize: 50,
    });
    const params = new URL(location.href).searchParams;
    const size = params.get("size");
    const page = params.get("page");
    $.ajax({
        url: "/list-post?size=" + size + "&page=" + page,
        type: "GET",
        success: function (data) {
            $("#post-container").html(data);
            $.LoadingOverlay("hide");
            if (!(size === null || page === null)) {
                window.history.pushState("", "", "/forum" + rewriteUrl(size, page));
            }
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                window.location.href = "/forum";
            }
        },
    });
}

function getListPost() {
    const params = new URL(location.href).searchParams;
    const size = params.get("size");
    const page = params.get("page");
    $.ajax({
        url: "/list-post?size=" + size + "&page=" + page,
        type: "GET",
        success: function (data) {
            $("#post-container").html(data);
            if (!(size === null || page === null)) {
                window.history.pushState("", "", "/forum" + rewriteUrl(size, page));
            }
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                window.location.href = "/forum";
            }
        },
    });
}

$(document).on("click", ".page-link", function (e) {
    e.preventDefault();
    $.LoadingOverlay("show", {
        size: 50,
        maxSize: 50,
    });
    const params = new URL(location.href).searchParams;
    let page = $(this).text();
    const size = $("#page-size").val();
    const total = $("#total-page").val();
    if (
        page === "Previous" &&
        params.get("page") !== null &&
        parseInt(params.get("page")) > 1
    ) {
        page = parseInt(params.get("page")) - 1;
    }
    if (
        page === "Next" &&
        params.get("page") !== null &&
        parseInt(params.get("page")) < total
    ) {
        page = parseInt(params.get("page")) + 1;
    }
    if (page === "Next") {
        page = 2;
    }
    $.ajax({
        url: "/list-post?size=" + size + "&page=" + page,
        type: "GET",
        success: function (data) {
            $("#post-container").html(data);
            $.LoadingOverlay("hide");
            if (!(size === null || page === null || page === "Previous")) {
                window.history.pushState("", "", "/forum" + rewriteUrl(size, page));
            }
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                window.location.href = "/forum";
            }
        },
    });
});

$(document).on("click", "#btn-addTopic", function () {
    getFormAddTopic();
});

$(document).on("click", "#btn-editTopic", function () {
    const postId = $(this).attr("postId");
    getFormAddTopic();
    $("#modal-content").LoadingOverlay("show", {
        size: 50,
        maxSize: 50,
    });
    $.ajax({
        url: "/edit-post/" + postId,
        type: "GET",
        success: function (data) {
            $("#modal-content").LoadingOverlay("hide");
            $("#modal-content").html(data);
        },
        error: function (xhr) {
            $("#modal-content").html("Error");
            if (xhr.status == 302 || xhr.status == 200) {
                window.location.href = "/forum";
            }
        },
    });
});

function getFormAddTopic() {
    $.ajax({
        url: "/add-post",
        type: "GET",
        success: function (data) {
            $("#modal-content").html(data);
        },
        error: function (xhr) {
            $("#modal-content").html("Error");
            if (xhr.status == 302 || xhr.status == 200) {
                window.location.href = "/forum";
            }
        },
    });
}

$(document).on("submit", "#post-form", function (e) {
    e.preventDefault();
    $("#loading-add").attr("hidden", false);
    $("#btn-post").attr("disabled", true);
    let dataForm = $("#post-form").serialize();
    $.ajax({
        url: "/add-post",
        type: "POST",
        data: dataForm,
        success: function (data) {
            const params = new URL(location.href).searchParams;
            const size = params.get("size");
            const page = params.get("page");
            $.ajax({
                url: "/list-post?size=" + size + "&page=" + page,
                type: "GET",
                success: function (data1) {
                    $("#post-container").html(data1);
                    $.showNotification({
                        body: data,
                        type: "success",
                        duration: 3000,
                        shadow: "0 2px 6px rgba(0,0,0,0.2)",
                        zIndex: 100,
                        margin: "1rem"
                    })
                    $("#topic-container").trigger("click");
                    $("#loading-add").attr("hidden", true);
                    if (!(size === null || page === null)) {
                        window.history.pushState("", "", "/forum" + rewriteUrl(size, page));
                    }
                },
                error: function (xhr) {
                    if (xhr.status == 302 || xhr.status == 200) {
                        window.location.href = "/forum";
                    }
                },
            });

        },
        error: function (xhr) {
            $("#modal-content").html("Error");
            if (xhr.status == 302 || xhr.status == 200) {
                window.location.href = "/forum";
            }
        },
    });
});

$(document).on("submit", ".form-comment", function (e) {
    e.preventDefault();
    const params = new URL(location.href).searchParams;
    const size = params.get("size");
    const page = params.get("page");
    const values = {};
    $.each($(this).serializeArray(), function (i, field) {
        values[field.name] = field.value;
    });
    const getValue = function (valueName) {
        return values[valueName];
    };
    const postId = getValue("postId");
    $(".loading-send").each(function () {
        if ($(this).attr("loadingPostId") === postId) {
            $(this).attr("hidden", false);
        }
    });
    let dataForm = $(this).serialize();
    $.ajax({
        url: "/add-comment",
        type: "POST",
        data: dataForm,
        success: function (data) {
            getListPost()
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                window.location.href = "/forum";
            }
        },
    });
});

function rewriteUrl(size, page) {
    let url = "";
    if ("" == url) {
        url += "?";
    } else {
        url += "&";
    }
    url += "size=" + size;
    if ("" == url) {
        url += "?";
    } else {
        url += "&";
    }
    url += "page=" + page;
    return url;
}
