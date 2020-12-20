let sizeComment;
const sizeDefault = 5;
let currentPost = null;
let postContainer;

$(document).ready(function () {
    sizeComment = sizeDefault;
    getListPostInit();

});

function loadCommentContainer(size, postId) {
    let id;
    let s;
    $(".list-post-container").each(function (i) {
        id = $(this).find("#post-id").val();
        let arr = $(this).find(".comment-container");
        let viewMoreElement = $(this).find("#view-more-comments");
        // check post selected
        if (postId.toString() !== id) {
            s = sizeDefault
        } else {
            s = size
        }
        let arrSize = arr.length;
        // check and set display for div comment
        if (arrSize >= s) {
            arr.each(function (j) {
                if (j < (arrSize - s)) {
                    $(this).addClass("d-none");
                } else {
                    $(this).removeClass("d-none");
                }

            })
        }
        if ((arrSize - s) < sizeDefault) {
            arr.each(function (j) {
                $(this).removeClass("d-none");
            })
            viewMoreElement.addClass("d-none");
        }
    })
}

$(document).on("click", ".view-more-comments", function (e) {
    e.preventDefault()
    const postId = $(this).attr("postId");
    //check click another post
    if(currentPost !== postId) {
        currentPost = postId;
        sizeComment = sizeDefault;
    }
    sizeComment += sizeDefault;
    loadCommentContainer(sizeComment, postId)
})

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
            loadCommentContainer(sizeDefault, -1);
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
            loadCommentContainer(sizeDefault, -1);
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
            loadCommentContainer(sizeDefault, -1);
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

$(document).on("click", "#btn-deleteTopic", function () {
    const postId = $(this).attr("postId");
    $(".btn-ok").click(function () {
        $("#loading-delete").attr("hidden", false);
        $.ajax({
            url: "/delete-post/" + postId,
            type: "GET",
            success: function (data) {
                $.showNotification({
                    body: data,
                    type: "success",
                    duration: 3000,
                    shadow: "0 2px 6px rgba(0,0,0,0.2)",
                    zIndex: 100,
                    margin: "1rem"
                })
                $("#loading-delete").attr("hidden", true);
                getListPost();
                $("#confirm-delete").trigger("click");
            },
            error: function (xhr) {
                if (xhr.status == 302 || xhr.status == 200) {
                    window.location.href = "/forum";
                }
            },
        });
    })
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
            let obj = JSON.parse(data);
            let str = '';
            if(obj.hasError) {
                $.map(obj.errors, function (n, i) {
                    str += n;
                    $("#error-container").append('<span class="text-danger">' + n + '</span><br>');
                });
                $("#error-container").removeClass("d-none");
                $("#loading-add").attr("hidden", true);
                $("#btn-post").attr("disabled", false);
                return false;
            }
            const params = new URL(location.href).searchParams;
            const size = params.get("size");
            const page = params.get("page");
            var formData = new FormData();
            $.each($("input[type=file]"), function(i, obj) {
                $.each(obj.files, function(j, file) {
                    formData.append('file[' + j + ']', file);
                    console.log(file);
                })
            });
            $.ajax({
                url: "/add-file-post/"+obj.data,
                type: "POST",
                enctype: 'multipart/form-data',
                data :formData,
                processData: false,  // tell jQuery not to process the data
                contentType: false,  // tell jQuery not to set contentType
                success: function (data2) {
                    $.ajax({
                        url: "/list-post?size=" + size + "&page=" + page,
                        type: "GET",
                        success: function (data1) {
                            $("#post-container").html(data1);
                            $.showNotification({
                                body: data2,
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
                            loadCommentContainer(sizeDefault, -1);
                        },
                        error: function (xhr) {
                            if (xhr.status == 302 || xhr.status == 200) {
                                window.location.href = "/forum";
                            }
                        },
                    });
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

function parseParams(str) {
    return str.split('&').reduce(function (params, param) {
        var paramSplit = param.split('=').map(function (value) {
            return decodeURIComponent(value.replace(/\+/g, ' '));
        });
        params[paramSplit[0]] = paramSplit[1];
        return params;
    }, {});
}

$(document).on("submit", ".form-comment", function (e) {
    e.preventDefault();
    let dataForm = $(this).serialize();
    let obj = parseParams(dataForm);
    if(obj.content === '') {
        return false;
    }
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
    $(".btn-comment").each(function () {
        if ($(this).attr("commentPostId") === postId) {
            $(this).attr("disabled", true);
        }
    });

    $.ajax({
        url: "/add-comment",
        type: "POST",
        data: dataForm,
        success: function (data) {
            getListPost();
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

$(document).on("click", "#chat", function (e) {
    e.preventDefault()
    $.LoadingOverlay("show", {
        size: 50,
        maxSize: 50,
    });
    postContainer = $("#content-body").html();
    const postId = 'gr_' + $(this).attr("postId");
    window.location.href = "/messenger/" + btoa(postId);
})

$(document).on("click", "#back-to-forum", function (e) {
    $("#content-body").html(postContainer);
})





