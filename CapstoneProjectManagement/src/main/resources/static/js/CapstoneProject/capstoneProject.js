let sizeComment;
const sizeDefault = 10;
let currentPost = null;
let postContainer;

$(document).ready(function () {
    sizeComment = sizeDefault;
    getListPostInit();
    var checkBtn = true;
    $("#myTable :checkbox:checked").each(function() {
        checkBtn = false;
    });
    if (checkBtn) {
        $('#btn-addApprove').prop('disabled', true);
        $('#btn-addReject').prop('disabled', true);
    }

    $('#notification-container').css('display', 'none');


});


function checkDisableButtonSelect(){
    var checkBtn = true;
    $("#myTable :checkbox:checked").each(function() {
        checkBtn = false;
    });
    if (checkBtn) {
        $('#btn-addApprove').prop('disabled', true);
        $('#btn-addReject').prop('disabled', true);
    }else {
        $('#btn-addApprove').prop('disabled', false);
        $('#btn-addReject').prop('disabled', false);
    }
}

function loadScrip(){
    $("#checkall").change(function() {
        
        if(this.checked) {
            $('input[type="checkbox"]').prop('checked', true);
            $('#btn-addApprove').prop('disabled', false);
            $('#btn-addReject').prop('disabled', false);
        }
        else {
            $('input[type="checkbox"]').prop('checked', false);
            $('#btn-addApprove').prop('disabled', true);
            $('#btn-addReject').prop('disabled', true);
        }
        var checkBtn = true;
        var count =0;
        $("#myTable :checkbox:checked").each(function() {
            if (count === 0){
                count ++;
            }else {
                checkBtn = false;
            }
            count ++;
        });
        if (checkBtn) {
            $('#btn-addApprove').prop('disabled', true);
            $('#btn-addReject').prop('disabled', true);
        }else {
            $('#btn-addApprove').prop('disabled', false);
            $('#btn-addReject').prop('disabled', false);
        }
    });
}
function loadProjectDetail(postId){
    $.LoadingOverlay("show", {
        size: 50,
        maxSize: 50,
    });
    $.ajax({
        url: "/list-StudentProject?projectid=" + postId,
        type: "GET",
        success: function (data) {
            const html = data.slice(17, data.leght);
            $("#CapstoneDetail-container").html(html);
            $.LoadingOverlay("hide");
            $('#confirm-View').modal('show');
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                window.location.href = "/forum";
            }
        },
    });
}
function getListPostInit() {
    const params = new URL(location.href).searchParams;
    const size = params.get("size");
    const page = params.get("page");
    const  pro = $('#SearchProfession').val();
    const  status = $('#SearchStatus').val();
    const  nameSearch = $('#nameSearch').val();

    $.LoadingOverlay("show", {
        size: 50,
        maxSize: 50,
    });
    $.ajax({
        url: "/list-project?size=" + size + "&page=" + page+ "&status=" + status+ "&profession=" + pro + "&nameSearch=" + nameSearch,
        type: "GET",
        success: function (data) {
            $("#Capstone-container").html(data);
            $.LoadingOverlay("hide");
            if (!(size === null || page === null)) {
                window.history.pushState("", "", "/ad/capstoneproject" + rewriteUrl(size, page));
            }
            loadScrip();

        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                $.LoadingOverlay("hide");
                window.location.href = "/ad/capstoneproject";
            }
        },
    });
}





