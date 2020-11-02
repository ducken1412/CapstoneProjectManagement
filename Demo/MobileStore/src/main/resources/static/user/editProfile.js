jQuery(document).ready(function() {
//	 edit user info
	$("#form-edit-user").validate({
        rules: {
        	username: {
                required: true,
                minlength: 5,
                maxlength: 50,
                checkSpaces: true
            },
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
                //minlength: 8,
                maxlength: 15,
                /* checkSpaces: true */
            },
            email: {
                required: true,
                email: true,
                maxlength: 64,
                /* checkSpaces: true */
            },
            address: {
                required: true,
                maxlength: 256
            },
        },
        messages: {
        	username: {
                required: "Please enter username.",
                minlength: "Username must contain at least 5 characters",
                maxlength: "Username must contain at less than 50 characters",
               /*  checkSpaces: "Username can not content space." */
            },
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
            email: {
                required: "Please enter email",
                email: "Please enter correct email",
                maxlength: "Email must contain at less than 64 characters",
                /* checkSpaces: "Email can not content space." */
            },
            address: {
                required: "Please enter address",
                maxlength: "Address must contain at less than 256 characters",
            },
        },
        submitHandler: function () {
        	
        	var id = $("#id").val();
        	var userName = $("#userName").val();
        	var firstName = $("#firstName").val();
        	var lastName = $("#lastName").val();
        	var birthDate= $("#birthDate").val();
        	var gender = $("input[name=gender]:checked").val();
        	var phone = $("#phone").val();
        	var email = $("#email").val();
        	var address = $("#address").val();

        	 $.ajax({
                type: "post",
                url: "/user/edit-user",
                data: {
                	id : id,
                	userName : userName,
                	firstName : firstName,
                	lastName : lastName,
                	birthDate : birthDate,
                	gender : gender,
                	phone : phone,
                	email : email,
                	address : address,
    			},
    			dataType: "json", // Du lieu Rest tra ve json
                success: function (response) {
                	if(response.status == 'success'){
                        showMessageSuccess(response.message, 5000);
                	}else{
                		showError('Edit!', response.message, 'OK')
                	}
                }
            });
        }
    });
});