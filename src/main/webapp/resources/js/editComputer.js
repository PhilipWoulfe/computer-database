$("#introduced").datepicker({
	dateFormat : 'yy-mm-dd',
	changeMonth : true,
	changeYear : true,
	minDate : new Date(1970, 1 - 1, 1)
});
$("#discontinued").datepicker({
	dateFormat : 'yy-mm-dd',
	changeMonth : true,
	changeYear : true
});
$.validator.addMethod("validDate", function(value, element) {
	if (value != "") {
		return value.match(/^\d{4}-0[1-9]|1[0-2]-0[1-9]|1[0-9]|2[0-9]|3[0-1]$/);
	}
	return true;
}, messages["form.error.date"]);
$('#editComputer').validate({
	rules : {
		introduced : {
			required : false,
			validDate : true
		},
		discontinued : {
			required : false,
			validDate : true
		},
		computerName : {
			required : true,
			minlength : 2
		}
	},
	messages : {
		computerName : {
			required : messages["form.error.nameNeeded"],
			minlength : messages["form.error.nameLength"]
		},
		introduced : messages["form.error.date"],
		discontinued : messages["form.error.date"]
	}
});

(function($) {
	$.fn.changeLanguage = function(lang) {
		var PageURL = decodeURIComponent(window.location.search.substring(1)),
		URLVariables = PageURL.split('&'),
		search="";
		for (i = 0; i < URLVariables.length; i++) {
	        ParameterName = URLVariables[i].split('=');
	        if (ParameterName[0]!== undefined && ParameterName[1] !== undefined && ParameterName[0] !== "locale") {
	        	search += ParameterName[0] + "=" + ParameterName[1] +"&";
	        }
		}
		search += "locale=" + lang;
		window.location.search = search;
	};
})(jQuery);