function getListPost() {
    const params = new URL(location.href).searchParams;
    const size = params.get("size");
    const page = params.get("page");
    const  pro = $('#SearchProfession').val();
    const  status = $('#SearchStatus').val();
    const  nameSearch = $('#nameSearch').val();
    $.ajax({
        url: "/list-project?size=" + size + "&page=" + page+ "&status=" + status+ "&profession=" + pro+ "&nameSearch=" + nameSearch,
        type: "GET",
        success: function (data) {
            $("#Capstone-container").html(data);
            if (!(size === null || page === null)) {
                window.history.pushState("", "", "/ad/capstoneproject" + rewriteUrl(size, page));
            }
            loadScrip();
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                $.LoadingOverlay("hide");
                window.location.href = "/forum";
            }
        },
    });
}
function getListPostSearch() {
    const params = new URL(location.href).searchParams;
    const size = null;
    const page = null;
    const  pro = $('#SearchProfession').val();
    const  status = $('#SearchStatus').val();
    const  nameSearch = $('#nameSearch').val();
    $.LoadingOverlay("show", {
        size: 50,
        maxSize: 50,
    });
    $.ajax({
        url: "/list-project?size=" + size + "&page=" + page+ "&status=" + status+ "&profession=" + pro+ "&nameSearch=" + nameSearch,
        type: "GET",
        success: function (data) {
            $("#Capstone-container").html(data);
            loadScrip();
            $.LoadingOverlay("hide");
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                window.location.href = "/forum";
            }
        },
    });
}
$(document).on("click", "#btn-ViewProject", function () {
    const postId = $(this).attr("postId");
    $.LoadingOverlay("show", {
        size: 50,
        maxSize: 50,
    });
    $.ajax({
        url: "/list-StudentProject?projectid=" + postId,
        type: "GET",
        success: function (data) {
            const html = data.slice(17, data.leght);
            $("#CapstoneDetail-container").html(html);
            $.LoadingOverlay("hide");
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                window.location.href = "/forum";
            }
        },
    });
});
$(document).on("click", "#btn-deleteProject", function () {
    const postId = $(this).attr("postId");
    let countCheck = 0;
    $(".btn-ok").click(function () {
        ++countCheck;
        if(countCheck > 1) {
            return false;
        }
        const des = $("#ApproveDes").val();
        $("#loading-Approve").attr("hidden", false);
        $.ajax({
            url: "/update-Status?id=" + postId + "&des=" + des,
            type: "GET",
            success: function (data) {
                $("#loading-Approve").attr("hidden", true);
                $("#ApproveDes").val("");
                $('.modal-backdrop').hide(); // for black background
                $('body').removeClass('modal-open'); // For scroll run
                $('#confirm-Approve').modal('hide');
                if(data === "The Project has been update successfully"){
                    $.showNotification({
                        body: data,
                        type: "success",
                        duration: 2000,
                        shadow: "0 2px 6px rgba(0,0,0,0.2)",
                        zIndex: 100,
                        margin: "1rem"
                    })
                }else {
                    $.showNotification({
                        body: data,
                        type: "danger",
                        duration: 2000,
                        shadow: "0 2px 6px rgba(0,0,0,0.2)",
                        zIndex: 100,
                        margin: "1rem"
                    })
                }
                getListPost();
            },
            error: function (xhr) {
                if (xhr.status == 302 || xhr.status == 200) {
                    window.location.href = "/ad/capstoneproject";
                }
            },
        });
    })
});

