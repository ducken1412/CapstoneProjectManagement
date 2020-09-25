$("#btn-addTopic").click(function() {
	getFormAddTopic();
});

function getFormAddTopic() {
	$.ajax({
		url : '/add-post',
		type : 'GET',
		success : function(data) {
			$('#modal-content').html(data);
		},
		error: function(xhr){
			
			$('#modal-content').html("Error");
			if(xhr.status == 302 || xhr.status == 200){
				window.location.href = '/forum'
			}
		},
		complete: function() {
			
		}
	});
}