$.noConflict();

jQuery(document).ready(function($) {
	
	
	// Menu Trigger
	$('#menuToggle').on('click', function(event) {
		var menu =$.cookie("menu");
		if(menu=='show'){
			$.cookie("menu",'hide', {expires: 7, path:'/admin'});
		}else{
			$.cookie("menu",'show', {expires: 7, path:'/admin'});
		}
		var windowWidth = $(window).width();   		 
		if (windowWidth<1010) { 
			$('body').removeClass('open'); 
			if (windowWidth<760){ 
				$('#left-panel').slideToggle(); 
			} else {
				$('#left-panel').toggleClass('open-menu');  
			} 
		} else {
			$('body').toggleClass('open');
			$('#left-panel').removeClass('open-menu');  
		} 
			 
	}); 
	
	var menu =$.cookie("menu");
	if(menu=='hide'){
		$('#menuToggle').trigger('click');
		$.cookie("menu",'hide', {expires: 7, path:'/admin'});
	}

	 

	// Load Resize 
	$(window).on("load resize", function(event) { 
		var windowWidth = $(window).width();  		 
		if (windowWidth<1010) {
			$('body').addClass('small-device'); 
		} else {
			$('body').removeClass('small-device');  
		} 
		
	});
  
 
});