$(document).on("click", "#btn-RejectProject", function () {

    const postId = $(this).attr("postId");
    let countCheck = 0;
    $(".btn-ok").click(function () {
        ++countCheck;
        if(countCheck > 1) {
            return false;
        }
        $("#loading-Reject").attr("hidden", false);
        const des = $("#RejectDes").val();
        $.ajax({
            url: "/reject?id=" + postId + "&des=" + des,
            type: "GET",
            success: function (data) {
                $("#loading-Reject").attr("hidden", true);
                $("#RejectDes").val("");
                $('.modal-backdrop').hide(); // for black background
                $('body').removeClass('modal-open'); // For scroll run
                $('#confirm-Reject').modal('hide');
                getListPost();
                $.showNotification({
                    body: data,
                    type: "success",
                    duration: 2000,
                    shadow: "0 2px 6px rgba(0,0,0,0.2)",
                    zIndex: 100,
                    margin: "1rem"
                })
            },
            error: function (xhr) {
                if (xhr.status == 302 || xhr.status == 200) {
                    window.location.href = "/ad/capstoneproject";
                }
            },
        });
    })
});

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
    const  pro = $('#SearchProfession').val();
    const  status = $('#SearchStatus').val();
    const  nameSearch = $('#nameSearch').val();
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
        url: "/list-project?size=" + size + "&page=" + page+ "&status=" + status+ "&profession=" + pro+ "&nameSearch=" + nameSearch,
        type: "GET",
        success: function (data) {
            $("#Capstone-container").html(data);
            $.LoadingOverlay("hide");
            if (!(size === null || page === null || page === "Previous")) {
                window.history.pushState("", "", "/ad/capstoneproject" + rewriteUrl(size, page));
            }
            loadScrip();
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


$(document).on("click", "#btn-addApprove", function () {
    let countCheck = 0;
    $(".btn-ok").click(function () {
        ++countCheck;
        if(countCheck > 1) {
            return false;
        }
        let postIdlist = "";
        $('td> label > input[type="checkbox"]').each(function () {
            if(this.checked){
                var postId = $(this).attr("postId");
                postIdlist = postIdlist + postId + ',';
            }

        });
        
        const des = $("#ApproveDeslist").val();
        $("#loading-Approvelist").attr("hidden", false);
        $.ajax({
            url: "/update-StatusList?id=" + postIdlist + "&des=" + des,
            type: "GET",
            success: function (data) {
                $("#loading-Approvelist").attr("hidden", true);
                $("#ApproveDeslist").val("");
                $('.modal-backdrop').hide(); // for black background
                $('body').removeClass('modal-open'); // For scroll run
                $('#confirm-Approvelist').modal('hide');

                if(data === "The Project has been update successfully"){
                    $.showNotification({
                        body: data,
                        type: "success",
                        duration: 2000,
                        shadow: "0 2px 6px rgba(0,0,0,0.2)",
                        zIndex: 100,
                        margin: "1rem"
                    })
                }else {
                    $.showNotification({
                        body: data,
                        type: "danger",
                        duration: 2000,
                        shadow: "0 2px 6px rgba(0,0,0,0.2)",
                        zIndex: 100,
                        margin: "1rem"
                    })
                }
                getListPost();

            },
            error: function (xhr) {
                if (xhr.status == 302 || xhr.status == 200) {
                    window.location.href = "/ad/capstoneproject";
                }
            },
        });
    })
});

$(document).on("click", "#btn-addReject", function () {
    let countCheck = 0;
    $(".btn-ok").click(function () {
        ++countCheck;
        if(countCheck > 1) {
            return false;
        }
        let postIdlist = "";
        $('td> label > input[type="checkbox"]').each(function () {
            
            if(this.checked){
                var postId = $(this).attr("postId");
                postIdlist = postIdlist + postId + ',';
            }

        });

        const des = $("#RejectDeslist").val();
        $("#loading-Rejectlist").attr("hidden", false);
        $.ajax({
            url: "/rejectList?id=" + postIdlist + "&des=" + des,
            type: "GET",
            success: function (data) {
                $("#loading-Rejectlist").attr("hidden", true);
                $("#RejectDeslist").val("");
                $('.modal-backdrop').hide(); // for black background
                $('body').removeClass('modal-open'); // For scroll run
                $('#confirm-Rejectlist').modal('hide');
                getListPost();
                if(data === "The Project has been update successfully"){
                    $.showNotification({
                        body: data,
                        type: "success",
                        duration: 2000,
                        shadow: "0 2px 6px rgba(0,0,0,0.2)",
                        zIndex: 100,
                        margin: "1rem"
                    })
                }else {
                    $.showNotification({
                        body: data,
                        type: "danger",
                        duration: 2000,
                        shadow: "0 2px 6px rgba(0,0,0,0.2)",
                        zIndex: 100,
                        margin: "1rem"
                    })
                }
            },
            error: function (xhr) {
                if (xhr.status == 302 || xhr.status == 200) {
                    window.location.href = "/ad/capstoneproject";
                }
            },
        });
    })
});

function ProjectDetailApp(postId) {
    $('.modal-backdrop').hide(); // for black background
    $('body').removeClass('modal-open'); // For scroll run
    $('#confirm-View').modal('hide');
    $('#confirm-ApproveDetail').modal('show');

    $(".btn-ok").click(function () {
        const des = $("#ApproveDesDetail").val();
        $("#loading-ApproveDetail").attr("hidden", false);
        
        $.ajax({
            url: "/update-Status-Detail?id=" + postId + "&des=" + des,
            type: "GET",
            success: function (data) {
                
                $("#loading-ApproveDetail").attr("hidden", true);
                $("#ApproveDesDetail").val("");
                $('.modal-backdrop').hide(); // for black background
                $('body').removeClass('modal-open'); // For scroll run
                $('#confirm-ApproveDetail').modal('hide');
                //loadProjectDetail(id);

                if(data === "The Project has been update successfully"){
                    $.showNotification({
                        body: data,
                        type: "success",
                        duration: 3000,
                        shadow: "0 2px 6px rgba(0,0,0,0.2)",
                        zIndex: 100,
                        margin: "1rem"
                    })
                }else {
                    $.showNotification({
                        body: data,
                        type: "danger",
                        duration: 3000,
                        shadow: "0 2px 6px rgba(0,0,0,0.2)",
                        zIndex: 100,
                        margin: "1rem"
                    })
                }
                getListPost();


            },
            error: function (xhr) {
                if (xhr.status == 302 || xhr.status == 200) {
                    window.location.href = "/ad/capstoneproject";
                }
            },
        });
    })
};

function ProjectDetailReject(postId) {
    $('.modal-backdrop').hide(); // for black background
    $('body').removeClass('modal-open'); // For scroll run
    $('#confirm-View').modal('hide');
    $('#confirm-RejectDetail').modal('show');
    $(".btn-okRejectDetail").click(function () {
        $("#loading-RejectDetail").attr("hidden", false);
        const des = $("#RejectDesDetail").val();
        
        $.ajax({
            url: "/rejectDetail?id=" + postId + "&des=" + des,
            type: "GET",
            success: function (data) {
                $("#loading-RejectDetail").attr("hidden", true);
                $("#RejectDesDetail").val("");
                $('.modal-backdrop').hide(); // for black background
                $('body').removeClass('modal-open'); // For scroll run
                $('#confirm-RejectDetail').modal('hide');
                //loadProjectDetail(id);

                getListPost();
                if(data === "The Project has been update successfully"){
                    $.showNotification({
                        body: data,
                        type: "success",
                        duration: 3000,
                        shadow: "0 2px 6px rgba(0,0,0,0.2)",
                        zIndex: 100,
                        margin: "1rem"
                    })
                }else {
                    $.showNotification({
                        body: data,
                        type: "danger",
                        duration: 3000,
                        shadow: "0 2px 6px rgba(0,0,0,0.2)",
                        zIndex: 100,
                        margin: "1rem"
                    })
                }
            },
            error: function (xhr) {
                if (xhr.status == 302 || xhr.status == 200) {
                    window.location.href = "/ad/capstoneproject";
                }
            },
        });
    })
};

$(document).on("click", "#btn-AddDetail", function () {
    $('.modal-backdrop').hide(); // for black background
    $('body').removeClass('modal-open'); // For scroll run
    $('#confirm-View').modal('hide');
    $('#Add-ProjectDetail').modal('show');
    
    const capstoneProject =$('#idproject').val();
    let countCheck = 0;
    $(".btn-ok").click(function () {
        ++countCheck;
        if(countCheck > 1) {
            return false;
        }
        $("#loading-AddDetail").attr("hidden", false);

        let id = getJsonMember();
        $.ajax({
            url: "/insertDetail?id=" + id + "&capstoneProject=" + capstoneProject,
            type: "GET",
            success: function (data) {
                $("#loading-AddDetail").attr("hidden", true);
                $('.modal-backdrop').hide(); // for black background
                $('body').removeClass('modal-open'); // For scroll run
                $('#Add-ProjectDetail').modal('hide');
                loadProjectDetail(capstoneProject);
                if(data === "Create Project Detail successfully"){
                    $.showNotification({
                        body: data,
                        type: "success",
                        duration: 2000,
                        shadow: "0 2px 6px rgba(0,0,0,0.2)",
                        zIndex: 100,
                        margin: "1rem"
                    })
                }else {
                    $.showNotification({
                        body: data,
                        type: "danger",
                        duration: 2000,
                        shadow: "0 2px 6px rgba(0,0,0,0.2)",
                        zIndex: 100,
                        margin: "1rem"
                    })
                }
            },
            error: function (xhr) {
                if (xhr.status == 302 || xhr.status == 200) {
                    window.location.href = "/ad/capstoneproject";
                }
            },
        });
    })
});

$(document).on("click", "#btn-EditSupervisors", function () {
    $('.modal-backdrop').hide(); // for black background
    $('body').removeClass('modal-open'); // For scroll run
    $('#confirm-View').modal('hide');
    $('#Edit-Supervisors').modal('show');
    
    const capstoneProject =$('#idproject').val();
    let countCheck = 0;
    $(".btn-ok").click(function () {
        ++countCheck;
        if(countCheck > 1) {
            return false;
        }
        $("#loading-EditSupervisors").attr("hidden", false);

        let id = getJsonMember();
        $.ajax({
            url: "/editSupervisors?id=" + id + "&capstoneProject=" + capstoneProject,
            type: "GET",
            success: function (data) {
                $("#loading-EditSupervisors").attr("hidden", true);
                $('.modal-backdrop').hide(); // for black background
                $('body').removeClass('modal-open'); // For scroll run
                $('#Edit-Supervisors').modal('hide');
                loadProjectDetail(capstoneProject);
                if(data === "Create Project Detail successfully"){
                    $.showNotification({
                        body: data,
                        type: "success",
                        duration: 3000,
                        shadow: "0 2px 6px rgba(0,0,0,0.2)",
                        zIndex: 100,
                        margin: "1rem"
                    })
                }else {
                    $.showNotification({
                        body: data,
                        type: "danger",
                        duration: 3000,
                        shadow: "0 2px 6px rgba(0,0,0,0.2)",
                        zIndex: 100,
                        margin: "1rem"
                    })
                }
            },
            error: function (xhr) {
                if (xhr.status == 302 || xhr.status == 200) {
                    window.location.href = "/ad/capstoneproject";
                }
            },
        });
    })
});

function getDataMember() {
    let ary = [];
    var member = '';

    $('.attrTable tr').each(function (a, b) {
        let name = $('.attrName', b).text();
        let value = $('.attrValue', b).find(":selected").text();
        ary.push({username: name, role: value});
        member = member + '{ "name":"'+name+'" , "value":"'+value+'" },'

    });
    return ary;
}
function getJsonMember() {
    var member = '';
    var i = 0;
    var count = 0;
    i = $('.attrTable tr').length;
    $('.attrTable tr').each(function (a, b) {
        
        let name = $('.attrName', b).text();
        if(name != ""){
            if(count == (i-1)){
                member = member +',' + name ;
            }
            else {
                member = member + name;
            }
            ++count;
        }
    });
    return member;
}
function getFormData(capstoneProject){

    var formData = new FormData();
    formData.append("capstoneProject", capstoneProject);

    var unindexed_array = formData ;
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });
    var member = getJsonMember();
    var text = '{ '
        + ' "capstoneProject": '+ capstoneProject +', '
        + ' "members" : ['
        + member
        + '  ] '
        + '}';
    var obj = JSON.parse(text);
    return obj;
}
$(document).on("click", "#btn-add-member", function (e) {
    e.preventDefault();
    $("#error-message").addClass("d-none");
    let username = $("#member").val();
    const capstoneProject =$('#idproject').val();
    let check = true;
    $.map(getDataMember(), function (n, i) {
        if (n.username.toUpperCase() === username.toUpperCase()) {
            check = false;
            return;
        }
    });
    if (!check) {
        return;
    }
    $.ajax({
        url: "/getMemberProject?username=" + username + "&capstoneProject=" + capstoneProject,
        type: "GET",
        success: function (data) {
            let obj = JSON.parse(data);
            if (obj.success) {
                $("#member-table").append('<tr class="tr-shadow"> <td class="pt-2"> <span class="block-email attrName">'+obj.user.username+'</span> </td> <td class="pt-2"> </td><td class="pt-2"> <div class="table-data-feature pl-2"> <a href="" class="item del-member" data-toggle="tooltip" data-placement="top" title="Delete"> <i class="fas fa-trash fa-xs"></i> </a>\n' +
                    ' </div> </td> </tr>')
            } else {
                $("#error-message").removeClass("d-none");
                $("#error-message").text(obj.message);
            }
        },
        error: function (xhr) {
        },
    });
})

