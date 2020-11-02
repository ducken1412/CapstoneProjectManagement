$("#btn-addProduct").click(function() {
	getFormAddProduct();
});

function getFormAddProduct() {
	$.LoadingOverlay("show", {
		 size : 5
	});
	$.ajax({
		url : '/admin/add-product',
		type : 'GET',
		success : function(data) {
			$('#modal-content').html(data);
		},
		error: function(xhr){
			$('#modal-content').html("Error");
			if(xhr.status == 302 || xhr.status == 200){
				window.location.href = '/admin/list-product'
			}
		},
		complete: function() {
			setTimeout(() => {
				$.LoadingOverlay("hide");
			}, 300);
			
		}
	});
}

var _rowProduct;

$(document).on("click", ".eBtn", function() {
	$.LoadingOverlay("show", {
		 size : 5
	});
	var productId = $(this).attr('productId');
	_rowProduct = $(this).closest(".row-product");
	$.ajax({
		url : '/admin/edit-product/' + productId,
		type : 'GET',
		success : function(data) {
			$('#modal-content').html(data);
			$('#title-modal').text("Edit Product");
		},
		error: function(xhr){
			if(xhr.status == 302 || xhr.status == 200){
				window.location.href = '/admin/list-product'
			}
		},
		complete: function() {
			setTimeout(() => {
				$.LoadingOverlay("hide");
			}, 300);
			
		}
		
	});
});

$(document).on("submit", "#product-form", function(e) {
	e.preventDefault();
	var productId = $("#id").val();
	dataForm = $("#product-form").serialize();
	var url;
	if (!isNaN(productId) && productId > 0) {
		editProduct(dataForm);
	} else {
		addProduct(dataForm);
	}
});

function addProduct(dataForm) {
	$.LoadingOverlay("show", {
		 size : 5
	});
	$.ajax({
				url : '/admin/add-product/',
				type : 'POST',
				data : dataForm,
				dataType : "json",
				success : function(output) {
					if (output.result) {
						showMessageSuccess(output.message, 5000)
						var edit = "<a class='btn btn-warning eBtn'  data-toggle='modal' data-target='#product-container' productId="
								+ output.product.id
								+ ">Edit</a>&nbsp;<a class='btn btn-danger dBtn'>Delete</a>";
						var table = $('#bootstrap-data-table').DataTable();
						var product = output.product;
						var productName = "<a class='redirect-url' href='/detail/"
								+ product.id + "'>" + product.name + "</a>";
						var condition = null;
						if (product.condition == 1) {
							condition = 'New';
						} else if (product.condition == 2) {
							condition = 'Old';
						} else if (product.condition == 3) {
							condition = 'Refurbished';
						}
						var rData = [ product.id, productName,
								product.category.name, product.price,
								product.quantity, product.manufacturer,
								condition, edit ];
						table.row.add(rData).draw();
						getFormAddProduct();

					} else {
						showMessageError(output.message, 5000)
					}

				},
				error : function(output) {
					showMessageError(output.message, 5000)
					if(output.status == 302 || output.status == 200){
						window.location.href = '/admin/list-product'
					}
				},
				complete: function() {
					setTimeout(() => {
						$.LoadingOverlay("hide");
					}, 300);
				}
			});
}

function editProduct(dataForm) {
	$.LoadingOverlay("show", {
		 size : 5
	});
	$.ajax({
		url : '/admin/edit-product/',
		type : 'POST',
		data : dataForm,
		dataType : "json",
		success : function(output) {
			if (output.result) {
				showMessageSuccess(output.message, 5000)
				var edit = _rowProduct.find('td').eq(7).html();
				var table = $('#bootstrap-data-table').DataTable();
				var tableRow = table.row((_rowProduct));
				var product = output.product;
				var productName = "<a class='redirect-url' href='/detail/"
						+ product.id + "'>" + product.name + "</a>";
				var condition = null;
				if (product.condition == 1) {
					condition = 'New';
				} else if (product.condition == 2) {
					condition = 'Old';
				} else if (product.condition == 3) {
					condition = 'Refurbished';
				}
				var rData = [ product.id, productName, product.category.name,
						product.price, product.quantity, product.manufacturer,
						condition, edit ];
				table.row(tableRow).data(rData)
				$('#product-container').modal('hide');
				$('#product-container').on('hidden.bs.modal', function() {
					$('.close').trigger('click');
				});
			} else {
				showMessageError(output.message, 5000)
			}
		},
		error : function(output) {
			showMessageError(output.message, 5000)
			if(output.status == 302 || output.status == 200){
				window.location.href = '/admin/list-product'
			}
		},
		complete: function() {
			setTimeout(() => {
				$.LoadingOverlay("hide");
			}, 300);
		}
	});
}

$(document).on("click", ".dBtn", function() {
//	$.LoadingOverlay("show", {
//		 size : 5
//	});
	var productId = $(this).attr('productId');
	currentRow = $(this).closest('tr');
	table = $('#bootstrap-data-table').DataTable();
	showWarning("WARNING","Do you want to delete product", "Delete", function(){
		$.LoadingOverlay("show", {
			 size : 5
		});
		$.ajax({
			url : '/admin/delete-product/' + productId,
			type : 'GET',
			dataType : "json",
			success : function(output) {
				if (output.result) {
					table.rows(currentRow).remove().draw(false);
					showSuccess('Deleted!', output.message, 'OK')
				} else {
					showError('Deleted!', output.message, 'OK')
				}
			},
			error : function(output) {
				showMessageError(output.message, 5000)
				if(output.status == 302 || output.status == 200){
					window.location.href = '/admin/list-product'
				}
			},
			complete: function() {
				setTimeout(() => {
					$.LoadingOverlay("hide");
				}, 300);
			}
		});
	});
});
