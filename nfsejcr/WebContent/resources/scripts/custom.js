var filterNumberOnly = function(inputText) {
	$(inputText).val($(inputText).val().replace(/[^0-9]/g, ''));
}