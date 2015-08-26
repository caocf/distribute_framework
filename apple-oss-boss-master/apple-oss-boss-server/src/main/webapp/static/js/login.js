/**
  *  write by ftt
  *  time 2013.08.22
  */

$(function(){
	$('.login_warp').css({"height": $(document).height() + "px"});
	if( $(window).height() < 768 ){
		$('.control_bg').css({"height": "668px"});
	}else{
		$('.control_bg').css({"height": $(document).height() - 100 + "px"});
	}
	
	// input激活
	$('.auto_hint').bind("focus", function(){
          if($(this).val() == $(this).attr('realValue')) {
               $(this).val('');
               $(this).removeClass('auto_hint');
          }
     });
	 $('.auto_hint').bind("blur", function(){
          if($(this).val() == '' &&$(this).attr('realValue') != '') {
               $(this).val($(this).attr('realValue'));
               $(this).addClass('auto_hint');
          }
     });
     $('.auto_hint').each(function() {
          if($(this).attr('realValue') == '') {return;}
          if($(this).val() == '') {
               $(this).val($(this).attr('realValue'));
          }else {
               $(this).addClass('auto_hint');
			   //$(this).removeClass('auto_hint');
          }
     });
});