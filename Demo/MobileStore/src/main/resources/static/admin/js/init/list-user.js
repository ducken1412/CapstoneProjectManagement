(function ($) {
	$(document).ready(function(){
		$('#bootstrap-data-table').DataTable({
		       lengthMenu: [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]],
		        initComplete: function () {
		        	var row = $("#bootstrap-data-table_length").closest('.row')
		        	row.append('<div class="col-12" id="search-container"></div>')
		        	row=$('#search-container')
		        	var index = 0;
					this.api().columns().every( function () {
						index++
						if(index == 8){
							var column = this;
							row.append('<label>'+column.header().textContent+'</label>')
							var select = $('<select class="form-control search-dropdown"><option value="">-- All --</option></select>')
								.appendTo(row)
								.on( 'change', function () {
									var val = $.fn.dataTable.util.escapeRegex(
										$(this).val()
									);
			 
									column
										.search( val ? ''+val+'' : '', true, false )
										.draw();
								} );
							select.append( '<option value="ROLE_ADMIN">ROLE_ADMIN</option>' )
							select.append( '<option value="ROLE_USER">ROLE_USER</option>' )
						}
						
					} );
					
					/*row.append('<button class=" btn btn-success eBtn" id="btn-add-user" data-toggle="modal" data-target="#user-container">Add</button>')*/
				}
		    });
		    
		    $(document).on('click','.redirect-url',function(){
		    	window.location.href=$(this).attr('data-url');
		    });
	})
})(jQuery);