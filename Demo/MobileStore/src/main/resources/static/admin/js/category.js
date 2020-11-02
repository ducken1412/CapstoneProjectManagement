

$(document).on("click", ".aBtn", function() {	  
	$.ajax({
		type : "GET",		
		url : "/admin/addCategory",	
		dataType : "text",
		success : function(response) {	
			$('#category-modal').html(response).modal('show');				
		},
		error : function() {
			
			alert("failure");			
		}
	});			
});

$(document).on("click", ".dBtn", function() {
	var id = $(this).attr('categoryid');
	var name = $(this).attr('categoryname');	
	showWarning("Confirm Delete", "Delete category: "+name , "Delete", function() {
		$.ajax({
			type : "GET",
			url : "/admin/deleteCategory?id=" + id,			
			dataType : "text",
			success : function() {
				window.location.replace("/admin/list-category");															
			},
			error : function() {
				alert("failure");
			}
		});
	})
});

$(document).on("click", ".eBtn", function() {
	var id = $(this).attr('categoryid');	
	$.ajax({
		type : "GET",	
		url : "/admin/editCategory?id=" + id,	
		dataType : "text",
		success : function(response) {	
			$('#category-modal').html(response).modal('show');	
		},
		error : function() {
			alert("failure");			
		}
	});		
	
});


$(document).on('submit', '#category-container', function(e){
    e.preventDefault();
    
    var dataForm = $(this).serialize();
    console.log(dataForm);
    $.ajax({
        url: '/',
        type: 'POST',
//        contentType : "application/json",
        data : dataForm,
//        dataType : 'json',
        success: function(data) {
            $('.card-body').html(data);
 //           $('#myModalAddBrand').modal('hide');
//            $(".modal-backdrop.show").css('opacity', '0');
//            $(".modal-backdrop.show").remove(); 
        }
    });
    return;
})

