
var row_;
$(document).on("click",".btn-editUser", function(){
//	$.LoadingOverlay("show", {
//		 size : 5
//	});
	row_ = $(this).closest('.user-line')
	
	var id = $(this).attr("userId");
	$.ajax({
		url : '/admin/user/edit-user?id=' + id,
		type : 'GET',
		success : function(data) {
			$('#modal-content').html(data);
		},
//		complete: function() {
//			setTimeout(() => {
//				$.LoadingOverlay("hide");
//			}, 300);
//			
//		}

	});
});

$(document).on("click",".btn-deleteUser", function(){
	var This = $(this);
	showWarning("WARNING","Do you want to delete user", "Delete", function(){
		row_ = This.closest('.user-line')
		var id = This.attr("userId");
		$.ajax({
			url : '/admin/user/delete-user?id=' + id,
			type : 'GET',
			dataType: "json", // Du lieu Rest tra ve json
			success : function(data) {
				if(data.status == 'success'){
					showSuccess('Deleted!', data.message, 'OK')
					
            		$('#bootstrap-data-table').DataTable().row((row_)).remove().draw(false);
				}else if(data.status == 'error'){
					showError('Deleted!', data.message, 'OK')
				}
			},
			error: function(xhr){
				if(xhr.status == 302 || xhr.status == 200){
					window.location.href = '/admin/user/list-user'
				}
			}

		});
	})
});

$(document).on("submit", "#form-add-user", function(e) {
	e.preventDefault();
	var userId = $("#id").val();
	dataForm = $("#form-add-user").serialize();
	var url;
	if (!isNaN(userId) && userId > 0) {
		editUser(dataForm);
	} else {
		//addUser(dataForm);
	}
});

function editUser(dataForm){
	
	$.ajax({
		url: '/admin/user/edit-user',
		type : 'POST',
		data : dataForm,
		dataType : 'json',
		success : function(output){
			if(output.result){
				
				var userName = $("#form-add-user input[name='userName']").val();
				var firstName = $("#form-add-user input[name='firstName']").val();
				var lastName = $("#form-add-user input[name='lastName']").val();
				var phone = $("#form-add-user input[name='phone']").val();
				var email = $("#form-add-user input[name='email']").val();
				var address = $("#form-add-user input[name='address']").val();
//				
				var listRole ="";
				$("input[name='roleName']:checked").each(function(){
					listRole+=$(this).val()+',';
				})
	        	
				if(listRole.length < 1){
					$("#role-error-empty").html("Role must be not empty.")
					$("#role-error-empty").css("display", "block");
					return;
				}
				
				var edit= row_.find('td').eq(8).html()
        		var tableRow = $('#bootstrap-data-table').DataTable().row((row_));
                var rData = [
                	row_.find('td').eq(0).text(),
                	userName,
                	firstName,
                	lastName,
                	phone,
                	email,
                	address,
                	listRole,
                	edit
                ];
                $('#bootstrap-data-table').DataTable()
                        .row( tableRow )
                        .data(rData)
             
                $('#user-container').modal('hide');	
				$('#user-container').on('hidden.bs.modal', function() {
				   $('.close').trigger('click');
				});
				
				showMessageSuccess(output.message, 5000);
				if(output.logout){
					window.location.href = '/login'
				}
			}else{
				showMessageError(output.message, 5000)
			}
		},
	});
}


