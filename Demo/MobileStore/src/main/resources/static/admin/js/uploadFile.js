function createImagesSilde(url) {
	return "<div class='image'><span class='delimage'><i class='fa fa-minus'></i></span><img src='"
			+ url
			+ "'>"
			+ "<input name='slide' value='"
			+ url
			+ "' hidden></div>"
}

function createImages(url) {
	return "<div class='image'><img src='" + url + "'>"
			+ "<input name='image' value='" + url + "' hidden></div>"
}

$("#file-multiple-input").change(function() {
	files = $("#file-multiple-input")[0].files;
	forms = new FormData();
	for (i = 0; i < files.length; i++) {
		forms.append("file", files[i]);
	}
	$.ajax({
		url : '/upload/0',
		type : 'post',
		contentType : false,
		data : forms,
		enctype : 'multipart/form-data',
		processData : false,
	}).done(function(output) {
		output = JSON.parse(output)
		if (!output.result) {
			alert("Upload Fail!!!")
			$("#file-multiple-input").val(null)
		} else {
			for (i = 0; i < output.paths.length; i++) {
				image = output.paths[i];
				$("#product_slide").append(createImagesSilde(image))
			}
		}
	}).always(function() {
	});

});

$("#file-input").change(function() {
	files = $("#file-input")[0].files;
	forms = new FormData();
	forms.append("file", files[0]);
	$.ajax({
		url : '/main-upload/0',
		type : 'post',
		contentType : false,
		data : forms,
		enctype : 'multipart/form-data',
		processData : false,
	}).done(function(output) {
		if (output == "") {
			alert("Upload Fail!!!")
			$("#file-input").val(null)
		} else {
			$("#product_image").empty();
			$("#product_image").append(createImages(output))
		}
	}).always(function() {
	});

});

$(document).on("click", ".delimage", function() {
	confirmed = confirm("Do you want to delete this image?");
	if (confirmed) {
		$(this).closest("div").remove();
	}
});