$(document).on("click", "#btn-add-supervisors", function (e) {
    e.preventDefault();
    $("#error-message").addClass("d-none");
    let username = $("#supervisors").val();
    const capstoneProject =$('#idproject').val();
    let check = true;
    $.map(getDataMember(), function (n, i) {
        if (n.username.toUpperCase() === username.toUpperCase()) {
            check = false;
            return;
        }
    });
    if (!check) {
        return;
    }
    $.ajax({
        url: "/getSupervisorsProject?username=" + username + "&capstoneProject=" + capstoneProject,
        type: "GET",
        success: function (data) {
            let obj = JSON.parse(data);
            if (obj.success) {
                $("#supervisors-table").append('<tr class="tr-shadow"> <td class="pt-2"> <span class="block-email attrName">'+obj.user.username+'</span> </td> <td class="pt-2"> </td><td class="pt-2"> <div class="table-data-feature pl-2"> <a href="" class="item del-member" data-toggle="tooltip" data-placement="top" title="Delete"> <i class="fas fa-trash fa-xs"></i> </a>\n' +
                    ' </div> </td> </tr>');
                $('#btn-add-supervisors').prop('disabled', true);
                $("#error-message-supervisors").text('');
            } else {
                $("#error-message-supervisors").removeClass("d-none");
                $("#error-message-supervisors").text(obj.message);
            }
        },
        error: function (xhr) {
        },
    });
})
$(document).on("click", ".del-member", function(e) {
    e.preventDefault();
    if (confirm('Do you want to delete this?')) {
        $(this).closest('tr').remove();
        $('#btn-add-supervisors').prop('disabled', false);
    }
})



