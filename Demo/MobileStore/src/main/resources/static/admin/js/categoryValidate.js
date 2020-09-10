/**
 * 
 */

$(document).ready(function() {
	
	$("#addCategory").validate({
		rules : {
			name : {
				required : true,				
				minlength : 5,
				maxlength : 32,				
			},
			description : {
				required : true,
				minlength : 10,
				maxlength : 120,
			},
		},
		messages : {
			name : {
				required : "Please enter category name",
				minlength : "category name at least 5 character",
				maxlength : "Name maximum 32 character",
			},
			description : {
				required : "Please enter description",
				minlength : "category description at least 10 character",
				maxlength : "Description maximum 120 character",
			},
		},
	});
	
	
	$("#editCategory").validate({
		rules : {
			name : {
				required : true,				
				minlength : 5,
				maxlength : 32,				
			},
			description : {
				required : true,
				minlength : 10,
				maxlength : 120,
			},
		},
		messages : {
			name : {
				required : "Please enter category name",
				minlength : "category name at least 5 character",
				maxlength : "Name maximum 32 character",
			},
			description : {
				required : "Please enter description",
				minlength : "category description at least 10 character",
				maxlength : "Description maximum 120 character",
			},
		},
	});
});