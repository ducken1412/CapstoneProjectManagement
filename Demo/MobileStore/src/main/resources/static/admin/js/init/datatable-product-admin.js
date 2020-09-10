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
					if(index == 3 || index == 6 || index == 7){
						var column = this;
						row.append('<label>'+column.header().textContent+'</label>')
						var select = $('<select class="form-control search-dropdown"><option value="">-- All --</option></select>')
							.appendTo(row)
							.on( 'change', function () {
								var val = $.fn.dataTable.util.escapeRegex(
									$(this).val()
								);
		 
								column
									.search( val ? '^'+val+'$' : '', true, false )
									.draw();
							} );
		 
						column.data().unique().sort().each( function ( d, j ) {
							select.append( '<option value="'+d+'">'+d+'</option>' )
						} );
					}
					
				} );
			}
	    
	    });
	    
	    $(document).on('click','.redirect-url',function(){
	    	window.location.href=$(this).attr('data-url');
	    });
	})
})(jQuery);