$(document).on("click", "#btn-exportExcel", function () {
    const  pro = $('#SearchProfession').val();
    const  status = $('#SearchStatus').val();
    const  nameSearch = $('#nameSearch').val();
    debugger;
    $.ajax({
        url: "/exportExcel?status=" + status+ "&profession=" + pro + "&nameSearch=" + nameSearch,
        type: "GET",
        success: function (data) {

            $('#linkDowloadExel')[0].click();
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                window.location.href = "/ad/capstoneproject";
            }
        },
    });
});

$(document).on("click", "#btn-passProject", function () {
    const postId = $(this).attr("postId");
    let countCheck = 0;
    $(".btn-ok").click(function () {
        ++countCheck;
        if(countCheck > 1) {
            return false;
        }
        const des = $("#PassDes").val();
        $("#loading-Approve").attr("hidden", false);
        $.ajax({
            url: "/update-Status?id=" + postId + "&des=" + des,
            type: "GET",
            success: function (data) {
                $("#loading-Pass").attr("hidden", true);
                $("#PassDes").val("");
                $('.modal-backdrop').hide(); // for black background
                $('body').removeClass('modal-open'); // For scroll run
                $('#confirm-Pass').modal('hide');
                if(data === "The Project has been update successfully"){
                    $.showNotification({
                        body: data,
                        type: "success",
                        duration: 2000,
                        shadow: "0 2px 6px rgba(0,0,0,0.2)",
                        zIndex: 100,
                        margin: "1rem"
                    })
                }else {
                    $.showNotification({
                        body: data,
                        type: "danger",
                        duration: 2000,
                        shadow: "0 2px 6px rgba(0,0,0,0.2)",
                        zIndex: 100,
                        margin: "1rem"
                    })
                }
                getListPost();
            },
            error: function (xhr) {
                if (xhr.status == 302 || xhr.status == 200) {
                    window.location.href = "/ad/capstoneproject";
                }
            },
        });
    })
});

$(document).on("click", "#btn-failProject", function () {

    const postId = $(this).attr("postId");
    let countCheck = 0;
    $(".btn-ok").click(function () {
        ++countCheck;
        if(countCheck > 1) {
            return false;
        }
        $("#loading-Fail").attr("hidden", false);
        const des = $("#FailDes").val();
        $.ajax({
            url: "/reject?id=" + postId + "&des=" + des,
            type: "GET",
            success: function (data) {
                $("#loading-Fail").attr("hidden", true);
                $("#FailDes").val("");
                $('.modal-backdrop').hide(); // for black background
                $('body').removeClass('modal-open'); // For scroll run
                $('#confirm-Fail').modal('hide');
                getListPost();
                $.showNotification({
                    body: data,
                    type: "success",
                    duration: 2000,
                    shadow: "0 2px 6px rgba(0,0,0,0.2)",
                    zIndex: 100,
                    margin: "1rem"
                })
            },
            error: function (xhr) {
                if (xhr.status == 302 || xhr.status == 200) {
                    window.location.href = "/ad/capstoneproject";
                }
            },
        });
    })
});