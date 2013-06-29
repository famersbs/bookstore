var app = app || {};
var ENTER_KEY = 13;

$(function() {
	'use strict';
	
	////////////////////////////////////////////////////////////////
	// BS Model
	app.Bookelement = Backbone.Model.extend({
		defaults: {
			title: '',
			book_id: '',
			link: '',
			highlight: ''
		},
		
		parse:function (response) {
	        //response.id = response._id;
	        this.highlight = jQuery.base64.decode(response.highlight);
	        response.highlight = this.highlight;
	        return response;
	    }
	});
	
});
