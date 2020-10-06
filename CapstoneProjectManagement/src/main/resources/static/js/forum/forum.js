$(document).ready(function () {
  getListPost();
});

function getListPost() {
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

$(document).on("click", ".page-link", function (e) {
  e.preventDefault();
  $.LoadingOverlay("show", {
    size: 50,
    maxSize: 50,
  });
  const params = new URL(location.href).searchParams;
  var page = $(this).text();
  var size = $("#page-size").val();
  var total = $("#total-page").val();
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
  var postId = $(this).attr("postId");
  getFormAddTopic()
  $('#modal-content').LoadingOverlay("show", {
	    size: 50,
	    maxSize: 50,
	  });
  $.ajax({
    url: "/edit-post/" + postId,
    type: "GET",
    success: function (data) {
    	$('#modal-content').LoadingOverlay("hide");
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
  var postId = $("#id").val();
  $('#loading-add').attr("hidden",false);
  dataForm = $("#post-form").serialize();
  $.ajax({
    url: "/add-post",
    type: "POST",
    data: dataForm,
    success: function (data) {
    	$("#post-container").html(data);
    	$('#topic-container').trigger('click');
    	 $('#loading-add').attr("hidden",true);
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
  $('#loading-send').attr("hidden",false);
  dataForm = $(this).serialize();
  $.ajax({
    url: "/add-comment",
    type: "POST",
    data: dataForm,
    success: function (data) {
      $("#post-container").html(data);
    },
    error: function (xhr) {
      if (xhr.status == 302 || xhr.status == 200) {
        window.location.href = "/forum";
      }
    },
  });
});

function rewriteUrl(size, page) {
  var url = "";
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
