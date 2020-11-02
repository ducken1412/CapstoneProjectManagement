	
jQuery(document).ready(function() {
	
    $.validator.addMethod("checkSpaces", function(value, element) {
		return value.indexOf(" ") < 0 && value != "";
    }, "Input cannot contain spaces.");
	
	$("#form-add-user").validate({
        rules: {
            firstName: {
                required: true,
                maxlength: 30
            },
            lastName: {
                required: true,
                maxlength: 30
            },
            phone: {
                required: true,
                minlength: 8,
                maxlength: 15,
                checkSpaces: true
            },
            address: {
                required: true,
                maxlength: 256
            },
        },
        messages: {
            firstName: {
                required: "Please enter first name.",
                maxlength: "First name must contain at less than 30 characters",
            },
            lastName: {
                required: "Please enter last name.",
                maxlength: "Last name must contain at less than 30 characters",
            },
            phone: {
                required: "Please enter phone.",
                minlength: "Phone must contain at least 8 characters",
                maxlength: "Phone must contain at less than 50 characters",
            },
            address: {
                required: "Please enter address",
                maxlength: "Address must contain at less than 256 characters",
            },
        },
    });
});
	
	